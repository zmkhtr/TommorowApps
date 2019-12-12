package com.sahabatpnj.tommorowapps.UI;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sahabatpnj.tommorowapps.BaseActivity;
import com.sahabatpnj.tommorowapps.Database.UserManager;
import com.sahabatpnj.tommorowapps.Model.User;
import com.sahabatpnj.tommorowapps.R;

public class RegisterActivity extends BaseActivity {
    private static final String TAG = "RegisterActivity";

    private EditText mName, mEmail, mPassword, mReTypePassword;
    private Button mProceedRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setActionBar("Register", true, false);

        declareView();
        buttonRegisterClick();
    }

    private void declareView() {
        mName = findViewById(R.id.edtRegisterName);
        mEmail = findViewById(R.id.edtRegisterEmail);
        mPassword = findViewById(R.id.edtRegisterPassword);
        mReTypePassword = findViewById(R.id.edtRegisterReTypePassword);
        mProceedRegister = findViewById(R.id.btnRegisterProceed);
    }

    private void buttonRegisterClick(){
        mProceedRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mName.getText().toString().trim();
                String email = mEmail.getText().toString().trim().toLowerCase();
                String password = mPassword.getText().toString().trim();
                String reTypePassword = mReTypePassword.getText().toString().trim();

                if (name.isEmpty() && email.isEmpty() && password.isEmpty() && reTypePassword.isEmpty()) {
                    mName.setError("Name cannot be empty");
                    mEmail.setError("Email cannot be empty");
                    mPassword.setError("Password cannot be empty");
                    mReTypePassword.setError("Re-Type Password cannot be empty");
                } else if (name.isEmpty()) {
                    mName.setError("Name cannot be empty");
                } else if (email.isEmpty()){
                    mEmail.setError("Email cannot be empty");
                } else if (password.isEmpty()){
                    mPassword.setError("Password cannot be empty");
                } else if (reTypePassword.isEmpty()){
                    mReTypePassword.setError("Re-Type Password cannot be empty");
                } else {
                    doRegister(name, email, password, reTypePassword);
                    createLoadingDialog("Registering...");
                }
            }
        });
    }

    public void doRegister(String name, String email, String password, String rePassword){
        if(UserManager.isUserAlreadyRegistered(email)){
            createToast("Email already registered");
            mEmail.setError("Email already registered");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            createToast("Please use the correct email format");
            mEmail.setError("Please use the correct email format");
        } else if (!password.equals(rePassword)) {
            createToast("Password not same");
            mReTypePassword.setError("Password not same");
        } else {
            createToast("Register successful");
            if (UserManager.registerNewUser(email, new User(name,email,password)))
            onBackPressed();
        }
    }
}
