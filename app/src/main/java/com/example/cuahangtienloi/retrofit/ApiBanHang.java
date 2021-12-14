package com.example.cuahangtienloi.retrofit;

import com.example.cuahangtienloi.model.GioHang;
import com.example.cuahangtienloi.model.HoaDonModel;
import com.example.cuahangtienloi.model.LoaiSpModel;
import com.example.cuahangtienloi.model.SanPhamMoiModel;
import com.example.cuahangtienloi.model.UserModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiBanHang {
    @GET("getcategory.php")
    Observable<LoaiSpModel> getLoaiSp();

    @GET("getspNew.php")
    Observable<SanPhamMoiModel> getSpMoi();

    @GET("getdrink.php")
    Observable<SanPhamMoiModel> getSpDrink();

    @GET("getfood.php")
    Observable<SanPhamMoiModel> getSpFood();

    @POST("dangnhap.php")
    @FormUrlEncoded
    Observable<UserModel> dangNhap(
            @Field("Username") String Username,
            @Field("Password") String Password
    );

    @POST("setaccount.php")
    @FormUrlEncoded
    Observable<UserModel> suaAcc(
            @Field("Username") String Username,
            @Field("Password") String Password,
            @Field("Id") int Id,
            @Field("Fullname") String Fullname
    );

    @POST("dangki.php")
    @FormUrlEncoded
    Observable<UserModel> Dangki(
            @Field("Username") String Username,
            @Field("Password") String Password,
            @Field("Fullname") String Fullname,
            @Field("Phone") String Phone,
            @Field("Address") String FAddress
    );

    @POST("giohang.php")
    @FormUrlEncoded
    Observable<UserModel> createOder(
            /*@Field("Username") String Username,
            @Field("Password") String Password*/
            @Field("TaiKhoanId") int TaiKhoanId,
            @Field("TongTien") String TongTien,
            @Field("Ten") String Ten,
            @Field("Phone") String Phone,
            @Field("Address") String Address,
            @Field("chitiet") String chitiet
    );

    @POST("getGiohang.php")
    @FormUrlEncoded
    Observable<HoaDonModel> xemDonHang(
            @Field("TaiKhoanId") int TaiKhoanId
    );

}
