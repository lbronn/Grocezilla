package com.example.partial;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class TaskRecyclerView extends RecyclerView.Adapter<TaskRecyclerView.ViewHolder> {
    Context context;
    ArrayList<AddTasks> addTasksArrayList;
    DatabaseReference databaseReference;

    public TaskRecyclerView(Context context, ArrayList<AddTasks> addTasksArrayList) {
        this.context = context;
        this.addTasksArrayList = addTasksArrayList;
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.addingtasks, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AddTasks addTasks = addTasksArrayList.get(position);
        holder.title.setText("Task Title: " + addTasks.getTaskName());
        holder.description.setText("Task Description: " + addTasks.getTaskDesc());
        holder.duedate.setText("Task Due Date: " + addTasks.getTaskDue());
        holder.priority.setText("Priority Level: " + addTasks.getTaskPriority());
        holder.status.setText("Task Status: " + addTasks.getTaskStatus());

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewDialogUpdate viewDialogUpdate = new ViewDialogUpdate();
                viewDialogUpdate.showDialog(context, addTasks.getUserID(), addTasks.getTaskName(), addTasks.getTaskDesc(), addTasks.getTaskDue(), addTasks.getTaskPriority(), addTasks.getTaskStatus());
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewDialogDelete viewDialogDelete = new ViewDialogDelete();
                viewDialogDelete.showDialog(context, addTasks.getUserID());
            }
        });
    }

    @Override
    public int getItemCount() {
        return addTasksArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        TextView duedate;
        TextView priority;
        TextView status;
        Button delete;
        Button update;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.titleTask);
            description = itemView.findViewById(R.id.descTask);
            duedate = itemView.findViewById(R.id.dueTask);
            priority = itemView.findViewById(R.id.priorityTask);
            status = itemView.findViewById(R.id.statusTask);
            delete = itemView.findViewById(R.id.btnTaskDelete);
            update = itemView.findViewById(R.id.btnTaskUpdate);
        }
    }

    public class ViewDialogUpdate {
        public void showDialog(Context context, String id, String title, String desc, String due, String prio, String status) {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.alertdialog_addnewtask);

            EditText addTitle = dialog.findViewById(R.id.tasktitleAdd);
            EditText addDesc = dialog.findViewById(R.id.taskdescAdd);
            EditText addDue = dialog.findViewById(R.id.taskdueAdd);
            EditText addPriority = dialog.findViewById(R.id.taskprioAdd);
            EditText addStatus = dialog.findViewById(R.id.taskstatusAdd);
            Button updateTask = dialog.findViewById(R.id.buttonAdd);
            Button exitTask = dialog.findViewById(R.id.buttonExit);

            addTitle.setText(title);
            addDesc.setText(desc);
            addDue.setText(due);
            addPriority.setText(prio);
            addStatus.setText(status);
            updateTask.setText("UPDATE");

            exitTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            updateTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String newTitle = addTitle.getText().toString();
                    String newDesc = addDesc.getText().toString();
                    String newDue = addDue.getText().toString();
                    String newPrio = addPriority.getText().toString();
                    String newStatus = addStatus.getText().toString();

                    if(newTitle.isEmpty() || newDesc.isEmpty() || newDue.isEmpty() || newPrio.isEmpty() || newStatus.isEmpty()) {
                        Toast.makeText(context, "Please provide information on all fields.", Toast.LENGTH_SHORT).show();
                    } else {
                        if(newTitle.equals(title) && newDesc.equals(desc) && newDue.equals(due) && newPrio.equals(prio) && newStatus.equals(status)) {
                            Toast.makeText(context, "Nothing is updated.", Toast.LENGTH_SHORT).show();
                        } else {
                            databaseReference.child("USERS").child(id).setValue(new AddTasks(id, newTitle, newDesc, newDue, newPrio, newStatus));
                            Toast.makeText(context, "A task is updated successfully!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                }
            });

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }
    }

    public class ViewDialogDelete {
        public void showDialog(Context context, String id) {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.alertdialog_deletetask);

            Button deleteTask = dialog.findViewById(R.id.buttonYesDel);
            Button exitTask = dialog.findViewById(R.id.buttonNoDel);

            exitTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            deleteTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (id != null) {
                        databaseReference.child("USERS").child(id).removeValue();
                        Toast.makeText(context, "A task is deleted successfully!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
            });

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }
    }
}
