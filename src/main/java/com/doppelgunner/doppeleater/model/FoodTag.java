package com.doppelgunner.doppeleater.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by robertoguazon on 07/01/2017.
 */
public class FoodTag {

    public static final char separator = ';';
    private StringProperty tagName = new SimpleStringProperty();
    //private FoodType foodType; //TODO - fix in future
    //'%word3%' for searching if tags contains in sql

    public enum FoodType {
        GO, GROW, GLOW
    }

    public FoodTag() {}
    public FoodTag(String tagName) {
        setTagName(tagName);
    }

    public boolean isEmpty() {
        return tagName == null || tagName.isEmpty().get();
    }

    public static char getSeparator() {
        return separator;
    }

    public String getTagName() {
        return tagName.get();
    }

    public StringProperty tagNameProperty() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName.set(tagName);
    }

    public String toString() {
        return getTagName();
    }
}
