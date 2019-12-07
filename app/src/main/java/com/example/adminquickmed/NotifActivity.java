package com.example.adminquickmed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class NotifActivity extends AppCompatActivity {
    private RecyclerView rvMessage;
    private ArrayList<Ukiran> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_notif);
        rvMessage = findViewById(R.id.rv_message);
        rvMessage.setHasFixedSize(true);
        list.addAll(UkiranData.getListData());
        showRecycleList();
    }
    private void showRecycleList() {
        rvMessage.setLayoutManager(new LinearLayoutManager(this));
        ListUkiranAdapter listUkiranAdapter = new ListUkiranAdapter(list);
        rvMessage.setAdapter(listUkiranAdapter);

        listUkiranAdapter.setOnItemClickCallback(new ListUkiranAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Ukiran data) {
                showSelectedUkiran(data);
                Intent intent = new Intent(NotifActivity.this, KonsultasiActivity.class);
                startActivity(intent);

            }
        });
    }
    private void showSelectedUkiran(Ukiran ukiran){
        Toast.makeText(this, "Kamu memilih "+ ukiran.getName(), Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(getBaseContext(), DetailActivity.class);
//        intent.putExtra("DATA_PHOTO", ukiran.getPhoto());
//        intent.putExtra("DATA_NAMA", ukiran.getName());
//        intent.putExtra("DATA_DETAIL", ukiran.getDetail());
//        startActivity(intent);


    }
}
