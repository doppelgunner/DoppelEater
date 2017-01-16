package _jooq;

import org.jooq.Name;

/**
 * Created by robertoguazon on 03/01/2017.
 */
public class AbstractTable {

    public final String ID;
    public final String NAME;

    public AbstractTable(String name, String id) {
        NAME = name;
        ID = id;
    }

    public String toString() {
        return NAME;
    }
}
