package com.example.cuahangtienloi.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.cuahangtienloi.R;
import com.example.cuahangtienloi.adapter.CategoryAdapter;
import com.example.cuahangtienloi.adapter.SanPhamMoiAdapter;
import com.example.cuahangtienloi.model.CategoryProduct;
import com.example.cuahangtienloi.model.SanPhamMoi;
import com.example.cuahangtienloi.model.SanPhamMoiModel;
import com.example.cuahangtienloi.retrofit.ApiBanHang;
import com.example.cuahangtienloi.retrofit.RetrofitClient;
import com.example.cuahangtienloi.utils.Utils;
import com.google.android.material.navigation.NavigationView;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


// Chỉnh IP WIFI từng nơi để chạy được, KTra ip wifi.

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewHome;
    NavigationView navigationView;
    ListView listViewHome;
    DrawerLayout drawerLayout;
    CategoryAdapter categoryAdapter;
    List<CategoryProduct> mangloaisp;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;

    LinearLayout linearLayout;
    NotificationBadge badge;

    ImageView imgAcc;

    List<SanPhamMoi> mangSpMoi;
    SanPhamMoiAdapter spAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiBanHang= RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        Anhxa();
        Actionbar();
        if (isConnected(this)){
            ActionViewFlipper();
            getLoaiSanPham();
            getSpMoi();
            getEventClick();
        }
        else{
            Toast.makeText(getApplicationContext(), "Không có Internet, kiểm tra Internet!", Toast.LENGTH_LONG).show();
        }
    }

    private void getEventClick() {
        listViewHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Intent trangchu = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(trangchu);
                        break;
                    case 1:
                        Intent food = new Intent(getApplicationContext(),FoodActivity.class);
                        startActivity(food);
                        break;
                    case 2:
                        Intent drink = new Intent(getApplicationContext(),DrinkActivity.class);
                        startActivity(drink);
                        break;
                }
            }
        });

        imgAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent acc = new Intent(getApplicationContext(),SuaAccActivity.class);
                startActivity(acc);
            }

        });
    }

    private void getSpMoi() {
        compositeDisposable.add(apiBanHang.getSpMoi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamMoiModel -> {
                            mangSpMoi = sanPhamMoiModel.getResult();
                            spAdapter = new SanPhamMoiAdapter(getApplicationContext(),mangSpMoi);
                            recyclerViewHome.setAdapter(spAdapter);
                            //Toast.makeText(getApplicationContext(),loaiSpModel.getResult().get(0).getTenLoai(),Toast.LENGTH_LONG).show();
                        }
                ));
    }

    private void getLoaiSanPham() {
        compositeDisposable.add(apiBanHang.getLoaiSp()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                loaiSpModel -> {
                    mangloaisp = loaiSpModel.getResult();
                    categoryAdapter = new CategoryAdapter(getApplicationContext(),mangloaisp);
                    listViewHome.setAdapter(categoryAdapter);
                    //Toast.makeText(getApplicationContext(),loaiSpModel.getResult().get(0).getTenLoai(),Toast.LENGTH_LONG).show();
                }
        ));
    }

    private void ActionViewFlipper() {
        List<String> mangBanner = new ArrayList<>();
        mangBanner.add("http://kesatkhohang.vn/upload/bai-viet/banner%20ke%20tap%20hoa%20sieu%20thi%20mini-380.jpg");
        mangBanner.add("https://namvietsoft.vn/wp-content/uploads/2019/01/banner-pm-sieu-thi-mini-ok.jpg");
        mangBanner.add("https://blog.abit.vn/wp-content/uploads/2021/03/mo-hinh-cua-hang-tien-loi1.jpg");
        for (int i = 0; i < mangBanner.size(); i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(mangBanner.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setOutAnimation(slide_out);
    }

    private void Actionbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void Anhxa() {
        toolbar = findViewById(R.id.ToolbarHome);
        viewFlipper = findViewById(R.id.viewFlipper);
        recyclerViewHome = findViewById(R.id.recyclerviewHome);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerViewHome.setLayoutManager(layoutManager);
        recyclerViewHome.setHasFixedSize(true);

        imgAcc = findViewById(R.id.imgacc);

        navigationView = findViewById(R.id.navigationview);
        listViewHome = findViewById(R.id.lsVHome);
        drawerLayout = findViewById(R.id.drawerlayout);

        //khoi tao list
        mangloaisp = new ArrayList<>();

        mangSpMoi = new ArrayList<>();

        // Xử lý giỏ hàng
        badge = findViewById(R.id.menu_sl);
        linearLayout = findViewById(R.id.Gio_Hang_Main);

        if (Utils.manggiohang==null){
            Utils.manggiohang = new ArrayList<>();
        }
        else{
            int soSP = 0;
            for (int i=0; i<Utils.manggiohang.size(); i++){
                soSP += Utils.manggiohang.get(i).getSoLuong();
            }
            badge.setText(String.valueOf(soSP));
        }

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent giohang = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(giohang);
            }
        });
    }

    @Override
    //xử lý số lượng hàng trogn giỏ khi ấn quay lại,
    protected void onResume() {
        super.onResume();
        int soSP = 0;
        for (int i=0; i<Utils.manggiohang.size(); i++){
            soSP += Utils.manggiohang.get(i).getSoLuong();
        }
        badge.setText(String.valueOf(soSP));
    }

    //ktra kết nối internet
    private boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi != null && wifi.isConnected()) || (mobile != null && mobile.isConnected())){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    //giải phóng tài nguyên trước khi activity đóng. Vòng đời của activity kết thúc ở đây.
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}