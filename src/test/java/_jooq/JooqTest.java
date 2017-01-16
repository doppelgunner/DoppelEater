package _jooq;

import _jooq.test.generated.tables.records.TestRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.util.sqlite.SQLiteDataType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static _jooq.test.generated.Tables.*;
/**
 * Created by robertoguazon on 03/01/2017.
 */
public class JooqTest {

    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:databases/test.db");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ERROR");
        }
        DSLContext create = DSL.using(connection, SQLDialect.SQLITE);
        /*
        create.createTable("eater")
                .column("id", SQLDataType.INTEGER)
                .column("userName", SQLDataType.VARCHAR)
                .column("email", SQLDataType.VARCHAR)
                .constraint(
                        DSL.constraint("pk_eater").primaryKey("id")
                )
                .execute();
        */

        //put it in a try catch
       /*
         create.insertInto(TEST
                ,TEST.USERNAME,TEST.EMAIL)
                .values("doppelgunner", "sweet@gmail.com")
                .values("monstersweets", "sweep@email.com")
                .execute();
        */

        for (TestRecord test : create.fetch(TEST)) {
            System.out.println(test.getUsername());
        }

        System.out.println("Closing...");
    }
}
