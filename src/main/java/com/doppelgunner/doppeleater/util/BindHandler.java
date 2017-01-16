package com.doppelgunner.doppeleater.util;

import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.util.VetoException;
import javafx.scene.Node;

/**
 * Created by robertoguazon on 12/01/2017.
 */
public class BindHandler {

    public static void bindWithGlobalLink(Flow flow, Class controller) {
        flow.withGlobalLink(controller.getSimpleName(), controller);
    }

    public static void activateFlowHandler(FlowHandler flowHandler, Class controller) {
        try {
            flowHandler.handle(controller.getSimpleName());
        } catch (VetoException e) {
            e.printStackTrace();
        } catch (FlowException e) {
            e.printStackTrace();
        }
    }
}
