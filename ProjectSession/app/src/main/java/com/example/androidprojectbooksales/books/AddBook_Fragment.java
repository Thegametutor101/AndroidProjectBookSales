package com.example.androidprojectbooksales.books;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidprojectbooksales.InterfaceServeur;
import com.example.androidprojectbooksales.R;
import com.example.androidprojectbooksales.RetrofitInstance;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddBook_Fragment extends Fragment {

    Button btnBookCover;
    ImageButton btnAdd, btnClear;
    EditText etTitle, etAuthor, etCategory, etSummary, etPrice;
    RadioGroup rgAvailable;
    int available;
    ImageView imCover;
    File fichierPhoto;
    AddBookInterface addBookInterface;
    TextView tvAddBookTitle;
    RadioButton rbAvailable, rbNotAvailable;




    public AddBook_Fragment() {
        // Required empty public constructor
    }


    public interface AddBookInterface
    {
        boolean checkFieldBasic(String field, String fieldName, int maxSize, String dataType);
        int getIdUser();
        void goToProfileFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        addBookInterface = (AddBookInterface)context;
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

        etTitle=view.findViewById(R.id.etAddBookTitle);
        etAuthor=view.findViewById(R.id.etAddBookAuthor);
        etCategory=view.findViewById(R.id.etAddBookCategory);
        etSummary=view.findViewById(R.id.etAddBookSummary);
        etPrice=view.findViewById(R.id.etAddBookPrice);

        tvAddBookTitle=view.findViewById(R.id.etAddBookPrice);
        rbAvailable=view.findViewById(R.id.rbAddBookAvailbleYes);
        rbNotAvailable=view.findViewById(R.id.rbAddBookAvailbleNo);

        btnAdd=view.findViewById(R.id.btnAddBookValidate);
        btnClear=view.findViewById(R.id.btnAddBookClear);
        btnBookCover=view.findViewById(R.id.btnBookCover);

        imCover=view.findViewById(R.id.ivBookCover);
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
                if(     addBookInterface.checkFieldBasic(etTitle.getText().toString(), "titre", 250, "None") &&
                        addBookInterface.checkFieldBasic(etAuthor.getText().toString(),"auteur",202,"CaracterOnly") &&
                        addBookInterface.checkFieldBasic(etCategory.getText().toString(),"catégorie",200,"CaracterOnly") &&
                        addBookInterface.checkFieldBasic(etSummary.getText().toString(),"description",2000000000,"No") &&
                        addBookInterface.checkFieldBasic(etPrice.getText().toString(),"prix",0,("NumberOnly"))){
                    sauvegarderImage();
                    etTitle.setText("");
                    etAuthor.setText("");
                    etCategory.setText("");
                    etSummary.setText("");
                    etPrice.setText("");
                    rbAvailable.setChecked(false);
                    rbNotAvailable.setChecked(false);
                }

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
                rbAvailable.setChecked(false);
                rbNotAvailable.setChecked(false);
            }
        });

        btnBookCover.setVisibility(View.INVISIBLE);

        if (verifierPermissions()){
            lancerProgramme();
        }

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(fichierPhoto.getAbsolutePath());
            imCover.setImageBitmap(bitmap);
            //sauvegarderImage();
        }
    }

    public void lancerProgramme()
    {
        btnBookCover.setVisibility(View.VISIBLE);
        btnBookCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lancerCamera();
            }
        });
    }

    private void lancerCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // on s'assure que l'activité de la camera existe bel et bien
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // on crée le fichier devant recevoir la photo
            fichierPhoto = null;
            try {
                fichierPhoto = creationFichierPhoto();
            } catch (IOException ex) {
                // gestion d'erreur eventuelle lors de la création du fichier
            }
            // on continue si le fichier est créé correctement
            if (fichierPhoto != null) {
                Uri photoURI = FileProvider.getUriForFile( getActivity(),
                        "com.example.androidprojectbooksales.fileprovider",
                        fichierPhoto);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, 1);
            }
        }
    }

    private File creationFichierPhoto() throws IOException {
        // un nom aléatoire sera créé en utilisant la date du système
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* dossier */
        );

        // on retient le chemin du fichier ainsi créé. CurrentPhotoPath est déclaré en global
        String currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    public boolean verifierPermissions()
    {
        String[] permissions = {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};

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
        else
            lancerProgramme();
    }


    public void sauvegarderImage() {
        MediaType mediaType = MediaType.parse("image/*");
        RequestBody fichier_requete = RequestBody.create(mediaType, fichierPhoto);

        String part_mobile ="y";

        String part_title = etTitle.getText().toString();

        String part_author = etAuthor.getText().toString();

        String part_category = etCategory.getText().toString();

        String part_summary = etSummary.getText().toString();

        String part_available = String.valueOf(available);

        String part_price = etPrice.getText().toString();

        MultipartBody.Part part_fichier = MultipartBody.Part.createFormData("cover",
                fichierPhoto.getName(),
                fichier_requete);

        String part_owner = String.valueOf(addBookInterface.getIdUser());


        InterfaceServeur serveur = RetrofitInstance.getInstance().create(InterfaceServeur.class);
        Call<String> addBookCall = serveur.addBook(part_mobile,part_title,part_author,part_category,part_summary,part_available,part_price,part_fichier,part_owner);
        addBookCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(getActivity(), "Le livre à bien été ajouté", Toast.LENGTH_SHORT).show();
                addBookInterface.goToProfileFragment();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getActivity(), "Une erreur est survenue, veuillez réessayer", Toast.LENGTH_SHORT).show();
            }
        });

    }



}