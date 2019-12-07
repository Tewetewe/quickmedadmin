package com.example.adminquickmed;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.adminquickmed.R;


public class ProfileFragment extends Fragment implements View.OnClickListener {
    Button b1, b2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        b1 = ((Button) rootView.findViewById(R.id.btn_signout));
        b1.setOnClickListener(this);
        b2 = ((Button) rootView.findViewById(R.id.btn_editprofile));
        b2.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_editprofile:
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_signout:
                Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent1);

                break;

        }
    }
}
