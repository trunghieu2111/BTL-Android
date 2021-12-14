package com.example.cuahangtienloi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cuahangtienloi.R;
import com.example.cuahangtienloi.model.HDChiTiet;

import java.text.DecimalFormat;
import java.util.List;

public class HdChiTietAdapter extends RecyclerView.Adapter<HdChiTietAdapter.MyViewHolder>{
    Context context;
    List<HDChiTiet> array;

    public HdChiTietAdapter(Context context, List<HDChiTiet> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chitiet, parent, false);

        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HDChiTiet hdChiTiet = array.get(position);
        holder.ten.setText(hdChiTiet.getTenSP());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.gia.setText("Giá: "+ decimalFormat.format(Double.parseDouble(hdChiTiet.getGia()))+"đ");
        holder.soluong.setText("Số lượng: "+ hdChiTiet.getSoLuong());
        Glide.with(context).load(hdChiTiet.getHinhAnh()).into(holder.imgHinhanh);
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imgHinhanh;
        TextView ten,soluong,gia;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHinhanh = itemView.findViewById(R.id.itemHDchitiet_image);
            ten = itemView.findViewById(R.id.itemHDchitiet_ten);
            soluong= itemView.findViewById(R.id.itemHDchitiet_soluong);
            gia = itemView.findViewById(R.id.itemHDchitiet_gia);
        }
    }
}
