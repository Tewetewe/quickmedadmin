package com.example.adminquickmed;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.adminquickmed.app.AppController;
import com.example.adminquickmed.util.Server;
import com.example.adminquickmed.data.DataProfile;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    LinearLayout btn_back;
    Adapter adapter;
    int success;
    View dialogView;
    ProgressDialog pDialog;
    EditText txt_id, txt_nama, txt_alamat, txt_email, txt_goldar, txt_username, txt_password;
    String user_id;
    Button btn_save;
    Intent intent;
    List<DataProfile> listProfile = new ArrayList<DataProfile>();
    ConnectivityManager conMgr;
    SharedPreferences sharedPreferences;

    private static final String TAG = EditProfileActivity.class.getSimpleName();

    private static String url_edit       = Server.URL + "edit_profile_user.php";
    private static String url_update     = Server.URL + "update_profile_user.php";

    public static final String TAG_ID       = "user_id";
    public static final String TAG_NAMA     = "nama";
    public static final String TAG_ALAMAT   = "alamat";
    public static final String TAG_EMAIL   = "email";
    public static final String TAG_GOLDAR   = "goldar";
    public static final String TAG_USERNAME   = "username";
    public static final String TAG_PASSWORD   = "password";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        sharedPreferences = EditProfileActivity.this.getSharedPreferences(LoginFragment.my_shared_preferences, Context.MODE_PRIVATE);
        user_id = getIntent().getStringExtra(TAG_ID);
        btn_back = findViewById(R.id.btn_back);

        txt_nama = findViewById(R.id.txt_nama);
        txt_alamat = findViewById(R.id.txt_alamat);
        txt_email = findViewById(R.id.txt_email);
        txt_goldar = findViewById(R.id.txt_goldar);
        txt_username = findViewById(R.id.txt_username);
        txt_password = findViewById(R.id.txt_password);
        btn_save = findViewById(R.id.btn_save);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(EditProfileActivity.this, MainActivity.class);
//                myIntent.putExtra("key", value); //Optional parameters
                startActivity(myIntent);
//                                            if (getSupportFragmentManager().findFragmentById(android.R.id.content) == null) {
//                                                getSupportFragmentManager().beginTransaction()
//                                                    .add(android.R.id.content, new HomeFragment())
//                                                    .commit();
//                                            }
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nama = txt_nama.getText().toString();
                String alamat = txt_alamat.getText().toString();
                String email = txt_email.getText().toString();
                String goldar = txt_goldar.getText().toString();
                String username = txt_username.getText().toString();
                String password = txt_password.getText().toString();

                checkUpdate(user_id, nama, alamat, email, goldar, username, password);
            }
        });

        pDialog = new ProgressDialog(EditProfileActivity.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, url_edit, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Response" + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    if (success == 1) {
                        Log.d("get edit data", jObj.toString());
                        String user_id = (jObj.getString(TAG_ID));
                        String nama = (jObj.getString(TAG_NAMA));
                        String alamat = (jObj.getString(TAG_ALAMAT));
                        String email = (jObj.getString(TAG_EMAIL));
                        String goldar = (jObj.getString(TAG_GOLDAR));
                        String username = (jObj.getString(TAG_USERNAME));
                        String password = (jObj.getString(TAG_PASSWORD));

                        if (!user_id.isEmpty()) {
                            txt_nama.setText(nama);
                            txt_alamat.setText(alamat);
                            txt_email.setText(email);
                            txt_goldar.setText(goldar);
                            txt_username.setText(username);
                            txt_password.setText(password);

                        } else {
                            Toast.makeText(EditProfileActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(EditProfileActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                hideDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(EditProfileActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();

                hideDialog();
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
    }

    private void checkUpdate(final String user_id, final String nama, final String alamat, final String email, final String goldar, final String username, final String password){
        pDialog = new ProgressDialog(EditProfileActivity.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, url_update, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Register Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {

                        Log.e("Successfully Register!", jObj.toString());

                        Toast.makeText(EditProfileActivity.this.getApplicationContext(),
                            jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        intent = new Intent(EditProfileActivity.this, MainActivity.class);
                        intent.putExtra(TAG_ID, user_id);
                        intent.putExtra(TAG_USERNAME, username);
                        finish();
                        startActivity(intent);
                    } else {
                        Toast.makeText(EditProfileActivity.this.getApplicationContext(),
                            jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(EditProfileActivity.this.getApplicationContext(),
                    error.getMessage(), Toast.LENGTH_LONG).show();

                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", user_id);
                params.put("nama", nama);
                params.put("alamat", password);
                params.put("email", email);
                params.put("goldar", goldar);
                params.put("username", username);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}

