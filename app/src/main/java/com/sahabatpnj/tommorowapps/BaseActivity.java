package com.sahabatpnj.tommorowapps;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {


    ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
         progressDialog = new ProgressDialog(this);
    }

    public void createToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void setActionBar(String setTitle, boolean showBackButton, boolean hideActionBar){
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(showBackButton);
            getSupportActionBar().setTitle(setTitle);
            if (hideActionBar){
                getSupportActionBar().hide();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
