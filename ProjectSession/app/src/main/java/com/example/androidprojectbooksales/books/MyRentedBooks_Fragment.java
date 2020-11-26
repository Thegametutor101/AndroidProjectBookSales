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
import com.example.androidprojectbooksales.Rentals;
import com.example.androidprojectbooksales.RetrofitInstance;
import com.example.androidprojectbooksales.user.Profile_Fragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyRentedBooks_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyRentedBooks_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView rvBookList;
    AdapterItemBook adapter;
    RentedBooksInterface rentedBooksInterface;

    public MyRentedBooks_Fragment() {
        // Required empty public constructor
    }

    public interface RentedBooksInterface {
        void goToBookListFragment();
        int getIdUser();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyRentedBooks_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyRentedBooks_Fragment newInstance(String param1, String param2) {
        MyRentedBooks_Fragment fragment = new MyRentedBooks_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        rentedBooksInterface = (RentedBooksInterface) context;
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
        return inflater.inflate(R.layout.fragment_my_rented_books, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvBookList = view.findViewById(R.id.rvBookList);
        rvBookList.setHasFixedSize(true);
        rvBookList.setLayoutManager(new LinearLayoutManager(view.getContext()));
        getRentals();
    }

    public void getRentals(){
        InterfaceServeur server = RetrofitInstance.getInstance().create(InterfaceServeur.class);
        Call<List<Book>> loadRentals = server.getRentedBooks("y", rentedBooksInterface.getIdUser());

        loadRentals.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                List<Book> rentals = response.body();
                if (rentals.size() > 0) {
                    adapter = new AdapterItemBook(rentals, "return");
                    rvBookList.setAdapter(adapter);
                } else {
                    Toast.makeText(getActivity(),"Vous n'avez aucun livre acheté", Toast.LENGTH_SHORT).show();
                    rentedBooksInterface.goToBookListFragment();
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Toast.makeText(getActivity(),"Erreur au chargement des livres", Toast.LENGTH_SHORT).show();
                rentedBooksInterface.goToBookListFragment();
            }
        });
    }
}