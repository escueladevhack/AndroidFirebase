package co.barnetapp.barnet.presentation.presenter;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import co.barnetapp.barnet.presentation.view.fragments.login.ISignUpView;

/**
 * Created by jggomezt on 14/10/2016.
 */
public class SignUpPresenter implements ISignUpPresenter {

    private ISignUpView registerView;
    private FirebaseAuth mAuth;

    public SignUpPresenter(ISignUpView view) {
        this.registerView = view;
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void signUp(String name, String email, String password) {
        registerView.showLoading();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    registerView.showAuthMessage();
                    //TODO: actividad Home
                } else {
                    registerView.showErrorAuthMessage(task.getException());
                }
            }
        });


        registerView.hideLoading();
    }
}
