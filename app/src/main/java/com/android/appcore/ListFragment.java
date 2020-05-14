package com.android.appcore;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {
    private static ListFragment listFragment;
    private RecyclerView listRecyclerView;
    private ArrayList<Todo> todoList = new ArrayList<>();
    private FloatingActionButton floatingActionButton;

    public ListFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(Bundle bundle) {
        if(listFragment == null) listFragment = new ListFragment();
        listFragment.setArguments(bundle);
        return listFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        init(view);
        return view;
    }

    private void init(View view) {

        listRecyclerView = view.findViewById(R.id.list_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listRecyclerView.setLayoutManager(linearLayoutManager);
        listRecyclerView.setAdapter(new TodoListAdapter(todoList, new TodoListAdapter.Listener() {
            @Override
            public void onItemClick(int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Todo.class.getSimpleName(), todoList.get(position));
                bundle.putInt("position", position);
                getActivity().getSupportFragmentManager()
                        .beginTransaction().replace(R.id.main_container, AddFragment.newInstance(bundle))
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();
                setArguments(null);
            }

            @Override
            public void onItemLongClick(int position) {
                Toast.makeText(getContext(), "刪除" + todoList.get(position).getTodoName() , Toast.LENGTH_SHORT).show();
                todoList.remove(position);
                listRecyclerView.getAdapter().notifyDataSetChanged();
            }
        }));


        floatingActionButton = view.findViewById(R.id.floating_action_button);

        floatingActionButton.setOnClickListener(fab -> {
            getActivity().getSupportFragmentManager()
                    .beginTransaction().replace(R.id.main_container, AddFragment.newInstance(null))
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null)
                    .commit();
            setArguments(null);

        });

        if(getArguments()!= null){
            if(listRecyclerView.getAdapter()!= null){
                Todo todo = (Todo) getArguments().getSerializable(Todo.class.getSimpleName());
                int position = getArguments().getInt("position");
                if(position == -1){
                    todoList.add(todo);
                } else {
                    todoList.remove(position);
                    todoList.add(position, todo);
                }
                listRecyclerView.getAdapter().notifyDataSetChanged();
            }
        }

    }
}
