package co.barnetapp.barnet.presentation.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.Timer;
import java.util.TimerTask;

import co.barnetapp.barnet.R;
import co.barnetapp.barnet.presentation.view.ChatRoomsActivity;

public class PublicidadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicidad);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                Intent intent = new Intent(PublicidadActivity.this, ChatRoomsActivity.class);
                startActivity(intent);
                finish();
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 5000);

    }


}
