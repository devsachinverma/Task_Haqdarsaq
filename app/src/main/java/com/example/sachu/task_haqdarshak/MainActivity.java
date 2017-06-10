package com.example.sachu.task_haqdarshak;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity
{
    private static final int CAMERA_REQUEST = 5000;

    Toolbar mActionBarToolbar;
    EditText name,sex,age,email,mob_no,password;
    Button signup,login;
    String mob_num,user_pswrd,n,s,a,e,m,p;
    ImageView imageView;
    Button buttonCamera, buttonGallery ;
    File file;
    Uri uri;
    Intent CamIntent, GalIntent, CropIntent ;
    public  static final int RequestPermissionCode  = 1 ;
    DisplayMetrics displayMetrics ;
    int width, height;
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
        EnableRuntimePermission();
        clearText();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                SharedPreferences pref = getSharedPreferences("User_info", MODE_PRIVATE);
                mob_num=pref.getString("user_mobno","");
                user_pswrd=pref.getString("user_password","");
                Intent intent=new Intent(MainActivity.this,Login.class);
                intent.putExtra("user_mobno",mob_num);
                intent.putExtra("user_password",user_pswrd);
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
                ClickImageFromCamera() ;
            }
        });

    }
    //--------------------------------------------------------------------------------------------------
    //Validation of Info


    private void Validation()
    {
        n=name.getText().toString();
        s=sex.getText().toString();
        a=age.getText().toString();
        e=email.getText().toString();
        m=mob_no.getText().toString();
        p=password.getText().toString();
        SharedPreferences pref = getSharedPreferences("User_info", MODE_PRIVATE);
        mob_num=pref.getString("user_mobno","");
        if (n.isEmpty() || n.equals("")) {
            name.setError("name is required!");
        }
        else
        if(s.isEmpty() || s.equals(""))
        {
            sex.setError("sex is required!");
        }
        else
        if(a.isEmpty() || a.equals(""))
        {
            age.setError("age is required!");
        }
        else
        if(e.isEmpty() || e.equals(""))
        {
            email.setError("email is required!");
        }
        else
        if(m.isEmpty() || m.equals(""))
        {
            mob_no.setError("mob_no is required!");
        }
        else
        if(p.isEmpty() || p.equals(""))
        {
            password.setError("password is required!");
        }
        else
        if(m.equals(mob_num))
        {
            mob_no.setError( "Its already exist" );
        }

        else
        {
            Intent intent = new Intent(MainActivity.this, Home.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

    }
//--------------------------------------------------------------------------------------------------
        //Save Data using shared preferences

        public void Saveinfo()
        {
            SharedPreferences pref = getSharedPreferences("User_info", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("user_name",n);
            editor.putString("user_sex", s);
            editor.putString("user_age", a);
            editor.putString("user_email", e);
            editor.putString("user_mobno", m);
            editor.putString("user_password", p);
            editor.commit();
        }



    private void clearText()
    {
        name.setText("");
        sex.setText("");
        age.setText("");
        email.setText("");
        mob_no.setText("");
        password.setText("");
    }

    //--------------------------------------------------------------------------------------------------
    //camera
public void ClickImageFromCamera() {

    CamIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

    file = new File(Environment.getExternalStorageDirectory(),
            "file" + String.valueOf(System.currentTimeMillis()) + ".jpg");
    uri = Uri.fromFile(file);

    CamIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri);

    CamIntent.putExtra("return-data", true);

    startActivityForResult(CamIntent, 0);

}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK) {

            ImageCropFunction();

        }
        else if (requestCode == 2) {

            if (data != null) {

                uri = data.getData();

                ImageCropFunction();

            }
        }
        else if (requestCode == 1) {

            if (data != null) {

                Bundle bundle = data.getExtras();

                Bitmap bitmap = bundle.getParcelable("data");

                imageView.setImageBitmap(bitmap);

            }
        }
    }

    public void ImageCropFunction() {

        // Image Crop Code
        try {
            CropIntent = new Intent("com.android.camera.action.CROP");

            CropIntent.setDataAndType(uri, "image/*");

            CropIntent.putExtra("crop", "true");
            CropIntent.putExtra("outputX", 180);
            CropIntent.putExtra("outputY", 180);
            CropIntent.putExtra("aspectX", 3);
            CropIntent.putExtra("aspectY", 4);
            CropIntent.putExtra("scaleUpIfNeeded", true);
            CropIntent.putExtra("return-data", true);

            startActivityForResult(CropIntent, 1);

        } catch (ActivityNotFoundException e) {

        }
    }
    //Image Crop Code End Here

    public void EnableRuntimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                Manifest.permission.CAMERA))
        {

            Toast.makeText(MainActivity.this,"CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                    Manifest.permission.CAMERA}, RequestPermissionCode);

        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                    System.out.print("");

                } else {

                    Toast.makeText(MainActivity.this,"", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }

}
