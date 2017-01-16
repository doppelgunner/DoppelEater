package com.doppelgunner.doppeleater.eating;

import com.doppelgunner.doppeleater.model.Disposable;
import com.doppelgunner.doppeleater.model.Eater;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.context.ViewFlowContext;
import io.datafx.controller.util.VetoException;

/**
 * Created by robertoguazon on 06/01/2017.
 */
public class Chosen {

    private static Eater eater;
    private static ViewFlowContext viewFlowContext;

    public static void sleep() {
        setEater(null);
    }

    public static void setEater(Eater e) {
        System.out.println("An eater has registered: " + e);
        eater = e;
    }

    public static Eater getEater() {
        return eater;
    }

    public static boolean isEating() {
        return eater != null;
    }

    public static void disposeAll() {
        if (eater == null) return;

        eater.dispose();
    }

    public static void setViewFlowContext(ViewFlowContext context) {
        viewFlowContext = context;
    }

    public static void goTo(Class controller) {
        String controllerName = controller.getSimpleName();
        FlowHandler centerFlowHandler = (FlowHandler)viewFlowContext.getRegisteredObject("centerFlowHandler");
        try {
            centerFlowHandler.handle(controllerName);
        } catch (VetoException e) {
            e.printStackTrace();
        } catch (FlowException e) {
            e.printStackTrace();
        }
        System.out.println("FINISHED LOADING: " + controllerName); //TODO -del
    }
}
