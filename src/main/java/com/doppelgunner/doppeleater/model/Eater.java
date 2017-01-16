package com.doppelgunner.doppeleater.model;

import com.doppelgunner.doppeleater.database.generated.tables.records.EaterRecord;
import com.doppelgunner.doppeleater.util.DBHandler;
import com.doppelgunner.doppeleater.util.DateHandler;
import com.doppelgunner.doppeleater.util.ImageHandler;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

import javax.swing.*;
import java.util.Date;

/**
 * Created by robertoguazon on 05/01/2017.
 */
public class Eater implements Searchable, Disposable {

    private StringProperty username = new SimpleStringProperty();
    private StringProperty email = new SimpleStringProperty();
    private StringProperty password = new SimpleStringProperty();
    private StringProperty bio = new SimpleStringProperty();
    private PrivacyType privacy = PrivacyType.PUBLIC;
    private GenderType gender = null;
    private Date timeStarted;
    private Date birthDay;
    private Image image; //TODO provide default image

    public void copy(Eater eater) {
        setUsername(eater.getUsername());
        setEmail(eater.getEmail());
        setPassword(eater.getPassword());
        setBio(eater.getBio());
        setPrivacy(eater.getPrivacy());
        setGender(eater.getGender());
        setTimeStarted(eater.getTimeStarted());
        setBirthDay(eater.getBirthDay());
        setImage(eater.getImage());
    }

    public Eater clone() {
        Eater newEater = new Eater();
        newEater.copy(this);
        return newEater;
    }

    public static Eater createEater() {
        Eater eater = new Eater();
        eater.timeStarted = null;
        eater.birthDay = null;
        eater.image = null;

        return eater;
    }

    public static Eater createEater(EaterRecord record) {
        Eater eater = createEater();
        eater.setUsername(record.getUsername());
        eater.setEmail(record.getEmail());
        eater.setPassword(record.getPassword());
        eater.setBio(record.getBio());
        eater.setPrivacy((PrivacyType) DBHandler.deserialize(record.getPrivacy()));
        eater.setGender((GenderType) DBHandler.deserialize(record.getGender()));
        eater.setTimeStarted(DateHandler.convertDate(record.getTimeStarted()));
        eater.setBirthDay(DateHandler.convertBirthDate(record.getBirthday()));
        eater.setImage(ImageHandler.convertToImage((ImageIcon)DBHandler.deserialize(record.getImage())));
        return eater;
    }

    private Eater() {}

    public String toString() {
        return "username: " + getUsername() + "\n" +
                "email: " + getEmail() + "\n" +
                "bio: " + getBio() + "\n" +
                "privacy: " + getPrivacy() + "\n" +
                "gender: " + getGender() + "\n" +
                "timeStarted: " + getTimeStarted()+ "\n" +
                "birthday: " + getBirthDay() + "\n" +
                "image: " + getImage();
    }

    @Override
    public Image getSearchImage() {
        return image;
    }

    @Override
    public String getSearchTitle() {
        return getUsername();
    }

    @Override
    public String getSearchDescription() {
        return getBio(); //TODO provide a separator to add gender, birthday, timeStarted etc
    }

    @Override
    public void dispose() {
        //TODO - dispose
    }

    public static final PrivacyType[] PRIVACY_TYPES = {
            PrivacyType.PUBLIC, PrivacyType.PRIVATE
    };

    public static final GenderType[] GENDER_TYPES = {
            GenderType.MALE, GenderType.FEMALE,
    };

    public enum PrivacyType {
        PUBLIC, PRIVATE,
    }

    public enum GenderType {
        MALE, FEMALE
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

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public PrivacyType getPrivacy() {
        return privacy;
    }

    public void setPrivacy(PrivacyType privacy) {
        this.privacy = privacy;
    }

    public GenderType getGender() {
        return gender;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public Date getTimeStarted() {
        return timeStarted;
    }

    public void setTimeStarted(Date timeStarted) {
        this.timeStarted = timeStarted;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getBio() {
        return bio.get();
    }

    public StringProperty bioProperty() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio.set(bio);
    }
}

