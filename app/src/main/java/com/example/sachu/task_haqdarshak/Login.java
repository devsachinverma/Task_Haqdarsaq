package com.example.sachu.task_haqdarshak;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity
{
    EditText mob,pass;
    String mob_no,password;
    Button login;
    int attempt=3,counter=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mob =(EditText)findViewById(R.id.mob);
        pass =(EditText)findViewById(R.id.pass);
        Intent intent=getIntent();
        mob_no=intent.getStringExtra("mobno");
        password=intent.getStringExtra("paswrd");
        login=(Button)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(mob.getText().toString().equals(mob_no) && pass.getText().toString().equals(password))
                {

                    Intent intent1=new Intent(Login.this,Home.class);
                    startActivity(intent1);
                }

                else if (counter == 0)
                // Disable button after 3 failed attempts
                {   login.setEnabled(false);

                    Toast alert = Toast.makeText(Login.this, "Login Disabled for 5 mins", Toast.LENGTH_SHORT);
                    alert.show();

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable()
                    {   @Override
                    public void run()
                    {   login.setEnabled(true);
                        counter = 2;
                    }
                    }, 300000);
                }
                else
                // Wrong password
                {   Toast alert = Toast.makeText(Login.this, "Wrong Credentials", Toast.LENGTH_SHORT);
                    alert.show();
                    counter--;
                };

                }


        });



    }
}
