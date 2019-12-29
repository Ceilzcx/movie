package com.example.movie.ui.user;

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

        String[] occuptions = {"大学/研究生", "农民", "医生", "律师", "程序员", "家庭主妇", "中小学生", "艺术家", "作者", "科学家", "个体经营者"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), R.layout.dropdown, occuptions);
        adapter.setDropDownViewResource(R.layout.dropdown);
        spinner_occuption.setAdapter(adapter);

        return root;
    }
}