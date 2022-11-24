package com.gmodelo.cutoverback.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.gmodelo.cutoverback.Activity.R;

public class FragmentCalc extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.calcpopup, container, false);
    }

    private TextView calcText;
    private Button calcb1, calcb2, calcb3, calcb4, calcb5, calcb6, calcb7, calcb8, calcb9, calcb0, calcbplus, calcbminus, calcbmul, calcbdiv, calcbc, calcac;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        calcText = view.findViewById(R.id.calc_Text);

        calcb1 = view.findViewById(R.id.calc_1);

        calcb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView innerText = v.findViewById(R.id.calc_Text);
                String valueOf = innerText.getText().toString();
                valueOf += "1";
                innerText.setText(valueOf);
            }
        });

        calcb2 = view.findViewById(R.id.calc_2);
        calcb3 = view.findViewById(R.id.calc_3);
        calcb4 = view.findViewById(R.id.calc_4);
        calcb5 = view.findViewById(R.id.calc_5);
        calcb6 = view.findViewById(R.id.calc_6);
        calcb7 = view.findViewById(R.id.calc_7);
        calcb8 = view.findViewById(R.id.calc_8);
        calcb9 = view.findViewById(R.id.calc_9);
        calcb0 = view.findViewById(R.id.calc_0);
        calcbplus = view.findViewById(R.id.calc_plus);
        calcbminus = view.findViewById(R.id.calc_minus);


    }
}
