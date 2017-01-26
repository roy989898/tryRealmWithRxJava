package pom.realmwithrxhava.Models;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * Created by Roy.Leung on 26/1/17.
 */

public class User extends RealmObject {
    private String name;
    private String age;

    @Ignore
    private int sessionId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }
}
