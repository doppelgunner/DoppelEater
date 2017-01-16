package popup;

import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.annotation.PostConstruct;

/**
 * Created by robertoguazon on 07/01/2017.
 */
@ViewController(value="PopupMain.fxml")
public class PopupMainController {

    @FXMLViewFlowContext
    private ViewFlowContext context;

    @ViewNode
    private Button addButton;

    @PostConstruct
    public void init() {
        Flow child = new Flow(PopupController.class);
        FlowHandler flowHandler = child.createHandler(context);
    }

    @FXML
    private void add() {
        Stage stage = (Stage) context.getRegisteredObject("stage");
        Stage childStage = new Stage();
        childStage.setWidth(200);
        childStage.setHeight(200);
        childStage.setX(stage.getX() + stage.getWidth() / 2 - childStage.getWidth() / 2);
        childStage.setY(stage.getY() + stage.getHeight() / 2 - childStage.getHeight() / 2);
        childStage.setResizable(false);
        childStage.initModality(Modality.WINDOW_MODAL);
        childStage.initOwner(stage);
        childStage.show();
    }
}
