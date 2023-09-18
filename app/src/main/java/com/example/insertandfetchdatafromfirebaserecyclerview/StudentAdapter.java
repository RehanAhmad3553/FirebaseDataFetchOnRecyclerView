package com.example.insertandfetchdatafromfirebaserecyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {


    Context context;
    List<StudentModel> studentModelList;

    public StudentAdapter(Context context, List<StudentModel> studentModelList) {
        this.context = context;
        this.studentModelList = studentModelList;
    }

    @NonNull
    @Override
    public StudentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_row_for_recyclerview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.ViewHolder holder, int position) {


        StudentModel studentModel = studentModelList.get(position);
        holder.textViewR1.setText(studentModel.getFirstName());
        holder.textViewR2.setText(studentModel.getLastName());





        String imageUrl= studentModel.getImage();


        Picasso.get().load(imageUrl).into(holder.imageViewR);



    }

    @Override
    public int getItemCount() {
        return studentModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewR;
        TextView textViewR1,textViewR2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewR=itemView.findViewById(R.id.imageViewRecycler);
            textViewR1=itemView.findViewById(R.id.textViewRecycler);
            textViewR2=itemView.findViewById(R.id.textView2Recycler);

        }
    }
}
