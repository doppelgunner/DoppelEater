package _datafx.view.dynamic;


import io.datafx.controller.ViewController;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.context.ActionHandler;
import io.datafx.controller.flow.context.FlowActionHandler;
import io.datafx.core.concurrent.Async;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import javax.annotation.PostConstruct;

/**
 * Created by robertoguazon on 03/01/2017.
 */

@ViewController(value ="DynamicMain.fxml")
public class DynamicMainController {

    @FXML
    private RadioButton radioButton1;
    @FXML
    private RadioButton radioButton2;
    @FXML
    private RadioButton radioButton3;

    @FXML
    @ActionTrigger("navigate")
    private Button navigateButton;

    @ActionHandler
    private FlowActionHandler actionHandler;

    private ToggleGroup group;

    @PostConstruct
    public void init() throws FlowException {
        radioButton1.setUserData(Dynamic1Controller.class);
        radioButton2.setUserData(Dynamic2Controller.class);
        radioButton3.setUserData(Dynamic3Controller.class);

        group = new ToggleGroup();
        group.getToggles().addAll(radioButton1,radioButton2,radioButton3);

        radioButton1.setSelected(true);
    }

    @ActionMethod("navigate")
    public void navigate() throws Exception, FlowException {
        actionHandler.navigate((Class<?>) group.getSelectedToggle().getUserData());
    }

}
