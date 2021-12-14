package com.example.cuahangtienloi.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.cuahangtienloi.R;
import com.example.cuahangtienloi.adapter.DrinkAdapter;
import com.example.cuahangtienloi.adapter.XemDonHangAdapter;
import com.example.cuahangtienloi.retrofit.ApiBanHang;
import com.example.cuahangtienloi.retrofit.RetrofitClient;
import com.example.cuahangtienloi.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class XemLichSuActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_lich_su);
        apiBanHang= RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);

        Anhxa();
        Actionbar();
        initControl();
    }

    private void initControl() {
        compositeDisposable.add(apiBanHang.xemDonHang(Utils.user_current.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        hoaDonModel -> {
                            XemDonHangAdapter adapter = new XemDonHangAdapter(getApplicationContext(), hoaDonModel.getResult());
                            recyclerView.setAdapter(adapter);
                            //Toast.makeText(getApplicationContext(), hoaDonModel.getResult().get(0).getItem().get(0).getTenSP(), Toast.LENGTH_LONG).show();
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(),throwable.getMessage(), Toast.LENGTH_LONG).show();
                        }
                ));
    }

    private void Anhxa() {
        toolbar = findViewById(R.id.toolbar_Lichsu);
        recyclerView= findViewById(R.id.recyclerview_Lichsu);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void Actionbar() {
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