package card;

import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.annotation.PostConstruct;

/**
 * Created by robertoguazon on 08/01/2017.
 */

@ViewController(value="CardTest.fxml")
public class CardTestController {

    @ViewNode
    private ImageView imageView;
    @ViewNode
    private Label titleLabel;
    @ViewNode
    private Label descLabel;

    @PostConstruct
    public void init() {
        //Image image = new Image("icon.png");
        //imageView.setImage(image);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        titleLabel.setText("12:20 pm, Today");
        descLabel.setText("deliciousness: 5\nhow fast: 5\nfood tags: chicken, meat, spicy, whatever");
    }
}
