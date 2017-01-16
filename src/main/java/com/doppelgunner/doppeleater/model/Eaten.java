package com.doppelgunner.doppeleater.model;

import com.doppelgunner.doppeleater.database.generated.tables.records.EatenRecord;
import com.doppelgunner.doppeleater.util.DBHandler;
import com.doppelgunner.doppeleater.util.DateHandler;
import javafx.beans.property.*;
import javafx.scene.image.Image;

import java.util.Date;
import java.util.Calendar;

/**
 * Created by robertoguazon on 05/01/2017.
 */
public class Eaten implements Searchable, Disposable {

    //foodId is only used optionally - database only (not important)
    private IntegerProperty foodId = new SimpleIntegerProperty();
    private MealType mealType = null;
    private FoodTagList foodTagList;
    private IntegerProperty howFast = new SimpleIntegerProperty();
    private IntegerProperty deliciousness = new SimpleIntegerProperty();
    private StringProperty username = new SimpleStringProperty();
    private Date dateEaten;

    public String toString() {
        return "foodId: " + foodId.get() + "\n" +
                "mealType: " + mealType + "\n" +
                "foodTagList: " + foodTagList + "\n" +
                "howFast: " + howFast.get() + "\n" +
                "deliciousness: " + deliciousness.get() + "\n" +
                "username: " + username.get() + "\n" +
                "dateEaten: " + dateEaten;
    }

    private Eaten () {}

    public static Eaten createEaten() {
        Eaten eaten = new Eaten();
        eaten.mealType = null;
        eaten.foodTagList = new FoodTagList();
        eaten.dateEaten = null;

        return eaten;
    }

    public static Eaten createEaten(EatenRecord eatenRecord) {
        Eaten eaten = createEaten();
        eaten.setFoodId(eatenRecord.getFoodid());
        eaten.setMealType((MealType) DBHandler.deserialize(eatenRecord.getMeal()));
        eaten.setFoodTagList(new FoodTagList(eatenRecord.getFoodtaglist()));
        eaten.setHowFast(eatenRecord.getHowfast());
        eaten.setDeliciousness(eatenRecord.getDeliciousness());
        eaten.setUsername(eatenRecord.getUsername());
        eaten.setDateEaten(DateHandler.convertDate(eatenRecord.getDateeaten()));
        return eaten;
    }

    public static final MealType[] MEAL_TYPES = {
            MealType.TASTE,
            MealType.LIGHT,
            MealType.NORMAL,
            MealType.HEAVY,
            MealType.GLUTTON};

    public enum MealType {
        TASTE, LIGHT, NORMAL, HEAVY, GLUTTON
    }

    @Override
    public Image getSearchImage() {
        return null;
    }

    @Override
    public String getSearchTitle() {
        String date = "";
        if (dateEaten != null) {
            date += DateHandler.convertLongDateToAgoString(dateEaten.getTime(), new Date().getTime());
        }

        return "FOOD ID: " + getFoodId() + "\n" +
                getMealType() + "\n" +
                date;
    }

    @Override
    public String getSearchDescription() {
        return "Food tags: " + getFoodTagList().toString().toUpperCase() + "\n" +
                "How fast: " + howFast.get() + "\n" +
                "Deliciousness: " + deliciousness.get();
    }

    public int getFoodId() {
        return foodId.get();
    }

    public IntegerProperty foodIdProperty() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId.set(foodId);
    }

    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }

    public FoodTagList getFoodTagList() {
        return foodTagList;
    }

    public void setFoodTagList(FoodTagList foodTagList) {
        this.foodTagList = foodTagList;
    }

    public int getHowFast() {
        return howFast.get();
    }

    public IntegerProperty howFastProperty() {
        return howFast;
    }

    public void setHowFast(int howFast) {
        this.howFast.set(howFast);
    }

    public int getDeliciousness() {
        return deliciousness.get();
    }

    public IntegerProperty deliciousnessProperty() {
        return deliciousness;
    }

    public void setDeliciousness(int deliciousness) {
        this.deliciousness.set(deliciousness);
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public Date getDateEaten() {
        return dateEaten;
    }

    public void setDateEaten(Date dateEaten) {
        this.dateEaten = dateEaten;
    }

    @Override
    public void dispose() {
        //TODO - dispose
    }
}
