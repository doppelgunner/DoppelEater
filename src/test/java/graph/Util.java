package graph;

import javafx.scene.Cursor;
import javafx.scene.Node;

/**
 * Created by robertoguazon on 04/01/2017.
 */
public class Util {

    public static void handOnMouseEnter(Node node) {
        node.setOnMouseEntered((event) -> {
            node.getScene().setCursor(Cursor.HAND);
        });

        node.setOnMouseExited((event) -> {
            node.getScene().setCursor(Cursor.DEFAULT);
        });
    }

}
