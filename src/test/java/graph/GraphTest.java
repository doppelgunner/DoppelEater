package graph;

import io.datafx.controller.context.FXMLApplicationContext;
import io.datafx.controller.context.FXMLViewContext;
import io.datafx.controller.context.ViewContext;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by robertoguazon on 04/01/2017.
 */
public class GraphTest extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Flow flow = new Flow(GraphContainer.class);
        flow.startInStage(primaryStage);
    }
}
