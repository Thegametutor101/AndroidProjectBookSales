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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchBookList_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchBookList_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView rvBookList;
    AdapterItemBook adapter;
    String searchValue, searchFilter, searchSort;
    SearchBookListInterface searchBookListInterface;

    public SearchBookList_Fragment() {
        // Required empty public constructor
    }

    public SearchBookList_Fragment(String searchValue, String searchFilter, String searchSort) {
        this.searchValue = searchValue;
        this.searchFilter = searchFilter;
        this.searchSort = searchSort;
    }

    public interface SearchBookListInterface {
        void goToBookListFragment();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchBookList_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchBookList_Fragment newInstance(String param1, String param2) {
        SearchBookList_Fragment fragment = new SearchBookList_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        searchBookListInterface = (SearchBookListInterface) context;
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
        return inflater.inflate(R.layout.fragment_search_book_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvBookList = view.findViewById(R.id.rvBookList);
        rvBookList.setHasFixedSize(true);
        rvBookList.setLayoutManager(new LinearLayoutManager(view.getContext()));
        getBooks();
    }

    public void getBooks(){
        InterfaceServeur server = RetrofitInstance.getInstance().create(InterfaceServeur.class);
        Call<List<Book>> loadBooks = server.loadBooksSearch("y", searchValue, searchFilter, searchSort);

        loadBooks.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                List<Book> lines = response.body();
                List<Book> availableBooks = new ArrayList<Book>();
                if (lines.size() > 0) {
                    for (Book book: lines) {
                        if (book.getAvailable() == 1) {
                            availableBooks.add(book);
                        }
                    }
                    adapter = new AdapterItemBook(availableBooks);
                    rvBookList.setAdapter(adapter);
                } else {
                    Toast.makeText(getActivity(),"Aucun livres ne correspondent à cette recherche", Toast.LENGTH_SHORT).show();
                    searchBookListInterface.goToBookListFragment();
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Toast.makeText(getActivity(),"Erreur au chargement des livres", Toast.LENGTH_SHORT).show();
                searchBookListInterface.goToBookListFragment();
            }
        });
    }
}