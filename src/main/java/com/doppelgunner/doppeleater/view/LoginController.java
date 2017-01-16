package com.doppelgunner.doppeleater.view;

import com.doppelgunner.doppeleater.eating.Chosen;
import com.doppelgunner.doppeleater.task.LoginService;
import com.doppelgunner.doppeleater.util.Validator;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.annotation.PostConstruct;

/**
 * Created by robertoguazon on 05/01/2017.
 */
@ViewController(value = "Login.fxml")
public class LoginController {

    @ViewNode
    private JFXTextField usernameTextField;
    @ViewNode
    private JFXPasswordField passwordTextField;
    @ViewNode
    private Button loginButton;

    @FXMLViewFlowContext
    private ViewFlowContext viewFlowContext;

    private StringProperty username = new SimpleStringProperty();
    private StringProperty password = new SimpleStringProperty();
    private LoginService loginService = new LoginService();

    @PostConstruct
    public void init() {
        clearAfterLogout();

        username.bind(usernameTextField.textProperty());
        password.bind(passwordTextField.textProperty());
    }

    private void clearAfterLogout() {
        Chosen.sleep();
        ImageView userImageView = (ImageView)viewFlowContext.getRegisteredObject("userImageView");
        userImageView.setImage(new Image("icon.png"));

        Label userLabel = (Label) viewFlowContext.getRegisteredObject("userLabel");
        userLabel.setText("Username");

        JFXButton homeButton = (JFXButton)viewFlowContext.getRegisteredObject("homeButton");
        if (homeButton != null) {
            homeButton.setVisible(false);
        }

        JFXListView sideBarList = (JFXListView) viewFlowContext.getRegisteredObject("sideBarList");
        if (sideBarList != null) {
            sideBarList.getItems().clear();
            Label loginLabel = (Label) viewFlowContext.getRegisteredObject("loginLabel");
            Label signupLabel = (Label) viewFlowContext.getRegisteredObject("signupLabel");
            sideBarList.getItems().addAll(loginLabel,signupLabel);
        }
    }

    @FXML
    private void login() {
        if (loginService.isRunning()) return;

        loginService.reset();
        loginService.setUsername(username.get());
        loginService.setPassword(password.get());

        loginService.setOnSucceeded(t -> {
            String error = (String)t.getSource().getValue();
            if (error == null) {
                System.out.println("Login success");
                //TODO HOME
                Chosen.goTo(HomeController.class);
                //TODO add LOGOUT
            } else {
                System.out.println("Login error: " + error);
            }
        });
        loginService.start();
    }
}
