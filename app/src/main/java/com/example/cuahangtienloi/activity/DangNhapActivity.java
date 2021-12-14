package com.example.cuahangtienloi.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cuahangtienloi.R;
import com.example.cuahangtienloi.adapter.SanPhamMoiAdapter;
import com.example.cuahangtienloi.retrofit.ApiBanHang;
import com.example.cuahangtienloi.retrofit.RetrofitClient;
import com.example.cuahangtienloi.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DangNhapActivity extends AppCompatActivity {
    EditText edtUser, edtPass;
    Button btnDangnhap;
    Button btnDangki;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);

        apiBanHang= RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        initView();
        initControl();
    }

    private void initControl() {
        btnDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUser.getText().toString().trim();
                String pass = edtPass.getText().toString().trim();
                if (TextUtils.isEmpty(username)){
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập tài khoản!", Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(pass)){
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập mật khẩu!", Toast.LENGTH_LONG).show();
                }
                else{
                    compositeDisposable.add(apiBanHang.dangNhap(username,pass)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    userModel -> {
                                        if(userModel.isSuccess()){
                                            //lưu vào dl vào user hiện tại để dùng sau, và get(0) là trong list có 1 tài khoản đầu tiên.
                                            Utils.user_current = userModel.getResult().get(0);
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(), "Sai tài khoản hoặc mật khẩu! Vui lòng nhập lại!", Toast.LENGTH_LONG).show();
                                        }
                                    }
                            ));
                }
            }
        });

        btnDangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dangki = new Intent(getApplicationContext(),DangKiActivity.class);
                startActivity(dangki);
            }
        });
    }

    private void initView() {
        edtUser = findViewById(R.id.username);
        edtPass = findViewById(R.id.password);
        btnDangnhap = findViewById(R.id.btnDangnhap);
        btnDangki = findViewById(R.id.btnDangki);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}