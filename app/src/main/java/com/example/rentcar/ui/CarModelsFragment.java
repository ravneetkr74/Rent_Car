package com.example.rentcar.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rentcar.CarDetailsFragment;
import com.example.rentcar.CarModelAdapter;
import com.example.rentcar.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CarModelsFragment extends Fragment {
    @BindView(R.id.recyclerview)
    RecyclerView recycler;
    CarModelAdapter  modelAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_car_models, container, false);
        ButterKnife.bind(this,view);
        // Inflate the layout for this fragment
        modelAdapter = new CarModelAdapter(getContext()) {
            @Override
            public void getBookings() {
                        CarDetailsFragment fragment = new CarDetailsFragment();

       getFragmentManager() .beginTransaction().replace(R.id.frame_container, fragment).addToBackStack(null).commit();
            }
        };
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setAdapter(modelAdapter);
        return view;
    }
}