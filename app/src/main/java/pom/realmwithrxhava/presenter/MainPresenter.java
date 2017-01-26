package pom.realmwithrxhava.presenter;

/**
 * Created by Roy.Leung on 26/1/17.
 */

public interface MainPresenter {
    void createOrUpdateUser(String name, String age);

    void queryAllUser();

    void deleteUser(int position);

    void unbind();
}
