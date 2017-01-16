package com.doppelgunner.doppeleater.task;

import com.doppelgunner.doppeleater.database.generated.tables.records.EatenRecord;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.jooq.Result;

/**
 * Created by robertoguazon on 16/01/2017.
 */
public class SearchEatensService extends Service<Result<EatenRecord>> {

    private String username;
    private String search;

    public SearchEatensService() {}

    public SearchEatensService(String username, String search) {
        this.username = username;
        this.search = search;
    }

    @Override
    protected Task<Result<EatenRecord>> createTask() {
        return new SearchEatensTask(username, search);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
