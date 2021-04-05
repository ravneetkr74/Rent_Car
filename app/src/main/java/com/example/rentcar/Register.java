package com.example.rentcar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    ImageButton close_register_screen;
    EditText firstName, lastName, email, password;
    Button goto_login_screen;
    ImageButton create_account_btn;
    boolean valid = true;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
       // getSupportActionBar().hide(); //will hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_register);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        firstName = findViewById(R.id.register_firstname);
        lastName = findViewById(R.id.register_lastname);
        email = findViewById(R.id.register_email);
        password = findViewById(R.id.register_password);
        close_register_screen = findViewById(R.id.close_register_btn);
        goto_login_screen = findViewById(R.id.login_btn);
        create_account_btn = findViewById(R.id.create_acc_btn);

        create_account_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkfields(firstName);
                checkfields(lastName);
                checkfields(email);
                checkfields(password);

                if (valid){
                    //code to start user registration process
                    fAuth.createUserWithEmailAndPassword(email.getText().toString(),
                            password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user = fAuth.getCurrentUser();
                            Toast.makeText(Register.this, "Account Created Successfully",
                                    Toast.LENGTH_LONG).show();
                            DocumentReference df = fStore.collection("Users").document(user.getUid());
                            Map<String,Object> userInfo = new HashMap<>();
                            userInfo.put("firstName",firstName.getText().toString());
                            userInfo.put("lastName",lastName.getText().toString());

                            //code to specify if user is admin or customer
                            userInfo.put("isAdmin","1");
                            df.set(userInfo);

                            startActivity(new Intent(getApplicationContext(),login.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Register.this, "Failed to create account",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        close_register_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent close_register_scr = new Intent(getApplicationContext(),
                        splash_screen_third.class);
                startActivity(close_register_scr);
            }
        });

        goto_login_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login_screen_jump = new Intent(getApplicationContext(),login.class);
                startActivity(login_screen_jump);
            }
        });
    }

    public boolean checkfields(EditText textfields){
        if (textfields.getText().toString().isEmpty()){
            textfields.setError("Error");
            valid = false;
        }
        else{
            valid = true;
        }

        return valid;
    }
}