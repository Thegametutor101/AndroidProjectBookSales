package com.example.androidprojectbooksales.user;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.androidprojectbooksales.R;
import com.squareup.picasso.Picasso;

import java.io.File;

public class Profile_Fragment extends Fragment {

    ImageView imgProfilePicture;
    Button btnAddBook, btnModifyBook, btnDeleteBook;
    UserInterface userInterface;

    public Profile_Fragment() {
        // Required empty public constructor
    }

    public interface UserInterface {
        void goToAddBookFragment();
        void goToModifyBookFragment();
        void goToDeleteBookFragment();
        int getIdUser();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        userInterface = (UserInterface) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.profile_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imgProfilePicture = view.findViewById(R.id.imgProfilePicture);
        btnAddBook = view.findViewById(R.id.btnAddBook);
        btnModifyBook = view.findViewById(R.id.btnModifyBook);
        btnDeleteBook = view.findViewById(R.id.btnDeleteBook);

        Picasso.get().load("http://206.167.140.56:8080/A2020/420505RI/Equipe_6/AppBundle/ressources/userPictures/"
                + userInterface.getIdUser() + ".png").into(imgProfilePicture);

        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInterface.goToAddBookFragment();
            }
        });

        btnModifyBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInterface.goToModifyBookFragment();
            }
        });

        btnDeleteBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInterface.goToDeleteBookFragment();
            }
        });
    }
}