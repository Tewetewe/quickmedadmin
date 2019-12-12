package com.example.adminquickmed;


import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.adminquickmed.data.AntrianData;

import java.util.ArrayList;

public class CardViewAntrianAdapter extends RecyclerView.Adapter<CardViewAntrianAdapter.CardViewViewHolder>{

    private ArrayList<AntrianData> listAntrian;

    public CardViewAntrianAdapter(Application application, ArrayList<AntrianData> list){
        this.listAntrian = list;
    }
    @NonNull
    @Override
    public CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_antrian_cardview, viewGroup, false);
        return new CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardViewViewHolder holder, int position) {
        AntrianData antrianData = listAntrian.get(position);

        Glide.with(holder.itemView.getContext())
            .load(antrianData.getPhoto())
            .apply(new RequestOptions().override(350, 550))
            .into(holder.imgPhoto);

        holder.tvNama.setText(antrianData.getNama());
        holder.tvTanggal.setText(antrianData.getCreated_at());
        holder.tvKeluhan.setText(antrianData.getKeluhan());
        holder.tvFaskes.setText(antrianData.getFaskes());

        holder.btnPeriksa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.itemView.getContext(), "Kamu memilih" + listAntrian.get(holder.getAdapterPosition()).getNama(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnTerima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.itemView.getContext(), "Terima"+
                    listAntrian.get(holder.getAdapterPosition()).getNama(), Toast.LENGTH_SHORT).show();
            }
        });
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
            btnTerima = itemView.findViewById(R.id.btn_periksa);
            btnPeriksa = itemView.findViewById(R.id.btn_terima);
        }
    }

}
