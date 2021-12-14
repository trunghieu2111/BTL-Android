package com.example.cuahangtienloi.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.cuahangtienloi.R;
import com.example.cuahangtienloi.adapter.DrinkAdapter;
import com.example.cuahangtienloi.adapter.SanPhamMoiAdapter;
import com.example.cuahangtienloi.model.SanPhamMoi;
import com.example.cuahangtienloi.retrofit.ApiBanHang;
import com.example.cuahangtienloi.retrofit.RetrofitClient;
import com.example.cuahangtienloi.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DrinkActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;

    List<SanPhamMoi> mangSpDrink;
    DrinkAdapter drinkAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);
        apiBanHang= RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        GetDrink();
        Anhxa();
        ActionBar();
    }

    private void GetDrink() {
        compositeDisposable.add(apiBanHang.getSpDrink()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamMoiModel -> {
                            mangSpDrink = sanPhamMoiModel.getResult();
                            drinkAdapter = new DrinkAdapter(getApplicationContext(),mangSpDrink);
                            recyclerView.setAdapter(drinkAdapter);
                            //Toast.makeText(getApplicationContext(),loaiSpModel.getResult().get(0).getTenLoai(),Toast.LENGTH_LONG).show();
                        }
                ));
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

    private void Anhxa() {
        toolbar = findViewById(R.id.toolbar);
        recyclerView= findViewById(R.id.recyclerview_drink);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        mangSpDrink = new ArrayList<>();
    }
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}