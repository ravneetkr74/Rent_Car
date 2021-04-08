package com.example.rentcar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rentcar.Model.Car;
import com.example.rentcar.ui.ImagePickerFragment;
import com.example.rentcar.ui.SharedPrefUtil;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class AddEditCar extends ImagePickerFragment {

    private static final int PICK_IMAGE_REQUEST = 22;
    // Uri indicates, where the image will be picked from
    private Uri filePath;
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
    // instance for firebase storage and StorageReference
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
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

                diesel.setTextColor(getResources().getColor(R.color.white));
                petrol.setTextColor(getResources().getColor(R.color.purple_700));
                diesel.setBackground(getResources().getDrawable(R.drawable.txt_custom_right_color_bg));
                petrol.setBackground(getResources().getDrawable(R.drawable.txt_custom_bg));


                break;

            case R.id.fuel:

                fuel_="petrol";
                diesel.setTextColor(getResources().getColor(R.color.purple_700));
                petrol.setTextColor(getResources().getColor(R.color.white));
                petrol.setBackground(getResources().getDrawable(R.drawable.txt_custom_color_bg));
                diesel.setBackground(getResources().getDrawable(R.drawable.txt_right_custom_bg));


                break;

            case R.id.add_car:

                if(CheckValidation()){
                    uploadImage();
                }

                break;
            case R.id.car_img:

               //gallery(getActivity());
                SelectImage();
                break;
        }
    }
    // Select Image method
    private void SelectImage()
    {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    // Override onActivityResult method
    @Override
    public void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data)
    {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getActivity().getContentResolver(),
                                filePath);
                car_img.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
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
    // UploadImage method
    private void uploadImage()
    {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref =  storageReference.child(
                            "images/"
                                    + UUID.randomUUID().toString());

            // adding listeners on upload
            // or failure of image
            UploadTask uploadTask = ref.putFile(filePath);
            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        Uri downloadUri = task.getResult();
                        String key =   mDatabase.child("Cars").push().getKey();
                                    Car car = new Car(name.getText().toString(),Car_Description.getText().toString(),price_edt.getText().toString(),mileage_edt.getText().toString(),fuel_,seat_edt.getText().toString(),key,downloadUri.toString());
                                    mDatabase.child("Cars").child(key).setValue(car);
                        Toast.makeText(getActivity(),
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(),
                                                    "Image Failed!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                    }
                }
            });
//                    .addOnSuccessListener(
//                            new OnSuccessListener<UploadTask.TaskSnapshot>() {
//
//                                @Override
//                                public void onSuccess(
//                                        UploadTask.TaskSnapshot taskSnapshot)
//                                {
//
//                                    // Image uploaded successfully
//                                    // Dismiss dialog
//                                    progressDialog.dismiss();
//                                    Toast
//                                            .makeText(getActivity(),
//                                                    "Image Uploaded!!",
//                                                    Toast.LENGTH_SHORT)
//                                            .show();
//                                    String key =   mDatabase.child("Cars").push().getKey();
//                                    Car car = new Car(name.getText().toString(),Car_Description.getText().toString(),price_edt.getText().toString(),mileage_edt.getText().toString(),fuel_,seat_edt.getText().toString(),key);
//                                    mDatabase.child("Cars").child(key).setValue(car);
//                                }
//                            })
//
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e)
//                        {
//
//                            // Error, Image not uploaded
//                            progressDialog.dismiss();
//                            Toast
//                                    .makeText(getActivity(),
//                                            "Failed " + e.getMessage(),
//                                            Toast.LENGTH_SHORT)
//                                    .show();
//                        }
//                    })
//                    .addOnProgressListener(
//                            new OnProgressListener<UploadTask.TaskSnapshot>() {
//
//                                // Progress Listener for loading
//                                // percentage on the dialog box
//                                @Override
//                                public void onProgress(
//                                        UploadTask.TaskSnapshot taskSnapshot)
//                                {
//                                    double progress
//                                            = (100.0
//                                            * taskSnapshot.getBytesTransferred()
//                                            / taskSnapshot.getTotalByteCount());
//                                    progressDialog.setMessage(
//                                            "Uploaded "
//                                                    + (int)progress + "%");
//                                }
//                            });
        }
    }

    @Override
    public void selectedImage(String imagePath, String type, String thumbnailPath) {

        if(imagePath!=null){
            profimage=imagePath;
            car_img.setImageURI(Uri.parse(profimage));
//            Bitmap myBitmap = BitmapFactory.decodeFile(profimage);
//            car_img.setImageBitmap(myBitmap);


        }

    }
}