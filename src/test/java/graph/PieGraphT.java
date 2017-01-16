package graph;

import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.context.FXMLApplicationContext;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

/**
 * Created by robertoguazon on 04/01/2017.
 */
@ViewController(value="PieGraphT.fxml")
public class PieGraphT {
    @FXMLViewFlowContext
    private ViewFlowContext context;

    @ViewNode
    private PieChart pieGraph;

    @PostConstruct
    public void init() {
        System.out.println("Entered: " + this.getClass().getSimpleName());
        pieGraph.setClockwise(true);

        Label containerLabel = (Label)context.getRegisteredObject("containerLabel");
        containerLabel.setText("Pie Graph");

        ArrayList<Data> data = (ArrayList<Data>)context.getRegisteredObject("data");
        for (int i = 0; i < data.size(); i++) {
            Data<String,Double> d = (Data<String,Double>)data.get(i);
            pieGraph.getData().add(new PieChart.Data(d.x,d.y));
        }
    }
}
