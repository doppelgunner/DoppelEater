package com.doppelgunner.doppeleater.model;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by robertoguazon on 07/01/2017.
 */
public class FoodTagList {

    public static final String SEPARATOR = ";";

    private ArrayList<FoodTag> foodTags;

    public FoodTagList(FoodTag ... foodTagsArray) {
        this();
        addAll(foodTagsArray);
    }

    public FoodTagList(String foodTagsString) {
        this();
        ArrayList<String> stringList = convertToArray(foodTagsString);
        stringList.forEach(this::add);
    }

    public FoodTagList(ArrayList<FoodTag> foodTags) {
        this();
        this.foodTags = foodTags;
    }

    public FoodTagList() {
        foodTags = new ArrayList<>();
    }

    public void addAll(FoodTag ... foodTagsArray) {
        for (int i = 0; i < foodTagsArray.length; i++) {
            add(foodTagsArray[i]);
        }
    }
    public void addAll(String ... foodTagsStringArray) {
        for (int i = 0; i < foodTagsStringArray.length; i++) {
            add(foodTagsStringArray[i]);
        }
    }

    public void add(FoodTag foodTag) {
        if (contains(foodTag)) return;
        foodTags.add(foodTag);
    }
    public void add(String foodTagString) {
        if (contains(foodTagString)) return;
        foodTags.add(new FoodTag(foodTagString));
    }

    public void remove(FoodTag foodTag) {
        foodTags.remove(foodTag);
    }

    public int getLength() {
        return foodTags.size();
    }

    public FoodTag getFoodTag(int at) {
        return foodTags.get(at);
    }


    public String getFoodTagsString() {
        if (foodTags == null || foodTags.isEmpty()) return null;

        String tagsString = "" + SEPARATOR;
        for (int i = 0; i < getLength(); i++) {
            tagsString += getFoodTag(i) + SEPARATOR;
        }
        return tagsString;
    }

    public ArrayList<FoodTag> getFoodTagsList() {
        return foodTags;
    }

    public static FoodTagList convertToFoodTagList(String foodTagsString) {
        FoodTagList foodTagList = new FoodTagList();
        ArrayList<String> foodTagsStringList = convertToArray(foodTagsString);
        foodTagsStringList.forEach(foodTagList::add);
        return foodTagList;
    }

    private static ArrayList<String> convertToArray(String foodTagsString) {
        if (foodTagsString == null || foodTagsString.isEmpty()) return null;

        if (foodTagsString.charAt(0) == ';') {
            foodTagsString = foodTagsString.substring(1,foodTagsString.length());
        }

        String[] foodTagsArray = foodTagsString.split(SEPARATOR);
        ArrayList<String> foodTagsStringList = new ArrayList<>();
        for (int i = 0; i < foodTagsArray.length; i++) {
            String string = foodTagsArray[i];
            if (!foodTagsStringList.contains(string)) {
                foodTagsStringList.add(string);
            }
        }
        return foodTagsStringList;
    }

    public boolean contains(String foodTagString) {
        for (FoodTag f : foodTags) {
            if (f.getTagName().contains(foodTagString))
                return true;
        }

        return false;
    }

    public boolean contains(FoodTag foodTag) {
        return contains(foodTag.getTagName());
    }

    public String getFoodTagsPrettyString() {
        if (foodTags == null || foodTags.isEmpty()) return null;
        return foodTags.stream().map(foodTag -> foodTag.toString()).collect(Collectors.joining(", "));
    }

    public String toString() {
        return getFoodTagsPrettyString();
    }
}
