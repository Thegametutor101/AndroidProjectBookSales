package com.example.androidprojectbooksales;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface InterfaceServeur {

    @POST("Management/addBook.php")
    @FormUrlEncoded
    Call<Void> addBook(@Field("title") String titleBook,@Field("author") String authorBook,@Field("category") String categoryBook,@Field("description") String descriptionBook, @Field("available") int availableBook, @Field("price") double priceBook,@Field("owner") String ownerBook);

    @POST("Management/addUser.php")
    @FormUrlEncoded
    Call<Void> addUser(@Field("firstName") String firstNameUser,@Field("lastName") String lastNameUser,@Field("email") String emailUser,@Field("phone") String phoneUser, @Field("password") String passwordUser);

    @POST("Management/deleteBook.php")
    @FormUrlEncoded
    Call<Void> deleteBook(@Field("id") String idBook);

    @POST("Management/getBook.php")
    @FormUrlEncoded
    Call<Book> getBook(@Field("id") String idBook);

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
    Call<JSONObject> loadBooks(@Field("searchValue") String searchValue,@Field("searchFilter") String searchFilter, @Field("searchFilter") String searchSort);

    @POST("Management/login.php")
    @FormUrlEncoded
    Call<JSONObject> login(@Field("email") String emailUser,@Field("password") String passwordUser);

    @POST("Management/rentBook.php")
    @FormUrlEncoded
    Call<JSONObject> rentBook(@Field("bookID") String bookID,@Field("userID") String userID);

    @POST("Management/returnBook.php")
    @FormUrlEncoded
    Call<JSONObject> returnBook(@Field("bookID") String bookID,@Field("userID") String userID);

    @POST("Management/updateBook.php")
    @FormUrlEncoded
    Call<JSONObject> updateBook(@Field("title") String titleBook,@Field("author") String authorBook,@Field("category") String categoryBook,@Field("description") String descriptionBook, @Field("available") int availableBook, @Field("price") double priceBook,@Field("owner") String ownerBook);

    @POST("Management/updateUser.php")
    @FormUrlEncoded
    Call<JSONObject> updateUser(@Field("firstName") String firstNameUser,@Field("lastName") String lastNameUser,@Field("email") String emailUser,@Field("phone") String phoneUser, @Field("password") String passwordUser);


    //Je n'ai pas mis udateProfile pour l'instant

}
