package com.doppelgunner.doppeleater.task;

import com.doppelgunner.doppeleater.database.generated.tables.records.EaterRecord;
import com.doppelgunner.doppeleater.model.Eater;
import com.doppelgunner.doppeleater.util.DBHandler;
import com.doppelgunner.doppeleater.util.Validator;
import javafx.concurrent.Task;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;

import static com.doppelgunner.doppeleater.database.generated.Tables.EATER;

/**
 * Created by robertoguazon on 07/01/2017.
 */
public class SignupTask extends Task<String> {

    private final int max = 100;
    private Eater eater;
    private String retypedPassword;

    public SignupTask(Eater eater, String retypedPassword) {
        this.eater = eater;
        this.retypedPassword = retypedPassword;
    }

    @Override
    protected String call() throws Exception {
        updateMessage("Checking username if empty...");
        if (Validator.isEmpty(eater.getUsername())) {
            updateProgress(max,max);
            return "Fill out username";
        }
        updateProgress(7,max);

        updateMessage("Checking email if empty...");
        if (Validator.isEmpty(eater.getEmail())) {
            updateProgress(max,max);
            return "Fill out email";
        }
        updateProgress(14,max);

        updateMessage("Checking password if empty...");
        if (Validator.isEmpty(eater.getPassword())) {
            updateProgress(max,max);
            return "Fill out password";
        }
        updateProgress(21,max);

        updateMessage("Checking retyped password if empty...");
        if (Validator.isEmpty(retypedPassword)) {
            updateProgress(max,max);
            return "Retype password";
        }
        updateProgress(28,max);

        updateMessage("Checking if password and retyped password match...");
        if (!retypedPassword.equals(eater.getPassword())) {
            updateProgress(max,max);
            return "Password and retyped password does not match";
        }
        updateProgress(35,max);

        updateMessage("Checking if account already exists...");
        String error = DBHandler.signupCheck(eater);
        if (error != null) {
            updateProgress(max,max);
            return error;
        }
        updateProgress(max,max);
        //returns null if no error
        return null;
    }
}
