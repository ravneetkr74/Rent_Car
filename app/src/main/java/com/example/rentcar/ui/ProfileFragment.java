package com.example.rentcar.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rentcar.Model.User;
import com.example.rentcar.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.rpc.context.AttributeContext;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ProfileFragment extends Fragment {

    @BindView(R.id.editTextTextPersonName)
    EditText name;
    @BindView(R.id.editTextEmail)
    EditText email;
    @BindView(R.id.editTextAddress)
    EditText address;
    @BindView(R.id.editTextPhone)
    EditText phone;
    @BindView(R.id.editTextPassword)
    EditText password;
    @BindView(R.id.editTextCity)
    EditText city;
    @BindView(R.id.editTextCode)
    EditText code;
    @BindView(R.id.updateProfile)
    Button update;

    User currentUser;
    private DatabaseReference mDatabase;



    public ProfileFragment() {
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
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this,view);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        getUserData();
        return view;
    }

    private void getUserData() {
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            String id  = FirebaseAuth.getInstance().getCurrentUser().getUid();
            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    currentUser = snapshot.getValue(User.class);
                    if (currentUser != null) {
                        name.setText(currentUser.name);
                        email.setText(currentUser.email);
                        if(!currentUser.phone.equals(" ")){
                            phone.setText(currentUser.phone);
                        }
                        if(!currentUser.city.equals(" ")){
                            city.setText(currentUser.city);
                        }
                        if(!currentUser.address.equals(" ")){
                            address.setText(currentUser.address);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            };
            mDatabase.child("Users").child(id).addValueEventListener(valueEventListener);
        }
    }

    private boolean checkValidation() {

        if (name.getText().toString().trim().length() == 0) {
         name.setError("Please enter name");
          name.requestFocus();
            return false;


        } else if (  phone.getText().toString().trim().length() == 0) {
            phone.setError("Please enter phone number");
            phone.requestFocus();
            return false;
        }else if (  address.getText().toString().trim().length() == 0) {
            phone.setError("Please enter address");
            phone.requestFocus();
            return false;
        }else if (  city.getText().toString().trim().length() == 0) {
            city.setError("Please enter city");
            city.requestFocus();
            return false;
        }
        else if (code.getText().toString().trim().length() == 0) {
            code.setError("Please enter code");
            code.requestFocus();
            return false;
        }
        return true;
    }
    @OnClick(R.id.updateProfile)
    public void onClick(View view) {
        if (checkValidation()){
            if(FirebaseAuth.getInstance().getCurrentUser() != null) {
                String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                User newU = new User(id,name.getText().toString(),email.getText().toString(),address.getText().toString(),city.getText().toString(),phone.getText().toString());
                mDatabase.child("Users").child(id).setValue(newU);
                Toast.makeText(getContext(), "Profile Updated Successfully!", Toast.LENGTH_SHORT).show();
            }
        }

    }
}