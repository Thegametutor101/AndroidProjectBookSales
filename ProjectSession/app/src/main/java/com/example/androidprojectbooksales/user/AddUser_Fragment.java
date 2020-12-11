package com.example.androidprojectbooksales.user;

import android.content.Context;
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

import com.example.androidprojectbooksales.InterfaceServeur;
import com.example.androidprojectbooksales.R;
import com.example.androidprojectbooksales.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddUser_Fragment extends Fragment {

    ImageButton btnAdd, btnClear;
    EditText etFirstName, etLastName, etEmail, etPhone, etPassword, etAdresse;
    NewUserInterface newUserInterface;

    public AddUser_Fragment() {
        // Required empty public constructor
    }

    public interface NewUserInterface {
        void goToProfileFragment();
        void setLoginInfo(int idUser, String ext);
        boolean checkFieldBasic(String field, String fieldName, double maxSize, String dataType);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        newUserInterface = (NewUserInterface) context;
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
        etAdresse=view.findViewById(R.id.etAdressNewUser);
        btnAdd=view.findViewById(R.id.btnValiderNewUser);
        btnClear=view.findViewById(R.id.btnResetNewUser);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(newUserInterface.checkFieldBasic(etFirstName.getText().toString(),"prénom",100, "CaracterOnly") &&
                        newUserInterface.checkFieldBasic(etLastName.getText().toString(),"nom",100, "CaracterOnly") &&
                        newUserInterface.checkFieldBasic(etEmail.getText().toString(),"email",100, "Email") &&
                        newUserInterface.checkFieldBasic(etPhone.getText().toString(),"téléphone",100, "NumberOnly") &&
                        newUserInterface.checkFieldBasic(etPassword.getText().toString(),"mot de passe",200, "no") &&
                        newUserInterface.checkFieldBasic(etAdresse.getText().toString(),"adresse",500, "no")
                    ){
                    if (etPhone.getText().length()==10){
                        addUser(etFirstName.getText().toString(),
                                etLastName.getText().toString(),
                                etEmail.getText().toString(),
                                etPhone.getText().toString(),
                                etPassword.getText().toString(),
                                etAdresse.getText().toString());
                    }
                    else{
                        Toast.makeText(getActivity(),"Le numéro de téléphone doit contenir 10 chiffres", Toast.LENGTH_SHORT).show();
                    }

                }


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
                etAdresse.setText("");
            }
        });
    }


    public void addUser(String fisrtNameUser,String lastNameUser,String emailUser,String phoneUser, String passwordUser, String adressUser){
        InterfaceServeur serveur = RetrofitInstance.getInstance().create(InterfaceServeur.class);
        Call<Void> addUserCall = serveur.addUser("y",fisrtNameUser,lastNameUser,emailUser,phoneUser,passwordUser,adressUser);


        addUserCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(getActivity(),"Utilisateur crée", Toast.LENGTH_SHORT).show();
                login(etEmail.getText().toString(),etPassword.getText().toString());
                etFirstName.setText("");
                etLastName.setText("");
                etEmail.setText("");
                etPhone.setText("");
                etPassword.setText("");
                etAdresse.setText("");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getActivity(),"Une erreur est survenue, veuillez réessayer", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void login(String email,String password){
        InterfaceServeur serveur = RetrofitInstance.getInstance().create(InterfaceServeur.class);
        Call<User> loginCall = serveur.login("y",email,password);

        loginCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
               /* if (response.body() == "no") {
                    Toast.makeText(getActivity(),"Mot de passe ou Courriel invalide", Toast.LENGTH_SHORT).show();
                } else if (response.body() == "not email") {
                    Toast.makeText(getActivity(),"Veuillex entrer un courriel valide", Toast.LENGTH_SHORT).show();
                } else if (response.body() == "error") {
                    Toast.makeText(getActivity(),"Une erreur est survenue, veuillez réessayer", Toast.LENGTH_SHORT).show();
                } else {*/
                    assert response.body() != null;
                    User user = response.body();
                    newUserInterface.setLoginInfo(Integer.parseInt(user.getId()),user.getExt());
                    newUserInterface.goToProfileFragment();
                //}
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getActivity(),"Une erreur est survenue, veuillez réessayer", Toast.LENGTH_SHORT).show();
            }
        });
    }
}