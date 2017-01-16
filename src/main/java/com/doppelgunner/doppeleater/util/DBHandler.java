package com.doppelgunner.doppeleater.util;

import com.doppelgunner.doppeleater.database.generated.tables.records.EatenRecord;
import com.doppelgunner.doppeleater.database.generated.tables.records.EaterRecord;
import com.doppelgunner.doppeleater.eating.Chosen;
import com.doppelgunner.doppeleater.model.Eaten;
import com.doppelgunner.doppeleater.model.Eater;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import static com.doppelgunner.doppeleater.database.generated.Tables.*;

import java.io.*;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.function.BiConsumer;

/**
 * Created by robertoguazon on 06/01/2017.
 */
public class DBHandler {

    public static final String DB_PATH = "databases/database.db";
    public static final String DB_URL = "jdbc:sqlite:" + DB_PATH;

    private static Connection connection;

    private static void connectToDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static DSLContext getExecutor() {
        if (connection == null) {
            connectToDB();
        }

        DSLContext exec = DSL.using(connection, SQLDialect.SQLITE);
        return exec;
    }

    public static String pushEater(Eater eater) {
        DSLContext exec = null;
        try {
            exec = getExecutor();
            exec.insertInto(EATER
                    ,EATER.USERNAME, EATER.EMAIL, EATER.PASSWORD, EATER.TIME_STARTED)
                    .values(eater.getUsername(),eater.getEmail(),eater.getPassword(), DateHandler.convertDate(eater.getTimeStarted()))
                    .executeAsync();
        } catch (Exception e) {
            e.printStackTrace();
            return "error in pushing data";
        } finally {
            exec.close();
        }

        return null;
    }

    public static String update(Eater eater) {
        DSLContext exec = getExecutor();
        try {
            exec.update(EATER)
                    .set(EATER.EMAIL, eater.getEmail())
                    .set(EATER.BIO, eater.getBio())
                    .set(EATER.GENDER, serialize(eater.getGender()))
                    .set(EATER.PRIVACY, serialize(eater.getPrivacy()))
                    .set(EATER.BIRTHDAY, DateHandler.convertBirthDate(eater.getBirthDay()))
                    .set(EATER.IMAGE, serialize(ImageHandler.convertToImageIcon(eater.getImage())))
                    .where(EATER.USERNAME.equal(eater.getUsername()))
                    .executeAsync();
        } catch (Exception e) {
            e.printStackTrace();
            return "error in updating data";
        } finally {
            exec.close();
        }

        return null;
    }

    public static String update(Eaten eaten) {

        DSLContext exec = getExecutor();
        try {
            exec.update(EATEN)
                    .set(EATEN.MEAL, serialize(eaten.getMealType()))
                    .set(EATEN.FOODTAGLIST, eaten.getFoodTagList().getFoodTagsString())
                    .set(EATEN.HOWFAST, eaten.getHowFast())
                    .set(EATEN.DELICIOUSNESS, eaten.getDeliciousness())
                    .set(EATEN.DATEEATEN, DateHandler.convertDate(eaten.getDateEaten()))
                    .where(EATEN.FOODID.equal(eaten.getFoodId()))
                    .executeAsync();
        } catch (Exception e) {
            e.printStackTrace();
            return "error in updating data";
        } finally {
            exec.close();
        }

        return null;
    }

    public static String delete(Eaten eaten) {
        DSLContext exec = null;
        try {
            exec = getExecutor();
            exec.deleteFrom(EATEN)
                    .where(EATEN.FOODID.equal(eaten.getFoodId()))
                    .executeAsync();
        } catch (Exception e) {
            e.printStackTrace();
            return "error in deleting data";
        } finally {
            exec.close();
        }

        return null;
    }

    public static String push(Eaten eaten) {
        DSLContext exec = null;
        try {
            exec = getExecutor();
            exec.insertInto(EATEN
                    ,EATEN.MEAL, EATEN.FOODTAGLIST,
                    EATEN.HOWFAST, EATEN.DELICIOUSNESS, EATEN.USERNAME,
                    EATEN.DATEEATEN)
                    .values(
                            serialize(eaten.getMealType()), eaten.getFoodTagList().getFoodTagsString(),
                            eaten.getHowFast(), eaten.getDeliciousness(), eaten.getUsername(),
                            DateHandler.convertDate(eaten.getDateEaten()))
                    .executeAsync();
        } catch (Exception e) {
            e.printStackTrace();
            return "error in pushing data";
        } finally {
            exec.close();
        }
        return null;
    }

    public static byte[] serialize(Serializable object) {
        ByteArrayOutputStream baos;
        ObjectOutputStream oos;

        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            oos.close();

            return baos.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Object deserialize(Object object) {
        if (object == null) return null;

        return deserialize((byte[]) object);
    }

    public static Object deserialize(byte[] bytes) {
        if (bytes == null) return null;

        ObjectInputStream ois;

        try {

            ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            Object object = ois.readObject();
            ois.close();

            return object;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    //TODO - must use username
    public static Eaten getEaten(int id) {
        DSLContext exec = null;
        Result<EatenRecord> result = null;
        try {
            exec = getExecutor();
            result = exec.selectFrom(EATEN).where(EATEN.FOODID.equal(id)).fetch();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            exec.close();
        }

        if (result != null && result.size() > 0) {
            EatenRecord eatenRecord = result.get(0);
            Eaten eaten = Eaten.createEaten(eatenRecord);

            return eaten;
        }

        return null;
    }

    public static Result<EatenRecord> getEatenMany(String username, long dateSince) {
        DSLContext exec = null;
        Result<EatenRecord> result = null;
        try {
            exec = DBHandler.getExecutor();
            result = exec
                    .selectFrom(EATEN)
                    .where(EATEN.USERNAME.equal(username).and(EATEN.DATEEATEN.greaterOrEqual(DateHandler.convertDate(dateSince))))
                    .orderBy(EATEN.DATEEATEN.desc())
                    .fetch();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            exec.close();
        }

        return result;
    }

    public static Result<EatenRecord> getEatenMany(String username, int number) {
        DSLContext exec = null;
        Result<EatenRecord> result = null;
        try {
            exec = DBHandler.getExecutor();
            result = exec
                    .selectFrom(EATEN)
                    .where(EATEN.USERNAME.equal(username))
                    .orderBy(EATEN.DATEEATEN.desc())
                    .limit(number)
                    .fetch();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            exec.close();
        }

        return result;
    }

    public static String login(String usernameOrEmail, String password) {
        DSLContext exec = null;
        try {
            exec = DBHandler.getExecutor();
            EaterRecord eaterRecord = exec
                    .selectFrom(EATER)
                    .where(EATER.USERNAME.equal(usernameOrEmail).or(EATER.EMAIL.equal(usernameOrEmail)))
                    .and(EATER.PASSWORD.equal(password))
                    .fetchAny();

            if (eaterRecord == null) {
                return "User not found";
            }

            Eater rebornEater = Eater.createEater(eaterRecord);
            Chosen.setEater(rebornEater); // set to eater to eating
        } catch (Exception e) {
            e.printStackTrace();
            return "Error in logging in";
        } finally {
            if (exec != null) exec.close();
        }

        return null;
    }

    public static String signupCheck(Eater eater) {
        DSLContext exec = null;
        Result<EaterRecord> result = null;
        try {
            exec = DBHandler.getExecutor();
            result = exec
                    .selectFrom(EATER)
                    .where(EATER.USERNAME.equal(eater.getUsername()).or(EATER.EMAIL.equal(eater.getEmail())))
                    .fetch();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (exec != null) exec.close();
        }
        int i = 0;
        for (EaterRecord eaterRecord : result) {
            System.out.println(eaterRecord.get(0));
            i++;
        }

        if (i > 0) {
            System.out.println("Same username/email with: " + i + " account");
            return "Account already exists";
        }

        return null;
    }

    public static Result<EaterRecord> searchEaters(String search) {
        DSLContext exec = null;
        Result<EaterRecord> result = null;

        String query = "%" + search + "%";
        try {
            exec = DBHandler.getExecutor();
            result = exec
                    .selectFrom(EATER)
                    .where(
                            EATER.USERNAME.like(query)
                            .or(EATER.EMAIL.like(query))
                            .or(EATER.BIO.like(query))
                            .or(EATER.TIME_STARTED.like(query))
                            .or(EATER.BIRTHDAY.like(query))
                    )
                    .fetch();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (exec != null) exec.close();
        }

        return result;
    }

    public static Result<EatenRecord> searchEatens(String username, String search) {
        DSLContext exec = null;
        Result<EatenRecord> result = null;

        String query = "%" + search + "%";
        try {
            exec = DBHandler.getExecutor();
            result = exec
                    .selectFrom(EATEN)
                    .where(
                            EATEN.FOODID.like(query)
                                    .or(EATEN.FOODTAGLIST.like(query))
                                    .or(EATEN.DATEEATEN.like(query))
                    ).and(EATEN.USERNAME.equal(username))
                    .fetch();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (exec != null) exec.close();
        }

        return result;
    }
}
