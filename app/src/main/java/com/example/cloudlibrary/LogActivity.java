package com.example.cloudlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Message;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cloudlibrary.util.Communication;

public class LogActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText userName;
    private EditText passWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        userName = findViewById(R.id.activity_log_username);
        passWord = findViewById(R.id.activity_log_password);
        passWord.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passWord.setTransformationMethod(PasswordTransformationMethod.getInstance());
        Button logButton = findViewById(R.id.activity_log_button);
        logButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String user = userName.getText().toString();
        String pass = passWord.getText().toString();
        if(user.equals("")) Toast.makeText(this,"用户名不能为空",Toast.LENGTH_LONG).show();
        else if(pass.equals("")) Toast.makeText(this,"密码不能为空",Toast.LENGTH_LONG).show();
        else if(!(new Communication().confirmAdmin(user,pass))) Toast.makeText(this,"用户名或密码错误",Toast.LENGTH_LONG).show();
        else{
            CLApplication cl = (CLApplication) getApplication();
            cl.setNowInfo(2);
            cl.setStudentInfo(null);
            cl.setUserName("管理员大人");
            Message msg = new Message();
            msg.what = 10;
            cl.getHandler().sendMessage(msg);
            finish();
        }
    }
}