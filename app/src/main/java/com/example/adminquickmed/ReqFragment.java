package com.example.adminquickmed;


import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import com.example.adminquickmed.util.Server;

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
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class ReqFragment extends Fragment  {
    private static final String TAG = ReqFragment.class.getSimpleName();
    public static final String TAG_PENDAFTARAN_ID = "pendaftaran_id";
    public static final String TAG_USER_ID         = "user_id";
    public static final String TAG_NAMA_FASKES       = "nama_faskes";
    public static final String TAG_CREATED_AT     = "created_at";
    public static final String TAG_NAMA        = "nama";
    public static final String TAG_KELUHAN  = "keluhan";
    public static final String TAG_STATUS       = "status";
    public static final String TAG_PHOTO       = "photo";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    private static String url_update 	     = Server.URL;
    private static String url_select = Server.URL + "selectAntrianReq.php";

    RecyclerView rvAntrian;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mManager;
    ProgressDialog pd;
    CardViewAntrianAdapter adapter;
    ArrayList<AntrianData> antrianDataArrayList = new ArrayList<>();

    int success;
    String tag_json_obj = "json_obj_req";
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_req, container, false);
        pd = new ProgressDialog(getContext());
        rvAntrian = (RecyclerView) rootView.findViewById(R.id.rv_antrian);
        mManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mAdapter = new CardViewAntrianAdapter(getContext(), antrianDataArrayList);
        rvAntrian.setLayoutManager(new LinearLayoutManager(getContext()));
        rvAntrian.setAdapter(mAdapter);
        rvAntrian.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), rvAntrian, new ClickListener() {
            @Override
            public void onPasienClick(View view, int position) {
                Toast.makeText(getActivity(),"Tekan Lama "+position,Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(getActivity(), RekamMedisPasienActivity.class);
//                intent.putExtra("user_id", antrianDataArrayList.get(position).getUser_id());
//                startActivity(intent);
            }

            @Override
            public void onPasienLongClick(View view, int position) {
                Toast.makeText(getActivity(),"nama"+antrianDataArrayList.get(position).getNama(),Toast.LENGTH_LONG).show();
                String user_id = antrianDataArrayList.get(position).getUser_id();
                update(user_id);
                Fragment fragment = new OngoingFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.pager, fragment, "Queue");
                transaction.addToBackStack(null);

                transaction.commit();
            }
        }));
        loadjson();
        return rootView;
    }

//    private void loadjsonUpdate(String user_id){
//        pd.setMessage("Mengambil Data");
//        pd.setCancelable(false);
//        pd.show();
//
//        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url_update+ "updateOngoing.php?user_id="+user_id, null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                pd.cancel();
//                Log.d("volley", "response : " + response.toString());
//                for (int i=0; i < response.length(); i++){
//                    try {
//                        JSONObject data = response.getJSONObject(i);
//                        AntrianData md = new AntrianData();
//                        md.setUser_id((data.getString(TAG_USER_ID)));
//                        md.setNama(data.getString(TAG_NAMA)); // memanggil nama array yang kita buat
//                        md.setFaskes(data.getString(TAG_NAMA_FASKES));
//                        md.setCreated_at(data.getString(TAG_CREATED_AT));
//                        md.setKeluhan(data.getString(TAG_KELUHAN));
//                        md.setPhoto(data.getString(TAG_PHOTO));
//
//                        antrianDataArrayList.add(md);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//                mAdapter.notifyDataSetChanged();
//            }
//        }, new Response.ErrorListener(){
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                pd.cancel();
//                Log.d("volley", "error : " + error.getMessage());
//            }
//        });
//        AppController.getInstance().addToRequestQueue(arrayRequest);
//    }
    private void loadjson(){
        pd.setMessage("Mengambil Data");
        pd.setCancelable(false);
        pd.show();

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url_select, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                pd.cancel();
                Log.d("volley", "response : " + response.toString());
                for (int i=0; i < response.length(); i++){
                    try {
                        JSONObject data = response.getJSONObject(i);
                        AntrianData md = new AntrianData();
                        md.setUser_id((data.getString(TAG_USER_ID)));
                        md.setNama(data.getString(TAG_NAMA)); // memanggil nama array yang kita buat
                        md.setFaskes(data.getString(TAG_NAMA_FASKES));
                        md.setCreated_at(data.getString(TAG_CREATED_AT));
                        md.setKeluhan(data.getString(TAG_KELUHAN));
                        md.setPhoto(data.getString(TAG_PHOTO));

                        antrianDataArrayList.add(md);
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


    private void update(final String user_id){
        StringRequest strReq = new StringRequest(Request.Method.POST, url_update+ "updateOngoing.php?user_id="+user_id, new Response.Listener<String>() {

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
                        String statusx = jObj.getString(TAG_STATUS);
                        String user_idx = jObj.getString(TAG_USER_ID);



//                        DialogForm(idx, namax, alamatx, createdx, user_idx, statusx, keluhanx, nama_faskesx,photox, "UPDATE");

                        adapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(getContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
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
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_idx", user_id);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
        loadjson();

    }
    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{
        private GestureDetector mGestureDetector;
        private ClickListener mClickListener;


        public RecyclerTouchListener(final Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.mClickListener = clickListener;
            mGestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(),e.getY());
                    if (child!=null && clickListener!=null){
                        clickListener.onPasienLongClick(child,recyclerView.getChildAdapterPosition(child));
                    }
                    super.onLongPress(e);
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child!=null && mClickListener!=null && mGestureDetector.onTouchEvent(e)){
                mClickListener.onPasienClick(child,rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
    public static interface ClickListener{
        public void onPasienClick(View view, int position);
        public void onPasienLongClick(View view, int position);
    }
}
