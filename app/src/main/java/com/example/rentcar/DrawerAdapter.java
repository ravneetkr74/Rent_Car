package com.example.rentcar;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public abstract class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.MyViewHolder> {


    Context mContext;
    List<DrawerModel> mList;

    public DrawerAdapter(Context mContext, List<DrawerModel> mList) {


        this.mContext = mContext;
        this.mList = mList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.drawer_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        DrawerModel mObj = mList.get(position);

        holder.imageView.setImageResource(mObj.getIcon());
        holder.txtTitle.setText(mObj.getName());




        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   holder.txtTitle.setTextColor(ContextCompat.getColor(mContext, R.color.txt_color));

                selectCurrItem(position);
                onClickView(position);
            }
        });



    }

    private void selectCurrItem(int position) {
        int size = mList.size();
        for (int i = 0; i < size; i++) {
            if (i == position)
                mList.get(i).setSelected(true);
            else
                mList.get(i).setSelected(false);

            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        TextView txtTitle;

        public MyViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.drawer_img);
            txtTitle = view.findViewById(R.id.drawer_txt);
        }
    }

    protected abstract void onClickView(int pos);
}
















