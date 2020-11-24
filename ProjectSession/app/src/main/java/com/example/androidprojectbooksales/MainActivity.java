package com.example.androidprojectbooksales;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.androidprojectbooksales.books.AddBook_Fragment;
import com.example.androidprojectbooksales.user.Login_Fragment;
import com.example.androidprojectbooksales.user.Profile_Fragment;
import com.example.androidprojectbooksales.books.BookList_Fragment;
import com.example.androidprojectbooksales.user.AddUser_Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements Login_Fragment.LoginInterface, Profile_Fragment.BookInterface {

    BottomNavigationView bottomNav;
    Research_Fragment researchFragment;
    Login_Fragment loginFragment;
    BookList_Fragment bookListFragment;

    AddUser_Fragment addUserFragment;
    Profile_Fragment profileFragment;
    AddBook_Fragment addBookFragment;


    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;


    SharedPreferences pref ;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref= getPreferences(MODE_PRIVATE);
        editor= pref.edit();

        editor.putBoolean("connected", false);
        editor.putInt("idUser", -1);
        editor.commit();

        researchFragment = new Research_Fragment();
        loginFragment = new Login_Fragment();
        profileFragment = new Profile_Fragment();
        addUserFragment = new AddUser_Fragment();
        addBookFragment = new AddBook_Fragment();
        bookListFragment = new BookList_Fragment();

        fragmentManager = getSupportFragmentManager();
        bottomNav = findViewById(R.id.bottomNav);
        fragmentTransaction  = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment,bookListFragment);
        fragmentTransaction.commit();

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.btnMenuLivres:
                        fragmentTransaction  = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.flFragment,bookListFragment);
                        fragmentTransaction.commit();
                        return true;
                    case R.id.btnMenuUtilisateur:
//                        if (loggedUser == 0) {
                            fragmentTransaction  = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.flFragment,loginFragment);
                            fragmentTransaction.commit();
//                        } else {
//                            fragmentTransaction  = fragmentManager.beginTransaction();
//                            fragmentTransaction.replace(R.id.flFragment,profileFragment);
//                            fragmentTransaction.commit();
//                        }
                        return true;
                }
                return false;
            }
        });
    }

    public void goToAddUserFragment(){
        fragmentTransaction  = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment,addUserFragment);
        fragmentTransaction.commit();
    }

    public void goToAddBookFragment(){
        fragmentTransaction  = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment,addBookFragment);
        fragmentTransaction.commit();
    }


    public void goToProfileFragment(){
        fragmentTransaction  = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment,profileFragment);
        fragmentTransaction.commit();
    }


    public void goToModifyBookFragment(){
        //fragmentTransaction  = fragmentManager.beginTransaction();
        //fragmentTransaction.replace(R.id.flFragment,addUserFragment);
        //fragmentTransaction.commit();
    }


    public void goToDeleteBookFragment(){
        //fragmentTransaction  = fragmentManager.beginTransaction();
        //fragmentTransaction.replace(R.id.flFragment,addUserFragment);
        //fragmentTransaction.commit();
    }


    public void setLoginInfo(int idUser){
        editor.putBoolean("connected", true);
        editor.putInt("idUser", idUser);
        editor.commit();
    }

    public int getIdUser(){
        return pref.getInt("idUser",-1);
    }

    public boolean getConnectedUser(){
        return pref.getBoolean("connected", false);
    }

}