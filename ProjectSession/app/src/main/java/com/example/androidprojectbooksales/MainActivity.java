package com.example.androidprojectbooksales;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.androidprojectbooksales.books.AddBook_Fragment;
import com.example.androidprojectbooksales.books.ModifyBook_Fragment;
import com.example.androidprojectbooksales.books.MyBooks_Fragment;
import com.example.androidprojectbooksales.books.MyRentedBooks_Fragment;
import com.example.androidprojectbooksales.books.SearchBookList_Fragment;
import com.example.androidprojectbooksales.books.ViewBook_Fragment;
import com.example.androidprojectbooksales.user.Login_Fragment;
import com.example.androidprojectbooksales.user.Profile_Fragment;
import com.example.androidprojectbooksales.books.BookList_Fragment;
import com.example.androidprojectbooksales.user.AddUser_Fragment;
import com.example.androidprojectbooksales.user.ViewProfile_Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements Login_Fragment.LoginInterface,
        Profile_Fragment.UserInterface,
        ViewBook_Fragment.ViewBookInterface,
        Research_Fragment.SearchInterface,
        SearchBookList_Fragment.SearchBookListInterface,
        MyBooks_Fragment.MyBookInterface,
        AddBook_Fragment.AddBookInterface,
        ModifyBook_Fragment.ModifyBookInterface,
        MyRentedBooks_Fragment.RentedBooksInterface,
        ViewProfile_Fragment.ViewProfileInterface,
        AddUser_Fragment.NewUserInterface{

    BottomNavigationView bottomNav;
    Research_Fragment researchFragment;
    Login_Fragment loginFragment;
    BookList_Fragment bookListFragment;
    ModifyBook_Fragment modifyBook_fragment;

    AddUser_Fragment addUserFragment;
    Profile_Fragment profileFragment;
    AddBook_Fragment addBookFragment;
    ViewBook_Fragment viewBookFragment;
    MyBooks_Fragment myBooks_fragment;
    SearchBookList_Fragment searchBookListFragment;
    MyRentedBooks_Fragment myRentedBooksFragment;
    ViewProfile_Fragment viewProfileFragment;
    Map_Fragment mapFragment;

    ViewBookBroadcastReceiver viewBookBroadcastReceiver;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    SharedPreferences pref ;
    SharedPreferences.Editor editor;
    MenuItem menuItem = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getPreferences(MODE_PRIVATE);
        editor = pref.edit();

        editor.putBoolean("connected", false);
        editor.putInt("idUser", -1);
        editor.apply();

        researchFragment = new Research_Fragment();
        loginFragment = new Login_Fragment();
        profileFragment = new Profile_Fragment();
        addUserFragment = new AddUser_Fragment();
        addBookFragment = new AddBook_Fragment();
        bookListFragment = new BookList_Fragment();
        myBooks_fragment = new MyBooks_Fragment();
        mapFragment = new Map_Fragment();
        viewBookBroadcastReceiver = new ViewBookBroadcastReceiver();

        fragmentManager = getSupportFragmentManager();
        bottomNav = findViewById(R.id.bottomNav);
        goToBookListFragment();

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (menuItem == null) {
                    menuItem = item;
                }
                switch(item.getItemId()){
                    case R.id.btnMenuLivres:
                        menuItem.setVisible(true);
                        goToBookListFragment();
                        return true;
                    case R.id.btnMenuUtilisateur:
                        menuItem.setVisible(true);
                        if (getIdUser() == -1) {
                            goToLoginFragment();
                        } else {
                            goToProfileFragment();
                        }
                        return true;
                    case R.id.btnMenuMap:
                        menuItem.setVisible(true);
                        goToMapFragment();
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter("com.example.androidprojectbooksales.VIEW_BOOK");
        this.registerReceiver(viewBookBroadcastReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(viewBookBroadcastReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_bar_search: {
                menuItem = item;
                item.setVisible(false);
                goToSearchFragment();
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    public void goToMapFragment(){
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment,mapFragment);
        fragmentTransaction.commit();
    }

    public void goToAddUserFragment(){
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment,addUserFragment);
        fragmentTransaction.commit();
    }

    public void goToAddBookFragment(){
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment,addBookFragment);
        fragmentTransaction.commit();
    }

    public void goToSearchFragment(){
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment,researchFragment);
        fragmentTransaction.commit();
    }

    public void goToLoginFragment() {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment,loginFragment);
        fragmentTransaction.commit();
    }

    public void goToProfileFragment(){
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment,profileFragment);
        fragmentTransaction.commit();
    }

    public void goToViewProfileFragment(){

        viewProfileFragment = new ViewProfile_Fragment();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment,viewProfileFragment);
        fragmentTransaction.commit();
    }

    public void goToBookListFragment() {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment,bookListFragment);
        fragmentTransaction.commit();
    }

    public void goToSearchBookListFragment(String searchValue, String searchFilter, String searchSort) {
        menuItem.setVisible(true);
        searchBookListFragment = new SearchBookList_Fragment(searchValue, searchFilter, searchSort);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment,searchBookListFragment);
        fragmentTransaction.commit();
    }

    public void goToViewBook(String id, String type){
        viewBookFragment = new ViewBook_Fragment(id, type);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment,viewBookFragment);
        fragmentTransaction.commit();
    }


    public void goToModifyBookFragment(String id){
        modifyBook_fragment = new ModifyBook_Fragment(id);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment,modifyBook_fragment);
        fragmentTransaction.commit();
    }


    public void goToMyBookSale(){
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment,myBooks_fragment);
        fragmentTransaction.commit();
    }


    public void goToMyRentedBooksFragment(){
        myRentedBooksFragment = new MyRentedBooks_Fragment();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment,myRentedBooksFragment);
        fragmentTransaction.commit();
    }


    public void setLoginInfo(int idUser){
        editor.putBoolean("connected", true);
        editor.putInt("idUser", idUser);
        editor.commit();
    }

    public int getIdUser(){
        return pref.getInt("idUser",-1);
    }

    public void disconnectUser(){
        editor.putBoolean("connected", false);
        editor.putInt("idUser", -1);
        editor.commit();
        goToLoginFragment();
    }

    public class ViewBookBroadcastReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent) {
            String id = intent.getStringExtra("id");
            String type = intent.getStringExtra("type");
            if (Integer.parseInt(intent.getStringExtra("owner"))==getIdUser()){
                goToModifyBookFragment(id);
            }
            else{
                goToViewBook(id, type);
            }

        }
    }



    public boolean checkFieldBasic(String field, String fieldName, double maxSize, String dataType){
        String errorMessage="tok";
        String regexDigit = "[0-9]+";
        String  regexCaracter = "\\D";
        String regexEmail="(\\w)(\\s+)([\\.,])";

        if (field.trim().isEmpty()){
            errorMessage="Le champs "+fieldName+" est vide";
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
            return false;
        }
        else if(!(dataType=="NumberOnly") && !(field.matches(regexDigit))){
            if(field.length()>=maxSize){
            errorMessage="Le champs "+fieldName+" dépasse la limite de caractère";
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
            return false;
            }
        }
        else if((dataType=="CaracterOnly") && !(field.matches(regexCaracter))){
            errorMessage="Le champs "+fieldName+" ne doit contenir que des valeurs non-numérique";
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
            return false;
        }
        else if((dataType=="Email") && !(field.matches(regexEmail))){
            errorMessage="Le champs "+fieldName+" doit contenir un email qui correspond au format suivant: exemple@gmail.com";
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

}