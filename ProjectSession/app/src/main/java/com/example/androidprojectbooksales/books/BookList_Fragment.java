package com.example.androidprojectbooksales.books;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.androidprojectbooksales.AdapterItemBook;
import com.example.androidprojectbooksales.InterfaceServeur;
import com.example.androidprojectbooksales.R;
import com.example.androidprojectbooksales.RetrofitInstance;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;


public class BookList_Fragment extends Fragment {

    RecyclerView rvBookList;
    AdapterItemBook adapter;
    List<Book> bookList;

    public BookList_Fragment() {
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
        return inflater.inflate(R.layout.book_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getBooks();
//        rvBookList =
//        rvBookList.setHasFixedSize(true);
//        rvBookList.setLayoutManager(new LinearLayoutManager(this));
//        adapterList = new AdapterList(productList);
//        rvBookList.setAdapter(adapterList);
    }

    public void getBooks(){
        InterfaceServeur server = RetrofitInstance.getInstance().create(InterfaceServeur.class);
        Call<JSONObject> loadBooks = server.loadBooks("", "", "");


        loadBooks.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                Toast.makeText(getActivity(),"book: ", Toast.LENGTH_SHORT).show();
                Log.d("TEST RESPONSE: ", response.toString());
//                bookList.add(response[0]);
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                Toast.makeText(getActivity(),"Erreur au chargement des livres", Toast.LENGTH_SHORT).show();
                System.exit(0);
            }
        });
    }
}