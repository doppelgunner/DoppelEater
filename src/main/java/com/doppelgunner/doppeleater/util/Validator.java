package com.doppelgunner.doppeleater.util;

import static com.doppelgunner.doppeleater.database.generated.Tables.*;

import com.doppelgunner.doppeleater.database.generated.tables.records.EaterRecord;
import com.doppelgunner.doppeleater.eating.Chosen;
import com.doppelgunner.doppeleater.model.Eater;
import com.doppelgunner.doppeleater.model.FoodTag;
import com.doppelgunner.doppeleater.model.FoodTagList;
import com.doppelgunner.doppeleater.task.LoginService;
import com.doppelgunner.doppeleater.task.LoginTask;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.concurrent.Task;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;

import java.util.Date;
import java.util.List;

/**
 * Created by robertoguazon on 06/01/2017.
 */
public class Validator {

    private static String validatorMessage = "Input required";

    public static void addValidator(JFXPasswordField passwordField) {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage(validatorMessage);

        passwordField.getValidators().add(validator);
        passwordField.focusedProperty().addListener((o,oldVal,newVal) -> {
            if (!newVal) passwordField.validate();
        });
    }

    public static void addValidator(JFXTextField textField) {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage(validatorMessage);

        textField.getValidators().add(validator);
        textField.focusedProperty().addListener((o,oldVal,newVal) -> {
            if (!newVal) textField.validate();
        });
    }

    public static boolean isEmpty(String s) {
        if (s == null || s.isEmpty()) {
            return true;
        }

        return false;
    }

    public static boolean isResultEmpty(Result result) {
        if (result == null) return true;
        if (result.isEmpty()) return true;
        return false;
    }

    public static boolean isTagEmpty(FoodTagList foodTagList) {
        String foodTags = foodTagList.getFoodTagsString();
        return foodTags == null || foodTags.isEmpty();
    }

    public static boolean isFoodTagStringValid(String foodTagString) {
        if (foodTagString == null || foodTagString.isEmpty()) return false;
        if (foodTagString.length() == 1 && foodTagString.contains(";")) return false;

        //TODO replace with regex?
        return true;
    }

    public static boolean isValid(Object object) {
        if (object == null) return false;
        return true;
    }

    public static boolean isValidDate(Date date) {
        if(!isValid(date)) return false;
        if (DateHandler.convertDate(date) == null) return false;

        return true;
    }

    public static boolean isListEmpty(List list) {
        if (list == null) return true;
        if (list.isEmpty()) return true;
        return false;
    }
}



