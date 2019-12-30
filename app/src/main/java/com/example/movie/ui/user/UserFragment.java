package com.example.movie.ui.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.movie.R;

import java.util.Objects;

public class UserFragment extends Fragment{
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user, container, false);

        EditText text_name = root.findViewById(R.id.edit_mine_name);
        Spinner spinner_occuption = root.findViewById(R.id.spinner_mine_occupation);
        EditText text_age = root.findViewById(R.id.edit_mine_age);
        RadioGroup group_gender = root.findViewById(R.id.edit_mine_gender);
        RadioButton button1 = root.findViewById(R.id.male);
        RadioButton button2 = root.findViewById(R.id.female);

        String[] occupations = {"中小学生", "艺术家", "大学/研究生","学术/教育研究者","医生","农民","家庭主妇","作者",
                "律师","程序员","退休","销售员","科学家","个体经营者","工程师","商人","失业", "管理员", "工匠", "其他", "文书/行政"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), R.layout.dropdown, occupations);
        adapter.setDropDownViewResource(R.layout.dropdown);
        spinner_occuption.setAdapter(adapter);

        SharedPreferences pref = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        text_name.setText(pref.getString("username", ""));
        if (pref.getInt("age", 0) != 0)
            text_age.setText(pref.getInt("age", 0) + " ");
        if (pref.getInt("occupation", -1) != -1)
            spinner_occuption.setSelection(pref.getInt("occupation", 0));
        String gender = pref.getString("gender", "");
        if (!gender.equals("")){
            if (gender.equals("M"))
                button1.setSelected(true);
            else
                button2.setSelected(true);
        }

        return root;
    }
}