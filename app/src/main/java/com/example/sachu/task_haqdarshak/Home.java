package com.example.sachu.task_haqdarshak;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    Button logout;
    LoginButton loginButton;
    CallbackManager callbackManager;
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_home);
        logout=(Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Home.this,MainActivity.class);
                startActivity(intent);
            }
        });
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

    @Override
    public void onBackPressed() {
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if(backStackEntryCount > 0)
        {
            fragmentManager.popBackStack();
        }
        else
        {
            doExitApp();
        }
    }
    private long exitTime = 0;
    public void doExitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "Press back again to exit the app", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        }
        else {
            finish();
        }
    }

}
