package com.example.androidprojectbooksales.user;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.androidprojectbooksales.R;

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

        btnAddBook=view.findViewById(R.id.btnAddBook);
        btnModifyBook=view.findViewById(R.id.btnModifyBook);
        btnDeleteBook=view.findViewById(R.id.btnDeleteBook);

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