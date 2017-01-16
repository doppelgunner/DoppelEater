package graph;

import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.context.FXMLApplicationContext;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.container.DefaultFlowContainer;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import io.datafx.controller.util.VetoException;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

/**
 * Created by robertoguazon on 04/01/2017.
 */
@ViewController(value = "GraphContainer.fxml", title="Graphs")
public class GraphContainer {

    private ViewFlowContext context;

    @ViewNode
    private Label containerLabel;
    @ViewNode
    private StackPane centerPane;
    @ViewNode
    private Button lineButton;
    @ViewNode
    private Button pieButton;
    @ViewNode
    private Button barButton;

    private FlowHandler flowHandler;
    private ArrayList<Data<String, Double>> data;

    @PostConstruct
    public void init() throws FlowException {
        data = new ArrayList<>();
        data.add(new Data("YesJulz", 9.4));
        data.add(new Data("Olivia Jensen", 10.0));
        data.add(new Data("Liza", 9.2));
        data.add(new Data("Bianca", 9.2));
        data.add(new Data("Bentong", 5.0));

        System.out.println("Entered: " + this.getClass().getSimpleName());

        context = new ViewFlowContext();
        context.register("containerLabel", containerLabel);
        context.register("data", data);

        Flow flow = new Flow(LineGraphT.class);
        setHandleFlow(flow, lineButton, LineGraphT.class);
        setHandleFlow(flow, pieButton, PieGraphT.class);
        setHandleFlow(flow, barButton, BarGraphT.class);

        flowHandler = flow.createHandler(context);

        Util.handOnMouseEnter(lineButton);
        Util.handOnMouseEnter(pieButton);
        Util.handOnMouseEnter(barButton);

        centerPane.getChildren().add(flowHandler.start(new DefaultFlowContainer()));
    }

    public void setHandleFlow(Flow flow, Node node, Class c) {
        flow.withGlobalLink(node.getId(),c);

        node.setOnMouseClicked((event) -> {
            try {
                flowHandler.handle(node.getId());
            } catch (VetoException e) {
                e.printStackTrace();
            } catch (FlowException e) {
                e.printStackTrace();
            }
        });
    }


}
