package com.doppelgunner.doppeleater.task;

import com.doppelgunner.doppeleater.database.generated.tables.records.EaterRecord;
import com.doppelgunner.doppeleater.model.Eater;
import com.doppelgunner.doppeleater.util.DBHandler;
import com.doppelgunner.doppeleater.util.Validator;
import javafx.concurrent.Task;
import org.jooq.Result;

import java.util.ArrayList;

/**
 * Created by robertoguazon on 16/01/2017.
 */
public class SearchEatersTask extends Task<Result<EaterRecord>> {

    private final int max = 100;

    private String search;

    public SearchEatersTask(String search) {
        this.search = search;
    }

    @Override
    protected Result<EaterRecord> call() throws Exception {
        updateMessage("checking text search if empty...");
        if (Validator.isEmpty(search)) {
            updateProgress(max,max);
            updateMessage("Text search is empty");
            return null;
        }
        updateProgress(10,max);

        updateMessage("finding matches...");
        Result<EaterRecord> result = DBHandler.searchEaters(search);
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
