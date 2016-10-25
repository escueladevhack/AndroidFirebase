package co.barnetapp.barnet.presentation.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import co.barnetapp.barnet.R;
import co.barnetapp.barnet.presentation.view.fragments.login.SignInFragment;
import co.barnetapp.barnet.presentation.view.fragments.login.SignUpFragment;

public class LoginActivity extends AppCompatActivity
        implements SignUpFragment.OnSignUpFragmentListener, SignInFragment.OnSignInFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        setFragment(SignInFragment.newInstance());
    }

    private <T extends Fragment> void setFragment(T fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.loginFrame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    @Override
    public void goToSignUp() {
        setFragment(SignUpFragment.newInstance());
    }
}
