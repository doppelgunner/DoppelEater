package com.doppelgunner.doppeleater.util;

import com.doppelgunner.doppeleater.model.Eaten;
import com.doppelgunner.doppeleater.model.Eater;
import com.doppelgunner.doppeleater.model.Searchable;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDecorator;
import com.sun.glass.ui.MenuItem;
import com.sun.org.apache.xpath.internal.operations.Number;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * Created by robertoguazon on 05/01/2017.
 */
public class Creator {

    public static final float CARD_RATIO = 0.75f;

    public static Label createLabel(String name) {
        Label label = new Label(name);
        label.setId(name);

        return label;
    }

    public static Stage createChildStage(Stage primaryStage, String title, Node content, int width, int height, boolean resizable) {
        Stage childStage = new Stage();
        childStage.setWidth(width);
        childStage.setHeight(height);
        childStage.setX(primaryStage.getX() + primaryStage.getWidth() / 2 - childStage.getWidth() / 2);
        childStage.setY(primaryStage.getY() + primaryStage.getHeight() / 2 - childStage.getHeight() / 2);
        childStage.setResizable(resizable);
        childStage.initModality(Modality.WINDOW_MODAL);
        childStage.initOwner(primaryStage);


        JFXDecorator decorator = new JFXDecorator(childStage,content, false, false, false);
        decorator.setOnCloseButtonAction(() ->{
            childStage.close();
        });
        decorator.setCustomMaximize(false);
        decorator.setMaximized(false);
        Scene scene = new Scene(decorator);
        childStage.setScene(scene);
        childStage.setTitle(title);

        return childStage;
    }

    public static Label createTestLabel() {
        Label label = new Label("Label");
        int width = (int)(Math.random()*50) + 151;
        int height = (int)(Math.random()*50) + 151;
        if (width <= height) {
            width = (int)(height * CARD_RATIO);
        } else {
            height = (int)(width * CARD_RATIO);
        }
        label.setMinWidth(width);
        label.setMaxWidth(width);
        label.setPrefWidth(width);

        label.setMinHeight(height);
        label.setMaxHeight(height);
        label.setPrefHeight(height);

        label.setText("Label " + width + " x " + height);
        label.setWrapText(true);
        label.setStyle("-fx-background-color: black; -fx-text-fill: white;");
        label.setAlignment(Pos.CENTER);
        return label;
    }

    public static Node createCard(Searchable searchable) {
        if (searchable == null) return null;
        VBox cardContainer = new VBox();
        Label titleLabel = new Label(searchable.getSearchTitle());
        Label descriptionLabel = new Label(searchable.getSearchDescription());

        int cardWidth = 150;
        int cardHeight = 200;
        int padding = 5;
        cardContainer.setPrefWidth(cardWidth);
        cardContainer.setPrefHeight(cardHeight);
        cardContainer.setFillWidth(true);
        cardContainer.setStyle("-fx-background-color: white;");
        cardContainer.setAlignment(Pos.CENTER);

        titleLabel.setPrefWidth(cardWidth);
        titleLabel.setMinWidth(cardWidth);
        titleLabel.setMaxWidth(cardWidth);

        descriptionLabel.setPrefWidth(cardWidth);
        descriptionLabel.setMinWidth(cardWidth);
        descriptionLabel.setMaxWidth(cardWidth);

        Image toDisplayImage = null;

        if (searchable instanceof Eater) {
            if (searchable.getSearchImage() == null) {
                toDisplayImage = new Image("icon.png");
            } else {
                toDisplayImage = searchable.getSearchImage();
            }
            //ImageView imageView = new ImageView(searchable.getSearchImage());
            Circle clip = new Circle(cardHeight * 1/4/2 - 5);
            clip.setFill(new ImagePattern(toDisplayImage));
            cardContainer.getChildren().add(clip);
            //TODO - set to circle rounded

            titleLabel.setMinHeight(cardHeight * 1/4 + (padding * 4));
            titleLabel.setMaxHeight(cardHeight * 1/4 + (padding * 4));
            titleLabel.setPrefHeight(cardHeight * 1/4 + (padding * 4));
            descriptionLabel.setMinHeight(cardHeight * 1/2 + (padding * 4));
            descriptionLabel.setMaxHeight(cardHeight * 1/2 + (padding * 4));
            descriptionLabel.setPrefHeight(cardHeight * 1/2 + (padding * 4));

        } else if (searchable.getSearchImage() != null) {
            //ImageView imageView = new ImageView(searchable.getSearchImage());
            Circle clip = new Circle(cardHeight * 1/4/2 - 5);
            clip.setFill(new ImagePattern(searchable.getSearchImage()));
            cardContainer.getChildren().add(clip);
            //TODO - set to circle rounded

            titleLabel.setMinHeight(cardHeight * 1/4 + (padding * 4));
            titleLabel.setMaxHeight(cardHeight * 1/4 + (padding * 4));
            titleLabel.setPrefHeight(cardHeight * 1/4 + (padding * 4));
            descriptionLabel.setMinHeight(cardHeight * 1/2 + (padding * 4));
            descriptionLabel.setMaxHeight(cardHeight * 1/2 + (padding * 4));
            descriptionLabel.setPrefHeight(cardHeight * 1/2 + (padding * 4));
        } else {
            titleLabel.setMinHeight(cardHeight *1/3 + (padding * 4));
            titleLabel.setMaxHeight(cardHeight * 1/3 + (padding * 4));
            titleLabel.setPrefHeight(cardHeight * 1/3 + (padding * 4));
            descriptionLabel.setMinHeight(cardHeight * 2/3 + (padding * 4));
            descriptionLabel.setMaxHeight(cardHeight * 2/3 + (padding * 4));
            descriptionLabel.setPrefHeight(cardHeight * 2/3 + (padding * 4));
        }

        titleLabel.setWrapText(true);
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setTextAlignment(TextAlignment.CENTER);
        titleLabel.setStyle("-fx-background-color: white; -fx-text-fill: black;"); //TODO - change use css file format
        titleLabel.setPadding(new Insets(padding));

        descriptionLabel.setStyle("-fx-background-color: black; -fx-text-fill: white;");
        descriptionLabel.setPadding(new Insets(padding));
        descriptionLabel.setWrapText(true);
        descriptionLabel.setAlignment(Pos.CENTER);


        cardContainer.getChildren().addAll(titleLabel,descriptionLabel);

        return cardContainer;
    }

    public static Callback<DatePicker,DateCell> getFutureDayCellsDisabled() {
        Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item,empty);
                        DayOfWeek day = DayOfWeek.from(item);
                        if (day == DayOfWeek.SUNDAY || day == DayOfWeek.SATURDAY) {
                            this.setTextFill(Color.web("#4CAF50"));
                        }

                        if (item.isAfter(LocalDate.now())) {
                            this.setDisable(true);
                        }
                    }
                };
            }
        };
        return dayCellFactory;
    }

    public static void setGraphAxisUpperBound(NumberAxis numberAxis, DateHandler.DateType dateTypeAxis) {
        numberAxis.setAutoRanging(false);
        numberAxis.setForceZeroInRange(true);
        numberAxis.setTickUnit(1);
        switch (dateTypeAxis) {
            case YEAR:
                numberAxis.setAutoRanging(true);
                break;
            case MONTH:
                numberAxis.setUpperBound(12);
                break;
            case WEEK:
                numberAxis.setUpperBound(4);
                break;
            case DAY:
                numberAxis.setUpperBound(7);
                break;
            case HOUR:
                numberAxis.setUpperBound(24);
                break;
            case MIN:
                numberAxis.setUpperBound(60);
                break;
            case SEC:
                numberAxis.setUpperBound(60);
                break;
        }
    }
}
