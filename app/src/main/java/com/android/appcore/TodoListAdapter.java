package com.android.appcore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class TodoListAdapter extends RecyclerView.Adapter {
    private ArrayList<Todo> mTodoList;
    private Listener mListener;

    public interface Listener {
        void onItemClick(int position);
        void onItemLongClick(int position);
    }

    public TodoListAdapter(ArrayList<Todo> todoList, TodoListAdapter.Listener listener) {
        this.mTodoList = todoList;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        RecyclerView.ViewHolder viewHolder = new TodoRecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TodoRecyclerViewHolder viewHolder = (TodoRecyclerViewHolder) holder;
        viewHolder.todoName.setText(mTodoList.get(position).getTodoName());
        viewHolder.itemView.setOnClickListener(view -> {
            mListener.onItemClick(position);
        });
        viewHolder.itemView.setOnLongClickListener(view -> {
            mListener.onItemLongClick(position);
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return mTodoList.size();
    }


    private class TodoRecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView todoName;
        public TodoRecyclerViewHolder(View view) {
            super(view);
            todoName = view.findViewById(R.id.todo_name);
        }

    }
}
