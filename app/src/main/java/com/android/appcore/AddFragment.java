package com.android.appcore;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddFragment extends Fragment implements View.OnClickListener{

//    private static AddFragment addFragment;
    private EditText todoInputEditText;
    private Button saveButton;
    private int position = -1;

    public AddFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(Bundle bundle) {
        AddFragment addFragment = new AddFragment();
        addFragment.setArguments(bundle);
        return addFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        todoInputEditText = view.findViewById(R.id.todo_input_edit_text);
        saveButton = view.findViewById(R.id.save_button);
        saveButton.setOnClickListener(this);
        if(getArguments()!= null && getArguments().getSerializable(Todo.class.getSimpleName()) != null) {
            todoInputEditText.setText(((Todo)getArguments().getSerializable(Todo.class.getSimpleName())).getTodoName());
            position = getArguments().getInt("position");
        } else {
            todoInputEditText.setText(null);
            position = -1;
        }
        todoInputEditText.requestFocus();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save_button:
                Bundle bundle = new Bundle();
                Todo todo = new Todo();
                todo.setTodoName(todoInputEditText.getText().toString());
                bundle.putSerializable(Todo.class.getSimpleName(), todo);
                bundle.putInt("position", position);
                ListFragment.newInstance(bundle);
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
    }
}
