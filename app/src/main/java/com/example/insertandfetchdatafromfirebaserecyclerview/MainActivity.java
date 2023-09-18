package com.example.insertandfetchdatafromfirebaserecyclerview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class MainActivity extends AppCompatActivity {

   ImageButton imageViewOne;
    EditText editTextFirst,editTextSecond;
    Button buttonUpload;

    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    FirebaseStorage mStorage;

    public static final int Gallery_Code=1;
    Uri imageUrl=null;

    ProgressDialog progressDialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        imageViewOne=findViewById(R.id.imageView);
        editTextFirst=findViewById(R.id.editTextTextPersonName);
        editTextSecond=findViewById(R.id.editTextTextPersonName2);
        buttonUpload=findViewById(R.id.button);

        mDatabase = FirebaseDatabase.getInstance();
        mRef=mDatabase.getReference().child("Student");

        mStorage=FirebaseStorage.getInstance();


        progressDialog = new ProgressDialog(this);


        imageViewOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/");
                startActivityForResult(intent,Gallery_Code);






            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==Gallery_Code && resultCode==RESULT_OK)
        {
            imageUrl=data.getData();
            imageViewOne.setImageURI(imageUrl);

        }


        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fn = editTextFirst.getText().toString().trim();
                String ln = editTextSecond.getText().toString().trim();

                if(!(fn.isEmpty() && ln.isEmpty() && imageUrl!=null))
                {

                    progressDialog.setTitle("Uploading..");
                    progressDialog.show();


                    StorageReference filepath=mStorage.getReference().child("imagePost").child(imageUrl.getLastPathSegment());
                    filepath.putFile(imageUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Task<Uri> downloadUrl=taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    String t = task.getResult().toString();

                                    DatabaseReference newPost = mRef.push();
                                    newPost.child("FirstName").setValue(fn);
                                    newPost.child("LastName").setValue(ln);
                                    newPost.child("image").setValue(task.getResult().toString());
                                    progressDialog.dismiss();


                                    Intent intent = new Intent(MainActivity.this,RetriveDataInRecyclerView.class);
                                    startActivity(intent);

                                }


                            });

                        }
                    });

                }
              else
                {
                    Toast.makeText(MainActivity.this, "Fill", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}