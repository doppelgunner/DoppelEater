package com.doppelgunner.doppeleater.task;

import com.doppelgunner.doppeleater.model.Eater;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Created by robertoguazon on 07/01/2017.
 */
public class SignupService extends Service<String> {

    private Eater eater;
    private String retypedPassword;

    public SignupService() {}

    public SignupService(Eater eater, String retypedPassword) {
        this.eater = eater;
        this.retypedPassword = retypedPassword;
    }

    public Eater getEater() {
        return eater;
    }

    public void setEater(Eater eater) {
        this.eater = eater;
    }

    public String getRetypedPassword() {
        return retypedPassword;
    }

    public void setRetypedPassword(String retypedPassword) {
        this.retypedPassword = retypedPassword;
    }

    @Override
    protected Task<String> createTask() {
        return new SignupTask(eater,retypedPassword);
    }
}
