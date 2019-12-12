package com.sahabatpnj.tommorowapps.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;
import com.orhanobut.hawk.Hawk;
import com.sahabatpnj.tommorowapps.BaseActivity;
import com.sahabatpnj.tommorowapps.Database.UserManager;
import com.sahabatpnj.tommorowapps.Model.User;
import com.sahabatpnj.tommorowapps.R;

public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    private EditText mEmail, mPassword;
    private Button mProceedLogin, mToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        declareView();
        buttonLoginClick();
        buttonRegisterClick();
        setActionBar("Login", false, true);
    }

    private void declareView() {
        mEmail = findViewById(R.id.edtLoginEmail);
        mPassword = findViewById(R.id.edtLoginPassword);
        mProceedLogin = findViewById(R.id.btnLoginProceed);
        mToRegister = findViewById(R.id.btnLoginToRegister);
    }

    private void buttonLoginClick() {
        mProceedLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString().trim().toLowerCase();
                String password = mPassword.getText().toString().trim();

                if (email.isEmpty() && password.isEmpty()) {
                    mEmail.setError("Email cannot be empty");
                    mPassword.setError("Password cannot be empty");
                } else if (email.isEmpty()) {
                    mEmail.setError("Email cannot be empty");
                } else if (password.isEmpty()){
                    mPassword.setError("Password cannot be empty");
                } else {
                    createLoadingDialog("Signing in....");
                    doLogin(email, password);
                }
            }
        });
    }

    private void buttonRegisterClick(){
        mToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }



    private void doLogin(String email, String password) {
        User user = UserManager.getUserData(email);
        Log.d(TAG, "doLogin: " + user.getEmail());
        if (!UserManager.isUserAlreadyRegistered(email)) {
            createToast("Email not Registered");
            mEmail.setError("Email not Registered");
        } else if (!user.getEmail().equals(email) || !user.getPassword().equals(password)) {
            createToast("Email or Password Wrong");
            mEmail.setError("Email or Password Wrong");
            mPassword.setError("Email or Password Wrong");
        } else if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
            createToast("Login Successful");
            UserManager.setUserLoginSession(true, email);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
}
