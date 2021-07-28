package com.dasmitian.simpletodo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.dasmitian.simpletodo.ui.tasks.TasksFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskExpandableListAdapter extends BaseExpandableListAdapter {

    private String listTitle = "Subtasks";
    private Context context;
    private List<SingleSubTask> subtaskList;
    private TasksList tasksList;

    public TaskExpandableListAdapter(Context context, List<SingleSubTask> subtaskList){
        this.context = context;
        this.subtaskList = subtaskList;
        tasksList = TasksList.getInstance();
    }

    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return subtaskList.size();
    }

    @Override
    public Object getGroup(int i) {
        return "title";
    }

    @Override
    public SingleSubTask getChild(int listPosition, int expandedListPosition) {
        return subtaskList.get(expandedListPosition);
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.list_group, null);
        TextView title = view.findViewById(R.id.listTitle);
        title.setText(listTitle);
        title.setPadding(100, 0, 0, 0);

        return view;
    }

    @Override
    public View getChildView(int listPosition, int expandedListPosition, boolean isLastChild, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.single_subtask, null);
        TextView title = view.findViewById(R.id.tvSubTaskName);
        TextView created = view.findViewById(R.id.tvSubtaskCreatedDate);
        TextView due = view.findViewById(R.id.tvSubtaskDueDate);
        TextView progress = view.findViewById(R.id.tvSubtaskProgress);

        title.setText(getChild(listPosition, expandedListPosition).getSubTaskName());
        created.setText("Created: " + getChild(listPosition, expandedListPosition).getSubTaskCreated());
        due.setText("Due: " + getChild(listPosition, expandedListPosition).getSubTaskDue());
        progress.setText(getChild(listPosition, expandedListPosition).getSubTaskStatus());

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                view.setBackgroundColor(Color.LTGRAY);
                    Dialog menuDialogNoSubtasks = showDialogSubtasks(view, progress, getChild(listPosition, expandedListPosition));
                    menuDialogNoSubtasks.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialogInterface) {
                            view.setBackground(null);
                        }
                    });
                    menuDialogNoSubtasks.show();
                return false;
            }
        });

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    private Dialog showDialogSubtasks(View view, TextView progress, SingleSubTask child){
        String[] menuItems = {"Mark as completed", "Mark as in progress", "Edit", "Delete"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.actions));
        builder.setItems(menuItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position) {
                view.setBackground(null);
                switch (position){
                    case 0: progress.setText(context.getResources().getString(R.string.completed));
                            child.setSubTaskStatus(context.getResources().getString(R.string.completed));
                            try {
                                tasksList.writeToJson(context.getApplicationInfo().dataDir + "/tasks.json");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            notifyDataSetChanged();

                        break;
                    case 1: progress.setText(context.getResources().getString(R.string.inProgress));
                            child.setSubTaskStatus(context.getResources().getString(R.string.inProgress));
                            try {
                                tasksList.writeToJson(context.getApplicationInfo().dataDir + "/tasks.json");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            notifyDataSetChanged();
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                }
            }
        });
        return builder.create();
    }
}
