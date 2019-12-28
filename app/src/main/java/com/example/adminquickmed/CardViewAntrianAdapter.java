package com.example.adminquickmed;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.adminquickmed.data.AntrianData;

import java.util.ArrayList;

import static com.example.adminquickmed.OngoingFragment.TAG_PENDAFTARAN_ID;
import static com.example.adminquickmed.OngoingFragment.TAG_USER_ID;

public class CardViewAntrianAdapter extends RecyclerView.Adapter<CardViewAntrianAdapter.CardViewViewHolder>{

    private ArrayList<AntrianData> listAntrian;
    private Context mContext;


    public CardViewAntrianAdapter(Context context, ArrayList<AntrianData> list){
        this.listAntrian = list;
        this.mContext = context;
    }


    @NonNull
    @Override
    public CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_antrian_cardview, viewGroup, false);
        return new CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardViewViewHolder holder, final int position) {
        AntrianData antrianData = listAntrian.get(position);

        Glide.with(holder.itemView.getContext())
            .load(antrianData.getPhoto())
            .apply(new RequestOptions().override(350, 550))
            .into(holder.imgPhoto);

        holder.tvNama.setText(antrianData.getNama());
        holder.tvTanggal.setText(antrianData.getCreated_at());
        holder.tvKeluhan.setText(antrianData.getKeluhan());
        holder.tvFaskes.setText(antrianData.getFaskes());
//        holder.btnPeriksa.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(holder.itemView.getContext(), "Kamu Terima" + listAntrian.get(holder.getAdapterPosition()).getNama(), Toast.LENGTH_SHORT).show();
//
//
//            }
//        });

//        holder.btnTerima.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(holder.itemView.getContext(), "Kamu Periksa"+
//                    listAntrian.get(holder.getAdapterPosition()).getNama(), Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(mContext, OngoingFragment.class);
//                intent.putExtra("user_id", listAntrian.get(position).getUser_id());

//               intent.putExtra(StrUingFixed.KEY_NAMA,dataList.get(position).getNama());
//                intent.putExtra(StringFixed.KEY_ALAMAT,dataList.get(position).getAlamat());
//                intent.putExtra(StringFixed.KEY_JAM_BUKA,dataList.get(position).getJam_buka());
//                intent.putExtra(StringFixed.KEY_PHONE,dataList.get(position).getPhone());
//                intent.putExtra(StringFixed.KEY_WEBSITE,dataList.get(position).getWebsite());
//                intent.putExtra(StringFixed.KEY_RATING,dataList.get(position).getRating());
//                intent.putExtra(StringFixed.KEY_DESCRIPTION,dataList.get(position).getDescription());
//                mContext.startActivity(intent);
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return listAntrian.size();
    }

    class CardViewViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvNama, tvTanggal, tvKeluhan, tvFaskes;
        Button btnTerima, btnPeriksa;


        CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
            tvNama = itemView.findViewById(R.id.tv_item_name);
            tvTanggal = itemView.findViewById(R.id.tv_item_tanggal);
            tvKeluhan = itemView.findViewById(R.id.tv_item_keluhan);
            tvFaskes = itemView.findViewById(R.id.tv_item_faskes);
//            btnTerima = itemView.findViewById(R.id.btn_periksa);
//            btnPeriksa = itemView.findViewById(R.id.btn_terima);
        }
    }

}
