package com.example.adminquickmed;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.adminquickmed.app.AppController;
import com.example.adminquickmed.util.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class HomeFragment extends Fragment implements View.OnClickListener {
    LinearLayout b1, b2, b3, b4, b5, b6;
    TextView display_name;

    String username;
    String user_id;
    SharedPreferences sharedPreferences;
    ImageView pic_foto;
    private static final String TAG = HomeFragment.class.getSimpleName();

    private String SELECT_URL = Server.URL + "select_photo_profile.php";

    public static final String TAG_ID = "user_id";
    public static final String TAG_USERNAME = "username";
    private static final String TAG_PHOTO = "photo";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    int success;
    String tag_json_obj = "json_obj_req";


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
        pic_foto = rootView.findViewById(R.id.pic_foto_register);
        TextView display_name = rootView.findViewById(R.id.textView4);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(LoginFragment.my_shared_preferences, Context.MODE_PRIVATE);
        username = sharedPreferences.getString(TAG_USERNAME, null);
        user_id = sharedPreferences.getString(TAG_ID, null);

        display_name.setText(username);
        StringRequest strReq = new StringRequest(Request.Method.POST, SELECT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Response" + response.toString());

                try {
//                    Toast.makeText(getActivity(), user_id, Toast.LENGTH_LONG).show();
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    if (success == 1) {
                        Log.d("get photo profile", jObj.toString());
                        String id = (jObj.getString(TAG_ID));
                        String photo = (jObj.getString(TAG_PHOTO));

                        if (!user_id.isEmpty()) {
                            Picasso.with(getActivity()).load(photo).centerCrop().fit().into(pic_foto);

                        } else {
                            Toast.makeText(getActivity(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(getActivity(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", user_id);

                return params;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
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
                Intent intent3 = new Intent(getActivity(), RekamMedisAllActivity.class);
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
            case R.id.btn_kelola_poli:
                Intent intent6 = new Intent(getActivity(), SendNotifActivity.class);
                startActivity(intent6);
                break;

        }

//        }

    }
}
