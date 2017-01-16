package com.doppelgunner.doppeleater.view;

import com.doppelgunner.doppeleater.eating.Chosen;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import io.datafx.controller.util.VetoException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import javax.annotation.PostConstruct;

/**
 * Created by robertoguazon on 05/01/2017.
 */
@ViewController(value="Home.fxml")
public class HomeController {

    @FXMLViewFlowContext
    private ViewFlowContext viewFlowContext;

    private FlowHandler centerFlowHandler;

    @PostConstruct

    //TODO SUMMARIZE DATA based on findings

    public void init() throws VetoException, FlowException {
        JFXListView sideBarList = (JFXListView) viewFlowContext.getRegisteredObject("sideBarList");
        Label logoutLabel = (Label) viewFlowContext.getRegisteredObject("logoutLabel");
        sideBarList.getItems().clear();
        sideBarList.getItems().addAll(logoutLabel);

        //set image
        ImageView userImageView = (ImageView) viewFlowContext.getRegisteredObject("userImageView");
        if (Chosen.getEater().getImage() != null) {
            userImageView.setImage(Chosen.getEater().getImage());
        }
        Label userLabel = (Label) viewFlowContext.getRegisteredObject("userLabel");
        userLabel.setText(Chosen.getEater().getUsername());

        JFXButton homeButton = (JFXButton) viewFlowContext.getRegisteredObject("homeButton");
        if (homeButton != null) {
            homeButton.setVisible(true);
        }
    }

    @FXML
    private void goToEaten() {
        Chosen.goTo(EatenMakerController.class); //TODO -fix these after adding the buttons for home page and summary of data
    }

    @FXML
    private void goToProfile() {
        Chosen.goTo(ProfileController.class);
    }


}
