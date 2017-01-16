package _datafx;

import _datafx.view.dynamic.DynamicMainController;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.container.DefaultFlowContainer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Created by robertoguazon on 03/01/2017.
 */
public class DynamicTest extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception {

        Flow flow = new Flow(DynamicMainController.class);
        FlowHandler flowHandler = flow.createHandler();

        StackPane pane = flowHandler.start(new DefaultFlowContainer());
        primaryStage.setScene(new Scene(pane));
        primaryStage.show();
    }
}
