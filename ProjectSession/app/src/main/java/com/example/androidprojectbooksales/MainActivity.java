package com.example.androidprojectbooksales;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        researchFragment = new Research_Fragment();
        loginFragment = new Login_Fragment();
        profileFragment = new Profile_Fragment();
        addUserFragment = new AddUser_Fragment();
        addBookFragment = new AddBook_Fragment();
        bookListFragment = new BookList_Fragment();

        fragmentManager = getSupportFragmentManager();
        bottomNav = findViewById(R.id.bottomNav);
        bottomNav.getMenu().getItem(1).setChecked(true);
        fragmentTransaction  = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment,bookListFragment);
        fragmentTransaction.commit();

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.btnMenuRecherche:
                        fragmentTransaction  = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.flFragment,researchFragment);
                        fragmentTransaction.commit();
                        return true;
                    case R.id.btnMenuLivres:
                        fragmentTransaction  = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.flFragment,bookListFragment);
                        fragmentTransaction.commit();
                        return true;
                    case R.id.btnMenuUtilisateur:
                        fragmentTransaction  = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.flFragment,loginFragment);
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