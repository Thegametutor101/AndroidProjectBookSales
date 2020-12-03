package com.example.androidprojectbooksales;

import com.example.androidprojectbooksales.books.Book;
import com.example.androidprojectbooksales.user.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface InterfaceServeur {

    @Multipart
    @POST("Management/addBook.php")
    Call<String> addBook(
            @Part ("mobile") String part_mobile,
            @Part ("title") String part_title,
            @Part ("author") String part_author,
            @Part ("category") String part_category,
            @Part ("description") String part_summary,
            @Part ("available") String part_available,
            @Part ("price") String part_price,
            @Part MultipartBody.Part part_fichier,
            @Part ("owner") String part_owner
    );

    @Multipart
    @POST("Management/updateBook.php")
    Call<String> updateBook(
            @Part ("mobile") String part_mobile,
            @Part ("id") String part_id,
            @Part ("title") String part_title,
            @Part ("author") String part_author,
            @Part ("category") String part_category,
            @Part ("description") String part_summary,
            @Part ("available") String part_available,
            @Part ("price") String part_price,
            @Part MultipartBody.Part part_fichier,
            @Part ("owner") String part_owner
    );

    @Multipart
    @POST("Management/updateBook.php")
    Call<String> updateBookWithoutCover(
            @Part ("mobile") String part_mobile,
            @Part ("id") String part_id,
            @Part ("title") String part_title,
            @Part ("author") String part_author,
            @Part ("category") String part_category,
            @Part ("description") String part_summary,
            @Part ("available") String part_available,
            @Part ("price") String part_price,
            @Part ("owner") String part_owner
    );

    @POST("Management/addUser.php")
    @FormUrlEncoded
    Call<Void> addUser(@Field("mobile") String mobile,@Field("firstName") String firstNameUser,@Field("lastName") String lastNameUser,@Field("email") String emailUser,@Field("phone") String phoneUser, @Field("password") String passwordUser);

    @POST("Management/deleteBook.php")
    @FormUrlEncoded
    Call<Void> deleteBook(@Field("id") String idBook);

    @POST("Management/getBook.php")
    @FormUrlEncoded
    Call<Book> getBook(@Field("mobile") String mobile, @Field("id") String idBook);

    @POST("Management/getMyBook.php")
    @FormUrlEncoded
    Call<JSONObject> getMyBook(@Field("id") String idBook);

    @POST("Management/getRentedBook.php")
    @FormUrlEncoded
    Call<JSONObject> getRentedBook(@Field("id") String idBook);

    @POST("Management/getUser.php")
    @FormUrlEncoded
    Call<User> getUser(@Field("mobile") String mobile, @Field("id") int idUser);

    @POST("Management/loadBooks.php")
    @FormUrlEncoded
    Call<List<Book>> loadBooks(@Field("mobile") String mobile);

    @POST("Management/getRentedBooks.php")
    @FormUrlEncoded
    Call<List<Book>> getRentedBooks(@Field("mobile") String mobile, @Field("id") int idUser);

    @POST("Management/loadBooks.php")
    @FormUrlEncoded
    Call<List<Book>> loadBooksSearch(@Field("mobile") String mobile, @Field("searchValue") String searchValue, @Field("searchFilter") String searchFilter, @Field("searchFilter") String searchSort);

    @POST("Management/login.php")
    @FormUrlEncoded
    Call<String> login(@Field("mobile") String mobile, @Field("email") String emailUser, @Field("password") String passwordUser);

    @POST("Management/rentBook.php")
    @FormUrlEncoded
    Call<String> rentBook(@Field("mobile") String mobile, @Field("bookID") String bookID, @Field("userID") int userID);

    @POST("Management/returnBook.php")
    @FormUrlEncoded
    Call<String> returnBook(@Field("mobile") String mobile, @Field("bookID") String bookID, @Field("userID") int userID);

    @POST("Management/updateUser.php")
    @FormUrlEncoded
    Call<String> updateUser(@Field("mobile") String mobile, @Field("id") int id, @Field("firstName") String firstNameUser,@Field("lastName") String lastNameUser,@Field("email") String emailUser,@Field("phone") String phoneUser);

    @POST("Management/updateUser.php")
    @FormUrlEncoded
    Call<String> updateUserWithPassword(@Field("mobile") String mobile, @Field("id") int id, @Field("firstName") String firstNameUser,@Field("lastName") String lastNameUser,@Field("email") String emailUser,@Field("phone") String phoneUser, @Field("password") String passwordUser);
}
