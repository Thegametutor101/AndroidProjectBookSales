package com.example.androidprojectbooksales;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.androidprojectbooksales.books.AddBook_Fragment;
import com.example.androidprojectbooksales.books.Book;
import com.example.androidprojectbooksales.user.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Map_Fragment extends Fragment {

    private MapView mMapView;
    private GoogleMap googleMap;
    List<User> userWithBook;

    public Map_Fragment() {
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
        inflater.inflate(R.layout.map_fragment, container, false);

        View view = inflater.inflate(R.layout.map_fragment, null, false);
        getUserWithBook();
        mMapView = (MapView) view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                addMarker();
            }
        });
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


    public boolean verifierPermissions()
    {
        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION};

        List<String> listePermissionsADemander = new ArrayList<>();

        for(int i=0; i< permissions.length; i++)
        {
            if(ContextCompat.checkSelfPermission(getActivity(),permissions[i]) != PackageManager.PERMISSION_GRANTED)
            {
                listePermissionsADemander.add(permissions[i]);
            }
        }

        if(listePermissionsADemander.isEmpty())
            return true;
        else
        {
            ActivityCompat.requestPermissions(getActivity(), listePermissionsADemander.toArray(new String[listePermissionsADemander.size()]),1111 );

            return false;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        int nbPermissionsRefusees = 0;

        for(int i = 0; i<grantResults.length; i++ )
        {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                nbPermissionsRefusees++;
            }
        }

        if(nbPermissionsRefusees > 0)
            Toast.makeText(getActivity(), "Veuillez accepter les permissions", Toast.LENGTH_LONG).show();
        //else
            //lancerProgramme();
    }


    public void getUserWithBook(){
        InterfaceServeur serveur = RetrofitInstance.getInstance().create(InterfaceServeur.class);
        Call<List<User>> getUserWithBookCall = serveur.getUserWithBook();

        getUserWithBookCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                userWithBook=response.body();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(getActivity(),"Une erreur est survenue, veuillez réessayer", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addMarker() {
        for(int l=0; l<userWithBook.size(); l++){
            try{
                getLocationFromAddress(getContext(),userWithBook.get(l).getAdress());
                googleMap.addMarker(new MarkerOptions().position(getLocationFromAddress(getContext(),userWithBook.get(l).getAdress())).title(userWithBook.get(l).getId()).snippet("Livre disponible pour la vente"));
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    public LatLng getLocationFromAddress(Context context,String strAddress) {
        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;
        for(int l=0; l<=userWithBook.size(); l++){
        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }
        }

        return p1;
    }


}