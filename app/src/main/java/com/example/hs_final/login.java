package com.example.hs_final;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class login extends AppCompatActivity {

    Button btnlogin,btnnext,btncancel;
    EditText registerednumber,etname,devicenumber,devicelocation;
    Context con =this;


    private FirebaseAuth mAuth;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    String mVerificationId;
    private static final String TAG = "PhoneAuthActivity";
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        pd= new ProgressDialog(this);
        pd.setMessage("Verifying your mobile number");
        pd.setCancelable(false);

        final Dialog d = new Dialog(con);



        btnlogin = (Button)findViewById(R.id.btnlogin);
        registerednumber = (EditText)findViewById(R.id.registerednumber);


        registerednumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(hasFocus){
                    registerednumber.setBackgroundResource(R.drawable.onfocusedittext);
                    registerednumber.setHint("");}

                else{
                    registerednumber.setBackgroundResource(R.drawable.underline);
                    registerednumber.setHint("type here");}
            }
        });


        etname = (EditText)findViewById(R.id.registeredname);
        etname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    etname.setBackgroundResource(R.drawable.onfocusedittext);
                    etname.setHint("");}
                else{
                    etname.setBackgroundResource(R.drawable.underline);
                    etname.setHint("type here");}
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (registerednumber.equals(null) || etname.equals(null)) {
                    registerednumber.setError("Enter your registered number please");
                } else {

                    pd.show();
                    startPhoneNumberVerification(registerednumber.getText().toString());

                }
            }
        });
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
            pd.dismiss();




                signup();

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.w(TAG, "onVerificationFailed", e);
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    pd.dismiss();
                    registerednumber.setError("Invalid phone number");
                    registerednumber.setText("");
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    pd.dismiss();
                    Toast.makeText(getApplicationContext(), "Quota exceeded.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                Log.d(TAG, "onCodeSent:" + verificationId);
                mVerificationId = verificationId;
                mResendToken = token;
                pd.setMessage("code sucessfully send");
            }
        };

    }

    void signup() {
        pd.setMessage("Checking information......");
        pd.show();
        final DatabaseReference reff = Myapp.ref.child("users").child(registerednumber.getText().toString());
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                reff.removeEventListener(this);
                if (dataSnapshot.getValue() == null) {
                    pd.setMessage("Registring user...");
                    Map<String, Object> data = new HashMap<>();
                    data.put("number", registerednumber.getText().toString());
                    data.put("name", etname.getText().toString());
                    //Map<String,String> data2 = new HashMap<>();
                    //data2.put("ID",devicenumber.getText().toString());
                   // data.put("Device",data2);

                   // Myapp.ref.child("users").child(registerednumber.getText().toString()).child("DEVICE").child(devicenumber.getText().toString()).setValue(devicelocation.getText().toString());

                    //data.put("imgurl", "https://firebasestorage.googleapis.com/v0/b/mtcfirebaseproject.appspot.com/o/profile-pictures.png?alt=media&token=d71170e9-f7b8-4294-b4ca-beb56567a034");

                    Myapp.userdata=data;
                    Myapp.ref.child("users").child(registerednumber.getText().toString()).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            pd.dismiss();

                            SharedPreferences.Editor edit = Myapp.pref.edit();
                            edit.putString("mynumber", registerednumber.getText().toString());
                            edit.putString("myname", etname.getText().toString());
                            edit.commit();
                            Myapp.mynumber = registerednumber.getText().toString();
                            Myapp.myname = etname.getText().toString();
                            Intent i = new Intent(login.this, additionalprofile.class);
                            startActivity(i);
                            finish();
                        }
                    });


                } else {
                    pd.dismiss();
                    Myapp.userdata = (Map<String, Object>) dataSnapshot.getValue();

                    SharedPreferences.Editor edit = Myapp.pref.edit();
                    edit.putString("mynumber", registerednumber.getText().toString());
                    edit.putString("myname", etname.getText().toString());
                    edit.commit();
                    Myapp.mynumber = registerednumber.getText().toString();
                    Myapp.myname = etname.getText().toString();
                    Intent i = new Intent(login.this, additionalprofile.class);
                    startActivity(i);
                    finish();


                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void startPhoneNumberVerification(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + phoneNumber,        // Phone number to verify
                120,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }
}
