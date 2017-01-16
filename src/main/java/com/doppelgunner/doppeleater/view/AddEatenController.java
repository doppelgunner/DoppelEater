package com.doppelgunner.doppeleater.view;

import com.doppelgunner.doppeleater.eating.Chosen;
import com.doppelgunner.doppeleater.model.Eaten;
import com.doppelgunner.doppeleater.model.FoodTagList;
import com.doppelgunner.doppeleater.util.Creator;
import com.doppelgunner.doppeleater.util.DBHandler;
import com.doppelgunner.doppeleater.util.DateHandler;
import com.doppelgunner.doppeleater.util.Validator;
import com.jfoenix.controls.*;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.stage.Window;

import javax.annotation.PostConstruct;

/**
 * Created by robertoguazon on 08/01/2017.
 */

@ViewController(value="AddEaten.fxml")
public class AddEatenController {

    @ViewNode
    private JFXComboBox mealTypeComboBox;
    @ViewNode
    private JFXSlider deliciousnessSlider;
    @ViewNode
    private JFXButton cancelButton;
    @ViewNode
    private JFXTextArea foodTagsTextArea;
    @ViewNode
    private JFXButton okButton;
    @ViewNode
    private JFXTextField foodTagTextField;
    @ViewNode
    private JFXButton addFoodTagButton;
    @ViewNode
    private JFXSlider howFastSlider;
    @ViewNode
    private HBox headHBox;

    @FXMLViewFlowContext
    private ViewFlowContext viewFlowContext;

    private Eaten newEaten;
    private JFXDatePicker datePicker;
    private JFXDatePicker timePicker;

    @PostConstruct
    public void init() {
        datePicker = new JFXDatePicker();
        datePicker.setPromptText("Pick a date");
        datePicker.setShowTime(false);
        timePicker = new JFXDatePicker();
        timePicker.setPromptText("Pick a time");
        timePicker.setShowTime(true);

        datePicker.setDayCellFactory(Creator.getFutureDayCellsDisabled());
        timePicker.setDayCellFactory(Creator.getFutureDayCellsDisabled());
        headHBox.getChildren().addAll(datePicker, timePicker);

        newEaten = Eaten.createEaten();

        //meal type
        mealTypeComboBox.getItems().addAll(Eaten.MEAL_TYPES);
        mealTypeComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            newEaten.setMealType((Eaten.MealType) newValue);
            System.out.println(newEaten.getMealType()); //TODO - delete
        });

        //howfast
        newEaten.howFastProperty().bind(howFastSlider.valueProperty());

        //deliciousness
        newEaten.deliciousnessProperty().bind(deliciousnessSlider.valueProperty());

        //username
        newEaten.setUsername(Chosen.getEater().getUsername()); //TODO -add after testing
        //newEaten.setUsername("bob"); //TODO -add after testing
    }

    @FXML
    private void addFoodTag() {
        if (Validator.isEmpty(foodTagTextField.getText())) return;
        foodTagsTextArea.appendText(foodTagTextField.getText() + ";");
        foodTagTextField.clear();
    }

    @FXML
    private void ok() {
        if (!Validator.isValid(newEaten.getMealType())) {
            System.err.println("Invalid: mealtype");
            return;
        }
        if (!Validator.isFoodTagStringValid(foodTagsTextArea.getText())) {
            System.err.println("Invalid: foodTagsTextArea");
            return;
        }
        if (Validator.isEmpty(newEaten.getUsername())) {
            System.err.println("Invalid: username");
            return;
        }


        String date = datePicker.getValue().toString();
        String time = timePicker.getTime().toString() + ":00";
        String timeAndDate = date + " " + time;
        newEaten.setDateEaten(DateHandler.convertDate(timeAndDate)); //TODO - add a calendar for picking time and date
        if (!Validator.isValidDate(newEaten.getDateEaten())) {
            System.err.println("Invalid: dateEaten");
            return;
        }

        newEaten.setFoodTagList(new FoodTagList(foodTagsTextArea.getText()));

        JFXMasonryPane masonryPane = (JFXMasonryPane) viewFlowContext.getRegisteredObject("eatenMakerMasonryPane");
        ScrollPane scrollPane = (ScrollPane) viewFlowContext.getRegisteredObject("eatenMakerScrollPane");

        String error = DBHandler.push(newEaten);
        if (error == null) {
            //Node card = Creator.createCard(newEaten);
            //masonryPane.getChildren().add(0,card); //TODO - ?
            hideWindow(okButton);
            System.out.println("Pushed eaten to databased");
        } else {
            System.err.println("Error: " + error);
        }
    }

    @FXML
    private void cancel() {
        hideWindow(cancelButton);
    }

    private void hideWindow(Button button) {
        Window window = cancelButton.getScene().getWindow();
        window.hide();
    }
}
