package co.barnetapp.barnet.presentation.view.fragments.login;

/**
 * Created by jggomezt on 14/10/2016.
 */

public interface ISignInView {

    void showLoading();

    void hideLoading();

    void clickSignIn();

    void clickSignUp();

    void showAuthSuccesful();

    void showAuthError(Exception error);
}
