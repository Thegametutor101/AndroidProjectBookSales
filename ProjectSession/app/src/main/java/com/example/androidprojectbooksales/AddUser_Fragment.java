package com.example.androidprojectbooksales;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddUser_Fragment extends Fragment {

    ImageButton btnAdd, btnClear;
    EditText etFirstName, etLastName, etEmail, etPhone, etPassword;

    public AddUser_Fragment() {
        // Required empty public constructor
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
        return inflater.inflate(R.layout.add_user_fragment, container, false);


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etFirstName=view.findViewById(R.id.etNewUserFirstName);
        etLastName=view.findViewById(R.id.etNewUserLastName);
        etEmail=view.findViewById(R.id.etNewUserEmail);
        etPhone=view.findViewById(R.id.etNewUserPhone);
        etPassword=view.findViewById(R.id.etNewUserPassword);

        btnAdd=view.findViewById(R.id.btnValiderNewUser);
        btnClear=view.findViewById(R.id.btnResetNewUser);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser(etFirstName.getText().toString(),etLastName.getText().toString(),etEmail.getText().toString(),etPhone.getText().toString(),etPassword.getText().toString());
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etFirstName.setText("");
                etLastName.setText("");
                etEmail.setText("");
                etPhone.setText("");
                etPassword.setText("");
            }
        });
    }


    public void addUser(String fisrtNameUser,String lastNameUser,String emailUser,String phoneUser, String passwordUser){
        InterfaceServeur serveur = RetrofitInstance.getInstance().create(InterfaceServeur.class);
        Call<Void> addUserCall = serveur.addUser(fisrtNameUser,lastNameUser,emailUser,phoneUser,passwordUser);


        addUserCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(getActivity(),"Utilisateur crée", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getActivity(),"Une erreur est survenue, veuillez réessayer", Toast.LENGTH_SHORT).show();
            }
        });
    }
}