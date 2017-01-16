package com.doppelgunner.doppeleater.task;

import com.doppelgunner.doppeleater.database.generated.tables.records.EatenRecord;
import com.doppelgunner.doppeleater.database.generated.tables.records.EaterRecord;
import com.doppelgunner.doppeleater.eating.Chosen;
import com.doppelgunner.doppeleater.model.Eater;
import com.doppelgunner.doppeleater.util.DBHandler;
import com.doppelgunner.doppeleater.util.Validator;
import javafx.concurrent.Task;
import org.jooq.DSLContext;
import org.jooq.Result;

import static com.doppelgunner.doppeleater.database.generated.Tables.EATEN;

/**
 * Created by robertoguazon on 07/01/2017.
 */
public class GetEatenTask extends Task<Result<EatenRecord>> {

    private final int max = 100;

    private String username;
    private int number;
    private long dateSince;
    private boolean useDateComparison;

    public GetEatenTask(String username, int number, long dateSince, boolean useDateComparison) {
        this.username = username;
        this.number = number;
        this.dateSince = dateSince;
        this.useDateComparison = useDateComparison;
    }

    @Override
    protected Result<EatenRecord> call() throws Exception {
        updateMessage("Checking username if empty...");
        if (Validator.isEmpty(username)) {
            updateProgress(100,max);
            updateMessage("Error: must have username");
            return null;
        }
        updateProgress(10,max);

        updateMessage("Checking if number is valid...");
        if (number <= 0) {
            updateProgress(100,max);
            updateMessage("Error: number must be greater than 0");
            return null;
        }
        updateProgress(20,max);

        Result<EatenRecord> result = null;

        updateMessage("Gathering data of eaten foods...");
        if (useDateComparison) {
            result = DBHandler.getEatenMany(username,dateSince);
        } else {
            result = DBHandler.getEatenMany(username, number);
        }
        updateProgress(80,max);

        if (result == null || result.size() <= 0) {
            updateProgress(100,max);
            updateMessage("No eaten record found");
            return null;
        }


        updateProgress(100,max);
        updateMessage("Task complete returning eaten list");
        //return null if no error
        return result;
    }

}
