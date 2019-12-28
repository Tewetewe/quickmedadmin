package com.example.adminquickmed;



import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.adminquickmed.app.AppController;
import com.example.adminquickmed.data.AntrianData;
import com.example.adminquickmed.data.RekamMedisData;
import com.example.adminquickmed.util.Server;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class RekamMedisPasienActivity extends AppCompatActivity  {
    private static final String TAG = RekamMedisFragment.class.getSimpleName();
    public static final String TAG_PENDAFTARAN_ID = "pendaftaran_id";
    public static final String TAG_USER_ID         = "user_id";
    public static final String TAG_NAMA_FASKES       = "nama_faskes";
    public static final String TAG_CREATED_AT     = "created_at";
    public static final String TAG_NAMA        = "nama";
    public static final String TAG_KELUHAN  = "keluhan";
    public static final String TAG_STATUS       = "status";
    public static final String TAG_PHOTO       = "photo";
    public static final String TAG_DIAGNOSA       = "diagnosa";
    public static final String TAG_ANJURAN       = "anjuran";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private String user_id;
    private  String pendaftaran_id;



    private static String url_select = Server.URL;
    private static String url_update 	     = Server.URL + "updateAntrian.php";

    RecyclerView rvRekam;
    String DetailApi;
    FloatingActionButton fab;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mManager;
    ProgressDialog pd;
    CardViewRekamMedisAdapter adapter;
    ArrayList<RekamMedisData> rekamDataArrayList = new ArrayList<>();

    int success;
    String tag_json_obj = "json_obj_req";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_rekam_medis);
        pd = new ProgressDialog(this);
        rvRekam = (RecyclerView) this.findViewById(R.id.rv_rekam);
        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mAdapter = new CardViewRekamMedisAdapter(this, rekamDataArrayList);
        rvRekam.setLayoutManager(new LinearLayoutManager(this));
        rvRekam.setAdapter(mAdapter);
        fab = (FloatingActionButton) this.findViewById(R.id.fab_add);
        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        pendaftaran_id = intent.getStringExtra("pendaftaran_id");
        DetailApi = url_select+"selectRekamMedis.php?user_id="+user_id;
        loadjson();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), RekamMedisActivity.class);
                intent.putExtra("pendaftaran_id", pendaftaran_id);
                startActivity(intent);
            }
        });
    }

    private void loadjson(){
        pd.setMessage("Mengambil Data");
        pd.setCancelable(false);
        pd.show();

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, DetailApi, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                pd.cancel();
                Log.d("volley", "response : " + response.toString());
                for (int i=0; i < response.length(); i++){
                    try {
                        JSONObject data = response.getJSONObject(i);
                        RekamMedisData md = new RekamMedisData();
                        md.setNama(data.getString(TAG_NAMA)); // memanggil nama array yang kita buat
                        md.setFaskes(data.getString(TAG_NAMA_FASKES));
                        md.setCreated_at(data.getString(TAG_CREATED_AT));
                        md.setKeluhan(data.getString(TAG_KELUHAN));
                        md.setPhoto(data.getString(TAG_PHOTO));
                        md.setDiagnosa(data.getString(TAG_DIAGNOSA));
                        md.setAnjuran(data.getString(TAG_ANJURAN));
                        rekamDataArrayList.add(md);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                pd.cancel();
                Log.d("volley", "error : " + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(arrayRequest);
    }

    private void update(final String pendaftaran_idx){

        StringRequest strReq = new StringRequest(Request.Method.GET, url_update, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Cek error node pada json
                    if (success == 1) {
                        Log.d("get edit data", jObj.toString());
                        String pendaftaran_idx = jObj.getString(TAG_PENDAFTARAN_ID);
                        String user_idx = jObj.getString(TAG_USER_ID);



//                        DialogForm(idx, namax, alamatx, createdx, user_idx, statusx, keluhanx, nama_faskesx,photox, "UPDATE");

                        adapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(getApplicationContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                params.put("pendaftaran_id", TAG_PENDAFTARAN_ID);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }
}

