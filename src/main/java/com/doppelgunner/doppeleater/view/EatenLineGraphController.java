package com.doppelgunner.doppeleater.view;

import com.doppelgunner.doppeleater.database.generated.tables.records.EatenRecord;
import com.doppelgunner.doppeleater.eating.Chosen;
import com.doppelgunner.doppeleater.model.Eaten;
import com.doppelgunner.doppeleater.task.GetEatenService;
import com.doppelgunner.doppeleater.util.Creator;
import com.doppelgunner.doppeleater.util.DateHandler;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.context.FXMLViewContext;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import org.jooq.Result;

import javax.annotation.PostConstruct;

/**
 * Created by robertoguazon on 12/01/2017.
 */

@ViewController("eatenLineGraph.fxml")
public class EatenLineGraphController {

    @ViewNode
    private LineChart lineChart;
    @ViewNode
    private CategoryAxis categoryAxis;
    @ViewNode
    private NumberAxis numberAxis;

    @FXMLViewFlowContext
    private ViewFlowContext viewFlowContext;

    private XYChart.Series series;
    private GetEatenService getEatenService = new GetEatenService();

    private DateHandler.DateType dateTypeSince;
    private DateHandler.DateType dateTypeAxis;

    @PostConstruct
    public void init() {
        dateTypeAxis = (DateHandler.DateType)viewFlowContext.getRegisteredObject("dateTypeAxis");
        dateTypeSince = (DateHandler.DateType)viewFlowContext.getRegisteredObject("dateTypeSince");
        gatherData();
    }

    private void gatherData() {
        if(getEatenService.isRunning()) return;
        getEatenService.reset();

        //TODO separate on instance creation?
        getEatenService.setUsername(Chosen.getEater().getUsername());
        getEatenService.setNumber(100);
        getEatenService.setUseDateComparison(true);

        getEatenService.setDateSince(DateHandler.getDateAgo(dateTypeSince.getValue()));
        getEatenService.setOnSucceeded(t -> {
            Result<EatenRecord> result = (Result<EatenRecord>) t.getSource().getValue();
            loadLineChart(result);
        });
        getEatenService.start();

    }

    private void loadLineChart(Result<EatenRecord> result) {
        categoryAxis.setAutoRanging(false);
        for (int i = 0; i < Eaten.MEAL_TYPES.length; i++) {
            categoryAxis.getCategories().add(Eaten.MEAL_TYPES[i].toString());
        }

        Creator.setGraphAxisUpperBound(numberAxis, dateTypeAxis);

        series = new XYChart.Series<>();
        series.setName("Eaten");

        if (result == null)  return;
        for (EatenRecord eatenRecord : result) {
            Eaten eaten = Eaten.createEaten(eatenRecord);
            series.getData().add(new XYChart.Data(
                    DateHandler.convertLongDateToAgo(eaten.getDateEaten().getTime(), dateTypeAxis.getValue()),
                    eaten.getMealType().toString()));

        }
        lineChart.getData().add(series);
        numberAxis.setLabel("Ago (" + dateTypeAxis.toString().toLowerCase() + "s)");
        lineChart.setTitle("Total eaten: " + result.size());
    }
}
