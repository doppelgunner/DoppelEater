package com.doppelgunner.doppeleater.view;

import com.doppelgunner.doppeleater.database.generated.tables.records.EatenRecord;
import com.doppelgunner.doppeleater.database.generated.tables.records.EaterRecord;
import com.doppelgunner.doppeleater.eating.Chosen;
import com.doppelgunner.doppeleater.model.Eaten;
import com.doppelgunner.doppeleater.model.Eater;
import com.doppelgunner.doppeleater.task.SearchEatensService;
import com.doppelgunner.doppeleater.task.SearchEatersService;
import com.doppelgunner.doppeleater.util.Creator;
import com.jfoenix.controls.JFXMasonryPane;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.jooq.Result;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by robertoguazon on 15/01/2017.
 */
@ViewController("SearchResults.fxml")
public class SearchResultsController {

    @ViewNode
    private ScrollPane scrollPane;
    @ViewNode
    private StackPane root;

    @FXMLViewFlowContext
    private ViewFlowContext viewFlowContext;

    private JFXMasonryPane masonryPane;
    private double scrollValue = 0.0;

    private SearchEatensService searchEatensService = new SearchEatensService();
    private SearchEatersService searchEatersService = new SearchEatersService();

    private String search;

    @PostConstruct
    public void init() {
        masonryPane = new JFXMasonryPane();
        masonryPane.getChildren().addListener((ListChangeListener<Node>) c -> {
            masonryPane.layout();
            scrollPane.layout();
            //scrollPane.setVvalue(0.0f);
        });
        masonryPane.setLayoutMode(JFXMasonryPane.LayoutMode.BIN_PACKING);
        scrollPane.setContent(masonryPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        search = (String) viewFlowContext.getRegisteredObject("search");

        System.out.println("MASONRY MAX_HEIGHT: " + masonryPane.getMaxHeight());
        System.out.println("MASONRY MAX_WIDTH: " + masonryPane.getMaxWidth());
        System.out.println("MASONRY PAD: " + masonryPane.getPadding());
        System.out.println("MASONRY HOR: " + masonryPane.getHSpacing());
        System.out.println("MASONRY VER: " + masonryPane.getVSpacing());
        System.out.println("SCROLLPANE MAX_HEIGHT: " + scrollPane.getMaxHeight());
        System.out.println("SCROLLPANE MAX_WIDTH: " + scrollPane.getMaxWidth());

        //always search even when logged in or out
        loadSearchEatersService();
        if (Chosen.isEating()) {
            loadSearchEatensService();
        }
    }

    private void loadSearchEatersService() {
        if (searchEatersService.isRunning()) return;
        searchEatersService.reset();

        searchEatersService.setSearch(search);
        searchEatersService.setOnSucceeded(t -> {
            Result<EaterRecord> result = (Result<EaterRecord>)t.getSource().getValue();
            if (result == null) return;
            ArrayList<Node> cardList = new ArrayList<Node>();
            for (EaterRecord eaterRecord : result) {
                Eater eater = Eater.createEater(eaterRecord);
                Node cardNode = Creator.createCard(eater);
                cardList.add(cardNode);
                scrollPane.setVvalue(scrollValue);

                System.out.println(eater.getUsername()); //TODO delete
            }

            masonryPane.getChildren().addAll(cardList.toArray(new Node[cardList.size()]));
            Platform.runLater(() -> {
                scrollPane.requestLayout();
            });
        });
        searchEatersService.start();
    }

    private void loadSearchEatensService() {
        if (searchEatensService.isRunning()) return;
        searchEatensService.reset();

        searchEatensService.setSearch(search);
        searchEatensService.setUsername(Chosen.getEater().getUsername()); //TODO replace to chosen
        searchEatensService.setOnSucceeded(t -> {
            Result<EatenRecord> result = (Result<EatenRecord>)t.getSource().getValue();
            if (result == null) return;
            ArrayList<Node> cardList = new ArrayList<>();
            for (EatenRecord eatenRecord : result) {
                Eaten eaten = Eaten.createEaten(eatenRecord);
                Node cardNode = Creator.createCard(eaten);
                cardList.add(cardNode);

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

                scrollPane.setVvalue(scrollValue);
                System.out.println(eaten);
            }

            masonryPane.getChildren().addAll(cardList.toArray(new Node[cardList.size()]));
            Platform.runLater(() -> {
                scrollPane.requestLayout();
            });
        });
        searchEatensService.start();
    }
}
