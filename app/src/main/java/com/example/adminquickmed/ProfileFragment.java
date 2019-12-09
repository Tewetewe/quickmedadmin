package com.example.adminquickmed;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.adminquickmed.R;


public class ProfileFragment extends Fragment implements View.OnClickListener {
    Button b1, b2;
    TextView txt_id, txt_username, txt_userHead;
    String user_id, username;
    SharedPreferences sharedpreferences;

    public static final String TAG_ID = "user_id";
    public static final String TAG_USERNAME = "username";
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        txt_id = (TextView) rootView.findViewById(R.id.tv_userid);
        txt_username = (TextView) rootView.findViewById(R.id.tv_username);
        txt_userHead = (TextView) rootView.findViewById(R.id.textView4);
        sharedpreferences = getActivity().getSharedPreferences(LoginFragment.my_shared_preferences, Context.MODE_PRIVATE);
        user_id = getActivity().getIntent().getStringExtra(TAG_ID);
        username = getActivity().getIntent().getStringExtra(TAG_USERNAME);
        txt_id.setText(user_id);
        txt_username.setText(username);
        txt_userHead.setText(username);

        // Inflate the layout for this fragment
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
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(LoginFragment.session_status, false);
                editor.putString(TAG_ID, null);
                editor.putString(TAG_USERNAME, null);
                editor.commit();
                Intent intent2 = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent2);
                break;

        }
    }
}
