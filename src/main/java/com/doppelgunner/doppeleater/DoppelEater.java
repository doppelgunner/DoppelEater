package com.doppelgunner.doppeleater;

import com.doppelgunner.doppeleater.database.generated.tables.records.EatenRecord;
import com.doppelgunner.doppeleater.database.generated.tables.records.EaterRecord;
import com.doppelgunner.doppeleater.eating.Chosen;
import com.doppelgunner.doppeleater.model.Eaten;
import com.doppelgunner.doppeleater.model.Eater;
import com.doppelgunner.doppeleater.model.FoodTag;
import com.doppelgunner.doppeleater.model.FoodTagList;
import com.doppelgunner.doppeleater.task.SearchEatensService;
import com.doppelgunner.doppeleater.task.SearchEatersService;
import com.doppelgunner.doppeleater.util.DBHandler;
import com.doppelgunner.doppeleater.util.DateHandler;
import com.doppelgunner.doppeleater.util.Validator;
import com.doppelgunner.doppeleater.view.MainController;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.svg.SVGGlyphLoader;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.container.DefaultFlowContainer;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.Select;
import org.jooq.impl.DSL;

import static com.doppelgunner.doppeleater.database.generated.Tables.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by robertoguazon on 04/01/2017.
 */
public class DoppelEater extends Application {

    public static int MIN_WIDTH = 824;
    public static int MIN_HEIGHT = 600;

    public static void main(String[] args){
        launch(args);
        //testTag(); //TODO delete tests
        //testDate();
        //testEaten();
        //testSQL();
    }

    @FXMLViewFlowContext
    private ViewFlowContext viewFlowContext;

    @Override
    public void start(Stage primaryStage) throws Exception {

        new Thread(()->{
            try {
                //he just loaded some svg from a font file
                SVGGlyphLoader.loadGlyphsFont(DoppelEater.class.getResourceAsStream("/fonts/icomoon.svg"),"icomoon.svg");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }).start();

        viewFlowContext = new ViewFlowContext();
        Chosen.setViewFlowContext(viewFlowContext);

        viewFlowContext.register("stage", primaryStage);

        Flow flow = new Flow(MainController.class);
        DefaultFlowContainer container = new DefaultFlowContainer();
        flow.createHandler(viewFlowContext).start(container);

        JFXDecorator decorator = new JFXDecorator(primaryStage, container.getView());
        decorator.setMinWidth(MIN_WIDTH);
        decorator.setMinHeight(MIN_HEIGHT);
        decorator.setCustomMaximize(true);
        Scene scene = new Scene(decorator);
        //scene.getStylesheets().add("css/jfoenix-design.css");
        //scene.getStylesheets().add("css/main.css");
        primaryStage.setMinWidth(MIN_WIDTH);
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
        /*
        Scene scene = new Scene(flow.start());
        primaryStage.setScene(scene);
        primaryStage.setTitle("DoppelEater");
        primaryStage.show();
         */

        //testSearch(); //TODO delete tests
    }

    //TODO - TESTS delete

    public static void testSearch() {
        Result<EaterRecord> eaterRecords = DBHandler.searchEaters("bob");
        for (EaterRecord eaterRecord : eaterRecords) {
            Eater eater = Eater.createEater(eaterRecord);
            System.out.println(eater.getUsername());
        }

        System.out.println("------------");

        Result<EatenRecord> eatenRecords = DBHandler.searchEatens("bob","chicken");
        for (EatenRecord eatenRecord : eatenRecords) {
            Eaten eaten = Eaten.createEaten(eatenRecord);
            System.out.println(eaten.getFoodId() + ": " + eaten.getFoodTagList());
        }

        //service check
        System.out.println("------services------");
        SearchEatersService searchEatersService = new SearchEatersService();
        searchEatersService.setSearch("bob");
        searchEatersService.setOnSucceeded(t -> {
            Result<EaterRecord> e1 = (Result<EaterRecord>) t.getSource().getValue();
            for (EaterRecord eaterRecord : e1) {
                Eater eater = Eater.createEater(eaterRecord);
                System.out.println(eater.getUsername());
            }
        });
        searchEatersService.start();

        System.out.println("------------");

        SearchEatensService searchEatensService = new SearchEatensService();
        searchEatensService.setSearch("a");
        searchEatensService.setUsername("bob");
        searchEatensService.setOnSucceeded(t -> {
            Result<EatenRecord> e1 = (Result<EatenRecord>) t.getSource().getValue();
            for (EatenRecord eatenRecord : e1) {
                Eaten eaten = Eaten.createEaten(eatenRecord);
                System.out.println(eaten.getFoodId() + ": " + eaten.getFoodTagList());
            }
        });
        searchEatensService.start();
    }

    public static void testSQL() {
        DSLContext exec = DBHandler.getExecutor();
        int count = exec.fetchCount(exec.selectFrom(EATEN).where("strftime('%m', dateEaten) = '01'"));
        System.out.println(count);

        String sqlTemporary = "create temporary table DateCount (" +
                "dateGroup text not null," +
                "eatenCount integer not null" +
                ")" +
                "" +
                "insert into DateCount(dateGroup, eatenCount)" +
                "";
    }

    public static void testEaten() {
        Eaten eaten = DBHandler.getEaten(5);
        System.out.println(eaten);
        System.out.println(DateHandler.convertLongDateToAgoString(eaten.getDateEaten(), new Date()));
    }

    public static void testTag() {
        //for testing
        FoodTagList foodTagList = new FoodTagList("master");
        System.out.println(foodTagList);
    }

    public static void testDate() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        Date sDate = DateHandler.convertDate("2017/1/2 9:44:00");

        System.out.println("HOUR: " + calendar.get(Calendar.HOUR));
        System.out.println("HOUR_OF_DAY: " + calendar.get(Calendar.HOUR_OF_DAY));
        System.out.println("AM_PM: " + calendar.get(Calendar.AM_PM));
        System.out.println("DATE: " + calendar.get(Calendar.DATE));
        System.out.println("DAY_OF_MONTH: " + calendar.get(Calendar.DAY_OF_MONTH));
        System.out.println("DAY_OF_WEEK: " + calendar.get(Calendar.DAY_OF_WEEK));
        System.out.println("DAY_OF_WEEK_IN_MONTH: " + calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH));
        System.out.println("DAY_OF_YEAR: " + calendar.get(Calendar.DAY_OF_YEAR));

        System.out.println("-----");
        System.out.println(DateHandler.convertLongDateToAgoString(sDate.getTime(),date.getTime()));
    }


}
