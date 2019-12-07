package com.example.adminquickmed;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class ListUkiranAdapter extends RecyclerView.Adapter<ListUkiranAdapter.ListViewHolder>  {
    private ArrayList <Ukiran> listUkiran;
    public ListUkiranAdapter(ArrayList<Ukiran> list){
        this.listUkiran = list;
    }
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_notif_konsultasi, viewGroup, false);
        return new ListViewHolder(view);
    }
    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback){
        this.onItemClickCallback = onItemClickCallback;
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {
        Ukiran ukiran = listUkiran.get(position);
        Glide.with(holder.itemView.getContext())
            .load(ukiran.getPhoto())
            .apply(new RequestOptions().override(55,55))
            .into(holder.imgPhoto);
        holder.tvName.setText(ukiran.getName());
        holder.tvDetail.setText(ukiran.getDetail());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItemClicked(listUkiran.get(holder.getAdapterPosition()));
            }
        });

    }
    public  interface OnItemClickCallback{
        void onItemClicked(Ukiran data);
    }

    @Override
    public int getItemCount() {
        return listUkiran.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvName, tvDetail;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_user);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvDetail = itemView.findViewById(R.id.tv_item_msg);
        }
    }
}
