package com.doppelgunner.doppeleater.view;

import com.doppelgunner.doppeleater.database.generated.tables.records.EatenRecord;
import com.doppelgunner.doppeleater.model.Eaten;
import com.doppelgunner.doppeleater.util.DateHandler;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import org.jooq.Result;

import javax.annotation.PostConstruct;

/**
 * Created by robertoguazon on 12/01/2017.
 */
@ViewController("EatenBarGraph.fxml")
public class EatenBarGraphController {

    @ViewNode
    private BarChart barChart;
    @ViewNode
    private NumberAxis agoAxis;
    @ViewNode
    private NumberAxis eatenCountAxis;

    @FXMLViewFlowContext
    private ViewFlowContext viewFlowContext;
    private XYChart.Series series;

    @PostConstruct
    public void init() {

        loadBarChart();
    }

    private void loadBarChart() {

    }
}
