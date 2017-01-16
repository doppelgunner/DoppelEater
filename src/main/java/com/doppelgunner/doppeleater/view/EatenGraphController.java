package com.doppelgunner.doppeleater.view;

import com.doppelgunner.doppeleater.database.generated.tables.records.EatenRecord;
import com.doppelgunner.doppeleater.eating.Chosen;
import com.doppelgunner.doppeleater.task.GetEatenService;
import com.doppelgunner.doppeleater.util.BindHandler;
import com.doppelgunner.doppeleater.util.DateHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import io.datafx.controller.ViewController;
import io.datafx.controller.ViewNode;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import org.jooq.Result;


import javax.annotation.PostConstruct;

/**
 * Created by robertoguazon on 11/01/2017.
 */
@ViewController("EatenGraph.fxml")
public class EatenGraphController {

    @ViewNode
    private JFXButton eatenButton;
    @ViewNode
    private BorderPane mainBorderPane;
    @ViewNode
    private JFXComboBox dateComboBox;
    @ViewNode
    private JFXComboBox graphComboBox;

    @FXMLViewFlowContext
    private ViewFlowContext viewFlowContext;

    private DateHandler.DateType dateTypeSince;
    private DateHandler.DateType dateTypeAxis;

    private Flow eatenCenterFlow;
    private FlowHandler eatenCenterFlowHandler;

    @PostConstruct
    public void init() {
        eatenCenterFlow = new Flow(EatenLineGraphController.class);
        eatenCenterFlowHandler = eatenCenterFlow.createHandler(viewFlowContext);

        //dateTypes
        dateTypeSince = DateHandler.DateType.DAY;
        dateTypeAxis = DateHandler.DateType.HOUR;

        //populate comboboxes
        for(int i = 2; i < DateHandler.DATE_TYPES.length; i++) {
            dateComboBox.getItems().add(DateHandler.DATE_TYPES[i]);
        }

        dateComboBox.valueProperty().addListener((o,ov,nv) -> {
            dateTypeSince = (DateHandler.DateType)nv;
            setDateTypeAxis(dateTypeSince);


            setContext();
            setCenter(EatenLineGraphController.class);
        });

        setContext();
        loadAndBind();
    }

    private void setContext() {
        viewFlowContext.register("dateTypeAxis", dateTypeAxis);
        viewFlowContext.register("dateTypeSince", dateTypeSince);
    }

    private void loadAndBind() {
        BindHandler.bindWithGlobalLink(eatenCenterFlow,EatenLineGraphController.class);
        try {
            mainBorderPane.setCenter(eatenCenterFlowHandler.start());
        } catch (FlowException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void eaten() {
        Chosen.goTo(EatenMakerController.class);
    }

    private void setCenter(Class controller) {
        BindHandler.activateFlowHandler(eatenCenterFlowHandler,controller);
    }

    private void setDateTypeAxis(DateHandler.DateType dateType) {
        switch (dateType) {
            case LIFETIME:
                dateTypeAxis = DateHandler.DateType.YEAR;
                break;
            case YEAR:
                dateTypeAxis = DateHandler.DateType.MONTH;
                break;
            case MONTH:
                dateTypeAxis = DateHandler.DateType.WEEK;
                break;
            case WEEK:
                dateTypeAxis = DateHandler.DateType.DAY;
                break;
            case DAY:
                dateTypeAxis = DateHandler.DateType.HOUR;
                break;
            case HOUR:
                dateTypeAxis = DateHandler.DateType.MIN;
                break;
        }
    }
}
