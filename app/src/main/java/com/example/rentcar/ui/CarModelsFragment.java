package com.example.rentcar.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rentcar.AddEditCar;
import com.example.rentcar.CarDetailsFragment;
import com.example.rentcar.CarModelAdapter;
import com.example.rentcar.Model.Car;
import com.example.rentcar.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CarModelsFragment extends Fragment {
    @BindView(R.id.recyclerview)
    RecyclerView recycler;
    @BindView(R.id.car_type)
    TextView car_type;
    @BindView(R.id.imageView4)
    ImageView imageView4;
    @BindView(R.id.user_name)
    TextView user_name;
    List<Car> mlist;
    private DatabaseReference mDatabase;
    CarModelAdapter  modelAdapter;
    SharedPrefUtil sharedPrefUtil;
    String admin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_car_models, container, false);
        ButterKnife.bind(this,view);
        sharedPrefUtil=SharedPrefUtil.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        admin=sharedPrefUtil.getString(SharedPrefUtil.ADMIN);
        mlist=new ArrayList<>();
        if(admin.equals("true")){

            car_type.setText("All Cars");
            imageView4.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_add_circle_outline_24));
            user_name.setText("Add");
            getAllCars();
        }else {
            car_type.setText(sharedPrefUtil.getString(SharedPrefUtil.CAR_TYPE));
            getAllCars();
        }
        // Inflate the layout for this fragment
        modelAdapter = new CarModelAdapter(getContext(),mlist) {
            @Override
            public void setBookingOrDelete(int pos) {

               handleBookingAndDelete(pos);
            }
        };
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setAdapter(modelAdapter);
        return view;
    }

    private void getAllCars() {
        ChildEventListener listener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                Car car=snapshot.getValue(Car.class);
                mlist.add(car);
                refreshRecyclerView();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    List<Car> result = mlist.stream()
                            .filter(item -> item.id.equals(snapshot.getKey()))
                            .collect(Collectors.toList());
                    if(result.size() != 0){
                        mlist.remove(result.get(0));
                        Car car=snapshot.getValue(Car.class);
                        mlist.add(car);
                    }
                }
                refreshRecyclerView();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    List<Car> result = mlist.stream()
                            .filter(item -> item.id.equals(snapshot.getKey()))
                            .collect(Collectors.toList());
                    if(result.size() != 0){
                        mlist.remove(result.get(0));
                    }
                }
                refreshRecyclerView();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mDatabase.child("Cars").addChildEventListener(listener);
    }

    private void refreshRecyclerView() {
        modelAdapter = new CarModelAdapter(getContext(),mlist) {
            @Override
            public void setBookingOrDelete(int pos) {
                handleBookingAndDelete(pos);
            }
        };
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setAdapter(modelAdapter);
    }

    private void handleBookingAndDelete(int pos) {
        if (admin.equals("true")) {

            final AlertDialog.Builder mainDialog = new AlertDialog.Builder(getContext());
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View dialogView = inflater.inflate(R.layout.alert_dialog, null);
            mainDialog.setView(dialogView);

            final Button cancel = (Button) dialogView.findViewById(R.id.cancel);
            final Button save = (Button) dialogView.findViewById(R.id.save);
            final TextView act_name=(TextView)dialogView.findViewById(R.id.cat_name);
            final TextView title=(TextView)dialogView.findViewById(R.id.title);
            final AlertDialog alertDialog = mainDialog.create();
            alertDialog.show();
            title.setText("Warning!");
            act_name.setText("Are you sure you want to delete this car?");

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    alertDialog.dismiss();

                }
            });

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mDatabase.child("Cars").child(mlist.get(pos).id).removeValue();
                    alertDialog.dismiss();
                }
            });
        } else {

            CarDetailsFragment fragment = new CarDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putString("carID", mlist.get(pos).id);
            fragment.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).addToBackStack(null).commit();
        }
    }

    @OnClick({R.id.user_name, R.id.imageView4})
    public void onClick(View view) {
        if (admin.equals("true")) {
        switch (view.getId()) {

                case R.id.user_name:
                case R.id.imageView4:
                    sharedPrefUtil.saveString(SharedPrefUtil.FROM,"add");
                    AddEditCar Addedit = new AddEditCar();
                    getFragmentManager().beginTransaction().replace(R.id.frame_container, Addedit).addToBackStack(null).commit();

                    break;
            }
        }
    }
}