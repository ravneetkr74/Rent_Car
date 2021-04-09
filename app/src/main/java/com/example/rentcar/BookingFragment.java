package com.example.rentcar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rentcar.ui.SharedPrefUtil;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BookingFragment extends Fragment {
    @BindView(R.id.recyclerview)
    RecyclerView recycler;
    SharedPrefUtil sharedPrefUtil;
    String admin;
    BookingAdapter bookingAdapter;




    public BookingFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_booking, container, false);
        ButterKnife.bind(this,view);
        sharedPrefUtil= SharedPrefUtil.getInstance();
        admin=sharedPrefUtil.getString(SharedPrefUtil.ADMIN);
        if(admin.equals("true")) {
        }else {

        }
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setAdapter(bookingAdapter);
            return view;
    }
}