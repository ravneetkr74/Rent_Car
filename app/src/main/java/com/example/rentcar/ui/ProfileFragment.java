package com.example.rentcar.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.rentcar.R;

import butterknife.BindView;
import butterknife.ButterKnife;


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
        return view;
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
}