package com.example.rentcar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentcar.Model.Car;
import com.example.rentcar.ui.CarModelsFragment;
import com.example.rentcar.ui.SharedPrefUtil;

import java.util.List;

public abstract class CarModelAdapter extends RecyclerView.Adapter<CarModelAdapter.Viewholder> {
    public Context context;
    SharedPrefUtil sharedPrefUtil;
    List<Car> mlist;
    public CarModelAdapter(Context context,List<Car> list) {
        this.context=context;
        this.mlist=list;
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
        sharedPrefUtil=SharedPrefUtil.getInstance();
        if(sharedPrefUtil.getString(SharedPrefUtil.ADMIN).equals("true")){
            holder.book.setText("Delete");
        }else {

        }
        holder.name.setText(""+mlist.get(position).name);
        holder.description.setText(""+mlist.get(position).description);
        holder.price.setText(""+mlist.get(position).price);

        holder.book.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        getBookings(position);


    }
});
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Button book;
        TextView name,description,price;
        public Viewholder(@NonNull View itemView) {

            super(itemView);
            book=(Button) itemView.findViewById(R.id.book);
            name=(TextView) itemView.findViewById(R.id.textView12);
            description=(TextView) itemView.findViewById(R.id.description);
            price=(TextView) itemView.findViewById(R.id.price);

               itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            if(sharedPrefUtil.getString(SharedPrefUtil.ADMIN).equals("true")){
                sharedPrefUtil.saveString(SharedPrefUtil.FROM,"edit");
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                AddEditCar addEditCar = new AddEditCar();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, addEditCar).addToBackStack(null).commit();

            }

        }
    }
    public abstract void getBookings(int pos);
}
