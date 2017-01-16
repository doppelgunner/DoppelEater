package scroll;

import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowHandler;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by robertoguazon on 16/01/2017.
 */
public class ScrollTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Flow flow = new Flow(ScrollController.class);
        flow.startInStage(primaryStage);
    }
}
