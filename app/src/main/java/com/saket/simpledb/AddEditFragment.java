package com.saket.simpledb;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.saket.simpledb.Database.DatabaseHelper;
import com.saket.simpledb.Model.Students;

import org.jetbrains.annotations.NotNull;


public class AddEditFragment extends Fragment {

    private NavController navController;
    private DatabaseHelper mDBHelper;

    TextInputLayout studentNameLayout,studentAgeLayout,studentDeptLayout,studentIdLayout;
    TextInputEditText studentNameEditText,studentAgeEditText,studentDeptEditText,studentIdEditText;

    Button saveBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_edit, container, false);
    }
    String mode = "ADD";
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity mainActivity = (MainActivity) requireActivity();
        mainActivity.toolbar.setTitle("Student Details");
        mDBHelper= mainActivity.mDBHelper;
        navController = Navigation.findNavController(view);
        studentNameLayout = view.findViewById(R.id.til_student_name);
        studentNameEditText=view.findViewById(R.id.et_student_name);
        studentNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                studentNameLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        studentAgeLayout = view.findViewById(R.id.til_student_age);
        studentAgeEditText = view.findViewById(R.id.et_student_age);
        studentAgeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    studentAgeLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        studentIdLayout =view.findViewById(R.id.til_student_id);
        studentIdEditText = view.findViewById(R.id.et_student_id);
        studentIdEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    studentIdLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        studentDeptLayout=view.findViewById(R.id.til_student_dept);
        studentDeptEditText=view.findViewById(R.id.et_student_dept);
        studentDeptEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                studentDeptLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        saveBtn=view.findViewById(R.id.btn_add_student);

        Bundle arg = getArguments();
        Students student = AddEditFragmentArgs.fromBundle(arg).getUpdate();
        if(student!=null){
            mode="Update";
            setViews(student);
        }
        saveBtn.setOnClickListener(v->{
            if(validateFields()){
                if(mode.equals("ADD"))
                    addStudent();
                else {
                    updateStudent(student);
                }
                navController.navigateUp();
            }

        });

    }

    private void addStudent() {

        Students newStudent = new Students(Integer.valueOf(studentIdEditText.getText().toString()),
                studentNameEditText.getText().toString(),
                Integer.valueOf(studentAgeEditText.getText().toString()),
                studentDeptEditText.getText().toString()
                );
        mDBHelper.addStudent(newStudent);
    }
    private void updateStudent(Students student)
    {
        Students newStudent = new Students(student.getId(),
                studentNameEditText.getText().toString(),
                Integer.valueOf(studentAgeEditText.getText().toString()),
                studentDeptEditText.getText().toString()
        );
        mDBHelper.deleteStudentById(student.getId());
        mDBHelper.addStudent(newStudent);
    }

    private boolean validateFields()
    {
        String studentName = studentNameEditText.getText().toString();
        String studentNameValidate = validateName(studentName);

        String studentAge = studentAgeEditText.getText().toString();
        String studentAgeValidate = validateAge(studentAge);

        String studentDept = studentDeptEditText.getText().toString();
        String studentDeptValidate = validateDept(studentDept);

        String studentId=studentIdEditText.getText().toString();
        String studentIdValidate = validateId(studentId);

        if(studentNameValidate == null && studentAgeValidate == null && studentDeptValidate == null && studentIdValidate == null)
            return true;

        if(studentNameValidate!=null)
            studentNameLayout.setError(studentNameValidate);

        if(studentAgeValidate !=null)
            studentAgeLayout.setError(studentAgeValidate);

        if(studentDeptValidate !=null)
            studentDeptLayout.setError(studentDeptValidate);

        if(studentIdValidate !=null)
            studentIdLayout.setError(studentIdValidate);

        return false;
    }

    private String validateId(String studentId) {

        int id = Integer.valueOf(studentId);
        if (studentId.trim().isEmpty()) {
            return "Student Id cannot be empty";
        }
        if(mDBHelper.checkID(id)&& mode.equals("ADD"))
            return "Student Id exists";
        return null;
    }

    private String validateAge(String studentAge) {
        if (studentAge.trim().isEmpty()) {
            return "Student Age cannot be empty";
        }
        return null;
    }

    private String validateDept(String studentDept) {

        if (studentDept.trim().isEmpty()) {
            return "Student Department cannot be empty";
        }
        return null;
    }

    private String validateName(String studentName) {

        if (studentName.trim().isEmpty()) {
            return "Student Name cannot be empty";
        } else if (studentName.trim().length() < 10) {
            return "Student Name should be of at least 10 characters";
        } else if (studentName.trim().length() > 20) {
            return "Student Name should be of maximum 20 characters";
        }
        return null;
    }

    private void setViews(Students student) {

        studentNameEditText.setText(student.getName());
        studentAgeEditText.setText(String.valueOf(student.getAge()));
        studentIdEditText.setText(String.valueOf(student.getId()));
        studentIdLayout.setFocusable(false);
        studentIdEditText.setFocusable(false);
        studentIdEditText.setClickable(false);
        studentIdEditText.setCursorVisible(false);
        studentIdLayout.setClickable(false);
        studentDeptEditText.setText(student.getDepartment());
        saveBtn.setText("Update Student");

    }
}