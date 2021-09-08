package com.saket.simpledb.Adapter;


import android.view.ViewGroup;

import com.saket.simpledb.Model.Students;
import com.saket.simpledb.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class studentAdapter
        extends RecyclerView.Adapter<studentAdapter.StudentViewHolder> {
    private final ArrayList<Students> studentList;

    private final studentOnClickListener onClickListener;


    public studentAdapter(ArrayList<Students> studentList, studentOnClickListener onClickListener) {

        this.studentList = studentList;
        this.onClickListener = onClickListener;
    }


    @NonNull
    @NotNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent,
                                                    int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new StudentViewHolder(
                layoutInflater.inflate(R.layout.item_student, parent, false));
    }



    @Override
    public void onBindViewHolder(@NonNull @NotNull StudentViewHolder holder, int position) {

        Students student = studentList.get(position);

        holder.studentName.setText("NAME : "+student.getName());

        holder.studentAge.setText("AGE : "+String.valueOf(student.getAge()));
        holder.studentId.setText("ID : "+String.valueOf(student.getId()));

        holder.studentDept.setText("DEPT : "+student.getDepartment());

        holder.deleteBtn.setOnClickListener(v -> {
            onClickListener.deleteStudent(position);
        });
        holder.details.setOnClickListener(v -> {
            onClickListener.editStudent(position);
        });

    }


    @Override
    public int getItemCount() {

        return studentList.size();
    }


    public interface studentOnClickListener {

        void editStudent(int position);
        void deleteStudent(int position);

    }


    static class StudentViewHolder extends RecyclerView.ViewHolder {

        TextView studentName, studentAge, studentDept,studentId;
        Button deleteBtn;
        LinearLayout details;


        public StudentViewHolder(@NonNull @NotNull View itemView) {

            super(itemView);

            details= itemView.findViewById(R.id.ll_details);
            studentAge=itemView.findViewById(R.id.tv_age);
            studentName=itemView.findViewById(R.id.tv_name);
            studentDept=itemView.findViewById(R.id.tv_dept);
            studentId = itemView.findViewById(R.id.tv_id);
            deleteBtn = itemView.findViewById(R.id.btn_delete_record);


        }

    }

}