package com.doppelgunner.doppeleater.task;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Created by robertoguazon on 07/01/2017.
 */
public class LoginService extends Service<String> {

    private String username;
    private String password;

    public LoginService() {}

    public LoginService(String username, String password) {
        setUsername(username);
        setPassword(password);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    protected Task<String> createTask() {
        return new LoginTask(username, password);
    }
}
