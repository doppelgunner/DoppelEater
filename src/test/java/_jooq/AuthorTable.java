package _jooq;

import org.jooq.Name;

/**
 * Created by robertoguazon on 03/01/2017.
 */
public class AuthorTable extends AbstractTable {

    public final String FIRST_NAME;
    public final String LAST_NAME;

    public AuthorTable(String name, String id, String firstName, String lastName) {
        super(name, id);
        FIRST_NAME = firstName;
        LAST_NAME = lastName;
    }
}
