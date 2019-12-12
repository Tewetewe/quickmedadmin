package com.example.adminquickmed;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.adminquickmed.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    LinearLayout b1, b2, b3, b4, b5, b6;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        b1 = ((LinearLayout) rootView.findViewById(R.id.btn_kelola_antrian));
        b1.setOnClickListener(this);
        b2 = ((LinearLayout) rootView.findViewById(R.id.btn_kelola_user));
        b2.setOnClickListener(this);
        b3 = ((LinearLayout) rootView.findViewById(R.id.btn_kelola_rekam_medis));
        b3.setOnClickListener(this);
        b4 = ((LinearLayout) rootView.findViewById(R.id.btn_kelola_artikel));
        b4.setOnClickListener(this);
        b5 = ((LinearLayout) rootView.findViewById(R.id.btn_kelola_konsultasi));
        b5.setOnClickListener(this);
        b6 = ((LinearLayout) rootView.findViewById(R.id.btn_kelola_poli));
        b6.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_kelola_antrian:
                Intent intent = new Intent(getActivity(), AntrianActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_kelola_user:
                Intent intent2 = new Intent(getActivity(), PasienActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_kelola_rekam_medis:
                Intent intent3 = new Intent(getActivity(), RekamMedisActivity.class);
                startActivity(intent3);
                break;
            case R.id.btn_kelola_artikel:
                Intent intent4 = new Intent(getActivity(), ArticleActivity.class);
                startActivity(intent4);
                break;
            case R.id.btn_kelola_konsultasi:
                Intent intent5 = new Intent(getActivity(), KonsultasiActivity.class);
                startActivity(intent5);
                break;
//            case R.id.btn_kelola_poli:
//                Intent intent6 = new Intent(getActivity(), SendNotifActivity.class);
//                startActivity(intent6);
//                break;

        }

//        }

    }
}
