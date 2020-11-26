package com.example.androidprojectbooksales.books;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidprojectbooksales.AdapterItemBook;
import com.example.androidprojectbooksales.InterfaceServeur;
import com.example.androidprojectbooksales.R;
import com.example.androidprojectbooksales.RetrofitInstance;
import com.example.androidprojectbooksales.user.Profile_Fragment;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ViewBook_Fragment extends Fragment {

    private String id;
    private ImageView imgBookCover;
    private TextView tvBookTitle, tvBookAuthor, tvBookCategory, tvBookDescription, tvBookPrice;
    private ViewBookInterface viewBookInterface;

    public ViewBook_Fragment() {
        // Required empty public constructor
    }
    public ViewBook_Fragment(String id) {
        this.id = id;
    }

    public interface ViewBookInterface {
        int getIdUser();
        void goToLoginFragment();
        void goToBookListFragment();
    }


    public static ViewBook_Fragment newInstance(String param1, String param2) {
        ViewBook_Fragment fragment = new ViewBook_Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewBookInterface = (ViewBookInterface) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_book, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgBookCover = view.findViewById(R.id.imgBookCover);
        tvBookTitle = view.findViewById(R.id.tvBookTitle);
        tvBookAuthor = view.findViewById(R.id.tvBookAuthor);
        tvBookCategory = view.findViewById(R.id.tvBookCategory);
        tvBookDescription = view.findViewById(R.id.tvBookDescription);
        tvBookPrice = view.findViewById(R.id.tvBookPrice);
        Button btnBuyBook = view.findViewById(R.id.btnBuyBook);
        getBook(id);
        btnBuyBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewBookInterface.getIdUser() == -1) {
                    viewBookInterface.goToLoginFragment();
                } else {
                    rentBook(id);
                }
            }
        });
    }

    public void getBook(String id){
        InterfaceServeur server = RetrofitInstance.getInstance().create(InterfaceServeur.class);
        Call<Book> getBook = server.getBook("y", id);

        getBook.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                Book book = response.body();
                Picasso.get().load("http://206.167.140.56:8080/A2020/420505RI/Equipe_6/AppBundle/ressources/bookPictures/"
                        + book.getId() + ".png").resize(110, 150).into(imgBookCover);
                tvBookTitle.setText("Titre: \n   " + book.getTitle());
                tvBookAuthor.setText("Autheur: \n   " + book.getAuthor());
                tvBookCategory.setText("Catégories: \n   " + book.getCategory());
                tvBookDescription.setText("Description: \n   " + book.getDescription());
                tvBookPrice.setText(Double.toString(book.getPrice()));
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Toast.makeText(getActivity(),"Erreur au chargement du livre", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void rentBook(String id) {
        InterfaceServeur server = RetrofitInstance.getInstance().create(InterfaceServeur.class);
        Call<String> rentBook = server.rentBook("y", id, viewBookInterface.getIdUser());

        rentBook.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String status = response.body();
                if (status == "ok") {
                    viewBookInterface.goToBookListFragment();
                    Toast.makeText(getActivity(),"Ce livre est maintenent à vous!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(),"Erreur à l'emprunt", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getActivity(),"Erreur à l'emprunt", Toast.LENGTH_SHORT).show();
            }
        });
    }
}