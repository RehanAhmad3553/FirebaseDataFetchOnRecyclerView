package com.example.insertandfetchdatafromfirebaserecyclerview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class RetriveDataInRecyclerView extends AppCompatActivity {




    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    FirebaseStorage mStorage;


    RecyclerView recyclerViewOne;
    StudentAdapter studentAdapter;

    List<StudentModel> studentMList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrive_data_in_recycler_view);

        mDatabase = FirebaseDatabase.getInstance();
        mRef=mDatabase.getReference().child("Student");
        mStorage=FirebaseStorage.getInstance();

        recyclerViewOne=findViewById(R.id.recyclerView);
        recyclerViewOne.setHasFixedSize(true);

        recyclerViewOne.setLayoutManager(new LinearLayoutManager(this));


        studentMList= new ArrayList<StudentModel>();




        studentAdapter = new StudentAdapter(RetriveDataInRecyclerView.this,studentMList);

        recyclerViewOne.setAdapter(studentAdapter);



        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


                StudentModel studentModel=snapshot.getValue(StudentModel.class);

                studentMList.add(studentModel);
                studentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}