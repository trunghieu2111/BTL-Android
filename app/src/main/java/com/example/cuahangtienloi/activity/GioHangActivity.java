package com.example.cuahangtienloi.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cuahangtienloi.R;
import com.example.cuahangtienloi.adapter.CategoryAdapter;
import com.example.cuahangtienloi.adapter.GioHangAdapter;
import com.example.cuahangtienloi.retrofit.ApiBanHang;
import com.example.cuahangtienloi.retrofit.RetrofitClient;
import com.example.cuahangtienloi.utils.Utils;
import com.google.gson.Gson;

import java.text.DecimalFormat;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GioHangActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView_Giohang;
    TextView tongtien, Lichsu;
    Button btnDathang;
    GioHangAdapter gioHangAdapter;
    long tinhTong = 0;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        apiBanHang= RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        initView();
        ActionBar();
        initControl();
        tinhTong();
    }

    private void tinhTong() {
        for (int i=0; i<Utils.manggiohang.size(); i++){
            int gia = Integer.parseInt(Utils.manggiohang.get(i).getGia());
            tinhTong += (Utils.manggiohang.get(i).getSoLuong() * gia);
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tongtien.setText(decimalFormat.format(tinhTong));
    }

    private void initControl() {
        gioHangAdapter = new GioHangAdapter(getApplicationContext(), Utils.manggiohang);
        recyclerView_Giohang.setAdapter(gioHangAdapter);

        btnDathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int TaiKhoanId = Utils.user_current.getId();
                String Tongtien = String.valueOf(tinhTong);
                String Ten = Utils.user_current.getFullname();
                String Phone = Utils.user_current.getPhone();
                String Address = Utils.user_current.getAddress();
                compositeDisposable.add(apiBanHang.createOder(TaiKhoanId, Tongtien, Ten, Phone, Address, new Gson().toJson(Utils.manggiohang))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                userModel -> {
                                        Toast.makeText(getApplicationContext(), "Đặt Hàng Thành Công!", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                },
                                throwable -> {
                                    Toast.makeText(getApplicationContext(),throwable.getMessage(), Toast.LENGTH_LONG).show();
                                }

                        ));
            }
        });

        Lichsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lichsu = new Intent(getApplicationContext(),XemLichSuActivity.class);
                startActivity(lichsu);
            }
        });
    }

    private void initView() {
        toolbar=findViewById(R.id.toolbar_giohang);
        recyclerView_Giohang=findViewById(R.id.recyclerview_giohang);
        tongtien = findViewById(R.id.txt_tongtien);
        btnDathang = findViewById(R.id.btnDatHang);
        Lichsu = findViewById(R.id.Lichsu);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView_Giohang.setLayoutManager(layoutManager);
        recyclerView_Giohang.setHasFixedSize(true);
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

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}