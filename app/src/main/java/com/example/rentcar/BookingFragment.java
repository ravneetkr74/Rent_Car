package com.example.rentcar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rentcar.Model.Booking;
import com.example.rentcar.Model.Car;
import com.example.rentcar.ui.SharedPrefUtil;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BookingFragment extends Fragment {
    @BindView(R.id.recyclerview)
    RecyclerView recycler;
    SharedPrefUtil sharedPrefUtil;
    String admin;
    List<Booking> mlist;
    BookingAdapter bookingAdapter;
    private DatabaseReference mDatabase;


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
        mDatabase = FirebaseDatabase.getInstance().getReference();
        if(admin.equals("true")) {
            getAllBookings();
        }else {

        }
//        bookingAdapter=new BookingAdapter(getContext(),mlist);
//        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
//        recycler.setItemAnimator(new DefaultItemAnimator());
//        recycler.setAdapter(bookingAdapter);
            return view;
    }

    private void getAllBookings() {
        ChildEventListener listener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Booking booking=snapshot.getValue(Booking.class);
                mlist.add(booking);
                refreshRecyclerView();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mDatabase.child("Booking").addChildEventListener(listener);
    }

    private void refreshRecyclerView() {
        bookingAdapter = new BookingAdapter(getContext(), mlist) {

        };
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setAdapter(bookingAdapter);
    }
}