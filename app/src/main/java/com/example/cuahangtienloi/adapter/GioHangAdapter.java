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
import com.example.cuahangtienloi.model.GioHang;
import com.example.cuahangtienloi.model.SanPhamMoi;

import java.text.DecimalFormat;
import java.util.List;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.MyViewHolder>{
    Context context;
    List<GioHang> array;

    public GioHangAdapter(Context context, List<GioHang> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang, parent, false);

        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GioHang gioHang = array.get(position);
        holder.Tensp.setText(gioHang.getTenSanPham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.Gia.setText("Giá: "+ decimalFormat.format(Double.parseDouble(gioHang.getGia()))+"đ");
        Glide.with(context).load(gioHang.getHinhAnh()).into(holder.Hinhanh);
        holder.Soluong.setText("Số lượng: " + (gioHang.getSoLuong()));
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder{
        TextView Tensp, Soluong, Gia;
        ImageView Hinhanh;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Tensp = itemView.findViewById(R.id.itemgiohang_ten);
            Soluong = itemView.findViewById(R.id.itemgiohang_soluong);
            Gia = itemView.findViewById(R.id.itemgiohang_gia);
            Hinhanh = itemView.findViewById(R.id.itemgiohang_image);
        }
    }
}
