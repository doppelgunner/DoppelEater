package com.doppelgunner.doppeleater.view;

import com.doppelgunner.doppeleater.eating.Chosen;
import com.doppelgunner.doppeleater.model.Eaten;
import com.doppelgunner.doppeleater.model.Eater;
import com.doppelgunner.doppeleater.model.FoodTagList;
import com.doppelgunner.doppeleater.util.*;
import com.jfoenix.controls.*;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

/**
 * Created by robertoguazon on 14/01/2017.
 */
@ViewController("Profile.fxml")
public class ProfileController {

    @ViewNode
    private JFXComboBox privacyComboBox;
    @ViewNode
    private ImageView profileImageView;
    @ViewNode
    private JFXTextArea bioTextArea;
    @ViewNode
    private JFXTextField emailTextField;
    @ViewNode
    private JFXComboBox genderComboBox;
    @ViewNode
    private Label usernameLabel;
    @ViewNode
    private JFXButton saveButton;
    @ViewNode
    private Label timeStartedLabel;
    @ViewNode
    private HBox birthdayHBox;

    @FXMLViewFlowContext
    private ViewFlowContext viewFlowContext;

    private JFXDatePicker birthDatePicker;
    private Eater currentEater;

    @PostConstruct
    public void init() {
        birthDatePicker = new JFXDatePicker();
        birthDatePicker.setShowTime(false);
        birthDatePicker.setPromptText("Pick your birthday");
        birthDatePicker.setDayCellFactory(Creator.getFutureDayCellsDisabled());
        birthdayHBox.getChildren().add(birthDatePicker);

        currentEater = Chosen.getEater().clone();
        usernameLabel.setText(currentEater.getUsername());
        timeStartedLabel.setText("Time joined: " +
                DateHandler.convertDate(currentEater.getTimeStarted(), DateHandler.TIME_STARTED_DATE_FORMAT));
        bioTextArea.setText(currentEater.getBio());
        currentEater.bioProperty().bind(bioTextArea.textProperty());

        emailTextField.setText(currentEater.getEmail());
        currentEater.emailProperty().bind(emailTextField.textProperty());

        genderComboBox.getItems().addAll(Eater.GENDER_TYPES);
        genderComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            currentEater.setGender((Eater.GenderType) newValue);
        });
        genderComboBox.setValue(currentEater.getGender());

        privacyComboBox.getItems().addAll(Eater.PRIVACY_TYPES);
        privacyComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            currentEater.setPrivacy((Eater.PrivacyType) newValue);
        });
        privacyComboBox.setValue(currentEater.getPrivacy());

        if (Validator.isValidDate(currentEater.getBirthDay())) {
            birthDatePicker.setValue(DateHandler.toLocalDate(currentEater.getBirthDay()));
        }

        Circle circle = new Circle(64);
        circle.setCenterX(64);
        circle.setCenterY(64);
        profileImageView.setPreserveRatio(false);
        profileImageView.setClip(circle);
        if (currentEater.getImage() == null) {
            profileImageView.setImage(new Image("icon.png"));
        } else {
            profileImageView.setImage(currentEater.getImage());
        }

        //currentEater.imageLocationProperty().bind(imageLocationTextField.textProperty());
        profileImageView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Stage stage = (Stage) viewFlowContext.getRegisteredObject("stage");
                File imageFile = Chooser.chooseImage(stage);
                if (imageFile != null) {
                    Image pickedImage = new Image("file:" + imageFile.getAbsolutePath());
                    currentEater.setImage(pickedImage);
                    profileImageView.setImage(pickedImage);
                }
            }
        });
    }

    @FXML
    private void save() {
        if (Validator.isValid(birthDatePicker.getValue())) {
            currentEater.setBirthDay(DateHandler.toDate(birthDatePicker.getValue()));
        }

        //TODO
        String error = DBHandler.update(currentEater); //TODO update editEaten
        if (error != null) {
            System.out.println("Error: " + error);
        } else {
            ImageView userImageView = (ImageView)viewFlowContext.getRegisteredObject("userImageView");
            if (userImageView != null && currentEater.getImage() != null) {
                userImageView.setImage(currentEater.getImage());
            }

            Chosen.setEater(currentEater);
        }
    }
}
