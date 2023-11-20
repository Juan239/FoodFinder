package com.example.foodfinder_02;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodfinder_02.clases.dataClass;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class infoLocalActivity extends AppCompatActivity {
    private Button btnGuardarInfo;
    private ImageView imagen;
    private EditText ETnombre, ETdescripcion;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private StorageReference storageReference;
    private FirebaseStorage storage;
    private ActivityResultLauncher<Intent> galleryLauncher;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_local);

        // Obtén la instancia de FirebaseAuth y usuario actual
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();



        btnGuardarInfo = findViewById(R.id.btnGuardarInfoLocal);
        ETnombre = findViewById(R.id.ETnombreLocal);
        ETdescripcion = findViewById(R.id.ETdescripcionLocal);
        imagen = findViewById(R.id.logoLocal);

        // Inicializar Firebase storage
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        //Inicializar Firebase database
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("locales");

        // Inicializar ActivityResultLauncher
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        handleImageSelection(result.getData().getData());
                    }
                });

        //Verificar que el usuario tenga la sesion iniciada
        if (currentUser != null) {
            checkIfImageExists(currentUser.getUid());
            //Abrir galeria al presionar el imageView
            imagen.setOnClickListener(view -> openGallery());

            //Guardar la informacion
            btnGuardarInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String usuario = currentUser.getUid();
                    subirImagenFirebase();

                    String nombreLocal = ETnombre.getText().toString();
                    String descripcion = ETdescripcion.getText().toString();

                    dataClass dataClass = new dataClass(nombreLocal, descripcion);
                    try {
                        reference.child(usuario).setValue(dataClass);

                        Toast.makeText(infoLocalActivity.this, "Se guardaron los datos correctamente", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(infoLocalActivity.this, "No se pudieron guardar los cambios", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        } else {
            startActivity(new Intent(infoLocalActivity.this, MenuPrincipal.class));
        }
    }

                                            //Metodos extras

    //Abrir galeria
    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryLauncher.launch(galleryIntent);
    }

    //Seleccionar imagen de la galeria
    private void handleImageSelection(Uri selectedImageUri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
            imagen.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Subir la imagen a firebase
    private void subirImagenFirebase() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String idCorreo = currentUser.getUid();
        // Obtener la imagen del ImageView

        BitmapDrawable drawable = (BitmapDrawable) imagen.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        // Comprimir la imagen en un formato adecuado
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        // Crear una referencia en Firebase Storage
        StorageReference imagesRef = storageReference.child("images").child(idCorreo);

        // Subir la imagen a Firebase Storage
        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // La imagen se ha subido exitosamente a Firebase Storage
                // Puedes obtener la URL de la imagen utilizando taskSnapshot.getDownloadUrl()
                // Aquí puedes agregar más lógica según tus necesidades

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Manejar errores durante la carga
                exception.printStackTrace();
            }
        });
    }

    //Poner la imagen del local si es que ya seleccionaron alguna antes
    private void checkIfImageExists(String imageName) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child("images").child(imageName);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("locales").child(imageName);

        // Verifica la existencia del archivo
        storageRef.getMetadata().addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                final long ONE_MEGABYTE = 1024 * 1024;
                storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {

                                    //Agregar imagen al imageView
                        // Convierte los bytes en un Bitmap (o en el formato que necesites)
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                        // Guarda el Bitmap en una variable global o según tus necesidades
                        // Puedes mostrar el Bitmap en una ImageView o realizar otras operaciones
                        // dependiendo de tus requerimientos.
                        // Por ejemplo, si necesitas mostrar la imagen en un ImageView:
                        ImageView imageView = findViewById(R.id.logoLocal);
                        imageView.setImageBitmap(bitmap);

                                    //Agregar los datos a los editText
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String nombreLocal = snapshot.child("nombreLocal").getValue(String.class);
                                String descripcionLocal = snapshot.child("descripcionLocal").getValue(String.class);
                                ETnombre.setText(nombreLocal);
                                ETdescripcion.setText(descripcionLocal);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });



                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Maneja la falla según tus necesidades
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // El archivo no existe
                // Puedes manejar esta situación según tus necesidades
            }
        });
    }



}