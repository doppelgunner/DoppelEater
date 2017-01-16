package card;

import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import popup.PopupMainController;

/**
 * Created by robertoguazon on 06/01/2017.
 */
public class CardTest extends Application {

    @FXMLViewFlowContext
    private ViewFlowContext context;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        context = new ViewFlowContext();
        context.register("stage", primaryStage);

        Flow flow = new Flow(CardTestController.class);
        FlowHandler flowHandler = flow.createHandler(context);

        Scene scene = new Scene(flowHandler.start());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
