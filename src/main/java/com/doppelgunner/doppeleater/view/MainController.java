package com.doppelgunner.doppeleater.view;

import com.doppelgunner.doppeleater.eating.Chosen;
import com.jfoenix.controls.*;
import com.jfoenix.svg.SVGGlyph;
import com.jfoenix.svg.SVGGlyphLoader;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.context.FXMLApplicationContext;
import io.datafx.controller.context.FXMLViewContext;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import javax.annotation.PostConstruct;

/**
 * Created by robertoguazon on 05/01/2017.
 */

@ViewController(value = "MainView.fxml", title = "DoppelEater")
public class MainController {

    @ViewNode
    private StackPane root;
    @ViewNode
    private JFXTextField searchTextField;
    @ViewNode
    private JFXDrawer centerDrawer; //TODO - center
    @ViewNode
    private HBox userHBox;
    @ViewNode
    private StackPane homeStackPane;
    @ViewNode
    private ImageView userImageView;
    @ViewNode
    private Label userLabel;
    @ViewNode
    private JFXButton homeButton;

    @FXMLViewFlowContext
    private ViewFlowContext viewFlowContext;

    private FlowHandler sideBarFlowHandler;
    private FlowHandler centerFlowHandler;
    private FlowHandler centerPopupFlowHandler;
    private Flow centerFlow;

    @PostConstruct
    public void init() throws FlowException {
        System.out.println("centerDrawer: " + centerDrawer);
        centerFlow = new Flow(LoginController.class);
        centerFlowHandler = centerFlow.createHandler(viewFlowContext);

        initToolbar();
        initCenter();
        initSideBar();
    }

    private void initToolbar() {
        userLabel.setText("Guest");
        Circle circle = new Circle(16,16,16);
        userImageView.setClip(circle);
        userImageView.setImage(new Image("icon.png"));
        userImageView.setPreserveRatio(false);


        SVGGlyph home2Glyph = SVGGlyphLoader.getIcoMoonGlyph("icomoon.svg.home2");
        if (home2Glyph != null) {
            home2Glyph.setSize(32,32);
            homeButton.setGraphic(home2Glyph);
        }
        System.out.println(SVGGlyphLoader.getIcoMoonGlyph("icomoon.svg.home2"));
        homeButton.setVisible(false);
        homeButton.setOnMouseClicked(e -> {
            Chosen.goTo(HomeController.class);
        });

        viewFlowContext.register("userImageView", userImageView);
        viewFlowContext.register("userLabel", userLabel);
        viewFlowContext.register("homeButton", homeButton);
        viewFlowContext.register("searchTextField", searchTextField);
    }

    private void initCenter() throws FlowException {
        viewFlowContext.register("centerFlow", centerFlow);
        viewFlowContext.register("centerFlowHandler", centerFlowHandler);

        centerDrawer.setContent(centerFlowHandler.start());
    }

    private void initSideBar() throws FlowException {
        Flow sideBarFlow = new Flow(SideBarController.class);
        viewFlowContext.register("sideBarFlow", sideBarFlow);
        viewFlowContext.register("centerDrawer", centerDrawer);

        sideBarFlowHandler = sideBarFlow.createHandler(viewFlowContext);
        centerDrawer.setSidePane(sideBarFlowHandler.start());

        userHBox.setOnMouseClicked((e) -> {
            if (centerDrawer.isShown() || centerDrawer.isShowing()) {
                centerDrawer.close();
            } else {
                centerDrawer.open();
            }
        });

        /*
        centerDrawer.setOnDrawerOpening((e) -> {
            transition.setRate(1);
            transition.play();
        });

        centerDrawer.setOnDrawerClosing((e) -> {
            transition.setRate(-1);
            transition.play();
        });
        */
    }

    @FXML
    private void search() {
        viewFlowContext.register("search", searchTextField.getText());
        Chosen.goTo(SearchResultsController.class);
    }

}
