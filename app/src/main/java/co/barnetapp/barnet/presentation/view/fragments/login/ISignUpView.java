package co.barnetapp.barnet.presentation.view.fragments.login;

/**
 * Created by jggomezt on 14/10/2016.
 */

public interface ISignUpView {

    void showLoading();

    void hideLoading();

    void clickRegister();

    void goHome();

    void showAuthMessage();

    void showErrorAuthMessage(Exception error);
}
