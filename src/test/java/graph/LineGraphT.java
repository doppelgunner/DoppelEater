package graph;

import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

/**
 * Created by robertoguazon on 04/01/2017.
 */
@ViewController (value = "LineGraphT.fxml")
public class LineGraphT {

    @FXMLViewFlowContext
    private ViewFlowContext context;

    @ViewNode
    private LineChart<?,?> lineGraph;

    @ViewNode
    private NumberAxis yAxis;

    @ViewNode
    private CategoryAxis xAxis;

    @PostConstruct
    public void init() {
        System.out.println("Entered: " + this.getClass().getSimpleName());
        Label containerLabel = (Label)context.getRegisteredObject("containerLabel");
        containerLabel.setText("Line Graph");

        ArrayList<Data> data = (ArrayList<Data>)context.getRegisteredObject("data");
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Sexy");
        for (int i = 0; i < data.size(); i++) {
            Data d = data.get(i);
            series1.getData().add(new XYChart.Data(d.x,d.y));
        }
        lineGraph.getData().addAll(series1);
    }

}
