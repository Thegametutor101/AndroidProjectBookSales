package com.example.androidprojectbooksales.books;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidprojectbooksales.InterfaceServeur;
import com.example.androidprojectbooksales.R;
import com.example.androidprojectbooksales.RetrofitInstance;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddBook_Fragment extends Fragment {

    TextView tvTexte;
    ImageButton btnAdd, btnClear;
    EditText etTitle, etAuthor, etCategory, etSummary, etPrice;
    RadioGroup rgAvailable;
    int available;

    public AddBook_Fragment() {
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
        return inflater.inflate(R.layout.add_book_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvTexte=view.findViewById(R.id.tvTexte);
        etTitle=view.findViewById(R.id.etAddBookTitle);
        etAuthor=view.findViewById(R.id.etAddBookAuthor);
        etCategory=view.findViewById(R.id.etAddBookCategory);
        etSummary=view.findViewById(R.id.etAddBookSummary);
        etPrice=view.findViewById(R.id.etAddBookPrice);

        btnAdd=view.findViewById(R.id.btnAddBookValidate);
        btnClear=view.findViewById(R.id.btnAddBookClear);

        rgAvailable=view.findViewById(R.id.rgAddBookAvailable);

        rgAvailable.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.rbAddBookAvailbleYes:
                        available=1;
                        break;
                    case R.id.rbAddBookAvailbleNo:
                        available=0;
                        break;
                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBook(etTitle.getText().toString(),etAuthor.getText().toString(),etCategory.getText().toString(),etSummary.getText().toString(),available,Double.parseDouble(etPrice.getText().toString()),"Alex");
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etTitle.setText("");
                etAuthor.setText("");
                etCategory.setText("");
                etSummary.setText("");
                etPrice.setText("");
            }
        });
    }


    public void addBook(String titleBook,String authorBook,String categoryBook,String descriptionBook,int availableBook,double priceBook, String ownerBook){
        InterfaceServeur serveur = RetrofitInstance.getInstance().create(InterfaceServeur.class);
        Call<Void> addBookCall = serveur.addBook(titleBook,authorBook,categoryBook,descriptionBook,availableBook,priceBook,ownerBook);


        addBookCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(getActivity(),"Le livre à bien été ajouté", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getActivity(),"Une erreur est survenue, veuillez réessayer", Toast.LENGTH_SHORT).show();
            }
        });
    }


}