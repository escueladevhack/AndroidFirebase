package co.barnetapp.barnet.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import butterknife.ButterKnife;
import co.barnetapp.barnet.R;
import co.barnetapp.barnet.presentation.view.activities.PublicidadActivity;
import co.barnetapp.barnet.presentation.view.fragments.login.SignInFragment;
import co.barnetapp.barnet.presentation.view.fragments.login.SignUpFragment;

public class LoginActivity extends AppCompatActivity
        implements SignUpFragment.OnSignUpFragmentListener, SignInFragment.OnSignInFragmentListener {

    private FirebaseRemoteConfig firebaseRemoteConfig;
    private final int cacheExpiration = 5;
    private final String Mostrar_Publicidad_Key = "mostrarPublicidad";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        setFragment(SignInFragment.newInstance());

        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        firebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);
    }

    private void showAdvertising() {
        firebaseRemoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            firebaseRemoteConfig.activateFetched();
                        } else {
                            Toast.makeText(LoginActivity.this,
                                    "Fecth Fallo > ", Toast.LENGTH_SHORT).show();
                        }

                        if (firebaseRemoteConfig.getBoolean(Mostrar_Publicidad_Key)) {
                            Intent intent = new Intent(LoginActivity.this,
                                    PublicidadActivity.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(LoginActivity.this,
                                    ChatRoomsActivity.class);
                            startActivity(intent);
                        }
                    }
                });
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
    }
}
