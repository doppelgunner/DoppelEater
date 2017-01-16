package com.doppelgunner.doppeleater.task;

import com.doppelgunner.doppeleater.database.generated.tables.records.EaterRecord;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.jooq.Result;

/**
 * Created by robertoguazon on 16/01/2017.
 */
public class SearchEatersService extends Service<Result<EaterRecord>> {

    private String search;

    public SearchEatersService() {}

    public SearchEatersService(String search) {
        this.search = search;
    }

    @Override
    protected Task<Result<EaterRecord>> createTask() {
        return new SearchEatersTask(search);
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}

