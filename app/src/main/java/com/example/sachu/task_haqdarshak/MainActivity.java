package com.example.sachu.task_haqdarshak;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity
{
    private static final int CAMERA_REQUEST = 5000;
    private ImageView imageView;
    EditText name,sex,age,email,mob_no,password;
    Button signup,login;
    String mob_num,user_pswrd;
    //--------------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) this.findViewById(R.id.profile_pic);
        name = (EditText) this.findViewById(R.id.name);
        sex = (EditText) this.findViewById(R.id.sex);
        age = (EditText) this.findViewById(R.id.age);
        email = (EditText) this.findViewById(R.id.email);
        mob_no = (EditText) this.findViewById(R.id.mob_no);
        password = (EditText) this.findViewById(R.id.password);
        signup = (Button) this.findViewById(R.id.signup);
        login = (Button) this.findViewById(R.id.login);
        SharedPreferences pref = getSharedPreferences("User_info", MODE_PRIVATE);
        mob_num=pref.getString("user_mobno","");
        user_pswrd=pref.getString("user_password","");
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(MainActivity.this,Login.class);
                intent.putExtra("mobno",mob_num);
                intent.putExtra("paswrd",user_pswrd);
                startActivity(intent);

            }
        });
        signup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Validation();
                Saveinfo();

            }
        });
        imageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
    }
    //--------------------------------------------------------------------------------------------------
    //Validation of Info

    private void Validation()
    {

        if(name.getText().toString().isEmpty())
        {
            name.setError( "name is required!" );
        }
        if(sex.getText().toString().isEmpty())
        {
            sex.setError( "sex is required!" );
        }
        if(age.getText().toString().isEmpty())
        {
            age.setError( "age is required!" );
        }
        if(email.getText().toString().isEmpty())
        {
            email.setError( "email is required!" );
        }
        if(mob_no.getText().toString().isEmpty())
        {
            mob_no.setError( "mob_no is required!" );
        }
        if(password.getText().toString().isEmpty())
        {
            password.setError( "password is required!" );
        }
        if(mob_no.getText().toString().equals(mob_num))
        {
            mob_no.setError( "Its already exist" );
        }
        else
        {
            Intent intent=new Intent(MainActivity.this,Home.class);
            startActivity(intent);
        }

    }
//--------------------------------------------------------------------------------------------------
    //camera
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
    }
//--------------------------------------------------------------------------------------------------
    //Save Data using shared preferences

    public void Saveinfo()
    {
        SharedPreferences pref = getSharedPreferences("User_info", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("user_name", name.getText().toString());
        editor.putString("user_sex", sex.getText().toString());
        editor.putString("user_age", age.getText().toString());
        editor.putString("user_email", email.getText().toString());
        editor.putString("user_mobno", mob_no.getText().toString());
        editor.putString("user_password", password.getText().toString());
        editor.commit();
    }
}
