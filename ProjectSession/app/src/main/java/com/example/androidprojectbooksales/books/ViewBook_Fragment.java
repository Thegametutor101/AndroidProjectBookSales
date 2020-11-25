package com.example.androidprojectbooksales.books;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidprojectbooksales.AdapterItemBook;
import com.example.androidprojectbooksales.InterfaceServeur;
import com.example.androidprojectbooksales.R;
import com.example.androidprojectbooksales.RetrofitInstance;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewBook_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewBook_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String id;
    private ImageView imgBookCover;
    private TextView tvBookTitle, tvBookAuthor, tvBookCategory, tvBookDescription, tvBookPrice;

    public ViewBook_Fragment() {
        // Required empty public constructor
    }
    public ViewBook_Fragment(String id) {
        this.id = id;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewBook.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewBook_Fragment newInstance(String param1, String param2) {
        ViewBook_Fragment fragment = new ViewBook_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
        getBook(id);
    }

    public void getBook(String id){
        InterfaceServeur server = RetrofitInstance.getInstance().create(InterfaceServeur.class);
        Call<Book> getBook = server.getBook("y", id);

        getBook.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                Book book = response.body();
                Picasso.get().load("http://206.167.140.56:8080/A2020/420505RI/Equipe_6/AppBundle/ressources/bookPictures/"
                        + book.getId() + ".png").into(imgBookCover);
                tvBookTitle.setText("Titre: \n   " + book.getTitle());
                tvBookAuthor.setText("Autheur: \n   " + book.getAuthor());
                tvBookCategory.setText("Catégories: \n   " + book.getCategory());
                tvBookDescription.setText("Description: \n   " + book.getDescription());
                tvBookPrice.setText(Double.toString(book.getPrice()));
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Toast.makeText(getActivity(),"Erreur au chargement des livres", Toast.LENGTH_SHORT).show();
            }
        });
    }
}