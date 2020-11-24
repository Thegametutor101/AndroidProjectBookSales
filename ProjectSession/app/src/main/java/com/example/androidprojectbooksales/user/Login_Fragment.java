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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.androidprojectbooksales.InterfaceServeur;
import com.example.androidprojectbooksales.R;
import com.example.androidprojectbooksales.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Login_Fragment extends Fragment {

    Button btnNewUser;
    ImageButton btnValider, btnEffacer;
    LoginInterface loginInterface;
    EditText etEmail, etPassword;

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

        btnValider=view.findViewById(R.id.btnValiderLogin);
        btnEffacer=view.findViewById(R.id.btnResetLogin);
        btnNewUser=view.findViewById(R.id.btnNouvelUtilisateur);

        etEmail=view.findViewById(R.id.etLoginEmail);
        etPassword=view.findViewById(R.id.etLoginPassword);


        btnNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginInterface.goToAddUserFragment();
            }
        });

        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(etEmail.getText().toString(),etPassword.getText().toString());
            }
        });

        btnEffacer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etEmail.setText("");
                etPassword.setText("");
            }
        });

    }


    public void login(String email,String password){
        InterfaceServeur serveur = RetrofitInstance.getInstance().create(InterfaceServeur.class);
        Call<String> loginCall = serveur.login("y",email,password);


        loginCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String test=response.toString();
                Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(),"Utilisateur connecté", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getActivity(),"Une erreur est survenue, veuillez réessayer", Toast.LENGTH_SHORT).show();
            }
        });
    }
}