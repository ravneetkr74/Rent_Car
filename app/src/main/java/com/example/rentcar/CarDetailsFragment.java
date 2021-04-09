package com.example.rentcar;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rentcar.Model.Car;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CarDetailsFragment extends Fragment {

    @BindView(R.id.date)
    TextView date_txt;

    @BindView(R.id.constraint)
    ConstraintLayout constraintLayout;

    @BindView(R.id.constraint1)
    ConstraintLayout constraintLayout1;

    @BindView(R.id.mileage)
    TextView mileage;
    @BindView(R.id.fuel)
    TextView fuel;
    @BindView(R.id.seats)
    TextView seats;
    @BindView(R.id.carDesc)
    TextView carDesc;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.txtCarTitle)
    TextView txtCarTitle;
    @BindView(R.id.imgCar)
    ImageView imgCar;
    @BindView(R.id.next)
    Button next;
    @BindView(R.id.booking)
    Button booking;
    Car selectedCar;
    private DatabaseReference mDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private void getCarDetail(String id) {
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                selectedCar =snapshot.getValue(Car.class);
                Log.e("@@",""+selectedCar);
                mileage.setText(selectedCar.mileage);
                fuel.setText(selectedCar.fuel);
                carDesc.setText(selectedCar.description);
                txtCarTitle.setText(selectedCar.name);
                price.setText("$ "+selectedCar.price+" Day");
                Picasso.get().load(selectedCar.image).into(imgCar);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mDatabase.child("Cars").child(id).addValueEventListener(eventListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_car_details, container, false);
        ButterKnife.bind(this,view);
        MaterialDatePicker<Pair<Long, Long>> dateRangePicker =
                MaterialDatePicker.Builder.dateRangePicker()
                        .setTitleText("Select dates")
                        .build();

        date_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateRangePicker.show(getFragmentManager(), "MATERIAL_DATE_PICKER");

            }
        });

        dateRangePicker.addOnPositiveButtonClickListener(
                new MaterialPickerOnPositiveButtonClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onPositiveButtonClick(Object selection) {

                        // if the user clicks on the positive
                        // button that is ok button update the
                        // selected date
                        date_txt.setText(""+ dateRangePicker.getHeaderText());
                        // in the above statement, getHeaderText
                        // will return selected date preview from the
                        // dialog
                    }
                });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                constraintLayout.setVisibility(View.GONE);
                constraintLayout1.setVisibility(View.VISIBLE);
            }
        });

        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                constraintLayout.setVisibility(View.VISIBLE);
                constraintLayout1.setVisibility(View.GONE);
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference();
        if (getArguments() != null) {
            String id = getArguments().getString("carID");
            getCarDetail(id);
        }
        return view;
    }
}