package co.barnetapp.barnet.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import butterknife.ButterKnife;
import co.barnetapp.barnet.FirebaseHelper;
import co.barnetapp.barnet.R;
import co.barnetapp.barnet.presentation.view.activities.PublicidadActivity;
import co.barnetapp.barnet.presentation.view.fragments.login.SignInFragment;
import co.barnetapp.barnet.presentation.view.fragments.login.SignUpFragment;

public class LoginActivity extends AppCompatActivity
        implements SignUpFragment.OnSignUpFragmentListener, SignInFragment.OnSignInFragmentListener {

    private FirebaseRemoteConfig firebaseRemoteConfig;
    private FirebaseRemoteConfig remoteConfig;
    private final int cacheExpiration = 5;
    private final String MOSTRAR_PUBLICIDAD_KEY = "mostrarPublicidad";
    private FirebaseAnalytics firebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        setFragment(SignInFragment.newInstance());

        remoteConfig = FirebaseRemoteConfig.getInstance();

        remoteConfig.setDefaults(R.xml.remote_config_defaults);

        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

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

    @Override
    public void goToChatRoom() {
        showAdvertising();

        Bundle datos = new Bundle();
        datos.putString(FirebaseAnalytics.Param.VALUE, FirebaseHelper.getInstance().getAuthUserEmail());
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, datos);
    }

    private void showAdvertising() {

        remoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            remoteConfig.activateFetched();
                        } else {
                            Toast.makeText(LoginActivity.this, "Error Fetched", Toast.LENGTH_SHORT).show();
                        }

                        if (remoteConfig.getBoolean(MOSTRAR_PUBLICIDAD_KEY)) {
                            Intent intent = new Intent(LoginActivity.this, PublicidadActivity.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(LoginActivity.this, ChatRoomsActivity.class);
                            startActivity(intent);
                        }

                    }
                });
    }
}
