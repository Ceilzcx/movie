package com.example.movie.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.movie.R;
import com.example.movie.bean.User;
import com.example.movie.controller.UserController;
import com.example.movie.ui.moviedetail.MovieDetail;

public class LoginActivity extends AppCompatActivity{
    private EditText edtUser;
    private SharedPreferences pref;
    private MyHandler handler;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_login);

        edtUser = findViewById(R.id.edt_user);

        pref = getSharedPreferences("user", Context.MODE_PRIVATE);
        handler = new MyHandler();
    }

    public void loginClick(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                try {
                    int userId = Integer.parseInt(edtUser.getText().toString());
                    Thread thread = new Thread(() -> {
                        UserController controller = new UserController();
                        user = controller.getUser(userId);
                        Message message = new Message();
                        if (user != null){
                            message.what = 1;
                            handler.sendMessage(message);
                        }else {
                            message.what = 2;
                            handler.sendMessage(message);
                        }
                    });
                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (NumberFormatException e) {
                    new AlertDialog.Builder(LoginActivity.this).setTitle("提示")
                            .setMessage("账号输入错误(•_•)")
                            .setPositiveButton("确定", null)
                            .show();
                }
                break;
        }
    }

    class MyHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1){
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("username", user.getUsername());
                editor.putString("gender", user.getGender());
                editor.putInt("age", user.getAge());
                editor.putInt("occupation", user.getOccupation());
                editor.putInt("userId", user.getUserId());
                editor.apply();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }else if (msg.what == 2){
                new AlertDialog.Builder(LoginActivity.this).setTitle("提示")
                        .setMessage("该用户不存在")
                        .setPositiveButton("确定", null).show();
            }
        }
    }

}