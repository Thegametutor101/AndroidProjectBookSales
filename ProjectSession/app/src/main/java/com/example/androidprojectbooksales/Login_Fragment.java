package com.example.androidprojectbooksales;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Login_Fragment extends Fragment {

    Button btnNewUser;
    LoginInterface loginInterface;

    public Login_Fragment() {
        // Required empty public constructor
    }

    public interface LoginInterface
    {
        void goToAddUserFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        loginInterface = (LoginInterface)context;
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
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnNewUser=view.findViewById(R.id.btnNouvelUtilisateur);

        btnNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginInterface.goToAddUserFragment();
            }
        });
    }
}