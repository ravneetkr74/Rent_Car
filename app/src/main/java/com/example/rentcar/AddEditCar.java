package com.example.rentcar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rentcar.Model.Car;
import com.example.rentcar.ui.ImagePickerFragment;
import com.example.rentcar.ui.SharedPrefUtil;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


public class AddEditCar extends ImagePickerFragment {

    @BindView(R.id.fuel)
    TextView petrol;
    @BindView(R.id.diesel)
    TextView diesel;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.Car_Description)
    TextView Car_Description;
    @BindView(R.id.price_edt)
    TextView price_edt;
    @BindView(R.id.mileage_edt)
    TextView mileage_edt;
    @BindView(R.id.seat_edt)
    TextView seat_edt;
    @BindView(R.id.add_car)
    Button add_car;
    @BindView(R.id.car_img)
    CircleImageView car_img;
    private DatabaseReference mDatabase;
    String fuel_;
    String profimage="",from="";
    SharedPrefUtil sharedPrefUtil;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_edit_car, container, false);
        ButterKnife.bind(this, view);
        sharedPrefUtil=SharedPrefUtil.getInstance();
        from=sharedPrefUtil.getString(SharedPrefUtil.FROM);
        if(from.equals("edit")){
            add_car.setText("Update");
        }else {

        }

        return view;
    }

    @OnClick({R.id.fuel, R.id.diesel, R.id.add_car,R.id.car_img})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.diesel:

                fuel_="diesel";

                petrol.setTextColor(getResources().getColor(R.color.white));
                diesel.setTextColor(getResources().getColor(R.color.purple_700));
                petrol.setBackground(getResources().getDrawable(R.drawable.txt_custom_right_color_bg));
                diesel.setBackground(getResources().getDrawable(R.drawable.txt_custom_bg));


                break;

            case R.id.fuel:

                fuel_="petrol";
                petrol.setTextColor(getResources().getColor(R.color.purple_700));
                diesel.setTextColor(getResources().getColor(R.color.white));
                diesel.setBackground(getResources().getDrawable(R.drawable.txt_custom_color_bg));
                petrol.setBackground(getResources().getDrawable(R.drawable.txt_right_custom_bg));


                break;

            case R.id.add_car:

                if(CheckValidation()){
                    String key =   mDatabase.child("Cars").push().getKey();
                    Car car = new Car(name.getText().toString(),Car_Description.getText().toString(),price_edt.getText().toString(),mileage_edt.getText().toString(),fuel_,seat_edt.getText().toString(),key);
                    mDatabase.child("Cars").child(key).setValue(car);
                }

                break;
            case R.id.car_img:

               gallery(getActivity());

                break;
        }
    }

    public boolean CheckValidation() {

        if (name.getText().toString().trim().length() == 0) {
            name.setError("Please enter name");
            name.requestFocus();
            return false;

        } else if (Car_Description.getText().toString().trim().length() ==0) {
            Car_Description.setError("Please enter description");
            Car_Description.requestFocus();
            return false;
        } else if (price_edt.getText().toString().trim().length() == 0) {
            price_edt.setError("Please enter price");
            price_edt.requestFocus();
            return false;
        } else if (mileage_edt.getText().toString().trim().length() ==0) {
            mileage_edt.setError("Please enter mileage");
            mileage_edt.requestFocus();
            return false;
        } else if (seat_edt.getText().toString().trim().length() == 0) {
            seat_edt.setError("Please enter number of seats");
            seat_edt.requestFocus();
            return false;
        }

        return true;
    }

    @Override
    public void selectedImage(String imagePath, String type, String thumbnailPath) {

        if(imagePath!=null){
            profimage=imagePath;
            Bitmap myBitmap = BitmapFactory.decodeFile(profimage);
            car_img.setImageBitmap(myBitmap);


        }

    }
}