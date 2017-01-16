package com.doppelgunner.doppeleater.task;

import com.doppelgunner.doppeleater.database.generated.tables.records.EatenRecord;
import com.doppelgunner.doppeleater.util.DBHandler;
import com.doppelgunner.doppeleater.util.Validator;
import javafx.concurrent.Task;
import org.jooq.Result;

/**
 * Created by robertoguazon on 16/01/2017.
 */
public class SearchEatensTask extends Task<Result<EatenRecord>> {

    private final int max = 100;
    private String username;
    private String search;

    public SearchEatensTask(String username, String search) {
        this.username = username;
        this.search = search;
    }

    @Override
    protected Result<EatenRecord> call() throws Exception {
        updateMessage("checking text search if empty...");
        if (Validator.isEmpty(search)) {
            updateProgress(max,max);
            updateMessage("Text search is empty");
            return null;
        }
        updateProgress(10,max);

        updateMessage("checking text username if empty...");
        if (Validator.isEmpty(username)) {
            updateProgress(max,max);
            updateMessage("Username is empty");
            return null;
        }
        updateProgress(20,max);

        updateMessage("finding matches...");
        Result<EatenRecord> result = DBHandler.searchEatens(username,search);
        if (Validator.isResultEmpty(result)) {
            updateProgress(max,max);
            updateMessage("No matches found");
            return null;
        }
        updateProgress(100,max);
        updateMessage("Task complete returning matches...");
        return result;
    }
}
