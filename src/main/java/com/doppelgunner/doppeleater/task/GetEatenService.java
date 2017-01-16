package com.doppelgunner.doppeleater.task;

import com.doppelgunner.doppeleater.database.generated.tables.records.EatenRecord;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.jooq.Result;

/**
 * Created by robertoguazon on 10/01/2017.
 */
public class GetEatenService extends Service<Result<EatenRecord>> {

    private String username;
    private int number;
    private long dateSince;
    private boolean useDateComparison;

    public GetEatenService() {}

    @Override
    protected Task<Result<EatenRecord>> createTask() {
        return new GetEatenTask(username, number, dateSince, useDateComparison);
    }

    public GetEatenService(String username, int number, long dateSince, boolean useDateComparison) {
        this.username = username;
        this.number = number;
        this.dateSince = dateSince;
        this.useDateComparison = useDateComparison;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    
    public long getDateSince() {
        return dateSince;
    }

    public void setDateSince(long dateSince) {
        this.dateSince = dateSince;
    }

    public boolean isUseDateComparison() {
        return useDateComparison;
    }

    public void setUseDateComparison(boolean useDateComparison) {
        this.useDateComparison = useDateComparison;
    }
}
