package com.example.cuahangtienloi.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cuahangtienloi.R;
import com.example.cuahangtienloi.retrofit.ApiBanHang;
import com.example.cuahangtienloi.retrofit.RetrofitClient;
import com.example.cuahangtienloi.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DangKiActivity extends AppCompatActivity {
    EditText edtFullname, edtUser, edtPass, edtPhone, edtAddress, edtPassXN;
    Button btnDangki;
    Toolbar toolbar;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ki);
        apiBanHang= RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        Anhxa();
        ActionBar();
        Control();
    }

    private void Control() {
            btnDangki.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String ten = edtFullname.getText().toString().trim();
                    String tk = edtUser.getText().toString().trim();
                    String mk = edtPass.getText().toString().trim();
                    String dt = edtPhone.getText().toString().trim();
                    String dc = edtAddress.getText().toString().trim();
                    String mkxn = edtPassXN.getText().toString().trim();
                    if (ten.isEmpty() || tk.isEmpty() || mk.isEmpty() || dt.isEmpty()|| dc.isEmpty()|| mkxn.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Nhập đầy đủ các trường!", Toast.LENGTH_LONG).show();
                    }
                    else if(mk.equals(mkxn)== false){
                        Toast.makeText(getApplicationContext(), "Mật khẩu không khớp nhau! Vui lòng nhập lại!", Toast.LENGTH_LONG).show();
                    }
                    else {
                        compositeDisposable.add(apiBanHang.Dangki(tk, mk, ten, dt, dc)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        userModel -> {
                                            Intent intent = new Intent(getApplicationContext(), DangNhapActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                )
                        );
                    }
                    //Toast.makeText(getApplicationContext(), mk +"-"+ mkxn, Toast.LENGTH_LONG).show();
                }
            });

    }

    private void Anhxa() {
        edtFullname=findViewById(R.id.edtFullname_dangki);
        edtUser=findViewById(R.id.edtUser_dangki);
        edtPass=findViewById(R.id.edtPass_dangki);
        edtAddress=findViewById(R.id.edtDiachi_dangki);
        edtPhone=findViewById(R.id.edtPhone_dangki);
        btnDangki= findViewById(R.id.btn_dangki);
        toolbar = findViewById(R.id.toolbar_dangki);
        edtPassXN = findViewById(R.id.edtPassXacNhan_dangki);
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