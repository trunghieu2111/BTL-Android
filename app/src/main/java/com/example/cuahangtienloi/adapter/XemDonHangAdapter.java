package com.example.cuahangtienloi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuahangtienloi.R;
import com.example.cuahangtienloi.model.GetHoaDon;
import com.example.cuahangtienloi.model.GioHang;

import java.util.List;

public class XemDonHangAdapter extends RecyclerView.Adapter<XemDonHangAdapter.MyViewHolder>{
    //gọi đến 1 recyclerview nữa nên cần khai báo cái này;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    Context context;
    List<GetHoaDon> array;

    public XemDonHangAdapter(Context context, List<GetHoaDon> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donhang, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GetHoaDon hoaDon = array.get(position);
        holder.txtItem_donhang.setText("Đơn hàng: "+ hoaDon.getId());
        holder.txtTongtienItem_donhang.setText("Tổng tiền: "+ hoaDon.getTongTien());
        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.recyclerView_Itemdonhang.getContext(),
                LinearLayoutManager.VERTICAL, false);
        layoutManager.setInitialPrefetchItemCount(hoaDon.getItem().size());
        //adapter chi tiết
        HdChiTietAdapter hdChiTietAdapter = new HdChiTietAdapter(context,hoaDon.getItem());
        holder.recyclerView_Itemdonhang.setLayoutManager(layoutManager);
        holder.recyclerView_Itemdonhang.setAdapter(hdChiTietAdapter);
        //cần khai báo pool ở trên đầu để sử dụng được;
        holder.recyclerView_Itemdonhang.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtItem_donhang, txtTongtienItem_donhang;
        RecyclerView recyclerView_Itemdonhang;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtItem_donhang = itemView.findViewById(R.id.donhang_itemdonhang);
            txtTongtienItem_donhang = itemView.findViewById(R.id.tongtien_itemdonhang);
            recyclerView_Itemdonhang= itemView.findViewById(R.id.recyclerview_itemdonhang);
        }
    }
}
