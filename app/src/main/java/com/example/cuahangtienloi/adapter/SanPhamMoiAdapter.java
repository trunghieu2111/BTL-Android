package com.example.cuahangtienloi.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cuahangtienloi.Interface.ItemClick;
import com.example.cuahangtienloi.R;
import com.example.cuahangtienloi.activity.ChiTietActivity;
import com.example.cuahangtienloi.model.SanPhamMoi;

import java.text.DecimalFormat;
import java.util.List;

public class SanPhamMoiAdapter extends RecyclerView.Adapter<SanPhamMoiAdapter.MyViewHolder> {
    Context context;
    List<SanPhamMoi> array;

    public SanPhamMoiAdapter(Context context, List<SanPhamMoi> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sp_new, parent, false);

        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SanPhamMoi sanPhamMoi = array.get(position);
        holder.txtTen.setText(sanPhamMoi.getTenSanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGia.setText("Giá: "+ decimalFormat.format(Double.parseDouble(sanPhamMoi.getGia()))+"đ");
        Glide.with(context).load(sanPhamMoi.getHinhAnh()).into(holder.imgHinhanh);

        holder.setItemClick(new ItemClick() {
            @Override
            public void onClick(View view, int pos, boolean isLongClick) {
                if (!isLongClick){
                    Intent intent = new Intent(context, ChiTietActivity.class);
                    //đẩy sản phẩm sang màn hình chi tiết SP
                    intent.putExtra("chitiet",sanPhamMoi);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder  extends  RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtTen, txtGia;
        ImageView imgHinhanh;
        private ItemClick itemClick;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTen=itemView.findViewById(R.id.itemsp_ten);
            txtGia=itemView.findViewById(R.id.itemsp_gia);
            imgHinhanh = itemView.findViewById(R.id.itemsp_image);

            itemView.setOnClickListener(this);
        }

        public void setItemClick(ItemClick itemClick) {
            this.itemClick = itemClick;
        }

        @Override
        public void onClick(View view) {
            itemClick.onClick(view, getAdapterPosition(), false);
        }
    }
}
