package scroll;

import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import javax.annotation.PostConstruct;

/**
 * Created by robertoguazon on 16/01/2017.
 */
@ViewController("Scroll.fxml")
public class ScrollController {

    @ViewNode
    private Button addButton;
    @ViewNode
    private ScrollPane scrollPane;

    private FlowPane boxFlowPane;

    @PostConstruct
    public void init() {
        boxFlowPane = new FlowPane();
        scrollPane.setContent(boxFlowPane);
        System.out.println(addButton);
        System.out.println(scrollPane);
        System.out.println(boxFlowPane);
    }

    @FXML
    private void add() {
        for (int i = 0; i < 100; i++) {
            VBox vBox = new VBox();
            Label label = new Label("LABEL 1");
            Label label2 = new Label("LABEL 2");
            vBox.getChildren().addAll(label,label2);
            boxFlowPane.getChildren().add(vBox);
        }
    }
}
