package com.example.cuahangtienloi.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cuahangtienloi.R;
import com.example.cuahangtienloi.model.GioHang;
import com.example.cuahangtienloi.model.SanPhamMoi;
import com.example.cuahangtienloi.utils.Utils;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DecimalFormat;

public class ChiTietActivity extends AppCompatActivity {
    ImageView imgHinhAnh;
    TextView txtTen, txtGia, txtSoluong, txtMota;
    Button btnThemgiohang;
    Toolbar toolbar;
    SanPhamMoi sanPhamMoi;
    NotificationBadge badge;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet);

        initView();
        ActionBar();
        initData();
        initControl();
    }

    private void initControl() {
        btnThemgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themGioHang();
            }
        });
    }

    private void themGioHang() {
        if (Utils.manggiohang.size()>0){
            boolean flag = false;
            // true sản phẩm trùng với sản phẩm trong giỏ hàng false thì sản phẩm thêm vào giỏ không trùng sp trong giỏ.
            for (int i=0; i<Utils.manggiohang.size();i++){
                if (Utils.manggiohang.get(i).getId()==sanPhamMoi.getId()){
                    Utils.manggiohang.get(i).setSoLuong(Utils.manggiohang.get(i).getSoLuong() +1 );
                    long gia = Long.parseLong(sanPhamMoi.getGia());
                    Utils.manggiohang.get(i).setGia(String.valueOf(gia));
                    flag = true;
                }
            }
            if (flag==false){
                int soluong = 1;
                GioHang gioHang = new GioHang();
                gioHang.setGia(sanPhamMoi.getGia());
                gioHang.setSoLuong(soluong);
                gioHang.setId(sanPhamMoi.getId());
                gioHang.setTenSanPham(sanPhamMoi.getTenSanpham());
                gioHang.setHinhAnh(sanPhamMoi.getHinhAnh());
                Utils.manggiohang.add(gioHang);
            }
        }else{
            int soluong = 1;
            GioHang gioHang = new GioHang();
            gioHang.setGia(sanPhamMoi.getGia());
            gioHang.setSoLuong(soluong);
            gioHang.setId(sanPhamMoi.getId());
            gioHang.setTenSanPham(sanPhamMoi.getTenSanpham());
            gioHang.setHinhAnh(sanPhamMoi.getHinhAnh());
            Utils.manggiohang.add(gioHang);
        }
        int soSP = 0;
        for (int i=0; i<Utils.manggiohang.size(); i++){
            soSP += Utils.manggiohang.get(i).getSoLuong();
        }
        badge.setText(String.valueOf(soSP));
    }

    private void initData() {
        //get dữ liệu từng sản phẩm về
        // ép về kiểm SanPhamMoi thì ở lớp đó đã có Serializable.
        sanPhamMoi= (SanPhamMoi) getIntent().getSerializableExtra("chitiet");
        txtTen.setText(sanPhamMoi.getTenSanpham());
        //lỗi vì kiểu số lượng int k thêm "" sẽ lỗi.
        txtSoluong.setText("Số lượng: " + sanPhamMoi.getSoluong());
        txtMota.setText(sanPhamMoi.getMoTa());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtGia.setText("Giá: "+ decimalFormat.format(Double.parseDouble(sanPhamMoi.getGia()))+"đ");
        Glide.with(getApplicationContext()).load(sanPhamMoi.getHinhAnh()).into(imgHinhAnh);
    }

    private void initView() {
        imgHinhAnh= findViewById(R.id.itemchitiet_image);
        txtTen= findViewById(R.id.itemchitiet_ten);
        txtGia= findViewById(R.id.itemchitiet_gia);
        txtSoluong= findViewById(R.id.itemchitiet_soluong);
        txtMota = findViewById(R.id.itemchitiet_mota);
        btnThemgiohang = findViewById(R.id.itemchitiet_giohang);
        toolbar= findViewById(R.id.toolbar_chitiet);
        badge = findViewById(R.id.menu_sl);
        //gio hang
        if (Utils.manggiohang != null){
            int soSP = 0;
            for (int i=0; i<Utils.manggiohang.size(); i++){
                soSP += Utils.manggiohang.get(i).getSoLuong();
            }
            badge.setText(String.valueOf(soSP));
        }

        linearLayout = findViewById(R.id.Gio_Hang);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent giohang = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(giohang);
            }
        });
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}