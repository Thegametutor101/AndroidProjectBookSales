package com.example.androidprojectbooksales;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.service.autofill.RegexValidator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.androidprojectbooksales.books.SearchBookList_Fragment;
import com.example.androidprojectbooksales.books.ViewBook_Fragment;


public class Research_Fragment extends Fragment {

    EditText etSearchValue;
    RadioGroup rgSearchCriteria, rgSearchSort;
    RadioButton rbSearchCriteria, rbSearchSort;
    ImageButton btnValidateSearch, btnClearSearch;
    SearchInterface searchInterface;

    public Research_Fragment() {
        // Required empty public constructor
    }

    public interface SearchInterface {
        void goToSearchBookListFragment(String searchValue, String searchFilter, String searchSort);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        searchInterface = (SearchInterface) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.research_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etSearchValue = view.findViewById(R.id.etSearchValue);
        rgSearchCriteria = view.findViewById(R.id.rgSearchCriteria);
        rgSearchSort = view.findViewById(R.id.rgSearchSort);
        btnValidateSearch = view.findViewById(R.id.btnValidateSearch);
        btnClearSearch = view.findViewById(R.id.btnClearSearch);
        View window = view;
        btnValidateSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filter, sort;
                rbSearchCriteria = window.findViewById(rgSearchCriteria.getCheckedRadioButtonId());
                rbSearchSort = window.findViewById(rgSearchSort.getCheckedRadioButtonId());
                if (rbSearchCriteria == null) {
                    filter = "-- Chercher par... --";
                } else {
                    filter = rbSearchCriteria.getText().toString();
                }
                if (rbSearchSort == null) {
                    sort = "-- Trier par... --";
                } else {
                    sort = rbSearchSort.getText().toString();
                }
                if (etSearchValue.getText().toString().matches ("[a-zA-Z0-9!_,' ?-]+\\.?")) {
                    searchInterface.goToSearchBookListFragment(etSearchValue.getText().toString(), filter, sort);
                } else {
                    Toast.makeText(getActivity(),"Veuillez entrer une valeur de recherche adéquate", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}