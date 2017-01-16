package _datafx.view.slides;

import io.datafx.controller.ViewController;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.container.AnimatedFlowContainer;
import io.datafx.controller.flow.container.ContainerAnimations;
import io.datafx.controller.util.VetoException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import javax.annotation.PostConstruct;

/**
 * Created by robertoguazon on 02/01/2017.
 */

@ViewController(value="Master.fxml", title="Sample Master app")
public class MasterController {

    @FXML
    @ActionTrigger("back")
    private Button backButton;

    @FXML
    @ActionTrigger("finish")
    private Button finishButton;

    @FXML
    @ActionTrigger("next")
    private Button nextButton;

    @FXML
    @ActionTrigger("help")
    private Hyperlink help;

    @FXML
    private StackPane centerPane;

    private FlowHandler flowHandler;

    @PostConstruct
    public void init() throws FlowException {
        Flow innerFlow = new Flow(OneController.class).
                withLink(OneController.class, "next", TwoController.class).
                withLink(TwoController.class, "next", LastController.class).
                withGlobalTaskAction("help", ()-> {
                    System.out.println("Whisper: HELP!");
                });

        flowHandler = innerFlow.createHandler();
        centerPane.getChildren().add(flowHandler.start(new AnimatedFlowContainer(Duration.millis(320), ContainerAnimations.ZOOM_IN)));
        backButton.setDisable(true);
    }

    @ActionMethod("back")
    public void onBack() throws VetoException, FlowException {
        flowHandler.navigateBack();
        if (flowHandler.getCurrentViewControllerClass().equals(OneController.class)) {
            backButton.setDisable(true);
        } else {
            backButton.setDisable(false);
        }
        finishButton.setDisable(false);
        nextButton.setDisable(false);
    }

    @ActionMethod("next")
    public void onNext() throws VetoException, FlowException {
        flowHandler.handle("next");
        if(flowHandler.getCurrentViewControllerClass().equals(LastController.class)) {
            nextButton.setDisable(true);
            finishButton.setDisable(true);
        } else {
            nextButton.setDisable(false);
        }
        backButton.setDisable(false);
    }

    @ActionMethod("finish")
    public void onFinish() throws VetoException, FlowException {
        flowHandler.navigateTo(LastController.class);
        finishButton.setDisable(true);
        nextButton.setDisable(true);
        backButton.setDisable(false);
    }

    @ActionMethod("help")
    public void onHelp() throws VetoException, FlowException {
        flowHandler.handle("help");
    }
}
