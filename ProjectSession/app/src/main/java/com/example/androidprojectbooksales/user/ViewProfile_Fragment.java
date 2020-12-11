package com.example.androidprojectbooksales.user;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidprojectbooksales.InterfaceServeur;
import com.example.androidprojectbooksales.R;
import com.example.androidprojectbooksales.RetrofitInstance;
import com.example.androidprojectbooksales.books.Book;
import com.example.androidprojectbooksales.books.ViewBook_Fragment;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class ViewProfile_Fragment extends Fragment {

    ImageView imgViewProfilePicture;
    TextView tvPassword;
    EditText etFirstName, etLastName, etEmail, etPhone, etNewPassword, etNewPasswordConfirm, etAdresse;
    Button btnSave, btnReset;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    boolean validEmail = true;
    ViewProfileInterface viewProfileInterface;

    public ViewProfile_Fragment() {
        // Required empty public constructor
    }

    public interface ViewProfileInterface {
        void goToBookListFragment();
        int getIdUser();
        String getExtension();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewProfileInterface = (ViewProfileInterface) context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgViewProfilePicture = view.findViewById(R.id.imgViewProfilePicture);
        tvPassword = view.findViewById(R.id.tvPassword);
        etFirstName = view.findViewById(R.id.etFirstName);
        etLastName = view.findViewById(R.id.etLastName);
        etEmail = view.findViewById(R.id.etEmail);
        etPhone = view.findViewById(R.id.etPhone);
        etNewPassword = view.findViewById(R.id.etNewPassword);
        etNewPasswordConfirm = view.findViewById(R.id.etNewPasswordConfirm);
        etAdresse= view.findViewById(R.id.etAdressViewProfile);
        btnSave = view.findViewById(R.id.btnSave);
        btnReset = view.findViewById(R.id.btnReset);
        Picasso.get().load("http://206.167.140.56:8080/A2020/420505RI/Equipe_6/AppBundle/ressources/userPictures/"
                + viewProfileInterface.getIdUser() + "."+viewProfileInterface.getExtension()).resize(300, 300).into(imgViewProfilePicture);
        getUser(viewProfileInterface.getIdUser());
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etEmail.getText().toString().trim().matches(emailPattern) && editable.length() > 0)
                {
                    validEmail = true;
                }
                else
                {
                    validEmail = false;
                }
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Entrez votre mot de passe pour confirmer.");

                // Set up the input
                final EditText input = new EditText(getContext());
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (tvPassword.getText().toString().equals(input.getText().toString())) {
                            if (validEmail) {
                                if (etNewPassword.getText().toString().trim().equals(etNewPasswordConfirm.getText().toString().trim())) {
                                    updateUser();
                                } else {
                                    Toast.makeText(getActivity(),"Nouveau mot de passe n'est pas identique.", Toast.LENGTH_SHORT).show();
                                }
                            } else  {
                                Toast.makeText(getActivity(),"Entrez un courriel valide", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(),"Mot de passe invalide.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUser(viewProfileInterface.getIdUser());
            }
        });
    }

    public void getUser(int id) {
        InterfaceServeur server = RetrofitInstance.getInstance().create(InterfaceServeur.class);
        Call<User> getUser = server.getUser("y", id);

        getUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                tvPassword.setText(user.getPassword());
                etFirstName.setText(user.getFirstName());
                etLastName.setText(user.getLastName());
                etEmail.setText(user.getEmail());
                etPhone.setText(user.getPhone());
                etAdresse.setText(user.getAdress());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getActivity(),"Erreur au chargement de votre profile", Toast.LENGTH_SHORT).show();
                viewProfileInterface.goToBookListFragment();
            }
        });
    }

    public void updateUser() {
        InterfaceServeur server = RetrofitInstance.getInstance().create(InterfaceServeur.class);
        Call<String> updateUser;
        if (etNewPassword.getText().toString().trim().length() > 0) {
            updateUser = server.updateUserWithPassword("y",
                    viewProfileInterface.getIdUser(),
                    etFirstName.getText().toString().trim(),
                    etLastName.getText().toString().trim(),
                    etEmail.getText().toString().trim(),
                    etPhone.getText().toString().trim(),
                    etNewPassword.getText().toString().trim(),
                    etAdresse.getText().toString().trim());
        } else {
            updateUser = server.updateUser("y",
                    viewProfileInterface.getIdUser(),
                    etFirstName.getText().toString().trim(),
                    etLastName.getText().toString().trim(),
                    etEmail.getText().toString().trim(),
                    etPhone.getText().toString().trim(),
                    etAdresse.getText().toString().trim());
        }

        updateUser.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body().equals("ok")) {
                    Toast.makeText(getActivity(),"Profile mis a jour", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(),"Erreur lors de la mise a jour", Toast.LENGTH_SHORT).show();
                }
                viewProfileInterface.goToBookListFragment();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getActivity(),"Erreur au chargement de votre profile", Toast.LENGTH_SHORT).show();
                viewProfileInterface.goToBookListFragment();
            }
        });
    }
}