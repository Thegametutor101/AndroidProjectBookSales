package com.example.androidprojectbooksales.books;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidprojectbooksales.R;


public class ModifyBook_Fragment extends Fragment {


    public ModifyBook_Fragment() {
        // Required empty public constructor
    }

    public ModifyBook_Fragment(String id) {

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
        return inflater.inflate(R.layout.modify_book_fragment, container, false);
    }
}