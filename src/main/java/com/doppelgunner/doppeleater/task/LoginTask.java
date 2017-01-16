package com.doppelgunner.doppeleater.task;

import com.doppelgunner.doppeleater.database.generated.tables.records.EaterRecord;
import com.doppelgunner.doppeleater.eating.Chosen;
import com.doppelgunner.doppeleater.model.Eater;
import com.doppelgunner.doppeleater.util.DBHandler;
import com.doppelgunner.doppeleater.util.Validator;
import javafx.concurrent.Task;
import org.jooq.DSLContext;

import static com.doppelgunner.doppeleater.database.generated.Tables.EATER;

/**
 * Created by robertoguazon on 07/01/2017.
 */
public class LoginTask extends Task<String> {

    private final int max = 100;

    private String usernameOrEmail;
    private String password;

    public LoginTask(String usernameOrEmail, String password) {
        this.usernameOrEmail = usernameOrEmail;
        this.password = password;
    }

    @Override
    protected String call() throws Exception {
        updateMessage("Checking username if empty...");
        if (Validator.isEmpty(usernameOrEmail)) {
            updateProgress(100,max);
            return "Input username";
        }
        updateProgress(10,max);

        updateMessage("Checking password if empty...");
        if (Validator.isEmpty(password)) {
            updateProgress(100,max);
            return "Input password";
        }
        updateProgress(20,max);

        updateMessage("Checking if user exists and logging in...");
        String error = DBHandler.login(usernameOrEmail,password);
        if (error != null) {
            return error;
        }

        updateProgress(100,max);
        //return null if no error
        return null;
    }

}
