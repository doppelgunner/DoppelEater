package popup;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.flow.container.DefaultFlowContainer;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javax.annotation.PostConstruct;

/**
 * Created by robertoguazon on 06/01/2017.
 */
@ViewController(value="Popup.fxml")
public class PopupController {

    @ViewNode
    private JFXPopup popup;
    @ViewNode
    private JFXButton button;

    @FXMLViewFlowContext
    private ViewFlowContext context;

    @PostConstruct
    public void init() {
        System.out.println(button);
        System.out.println(popup);
        popup.setSource(button);
        VBox vBox = new VBox();
        vBox.getChildren().addAll(new JFXButton("Another"),new JFXButton("Another"),new JFXButton("Another"));
        //popup.setPopupContainer(new StackPane());
        popup.setContent(vBox);
        button.setOnMouseClicked((e) -> popup.show(JFXPopup.PopupVPosition.BOTTOM, JFXPopup.PopupHPosition.RIGHT,10,10));
    }

}
