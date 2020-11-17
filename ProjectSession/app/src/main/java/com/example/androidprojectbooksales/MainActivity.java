package com.example.androidprojectbooksales;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements Login_Fragment.LoginInterface, Book_Fragment.BookInterface {


    BottomNavigationView bottomNav;

    Research_Fragment researchFragment;
    Login_Fragment loginFragment;
    AddUser_Fragment addUserFragment;
    Book_Fragment bookFragment;
    AddBook_Fragment addBookFragment;
    BuyBook_Fragment buyBookFragment;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        researchFragment = new Research_Fragment();
        loginFragment = new Login_Fragment();
        bookFragment= new Book_Fragment();
        addUserFragment = new AddUser_Fragment();
        addBookFragment = new AddBook_Fragment();
        buyBookFragment = new BuyBook_Fragment();

        fragmentManager= getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.flFragment,researchFragment);
        fragmentTransaction.commit();


        bottomNav=findViewById(R.id.bottomNav);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.btnMenuUtilisateur:
                        fragmentTransaction  = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.flFragment,loginFragment);
                        fragmentTransaction.commit();
                        return true;
                    case R.id.btnMenuRecherche:
                        fragmentTransaction  = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.flFragment,researchFragment);
                        fragmentTransaction.commit();
                        return true;
                    case R.id.btnMenuLivre:
                        fragmentTransaction  = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.flFragment,bookFragment);
                        fragmentTransaction.commit();
                        return true;
                    case R.id.btnMenuAcheter:
                        fragmentTransaction  = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.flFragment,buyBookFragment);
                        fragmentTransaction.commit();
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



}