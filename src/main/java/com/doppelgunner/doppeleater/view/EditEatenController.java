package com.doppelgunner.doppeleater.view;

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
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.stage.Window;

import javax.annotation.PostConstruct;

/**
 * Created by robertoguazon on 13/01/2017.
 */
@ViewController("AddEaten.fxml")
public class EditEatenController {

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
    @ViewNode
    private HBox buttonHBox;

    @FXMLViewFlowContext
    private ViewFlowContext viewFlowContext;

    private Eaten editEaten;
    private JFXDatePicker datePicker;
    private JFXDatePicker timePicker;
    private JFXButton deleteButton;

    @PostConstruct
    public void init() {
        okButton.setText("Update");
        deleteButton = new JFXButton("Delete");
        deleteButton.setPrefWidth(75.0);
        deleteButton.setPrefHeight(47.0);
        buttonHBox.getChildren().add(0,deleteButton);

        //TODO - when the deleteButton is put on fxml put @FXML on top of delete method
        deleteButton.setOnAction(event-> {
            delete();
        });

        datePicker = new JFXDatePicker();
        datePicker.setPromptText("Change the date?");
        datePicker.setShowTime(false);
        timePicker = new JFXDatePicker();
        timePicker.setPromptText("Change the time?");
        timePicker.setShowTime(true);

        datePicker.setDayCellFactory(Creator.getFutureDayCellsDisabled());
        timePicker.setDayCellFactory(Creator.getFutureDayCellsDisabled());
        headHBox.getChildren().addAll(datePicker, timePicker);

        editEaten = (Eaten)viewFlowContext.getRegisteredObject("editEaten");
        //TODO add to edit once clicked
        //set data to gui
        mealTypeComboBox.getItems().addAll(Eaten.MEAL_TYPES);
        mealTypeComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            editEaten.setMealType((Eaten.MealType) newValue);
        });
        mealTypeComboBox.setValue(editEaten.getMealType());

        datePicker.setValue(DateHandler.toLocalDate(editEaten.getDateEaten()));
        timePicker.setTime(DateHandler.toLocalTime(editEaten.getDateEaten()));

        howFastSlider.setValue(editEaten.getHowFast());
        editEaten.howFastProperty().bind(howFastSlider.valueProperty());

        deliciousnessSlider.setValue(editEaten.getDeliciousness());
        editEaten.deliciousnessProperty().bind(deliciousnessSlider.valueProperty());

        foodTagsTextArea.setText(editEaten.getFoodTagList().getFoodTagsString());
    }

    @FXML
    private void cancel() {
        hideWindow(cancelButton);
    }

    @FXML
    private void addFoodTag() {
        if (Validator.isEmpty(foodTagTextField.getText())) return;
        foodTagsTextArea.appendText(foodTagTextField.getText() + ";");
        foodTagTextField.clear();
    }

    private void delete() {
        String error = DBHandler.delete(editEaten);
        hideWindow(deleteButton,error,"Deleted eaten from database");
    }

    @FXML
    private void ok() {
        if (!Validator.isValid(editEaten.getMealType())) {
            System.err.println("Invalid: mealtype");
            return;
        }
        if (!Validator.isFoodTagStringValid(foodTagsTextArea.getText())) {
            System.err.println("Invalid: foodTagsTextArea");
            return;
        }
        if (Validator.isEmpty(editEaten.getUsername())) {
            System.err.println("Invalid: username");
            return;
        }

        String date = datePicker.getValue().toString();
        String time = timePicker.getTime().toString() + ":00";
        String timeAndDate = date + " " + time;
        editEaten.setDateEaten(DateHandler.convertDate(timeAndDate));
        if (!Validator.isValidDate(editEaten.getDateEaten())) {
            System.err.println("Invalid: dateEaten");
            return;
        }

        editEaten.setFoodTagList(new FoodTagList(foodTagsTextArea.getText()));

        //TODO
        String error = DBHandler.update(editEaten); //TODO update editEaten
        hideWindow(okButton, error, "Updated eaten from database");
    }

    private void hideWindow(Button button, String error, String completeMessage) {
        if (error == null) {
            //Node card = Creator.createCard(newEaten);
            //masonryPane.getChildren().add(0,card); //TODO - ?
            hideWindow(button);
            System.out.println(completeMessage);
        } else {
            System.err.println("Error: " + error);
        }
    }

    private void hideWindow(Button button) {
        Window window = cancelButton.getScene().getWindow();
        window.hide();
    }
}
