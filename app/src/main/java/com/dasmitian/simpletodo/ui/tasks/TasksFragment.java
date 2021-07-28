package com.dasmitian.simpletodo.ui.tasks;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.dasmitian.simpletodo.R;
import com.dasmitian.simpletodo.SingleTask;
import com.dasmitian.simpletodo.TaskExpandableListAdapter;
import com.dasmitian.simpletodo.databinding.FragmentTasksBinding;

import java.util.ArrayList;

public class TasksFragment extends Fragment {

    private TasksViewModel tasksViewModel;
    private FragmentTasksBinding binding;
    private NestedScrollView lvTasks;
    private LinearLayout mainLinearLayout;

    private static class ViewHolder{
        TextView tvTaskName;
        TextView tvTaskCreated;
        TextView tvTaskDue;
        ExpandableListView expandableListView;
        ProgressBar pbTaskProgress;
        TextView tvProgressBarText;
        TextView tvProgressText;
        TextView tvProgressTextNoSubTasks;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        tasksViewModel =
                new ViewModelProvider(this).get(TasksViewModel.class);

        binding = FragmentTasksBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        lvTasks = binding.lvTasks;
        mainLinearLayout = lvTasks.findViewById(R.id.mainLinear);

        ArrayList<SingleTask> taskList = tasksViewModel.getTaskList().getValue();

        ViewHolder viewHolder;

        if (taskList != null) {
            for(int i = 0; i < taskList.size(); i++){
                LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View taskView = layoutInflater.inflate(R.layout.single_task, mainLinearLayout, false);
                viewHolder = new ViewHolder();
                viewHolder.tvTaskName = taskView.findViewById(R.id.tvTaskName);
                viewHolder.tvTaskCreated = taskView.findViewById(R.id.tvCreatedDate);
                viewHolder.tvTaskDue = taskView.findViewById(R.id.tvDueDate);
                viewHolder.pbTaskProgress = taskView.findViewById(R.id.pbTaskProgress);
                viewHolder.tvProgressBarText = taskView.findViewById(R.id.tvProgressBarText);
                viewHolder.expandableListView = taskView.findViewById(R.id.subtasks_list);
                viewHolder.tvProgressText = taskView.findViewById(R.id.tvProgressText);
                viewHolder.tvProgressTextNoSubTasks = taskView.findViewById(R.id.tvProgressTextNoSubTasks);

                int subTasksCount = taskList.get(i).getSubtaskList().size();
                if(subTasksCount > 0) {
                    int completedSubTasksCount = taskList.get(i).getCompletedSubtasksCount();
                    String progressBarText = completedSubTasksCount + "/" + subTasksCount;

                    viewHolder.pbTaskProgress.setProgress(completedSubTasksCount*100/subTasksCount);
                    viewHolder.tvProgressBarText.setText(progressBarText);
                    viewHolder.expandableListView.setAdapter(new TaskExpandableListAdapter(getContext(), taskList.get(i).getSubtaskList()));
                    if(completedSubTasksCount == subTasksCount){
                        viewHolder.tvProgressText.setText(getResources().getString(R.string.completed));
                    } else {
                        viewHolder.tvProgressText.setText(getResources().getString(R.string.inProgress));
                    }
                    viewHolder.tvProgressTextNoSubTasks.setVisibility(View.GONE);
                } else {
                    viewHolder.pbTaskProgress.setVisibility(View.GONE);
                    viewHolder.tvProgressBarText.setVisibility(View.GONE);
                    viewHolder.expandableListView.setVisibility(View.GONE);
                    viewHolder.tvProgressText.setVisibility(View.GONE);

                    viewHolder.tvProgressTextNoSubTasks.setText(getResources().getString(R.string.inProgress));
                }

                viewHolder.tvTaskName.setText(taskList.get(i).getTaskName());
                viewHolder.tvTaskCreated.setText("Created: " +  taskList.get(i).getTaskCreated());
                viewHolder.tvTaskDue.setText("Due: " + taskList.get(i).getTaskDue());

                ViewHolder finalViewHolder = viewHolder;
                taskView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        view.setBackgroundColor(Color.LTGRAY);
                        if(subTasksCount == 0) {
                            Dialog menuDialogNoSubtasks = showDialogNoSubtasks(view, finalViewHolder);
                            menuDialogNoSubtasks.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialogInterface) {
                                    view.setBackground(null);
                                }
                            });
                            menuDialogNoSubtasks.show();
                        } else {
                            Dialog menuDialog = showDialog(view, finalViewHolder);
                            menuDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialogInterface) {
                                    view.setBackground(null);
                                }
                            });
                            menuDialog.show();
                        }
                        return false;
                    }
                });

                mainLinearLayout.addView(taskView);
            }
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private Dialog showDialogNoSubtasks(View view, ViewHolder viewHolder){
        String[] menuItems = {"Mark as completed", "Mark as in progress", "Edit", "Delete"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.actions));
        builder.setItems(menuItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position) {
                view.setBackground(null);
                switch (position){
                    case 0: viewHolder.tvProgressTextNoSubTasks.setText(getResources().getString(R.string.completed));
                    break;
                    case 1: viewHolder.tvProgressTextNoSubTasks.setText(getResources().getString(R.string.inProgress));
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

    private Dialog showDialog(View view, ViewHolder viewHolder){
        String[] menuItems = {"Mark as completed", "Edit", "Delete"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.actions));
        builder.setItems(menuItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position) {
                view.setBackground(null);
                switch (position){
                    case 0: viewHolder.tvProgressText.setText(getResources().getString(R.string.completed));
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
            }
        });
        return builder.create();
    }
}