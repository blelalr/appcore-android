package com.android.appcore;

import java.io.Serializable;

public class Todo implements Serializable {

    private String todoName;

    public String getTodoName() {
        return todoName;
    }

    public void setTodoName(String todoName) {
        this.todoName = todoName;
    }
}
