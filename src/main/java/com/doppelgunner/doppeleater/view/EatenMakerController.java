package com.doppelgunner.doppeleater.view;


import com.doppelgunner.doppeleater.database.generated.tables.records.EatenRecord;
import com.doppelgunner.doppeleater.eating.Chosen;
import com.doppelgunner.doppeleater.model.Eaten;
import com.doppelgunner.doppeleater.task.GetEatenService;
import com.doppelgunner.doppeleater.util.Creator;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXMasonryPane;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;

import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.jooq.Result;

import javax.annotation.PostConstruct;
import java.awt.event.KeyEvent;

/**
 * Created by robertoguazon on 07/01/2017.
 */
@ViewController(value="EatenMaker.fxml")
public class EatenMakerController {

    @ViewNode
    private StackPane root;
    @ViewNode
    private ScrollPane scrollPane;
    @ViewNode
    private JFXButton addEatenButton;
    @ViewNode
    private JFXButton refreshButton;
    @ViewNode
    private JFXButton graphButton;

    @FXMLViewFlowContext
    private ViewFlowContext viewFlowContext;

    private JFXMasonryPane masonryPane;
    private double scrollValue = 0.0;
    private GetEatenService getEatenService = new GetEatenService();

    @PostConstruct
    public void init() {
        //TODO - update cards every 20 seconds?
        masonryPane = new JFXMasonryPane();
        masonryPane.getChildren().addListener((ListChangeListener<Node>) c -> {
            masonryPane.layout();
            scrollPane.layout();
            //scrollPane.setVvalue(0.0f);
        });

        scrollPane.setContent(masonryPane);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        /* TODO - similar to twitter on scroll reached? fix
        scrollPane.vvalueProperty().addListener((o,ov,nv) -> {
            if (nv.doubleValue() != ov.doubleValue() && nv.doubleValue() == 1) {
                if (getEatenService.isRunning()) return;
                getEatenService.reset();
                getEatenService.start();
            }
        });
        */

        viewFlowContext.register("eatenMakerMasonryPane", masonryPane);
        viewFlowContext.register("eatenMakerScrollPane", scrollPane);


        loadGetEatenService();
    }

    private void loadGetEatenService() {
        if (getEatenService.isRunning()) return;
        getEatenService.reset();

        getEatenService.setUsername(Chosen.getEater().getUsername());
        getEatenService.setNumber(100);
        getEatenService.setOnSucceeded(t -> {
            Result<EatenRecord> result = (Result<EatenRecord>)t.getSource().getValue();
            if (result == null) return;
            for (EatenRecord eatenRecord : result) {
                Eaten eaten = Eaten.createEaten(eatenRecord);
                Node cardNode = Creator.createCard(eaten);
                masonryPane.getChildren().add(cardNode);

                cardNode.setOnMouseClicked(event -> {
                    //if not same username as card not allowed to edit
                    if (!eaten.getUsername().equals(Chosen.getEater().getUsername())) return;
                    if(!(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2)) return;

                    try {
                        Flow editEatenFlow = new Flow(EditEatenController.class);
                        FlowHandler editEatenFlowHandler = editEatenFlow.createHandler(viewFlowContext);
                        Stage primaryStage = (Stage) viewFlowContext.getRegisteredObject("stage");
                        viewFlowContext.register("editEaten",eaten);

                        //wrap
                        BorderPane borderPane = new BorderPane();
                        borderPane.setCenter(editEatenFlowHandler.start()); //TODO check if appropriate
                        HBox hBox = new HBox();
                        hBox.getChildren().add(new Label("FOOD ID: " + eaten.getFoodId()));
                        hBox.setAlignment(Pos.CENTER);
                        borderPane.setTop(hBox);

                        Stage childStage = Creator.createChildStage(primaryStage, "Edit eaten", borderPane , 600,400, false);
                        childStage.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                Platform.runLater(() -> {
                    scrollPane.requestLayout();
                });
                scrollPane.setVvalue(scrollValue);
            }
        });
        getEatenService.start();
    }

    @FXML
    private void addEaten() {
        //masonryPane.getChildren().add(Creator.createTestLabel()); //TODO - delete
        //TODO - real
        try {
            Flow addEatenFlow = new Flow(AddEatenController.class);
            FlowHandler addEatenFlowHandler = addEatenFlow.createHandler(viewFlowContext);
            Stage primaryStage = (Stage) viewFlowContext.getRegisteredObject("stage");
            Stage childStage = Creator.createChildStage(primaryStage, "Add eaten", addEatenFlowHandler.start() , 600,400, false);
            childStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void refresh() {
        scrollValue = scrollPane.getVvalue();
        if (getEatenService.isRunning()) return;
        getEatenService.reset();
        masonryPane.getChildren().clear();
        getEatenService.start();
    }

    @FXML
    private void graph() {
        Chosen.goTo(EatenGraphController.class);
    }

}
