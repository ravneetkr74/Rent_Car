package com.example.rentcar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentcar.ui.SharedPrefUtil;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.Viewholder> {
    public Context context;
    SharedPrefUtil sharedPrefUtil;

    @NonNull
    @Override
    public BookingAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.carmodel_item, parent, false);
        return new BookingAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingAdapter.Viewholder holder, int position) {
        sharedPrefUtil=SharedPrefUtil.getInstance();

        if(sharedPrefUtil.getString(SharedPrefUtil.ADMIN).equals("true")){
            holder.book.setText("Approve");
            holder.reject.setVisibility(View.VISIBLE);
        }else {
            holder.reject.setVisibility(View.GONE);

        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        Button book,reject;
        TextView name,description,price;
        ImageView imgCar;
        public Viewholder(@NonNull View itemView) {

            super(itemView);
            imgCar = itemView.findViewById(R.id.imgCar);
            book=(Button) itemView.findViewById(R.id.book);
            name=(TextView) itemView.findViewById(R.id.txtCarTitle);
            description=(TextView) itemView.findViewById(R.id.carDesc);
            price=(TextView) itemView.findViewById(R.id.price);
            reject=(Button) itemView.findViewById(R.id.reject);

        }
    }
}
