package com.example.adminquickmed;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.adminquickmed.R;
import com.example.adminquickmed.app.AppController;
import com.example.adminquickmed.util.Server;
import com.squareup.picasso.Picasso;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static com.example.adminquickmed.LoginFragment.session_status;


public class ProfileFragment extends Fragment implements View.OnClickListener {
    Button b1, b2, b3;
    TextView txt_id, txt_username, txt_userHead;
    String user_id, username;
    SharedPreferences sharedpreferences;
    ImageView pic_foto;
    Bitmap bitmap, decoded;
    private ProfileViewModel profileViewModel;

    int success;
    int PICK_IMAGE_REQUEST = 1;
    int bitmap_size = 60;
    private String UPLOAD_URL = Server.URL + "upload_foto_user.php";
    private String SELECT_URL = Server.URL + "select_photo_profile.php";
    private static final String TAG = ProfileFragment.class.getSimpleName();
    public static final String TAG_ID = "user_id";
    public static final String TAG_USERNAME = "username";
    private static final String TAG_PHOTO = "photo";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String KEY_PHOTO = "photo";

    String tag_json_obj = "json_obj_req";
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        profileViewModel =
            ViewModelProviders.of(this).get(ProfileViewModel.class);
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        txt_id = (TextView) rootView.findViewById(R.id.tv_userid);
        txt_username = (TextView) rootView.findViewById(R.id.tv_username);
        txt_userHead = (TextView) rootView.findViewById(R.id.textView4);
        sharedpreferences = getActivity().getSharedPreferences(LoginFragment.my_shared_preferences, Context.MODE_PRIVATE);
        user_id = getActivity().getIntent().getStringExtra(TAG_ID);
        username = getActivity().getIntent().getStringExtra(TAG_USERNAME);
        pic_foto = rootView.findViewById(R.id.pic_foto);

        txt_id.setText(user_id);
        txt_username.setText(username);
        txt_userHead.setText(username);

        // Inflate the layout for this fragment
        b1 = ((Button) rootView.findViewById(R.id.btn_signout));
        b1.setOnClickListener(this);
        b2 = ((Button) rootView.findViewById(R.id.btn_editprofile));
        b2.setOnClickListener(this);
        b3 = ((Button) rootView.findViewById(R.id.btn_foto));
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
        StringRequest strReq = new StringRequest(Request.Method.POST, SELECT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Response" + response.toString());

                try {
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
            case R.id.btn_editprofile:
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                SharedPreferences.Editor editor1 = sharedpreferences.edit();
                editor1.putBoolean(session_status, true);
                editor1.putString(TAG_ID, user_id);
                editor1.putString(TAG_USERNAME, username);
                editor1.commit();
                intent.putExtra(TAG_ID, user_id);
                intent.putExtra(TAG_USERNAME, username);
                startActivity(intent);
                break;
            case R.id.btn_signout:
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(session_status, false);
                editor.putString(TAG_ID, null);
                editor.putString(TAG_USERNAME, null);
                editor.commit();
                Intent intent2 = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent2);
                break;

        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage(final String id) {
        //menampilkan progress dialog
        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e(TAG, "Response: " + response.toString());

                    try {
                        JSONObject jObj = new JSONObject(response);
                        success = jObj.getInt(TAG_SUCCESS);

                        if (success == 1) {
                            Log.e("v Add", jObj.toString());

                            Toast.makeText(getActivity(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(getActivity(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //menghilangkan progress dialog
                    loading.dismiss();
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //menghilangkan progress dialog
                    loading.dismiss();

                    //menampilkan toast
                    Toast.makeText(getActivity(), error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    Log.e(TAG, error.getMessage().toString());
                }
            }) {
            @Override
            protected Map<String, String> getParams() {
                //membuat parameters
                Map<String, String> params = new HashMap<String, String>();

                //menambah parameter yang di kirim ke web servis
                params.put(KEY_PHOTO, getStringImage(decoded));
                params.put("user_id", id);

                //kembali ke parameters
                Log.e(TAG, "" + params);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
//            sharedPreferences = getActivity().getSharedPreferences(SignInAct.my_shared_preferences, Context.MODE_PRIVATE);
//            user_id = sharedPreferences.getString(TAG_ID, null);
//
//            String id = user_id;
            sharedpreferences = getActivity().getSharedPreferences(LoginFragment.my_shared_preferences, Context.MODE_PRIVATE);
            String id = getActivity().getIntent().getStringExtra(TAG_ID);
            username = getActivity().getIntent().getStringExtra(TAG_USERNAME);

            try {
                //mengambil fambar dari Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                // 512 adalah resolusi tertinggi setelah image di resize, bisa di ganti.
                setToImageView(getResizedBitmap(bitmap, 512));

                uploadImage(id);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void kosong() {
        pic_foto.setImageResource(0);
    }

    private void setToImageView(Bitmap bmp) {
        //compress image
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));

        //menampilkan gambar yang dipilih dari camera/gallery ke ImageView
        pic_foto.setImageBitmap(decoded);
    }

    // fungsi resize image
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }


}
