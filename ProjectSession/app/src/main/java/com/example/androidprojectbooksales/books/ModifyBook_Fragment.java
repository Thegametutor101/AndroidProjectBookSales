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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.androidprojectbooksales.AdapterItemBook;
import com.example.androidprojectbooksales.InterfaceServeur;
import com.example.androidprojectbooksales.R;
import com.example.androidprojectbooksales.RetrofitInstance;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ModifyBook_Fragment extends Fragment {

    EditText etTitre, etAuteur, etCategory, etDescription, etPrix;
    Button btnValider, btnEffacer, btnSupprimer, btnBookCover;
    ImageView imCover;
    RadioButton rbAvailable, rbNotAvailable;
    ModifyBookInterface modifyBook;
    File fichierPhoto;
    RadioGroup rgAvailable;
    int available;
    String bookID;


    public ModifyBook_Fragment() {
        // Required empty public constructor
    }

    public ModifyBook_Fragment(String id) {
        getBook(id);
    }

    public interface ModifyBookInterface {
        boolean checkFieldBasic(String field, String fieldName, double maxSize, String dataType);

        int getIdUser();

        void goToProfileFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        modifyBook = (ModifyBookInterface) context;
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
        return inflater.inflate(R.layout.modify_book_fragment, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etTitre = view.findViewById(R.id.etModifyBookTitle);
        etAuteur = view.findViewById(R.id.etModifyBookAuthor);
        etCategory = view.findViewById(R.id.etModifyBookCategory);
        etDescription = view.findViewById(R.id.etModifyBookSummary);
        etPrix = view.findViewById(R.id.etModifyBookPrix);
        btnValider = view.findViewById(R.id.btnModifyBookValidate);
        btnEffacer = view.findViewById(R.id.btnModifyBookClear);
        btnSupprimer = view.findViewById(R.id.btnDeleteMyBook);
        btnBookCover = view.findViewById(R.id.btnChangeBookCover);
        imCover = view.findViewById(R.id.imModifyBook);
        rbAvailable = view.findViewById(R.id.rbModifyBookAvailableYes);
        rbNotAvailable = view.findViewById(R.id.rbModifyBookAvailableNo);
        rgAvailable = view.findViewById(R.id.rgModifyBookAvailable);

        rgAvailable.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbAddBookAvailbleYes:
                        available = 1;
                        break;
                    case R.id.rbAddBookAvailbleNo:
                        available = 0;
                        break;
                }
            }
        });

        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (modifyBook.checkFieldBasic(etTitre.getText().toString(), "titre", 250, "None") &&
                        modifyBook.checkFieldBasic(etAuteur.getText().toString(), "auteur", 202, "CaracterOnly") &&
                        modifyBook.checkFieldBasic(etCategory.getText().toString(), "catégorie", 200, "CaracterOnly") &&
                        modifyBook.checkFieldBasic(etDescription.getText().toString(), "description", 2000000000, "No") &&
                        modifyBook.checkFieldBasic(etPrix.getText().toString(), "prix", 0, ("NumberOnly"))) {
                    sauvegarderImage();
                    etTitre.setText("");
                    etAuteur.setText("");
                    etCategory.setText("");
                    etDescription.setText("");
                    etPrix.setText("");
                    rbAvailable.setChecked(false);
                    rbNotAvailable.setChecked(false);
                }

            }
        });

        btnEffacer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etTitre.setText("");
                etAuteur.setText("");
                etCategory.setText("");
                etDescription.setText("");
                etPrix.setText("");
                rbAvailable.setChecked(false);
                rbNotAvailable.setChecked(false);
            }
        });

        btnBookCover.setVisibility(View.INVISIBLE);

        if (verifierPermissions()) {
            lancerProgramme();
        }

        btnSupprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteBook();
            }
        });

    }


    public void getBook(String id) {
        InterfaceServeur server = RetrofitInstance.getInstance().create(InterfaceServeur.class);
        Call<Book> getBook = server.getBook("y", id);

        getBook.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                Book book = response.body();
                Picasso.get().load("http://206.167.140.56:8080/A2020/420505RI/Equipe_6/AppBundle/ressources/bookPictures/"
                        + book.getId() + ".png").resize(110, 150).into(imCover);
                etTitre.setText(book.getTitle());
                etAuteur.setText(book.getAuthor());
                etCategory.setText(book.getCategory());
                etDescription.setText(book.getDescription());
                etPrix.setText(Double.toString(book.getPrice()));
                if (book.available == 1) {
                    available=1;
                    rbAvailable.setChecked(true);
                } else {
                    available=0;
                    rbNotAvailable.setChecked(true);
                }

                bookID=book.getId();
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Toast.makeText(getActivity(), "Erreur au chargement du livre", Toast.LENGTH_SHORT).show();
            }
        });
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

    public void lancerProgramme() {
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
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
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


    public boolean verifierPermissions() {
        String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        List<String> listePermissionsADemander = new ArrayList<>();

        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(getActivity(), permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                listePermissionsADemander.add(permissions[i]);
            }
        }

        if (listePermissionsADemander.isEmpty())
            return true;
        else {
            ActivityCompat.requestPermissions(getActivity(), listePermissionsADemander.toArray(new String[listePermissionsADemander.size()]), 1111);

            return false;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        int nbPermissionsRefusees = 0;

        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                nbPermissionsRefusees++;
            }
        }

        if (nbPermissionsRefusees > 0)
            Toast.makeText(getActivity(), "Veuillez accepter les permissions", Toast.LENGTH_LONG).show();
        else
            lancerProgramme();
    }


    public void sauvegarderImage() {
        if (fichierPhoto == null) {
            String part_mobile = "y";

            String part_id=bookID;

            String part_title = etTitre.getText().toString();

            String part_author = etAuteur.getText().toString();

            String part_category = etCategory.getText().toString();

            String part_summary = etDescription.getText().toString();

            String part_available = String.valueOf(available);

            String part_price = etPrix.getText().toString();

            String part_owner = String.valueOf(modifyBook.getIdUser());



            InterfaceServeur serveur = RetrofitInstance.getInstance().create(InterfaceServeur.class);
            Call<String> updateBookWithoutCoverCall = serveur.updateBookWithoutCover(part_mobile,part_id, part_title, part_author, part_category, part_summary, part_available, part_price, part_owner);
            updateBookWithoutCoverCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Toast.makeText(getActivity(), "Le livre à bien été ajouté", Toast.LENGTH_SHORT).show();
                    modifyBook.goToProfileFragment();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(getActivity(), "Une erreur est survenue, veuillez réessayer", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            MediaType mediaType = MediaType.parse("image/*");
            RequestBody fichier_requete = RequestBody.create(mediaType, fichierPhoto);

            String part_mobile = "y";

            String part_id=bookID;

            String part_title = etTitre.getText().toString();

            String part_author = etAuteur.getText().toString();

            String part_category = etCategory.getText().toString();

            String part_summary = etDescription.getText().toString();

            String part_available = String.valueOf(available);

            String part_price = etPrix.getText().toString();

            MultipartBody.Part part_fichier = MultipartBody.Part.createFormData("cover",
                    fichierPhoto.getName(),
                    fichier_requete);

            String part_owner = String.valueOf(modifyBook.getIdUser());

            InterfaceServeur serveur = RetrofitInstance.getInstance().create(InterfaceServeur.class);
            Call<String> updateBookCall = serveur.updateBook(part_mobile,part_id, part_title, part_author, part_category, part_summary, part_available, part_price, part_fichier, part_owner);
            updateBookCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Toast.makeText(getActivity(), "Le livre à bien été ajouté", Toast.LENGTH_SHORT).show();
                    modifyBook.goToProfileFragment();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(getActivity(), "Une erreur est survenue, veuillez réessayer", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    public void deleteBook(){
        InterfaceServeur server = RetrofitInstance.getInstance().create(InterfaceServeur.class);
        Call<Void> deleteBook = server.deleteBook(bookID);

        deleteBook.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                modifyBook.goToProfileFragment();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getActivity(),"Erreur lors de la suppression", Toast.LENGTH_SHORT).show();
            }
        });
    }
}