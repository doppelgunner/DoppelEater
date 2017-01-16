package com.doppelgunner.doppeleater.view;

/**
 * Created by robertoguazon on 05/01/2017.
 */

import com.doppelgunner.doppeleater.eating.Chosen;
import com.doppelgunner.doppeleater.model.Eater;
import com.doppelgunner.doppeleater.task.SignupService;
import com.doppelgunner.doppeleater.util.DBHandler;
import com.doppelgunner.doppeleater.util.DateHandler;
import com.doppelgunner.doppeleater.util.Validator;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;

import javax.annotation.PostConstruct;

@ViewController(value="Signup.fxml")
public class SignupController {

    @FXMLViewFlowContext
    ViewFlowContext viewFlowContext;

    @ViewNode
    private JFXPasswordField passwordTextField;
    @ViewNode
    private JFXPasswordField retypePasswordTextField;
    @ViewNode
    private JFXTextField emailTextField;
    @ViewNode
    private JFXTextField usernameTextField;

    @ViewNode
    private JFXButton signupButton;

    private Eater newEater;
    private StringProperty retypedPassword = new SimpleStringProperty();

    private String validatorMessage = "Input required";
    private SignupService signupService = new SignupService();

    @PostConstruct
    public void init() {
        newEater = Eater.createEater();
        newEater.usernameProperty().bind(usernameTextField.textProperty());
        newEater.emailProperty().bind(emailTextField.textProperty());
        newEater.passwordProperty().bind(passwordTextField.textProperty());
        retypedPassword.bind(retypePasswordTextField.textProperty());

        Validator.addValidator(usernameTextField);
        Validator.addValidator(passwordTextField);
        Validator.addValidator(emailTextField);
        Validator.addValidator(retypePasswordTextField);
    }

    @FXML
    private void signup() {
        if (signupService.isRunning()) return;

        signupService.reset();
        signupService.setEater(newEater);
        signupService.setRetypedPassword(retypedPassword.get());
        signupService.setOnSucceeded(t -> {
            String error = (String)t.getSource().getValue();
            if (error == null) {
                newEater.setTimeStarted(DateHandler.getDateNow());
                System.out.println("Valid account");
                error = DBHandler.pushEater(newEater);
                if (error != null) {
                    System.out.println("Error: " + error);
                } else {
                    //TODO HOME
                    Chosen.setEater(newEater);
                    Chosen.goTo(HomeController.class);
                }
                //TODO - verify logic
                //TODO ADD LOGOUT
            } else {
                System.out.println("Signup error: " + error);
            }
        });
        signupService.start();
    }
}
