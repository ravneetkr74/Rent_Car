package com.example.rentcar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.rentcar.ui.SharedPrefUtil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class login extends AppCompatActivity {

    EditText email,password;
    ImageButton close_btn_register, login_btn;
    boolean valid = true;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String admin="false";
    SharedPrefUtil sharedPrefUtil;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
      //  getSupportActionBar().hide(); //will hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_login);
        sharedPrefUtil = SharedPrefUtil.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        close_btn_register = findViewById(R.id.close_register_btn2);
        login_btn = findViewById(R.id.login_btn_app);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkfields(email);
                checkfields(password);

                if (valid){
                    if(email.getText().toString().equals("admin@gmail.com")&&password.getText().toString().equals("123456")){
                        admin="true";
                        sharedPrefUtil.saveString(SharedPrefUtil.ADMIN,admin);
                        sharedPrefUtil.saveBoolean(SharedPrefUtil.LOGIN,true);
                        Toast.makeText(login.this, "admin Login Successfully", Toast.LENGTH_LONG).show();
                       Intent i=new Intent(getApplicationContext(),HomeActivity.class);

                       i.putExtra("admin",admin);
                       startActivity(i);
                       finish();

                    }else {
                        fAuth.signInWithEmailAndPassword(email.getText().toString(),
                                password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(login.this, "Login Successfully", Toast.LENGTH_LONG).show();
                                sharedPrefUtil.saveBoolean(SharedPrefUtil.LOGIN,true);

                                checkUserIsAdmin(authResult.getUser().getUid());
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                    }
                }
            }
        });
        close_btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goto_register = new Intent(getApplicationContext(), Register.class);
                startActivity(goto_register);
            }
        });
    }

    private void checkUserIsAdmin(String uid) {
        DocumentReference df = fStore.collection("Users").document(uid);
        //extract the data from the document
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG", "onSuccess" + documentSnapshot.getData());

                //Identify user access level whether the user is admin or customer
                if (documentSnapshot.getString("isAdmin") != null){
                    //If User is Admin
                    sharedPrefUtil.saveBoolean(SharedPrefUtil.LOGIN,true);

                    Intent i=new Intent(getApplicationContext(),HomeActivity.class);

                    startActivity(i);
                    finish();
                }
                if (documentSnapshot.getString("isCustomer") != null){
                    //If User is Customer
                    startActivity(new Intent(getApplicationContext(),Register.class));
                    finish();
                }
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

    @Override
    protected void onStart() {
        super.onStart();
         if ((FirebaseAuth.getInstance().getCurrentUser()) != null || (SharedPrefUtil.getInstance().getString(SharedPrefUtil.ADMIN).equals("true"))){
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            finish();
        }
    }
}