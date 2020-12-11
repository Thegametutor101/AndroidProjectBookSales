package com.example.androidprojectbooksales.user;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.androidprojectbooksales.InterfaceServeur;
import com.example.androidprojectbooksales.R;
import com.example.androidprojectbooksales.RetrofitInstance;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile_Fragment extends Fragment {

    ImageView imgProfilePicture;
    Button btnViewProfile, btnAddBook, btnMyBooks, btnMyRentedBooks, btnDisconnect;
    UserInterface userInterface;

    public Profile_Fragment() {
        // Required empty public constructor
    }

    public interface UserInterface {
        void goToAddBookFragment();
        void disconnectUser();
        int getIdUser();
        void goToMyBookSale();
        void goToMyRentedBooksFragment();
        void goToViewProfileFragment();
        String getExtension();
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
        btnViewProfile = view.findViewById(R.id.btnViewProfile);
        btnAddBook = view.findViewById(R.id.btnAddBook);
        btnMyBooks = view.findViewById(R.id.btnMyBooks);
        btnMyRentedBooks = view.findViewById(R.id.btnMyRentedBooks);
        btnDisconnect = view.findViewById(R.id.btnDisconnect);
        final Context context = view.getContext();

    /*if (user.getExt()==""){
        Picasso.get().load("http://206.167.140.56:8080/A2020/420505RI/Equipe_6/AppBundle/ressources/userPictures/"
            + userInterface.getIdUser() + ".png").resize(300, 300).into(imgProfilePicture);
    }
    else{
        Picasso.get().load("http://206.167.140.56:8080/A2020/420505RI/Equipe_6/AppBundle/ressources/userPictures/"
                + userInterface.getIdUser() + "."+user.getExt()).resize(300, 300).into(imgProfilePicture);
    }*/
        Picasso.get().load("http://206.167.140.56:8080/A2020/420505RI/Equipe_6/AppBundle/ressources/userPictures/"
                + userInterface.getIdUser() + "."+userInterface.getExtension()).resize(300, 300).into(imgProfilePicture);

        btnViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInterface.goToViewProfileFragment();
            }
        });

        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInterface.goToAddBookFragment();
            }
        });

        btnMyBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInterface. goToMyBookSale();
            }
        });

        btnMyRentedBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInterface.goToMyRentedBooksFragment();
            }
        });

        btnDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Souhaitez-vous vous déconnecter?");
                builder.setTitle("Déconnection");
                builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        Toast.makeText(getActivity(), "Ravis de votre présence", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        userInterface.disconnectUser();
                        Toast.makeText(getActivity(), "Bonne journée", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

}
