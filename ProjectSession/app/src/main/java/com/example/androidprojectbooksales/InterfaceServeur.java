package com.example.androidprojectbooksales;

import com.example.androidprojectbooksales.books.Book;
import com.example.androidprojectbooksales.user.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface InterfaceServeur {

    @POST("Management/addBook.php")
    @FormUrlEncoded
    Call<Void> addBook(@Field("title") String titleBook,@Field("author") String authorBook,@Field("category") String categoryBook,@Field("description") String descriptionBook, @Field("available") int availableBook, @Field("price") double priceBook,@Field("owner") String ownerBook);

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
    Call<JSONObject> getUser(@Field("id") String idUser);

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

    @POST("Management/updateBook.php")
    @FormUrlEncoded
    Call<JSONObject> updateBook(@Field("title") String titleBook,@Field("author") String authorBook,@Field("category") String categoryBook,@Field("description") String descriptionBook, @Field("available") int availableBook, @Field("price") double priceBook,@Field("owner") String ownerBook);

    @POST("Management/updateUser.php")
    @FormUrlEncoded
    Call<JSONObject> updateUser(@Field("firstName") String firstNameUser,@Field("lastName") String lastNameUser,@Field("email") String emailUser,@Field("phone") String phoneUser, @Field("password") String passwordUser);
}
