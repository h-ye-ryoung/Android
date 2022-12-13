package com.example.machumbub;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class LevlelSolveFragment extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_levlel_solve,container,false);
        //레벨 버튼 하나 만들어주고 액티비티니까 인텐트로 넘겨줌
        Button level1btn = (Button)view.findViewById(R.id.level1);

        level1btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), level1Activity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}