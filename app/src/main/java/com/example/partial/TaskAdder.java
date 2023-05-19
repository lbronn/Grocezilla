package com.example.partial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class TaskAdder extends AppCompatActivity {
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    ArrayList<AddTasks> addTasksArrayList;
    TaskRecyclerView adapter;
    Button addTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_adder);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        addTasksArrayList = new ArrayList<>();
        addTask = findViewById(R.id.btnTaskAdd);

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewDialogAdd viewDialogAdd = new ViewDialogAdd();
                viewDialogAdd.showDialog(TaskAdder.this);
            }
        });

        readData();
    }

    private void readData() {
        databaseReference.child("USERS").orderByChild("userName").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                addTasksArrayList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    AddTasks addTasks = dataSnapshot.getValue(AddTasks.class);
                    addTasksArrayList.add(addTasks);
                }
                adapter = new TaskRecyclerView(TaskAdder.this, addTasksArrayList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public class ViewDialogAdd {
        public void showDialog(Context context) {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.alertdialog_addnewtask);

            EditText addTitle = dialog.findViewById(R.id.tasktitleAdd);
            EditText addDesc = dialog.findViewById(R.id.taskdescAdd);
            EditText addDue = dialog.findViewById(R.id.taskdueAdd);
            EditText addPriority = dialog.findViewById(R.id.taskprioAdd);
            EditText addStatus = dialog.findViewById(R.id.taskstatusAdd);
            Button addTask = dialog.findViewById(R.id.buttonAdd);
            Button exitTask = dialog.findViewById(R.id.buttonExit);
            addTask.setText("ADD");

            exitTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            addTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id = "user" + new AddTasks().getUserID();
                    String title = addTitle.getText().toString();
                    String desc = addDesc.getText().toString();
                    String due = addDue.getText().toString();
                    String prio = addPriority.getText().toString();
                    String status = addStatus.getText().toString();

                    if(title.isEmpty() || desc.isEmpty() || due.isEmpty() || prio.isEmpty() || status.isEmpty()) {
                        Toast.makeText(context, "Please provide information on all fields.", Toast.LENGTH_SHORT).show();
                    } else {
                        String taskId = databaseReference.child("USERS").push().getKey(); // Generate a unique taskId
                        AddTasks newTask = new AddTasks(taskId, title, desc, due, prio, status);
                        databaseReference.child("USERS").child(taskId).setValue(newTask); // Set value at the new taskId
                        Toast.makeText(context, "Successfully added a new task!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
            });

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }
    }
}