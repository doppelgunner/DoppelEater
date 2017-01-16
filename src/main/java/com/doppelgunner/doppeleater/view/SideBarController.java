package com.doppelgunner.doppeleater.view;

import com.doppelgunner.doppeleater.eating.Chosen;
import com.doppelgunner.doppeleater.util.Creator;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import io.datafx.controller.util.VetoException;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.annotation.PostConstruct;

/**
 * Created by robertoguazon on 05/01/2017.
 */
@ViewController(value = "SideBar.fxml")
public class SideBarController {

    @ViewNode
    private JFXListView sideBarList;

    @FXMLViewFlowContext
    private ViewFlowContext viewFlowContext;

    @PostConstruct
    public void init() throws FlowException {
        System.out.println("sideBarList: " + sideBarList);
        sideBarList.propagateMouseEventsToParent();

        //center
        FlowHandler centerFlowHandler = (FlowHandler) viewFlowContext.getRegisteredObject("centerFlowHandler");
        Flow centerFlow = (Flow) viewFlowContext.getRegisteredObject("centerFlow");
        JFXDrawer centerDrawer = (JFXDrawer) viewFlowContext.getRegisteredObject("centerDrawer");

        Label loginLabel = Creator.createLabel("Login");
        Label signupLabel = Creator.createLabel("Signup");
        Label logoutLabel = Creator.createLabel("Logout");

        viewFlowContext.register("loginLabel", loginLabel);
        viewFlowContext.register("signupLabel", signupLabel);
        viewFlowContext.register("logoutLabel", logoutLabel);
        viewFlowContext.register("sideBarList", sideBarList);

        sideBarList.getItems().addAll(loginLabel, signupLabel);

        System.out.println("loginLabel id: " + loginLabel.getId());
        System.out.println("signupLabel id: " + signupLabel.getId());


        bindNodeToController(loginLabel, LoginController.class, centerFlow, centerFlowHandler, centerDrawer);
        bindNodeToController(signupLabel, SignupController.class, centerFlow, centerFlowHandler, centerDrawer);
        bindNodeToController(logoutLabel, LoginController.class, centerFlow, centerFlowHandler, centerDrawer);

        bindNodeToController(HomeController.class,centerFlow);
        bindNodeToController(EatenMakerController.class,centerFlow);
        bindNodeToController(EatenGraphController.class,centerFlow);
        bindNodeToController(ProfileController.class, centerFlow);
        bindNodeToController(SearchResultsController.class,centerFlow);
        //test(); //TODO delete
    }

    private void bindNodeToController(Node node, Class controllerClass, Flow flow, FlowHandler flowHandler, JFXDrawer centerDrawer) {
        flow.withGlobalLink(node.getId(), controllerClass);
        node.setOnMouseClicked((e) -> {
            try {
                flowHandler.handle(node.getId());
                centerDrawer.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private void bindNodeToController(Class controllerClass, Flow flow) {
        flow.withGlobalLink(controllerClass.getSimpleName(), controllerClass);
    }

    private void test() {
        Chosen.goTo(EatenMakerController.class); //TODO delete
    }
}
