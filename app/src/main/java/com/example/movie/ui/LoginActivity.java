package com.example.movie.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.movie.R;

public class LoginActivity extends AppCompatActivity{
    private Toolbar toolbar;
    private EditText edtUser;
    private EditText edtPasswd;
    private SharedPreferences pref;
//    private User user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_login);
    }

    public void loginClick(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                try {
                    int userId = Integer.parseInt(edtUser.getText().toString());

                } catch (NumberFormatException e) {
                    new AlertDialog.Builder(LoginActivity.this).setTitle("提示")
                            .setMessage("账号输入错误(•_•)")
                            .setPositiveButton("确定", null)
                            .show();
                }
                break;
        }
    }
}