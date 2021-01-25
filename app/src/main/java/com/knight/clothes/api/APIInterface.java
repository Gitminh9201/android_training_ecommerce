package com.knight.clothes.api;

import com.knight.clothes.models.Banner;
import com.knight.clothes.models.Brand;
import com.knight.clothes.models.Category;
import com.knight.clothes.models.Coupon;
import com.knight.clothes.models.Group;
import com.knight.clothes.models.Invoice;
import com.knight.clothes.models.Product;
import com.knight.clothes.models.ProductSuggest;
import com.knight.clothes.models.ResponseList;
import com.knight.clothes.models.ResponseObject;
import com.knight.clothes.models.User;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface APIInterface {

    @GET(AppClient.MIDDLE_URL + "api/categories")
    Call<ResponseList<Category>> categories();

    @GET(AppClient.MIDDLE_URL + "api/banners")
    Call<ResponseList<Banner>> banners();

    @GET(AppClient.MIDDLE_URL + "api/coupons")
    Call<ResponseList<Coupon>> coupons();

    @GET(AppClient.MIDDLE_URL + "api/brands")
    Call<ResponseList<Brand>> brands();

    @GET(AppClient.MIDDLE_URL + "api/groups")
    Call<ResponseList<Group>> groups();

    @GET(AppClient.MIDDLE_URL + "api/products")
    Call<ResponseList<Product>> products(@QueryMap Map<String, String> options);

    @GET(AppClient.MIDDLE_URL + "api/products/get")
    Call<ResponseObject<Product>> detail(@Query("id") int id);

    //ids: "1,2,6"
    @GET(AppClient.MIDDLE_URL + "api/products/gets")
    Call<ResponseList<Product>> productCart(@Query("ids") String ids);

    @GET(AppClient.MIDDLE_URL + "api/products/suggest")
    Call<ResponseList<ProductSuggest>> productSuggest(@Query("keyword") String keyword);

    @FormUrlEncoded
    @POST(AppClient.MIDDLE_URL + "api/auth/google")
    Call<ResponseObject<User>> loginGoogle(
            @Field("id") String id,
            @Field("email") String email,
            @Field("name") String name,
            @Field("picture") String picture,
            @Field("accessToken") String accessToken);

    @POST(AppClient.MIDDLE_URL + "api/auth/me")
    Call<ResponseObject<User>> getUser(@HeaderMap Map<String, String> headers);

    @POST(AppClient.MIDDLE_URL + "api/auth/me")
    Call<ResponseObject<User>> uploadUser(@HeaderMap Map<String, String> headers, @QueryMap Map<String, String> options);

    @Multipart
    @POST(AppClient.MIDDLE_URL + "api/auth/me/picture")
    Call<ResponseObject<User>> uploadAvatar(@HeaderMap Map<String, String> headers, @Part MultipartBody.Part file);

    @POST(AppClient.MIDDLE_URL + "api/collection/get")
    Call<ResponseList<Product>> collection(@HeaderMap Map<String, String> headers);

    @POST(AppClient.MIDDLE_URL + "api/collection/get")
    Call<ResponseList<Product>> collection(@HeaderMap Map<String, String> headers, @Query("offset") int offset);

    @POST(AppClient.MIDDLE_URL + "api/collection/make")
    Call<ResponseObject> makeCollection(@HeaderMap Map<String, String> headers, @Query("product_id") int productID);

    @POST(AppClient.MIDDLE_URL + "api/collection/check")
    Call<ResponseObject> checkCollection(@HeaderMap Map<String, String> headers, @Query("product_id") int productID);

    @FormUrlEncoded
    @POST(AppClient.MIDDLE_URL + "api/order/create")
    Call<ResponseObject<Invoice>> createOrder(
            @HeaderMap Map<String, String> headers,
            @Field("products") String products,
            @Field("quantities") String quantities,
            @Field("address") String address,
            @Field("note") String note,
            @Field("phone") String phone,
            @Field("code") String code
    );

    @POST(AppClient.MIDDLE_URL + "api/order")
    Call<ResponseList<Invoice>> getOrders(@HeaderMap Map<String, String> headers);
    @POST(AppClient.MIDDLE_URL + "api/order")
    Call<ResponseList<Invoice>> getOrders(@HeaderMap Map<String, String> headers, @Query("offset") int offset);
    @POST(AppClient.MIDDLE_URL + "api/order")
    Call<ResponseList<Invoice>> getOrders(@HeaderMap Map<String, String> headers, @Query("offset") int offset, @Query("limit") int limit);

    @POST(AppClient.MIDDLE_URL + "api/order/get")
    Call<ResponseObject<Invoice>> getOrderDetail(@HeaderMap Map<String, String> headers, @Query("order_id") int orderID);
    @POST(AppClient.MIDDLE_URL + "api/order/cancel")
    Call<ResponseObject> cancelOrder(@HeaderMap Map<String, String> headers, @Query("order_id") int orderID);
}
