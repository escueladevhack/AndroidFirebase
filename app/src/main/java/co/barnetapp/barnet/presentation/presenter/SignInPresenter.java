package co.barnetapp.barnet.presentation.presenter;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import co.barnetapp.barnet.presentation.view.fragments.login.ISignInView;

/**
 * Created by jggomezt on 14/10/2016.
 */
public class SignInPresenter implements ISignInPresenter {

    private ISignInView signInView;
    private FirebaseAuth mAuth;

    public SignInPresenter(ISignInView view) {
        signInView = view;
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void signIn(String email, String password) {
        signInView.showLoading();


        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            signInView.showAuthSuccesful();

                        } else {
                            signInView.showAuthError(task.getException());
                        }
                    }
                });

        signInView.hideLoading();
    }
}
