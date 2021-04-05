package com.example.rentcar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentcar.ui.CarModelsFragment;

public abstract class CarModelAdapter extends RecyclerView.Adapter<CarModelAdapter.Viewholder> {
    public Context context;
    public CarModelAdapter(Context context) {
        this.context=context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.carmodel_item, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
holder.book.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        getBookings();


    }
});
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        Button book;
        public Viewholder(@NonNull View itemView) {

            super(itemView);
            book=(Button) itemView.findViewById(R.id.book);

        }
    }
    public abstract void getBookings();
}
