package com.example.androidprojectbooksales.books;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.androidprojectbooksales.AdapterItemBook;
import com.example.androidprojectbooksales.InterfaceServeur;
import com.example.androidprojectbooksales.R;
import com.example.androidprojectbooksales.RetrofitInstance;
import com.example.androidprojectbooksales.user.Profile_Fragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyBooks_Fragment extends Fragment {


    RecyclerView rvBookList;
    AdapterItemBook adapter;
    MyBookInterface myBookInterface;

    public MyBooks_Fragment() {
        // Required empty public constructor
    }


    public interface MyBookInterface {
        int getIdUser();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        myBookInterface = (MyBookInterface) context;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvBookList = view.findViewById(R.id.rvMyBook);
        rvBookList.setHasFixedSize(true);
        rvBookList.setLayoutManager(new LinearLayoutManager(view.getContext()));
        getBooks();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.my_book_fragment, container, false);
    }


    public void getBooks(){
        InterfaceServeur server = RetrofitInstance.getInstance().create(InterfaceServeur.class);
        Call<List<Book>> loadBooks = server.loadBooks("y");

        loadBooks.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                List<Book> lines = response.body();
                List<Book> myBook = new ArrayList<Book>();
                for (Book book: lines) {
                    if (Integer.parseInt(book.getOwner()) == myBookInterface.getIdUser()) {
                        myBook.add(book);
                    }
                }
                adapter = new AdapterItemBook(myBook);
                rvBookList.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Toast.makeText(getActivity(),"Erreur au chargement des livres", Toast.LENGTH_SHORT).show();
                System.exit(0);
            }
        });
    }
}