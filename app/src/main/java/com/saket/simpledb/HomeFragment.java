package com.saket.simpledb;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.saket.simpledb.Adapter.studentAdapter;
import com.saket.simpledb.Database.DatabaseHelper;
import com.saket.simpledb.Model.Students;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private NavController navController;
    private DatabaseHelper mDBHelper;
    private FloatingActionButton addFab;
    private ArrayList<Students> mStudentList;
    studentAdapter studentAdapter;
    RecyclerView recyclerView ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity mainActivity = (MainActivity) requireActivity();
       mainActivity.toolbar.setTitle("Home");
        recyclerView= view.findViewById(R.id.rv_view_student);
        mDBHelper= mainActivity.mDBHelper;
        addFab=view.findViewById(R.id.fab);

        navController = Navigation.findNavController(view);
        mStudentList = mDBHelper.getListStudent();
        Log.i("Home Fragment",mStudentList.toString());
        setUpRecyclerView();
        addFab.setOnClickListener(v->{
            navController.navigate(R.id.action_homeFragment_to_addEditFragment);
        });
    }
    private void setUpRecyclerView() {

        studentAdapter = new studentAdapter(mStudentList, new studentAdapter.studentOnClickListener() {
            @Override
            public void editStudent(int position) {
                editStudentDetails(position);
            }

            @Override
            public void deleteStudent(int position) {
                deleteStudentDetails(position);
            }
        });

        recyclerView.addItemDecoration(
                new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(studentAdapter);
    }

    private void deleteStudentDetails(int i) {
        Students student = mStudentList.get(i);
        mStudentList.remove(i);
        studentAdapter.notifyItemRemoved(i);
        studentAdapter.notifyItemRangeChanged(i,mStudentList.size());
       // studentAdapter.notifyDataSetChanged();
        mDBHelper.deleteStudentById(student.getId());
        Log.i("Home Fragment Delete",mStudentList.toString());

    }

    private void editStudentDetails(int i) {
        Students student = mStudentList.get(i);
        navController.navigate(HomeFragmentDirections.actionHomeFragmentToAddEditFragment().setUpdate(student));
        //navController.navigate(SearchFragmentDirections.actionBook(train));
    }
}