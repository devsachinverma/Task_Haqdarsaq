package com.example.sachu.task_haqdarshak;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class Home extends AppCompatActivity
{
    TextView txt_status;
    LoginButton loginButton;
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_home);
        initializeControls();
        loginwithfb();

    }

    public void initializeControls()
    {
        callbackManager=CallbackManager.Factory.create();
        txt_status=(TextView)findViewById(R.id.txt_status);
        loginButton=(LoginButton)findViewById(R.id.login_button);
    }

    private void loginwithfb()
    {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                txt_status.setText("Login Success\n"+loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {
                txt_status.setText("Login Cancel");
            }

            @Override
            public void onError(FacebookException error) {
                txt_status.setText("Login Error"+error.getMessage());
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
