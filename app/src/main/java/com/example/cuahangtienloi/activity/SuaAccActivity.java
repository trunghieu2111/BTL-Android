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

public class SuaAccActivity extends AppCompatActivity {
    EditText edtFullname, edtUser, edtPass, edtPassXN;
    Button btnSua;
    Toolbar toolbar;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_acc);

        apiBanHang= RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        Anhxa();
        ActionBar();
        Control();
    }

    private void Control() {
        edtFullname.setText(Utils.user_current.getFullname());
        edtUser.setText(Utils.user_current.getUsername());
        edtPass.setText(Utils.user_current.getPassword());
        edtPassXN.setText(Utils.user_current.getPassword());
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int Id= Utils.user_current.getId();
                String fullname = edtFullname.getText().toString().trim();
                String username = edtUser.getText().toString().trim();
                String pass = edtPass.getText().toString().trim();
                String passXN = edtPassXN.getText().toString().trim();
                if(pass.equals(passXN)== false){
                    Toast.makeText(getApplicationContext(), "Mật khẩu không khớp nhau! Vui lòng nhập lại!", Toast.LENGTH_LONG).show();
                }
                else {
                    compositeDisposable.add(apiBanHang.suaAcc(username,pass,Id,fullname)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                userModel -> {
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                        )
                    );
                }
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

    private void Anhxa() {
        edtFullname=findViewById(R.id.edtFullnameAcc);
        edtUser=findViewById(R.id.edtUserAcc);
        edtPass=findViewById(R.id.edtPassAcc);
        btnSua= findViewById(R.id.btnSuaAcc);
        toolbar = findViewById(R.id.toolbar_acc);
        edtPassXN = findViewById(R.id.edtPassXacNhan_Acc);
    }
}