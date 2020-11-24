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

import java.io.File;

public class Profile_Fragment extends Fragment {

    Button btnAddBook, btnModifyBook, btnDeleteBook;
    BookInterface bookInterface;

    public Profile_Fragment() {
        // Required empty public constructor
    }

    public interface BookInterface
    {
        void goToAddBookFragment();
        void goToModifyBookFragment();
        void goToDeleteBookFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        bookInterface = (BookInterface)context;
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

        btnAddBook = view.findViewById(R.id.btnAddBook);
        btnModifyBook = view.findViewById(R.id.btnModifyBook);
        btnDeleteBook = view.findViewById(R.id.btnDeleteBook);

        File imgFile = new  File("http://206.167.140.56:8080/A2020/420505RI/Equipe_6/AppBundle/ressources/userPictures/defaultUserProfile.png");
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ImageView imgProfilePicture = (ImageView) view.findViewById(R.id.imgProfilePicture);
            imgProfilePicture.setImageBitmap(myBitmap);
        }

        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookInterface.goToAddBookFragment();
            }
        });

        btnModifyBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookInterface.goToModifyBookFragment();
            }
        });

        btnDeleteBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookInterface.goToDeleteBookFragment();
            }
        });

    }
}