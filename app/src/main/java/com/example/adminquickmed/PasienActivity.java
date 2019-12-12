package com.example.adminquickmed;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.adminquickmed.adapter.Adapter;
import com.example.adminquickmed.app.AppController;
import com.example.adminquickmed.data.Data;
import com.example.adminquickmed.util.Server;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PasienActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    Toolbar toolbar;
    FloatingActionButton fab;
    ListView list;
    SwipeRefreshLayout swipe;
    List<Data> itemList = new ArrayList<Data>();
    Adapter adapter;
    int success;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    EditText txt_id, txt_nama, txt_alamat, txt_tgl_lahir, txt_goldar, txt_email, txt_username, txt_password;
    String id, nama, alamat, tgl_lahir, goldar, email, username, password;

    private static final String TAG = PasienActivity.class.getSimpleName();

    private static String url_select     = Server.URL + "selectPasien.php";
    private static String url_insert     = Server.URL + "insertPasien.php";
    private static String url_edit       = Server.URL + "editPasien.php";
    private static String url_update     = Server.URL + "updatePasien.php";
    private static String url_delete     = Server.URL + "deletePasien.php";

    public static final String TAG_ID       = "user_id";
    public static final String TAG_NAMA     = "nama";
    public static final String TAG_ALAMAT   = "alamat";
    public static final String TAG_TGL_LAHIR = "tgl_lahir";
    public static final String TAG_GOLDAR     = "goldar";
    public static final String TAG_USERNAME   = "username";
    public static final String TAG_PASSWORD   = "password";
    public static final String TAG_EMAIL = "email";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasien);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // menghubungkan variablel pada layout dan pada java
        fab     = (FloatingActionButton) findViewById(R.id.fab_add);
        swipe   = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        list    = (ListView) findViewById(R.id.list);

        // untuk mengisi data dari JSON ke dalam adapter
        adapter = new Adapter(PasienActivity.this, itemList);
        list.setAdapter(adapter);

        // menamilkan widget refresh
        swipe.setOnRefreshListener(this);

        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           swipe.setRefreshing(true);
                           itemList.clear();
                           adapter.notifyDataSetChanged();
                           callVolley();
                       }
                   }
        );

        // fungsi floating action button memanggil form biodata
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogForm("", "", "","","","","","","SIMPAN");
            }
        });

        // listview ditekan lama akan menampilkan dua pilihan edit atau delete data
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view,
                                           final int position, long id) {
                // TODO Auto-generated method stub
                final String idx = itemList.get(position).getId();

                final CharSequence[] dialogitem = {"Edit", "Delete"};
                dialog = new AlertDialog.Builder(PasienActivity.this);
                dialog.setCancelable(true);
                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        switch (which) {
                            case 0:
                                edit(idx);
                                break;
                            case 1:
                                delete(idx);
                                break;
                        }
                    }
                }).show();
                return false;
            }
        });

    }

    @Override
    public void onRefresh() {
        itemList.clear();
        adapter.notifyDataSetChanged();
        callVolley();
    }

    // untuk mengosongi edittext pada form
    private void kosong(){
        txt_id.setText(null);
        txt_nama.setText(null);
        txt_alamat.setText(null);
        txt_email.setText(null);
        txt_goldar.setText(null);
        txt_tgl_lahir.setText(null);
        txt_username.setText(null);
        txt_password.setText(null);
    }

    // untuk menampilkan dialog from biodata
    private void DialogForm(String idx, String namax, String alamatx, String tgl_lahirx, String goldarx, String usernamex, String passwordx, String emailx, String button) {
        dialog = new AlertDialog.Builder(PasienActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.form_biodata, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Form Biodata");

        txt_id      = (EditText) dialogView.findViewById(R.id.txt_id);
        txt_nama    = (EditText) dialogView.findViewById(R.id.txt_nama);
        txt_alamat  = (EditText) dialogView.findViewById(R.id.txt_alamat);
        txt_email      = (EditText) dialogView.findViewById(R.id.txt_email);
        txt_username    = (EditText) dialogView.findViewById(R.id.txt_username);
        txt_password  = (EditText) dialogView.findViewById(R.id.txt_password);
        txt_goldar  = (EditText) dialogView.findViewById(R.id.txt_goldar);
        txt_tgl_lahir = (EditText) dialogView.findViewById(R.id.txt_tgl_lahir);
        if (!idx.isEmpty()){
            txt_id.setText(idx);
            txt_nama.setText(namax);
            txt_alamat.setText(alamatx);
            txt_email.setText(emailx);
            txt_tgl_lahir.setText(tgl_lahirx);
            txt_goldar.setText(goldarx);
            txt_username.setText(usernamex);
            txt_password.setText(passwordx);
        } else {
            kosong();
        }

        dialog.setPositiveButton(button, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                id      = txt_id.getText().toString();
                nama    = txt_nama.getText().toString();
                alamat  = txt_alamat.getText().toString();
                tgl_lahir = txt_tgl_lahir.getText().toString();
                email    = txt_email.getText().toString();
                goldar  = txt_goldar.getText().toString();
                username      = txt_username.getText().toString();
                password    = txt_password.getText().toString();

                simpan_update();
                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                kosong();
            }
        });

        dialog.show();
    }

    // untuk menampilkan semua data pada listview
    private void callVolley(){
        itemList.clear();
        adapter.notifyDataSetChanged();
        swipe.setRefreshing(true);

        // membuat request JSON
        JsonArrayRequest jArr = new JsonArrayRequest(url_select, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                // Parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        Data item = new Data();

                        item.setId(obj.getString(TAG_ID));
                        item.setNama(obj.getString(TAG_NAMA));
                        item.setAlamat(obj.getString(TAG_ALAMAT));
                        item.setEmail(obj.getString(TAG_EMAIL));
                        item.setGoldar(obj.getString(TAG_GOLDAR));
                        item.setTgl_lahir(obj.getString(TAG_TGL_LAHIR));
                        item.setUsername(obj.getString(TAG_USERNAME));
                        item.setPassword(obj.getString(TAG_PASSWORD));

                        // menambah item ke array
                        itemList.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                // notifikasi adanya perubahan data pada adapter
                adapter.notifyDataSetChanged();

                swipe.setRefreshing(false);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                swipe.setRefreshing(false);
            }
        });

        // menambah request ke request queue
        AppController.getInstance().addToRequestQueue(jArr);
    }

    // fungsi untuk menyimpan atau update
    private void simpan_update() {
        String url;
        // jika id kosong maka simpan, jika id ada nilainya maka update
        if (id.isEmpty()){
            url = url_insert;
        } else {
            url = url_update;
        }

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Cek error node pada json
                    if (success == 1) {
                        Log.d("Add/update", jObj.toString());

                        callVolley();
                        kosong();

                        Toast.makeText(PasienActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                        adapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(PasienActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
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
                Toast.makeText(PasienActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                // jika id kosong maka simpan, jika id ada nilainya maka update
                if (id.isEmpty()){
                    params.put("nama", nama);
                    params.put("alamat", alamat);
                    params.put("email", email);
                    params.put("tgl_lahir", tgl_lahir);
                    params.put("goldar", goldar);
                    params.put("username", username);
                    params.put("password", password);
                } else {
                    params.put("id", id);
                    params.put("nama", nama);
                    params.put("alamat", alamat);
                    params.put("email", email);
                    params.put("tgl_lahir", tgl_lahir);
                    params.put("goldar", goldar);
                    params.put("username", username);
                    params.put("password", password);
                }

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    // fungsi untuk get edit data
    private void edit(final String idx){
        StringRequest strReq = new StringRequest(Request.Method.POST, url_edit, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Cek error node pada json
                    if (success == 1) {
                        Log.d("get edit data", jObj.toString());
                        String idx      = jObj.getString(TAG_ID);
                        String namax    = jObj.getString(TAG_NAMA);
                        String alamatx  = jObj.getString(TAG_ALAMAT);
                        String tgl_lahirx = jObj.getString(TAG_TGL_LAHIR);
                        String emailx = jObj.getString(TAG_EMAIL);
                        String goldarx = jObj.getString(TAG_GOLDAR);
                        String usernamex = jObj.getString(TAG_USERNAME);
                        String passwordx = jObj.getString(TAG_PASSWORD);

                        DialogForm(idx, namax, alamatx, tgl_lahirx, emailx, goldarx, usernamex, passwordx, "UPDATE");

                        adapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(PasienActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
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
                Toast.makeText(PasienActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", idx);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    // fungsi untuk menghapus
    private void delete(final String idx){
        StringRequest strReq = new StringRequest(Request.Method.POST, url_delete, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Cek error node pada json
                    if (success == 1) {
                        Log.d("delete", jObj.toString());

                        callVolley();

                        Toast.makeText(PasienActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        adapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(PasienActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
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
                Toast.makeText(PasienActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", idx);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

}
