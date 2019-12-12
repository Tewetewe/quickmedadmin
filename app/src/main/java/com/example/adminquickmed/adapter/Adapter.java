package com.example.adminquickmed.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.adminquickmed.R;
import com.example.adminquickmed.app.AppController;
import com.example.adminquickmed.data.Data;

import java.util.List;

/**
 * Created by Kuncoro on 26/03/2016.
 */
public class Adapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Data> items;

    public Adapter(Activity activity, List<Data> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int location) {
        return items.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        TextView id = (TextView) convertView.findViewById(R.id.id);
        TextView nama = (TextView) convertView.findViewById(R.id.nama);
        TextView alamat = (TextView) convertView.findViewById(R.id.alamat);
        TextView tgl_lahir = (TextView) convertView.findViewById(R.id.tgl_lahir);
        TextView email = (TextView) convertView.findViewById(R.id.email);
        TextView username = (TextView) convertView.findViewById(R.id.username);
        TextView password = (TextView) convertView.findViewById(R.id.pasword);
        TextView goldar = (TextView) convertView.findViewById(R.id.goldar);

        Data data = items.get(position);

        id.setText(data.getId());
        nama.setText(data.getNama());
        alamat.setText(data.getAlamat());
        tgl_lahir.setText(data.getTgl_lahir());
        email.setText(data.getEmail());
        username.setText(data.getUsername());
        password.setText(data.getPassword());
        goldar.setText(data.getGoldar());

        return convertView;
    }

}
