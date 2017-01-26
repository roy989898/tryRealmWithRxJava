package pom.realmwithrxhava.view;

import java.util.List;

import pom.realmwithrxhava.Models.User;

/**
 * Created by Roy.Leung on 26/1/17.
 */

public interface MainView {
    void showAllTheUserinUI(List<User> userList);
}
