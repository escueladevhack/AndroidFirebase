package co.barnetapp.barnet.presentation.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.barnetapp.barnet.FirebaseHelper;
import co.barnetapp.barnet.Mensaje;
import co.barnetapp.barnet.R;

public class ChatRoomsActivity extends AppCompatActivity implements ChildEventListener {

    private ArrayAdapter<String> arrayAdapter;
    private List<String> mensajes;
    private DatabaseReference reference;
    private FirebaseAnalytics firebaseAnalytics;

    @Bind(R.id.chat_room)
    ListView lstChatRoom;

    @Bind(R.id.chat_room_mensaje)
    EditText txtChatMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_rooms);

        ButterKnife.bind(this);

        mensajes = new ArrayList<>();

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mensajes);

        lstChatRoom.setAdapter(arrayAdapter);

        reference = FirebaseDatabase.getInstance().getReference().child("questions");

        reference.addChildEventListener(this);

        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

    }

    @OnClick(R.id.btnInvite)
    public void clickInvite() {

        Bundle datos = new Bundle();
        datos.putString(FirebaseAnalytics.Param.GROUP_ID, "invites");
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SHARE, datos);

        Bundle datosCompra = new Bundle();
        datos.putString(FirebaseAnalytics.Param.VALUE, "50000");
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.ECOMMERCE_PURCHASE, datosCompra);

        firebaseAnalytics.setUserProperty("favoritacomida", "Frijoles");

        // Se crea intente para que se abra la actividad para invitar a otras personas a la app
        Intent intent = new AppInviteInvitation.IntentBuilder("Invitación al chat")
                .setMessage("Empieza a chatear con tus amigos")
                .setDeepLink(Uri.parse("http://devhack.co"))
                .build();

        startActivityForResult(intent, 1);

    }

    @OnClick(R.id.btnEnviar)
    public void clickEnviar() {

        try {

            // Crear un mensaje, y push a firebase
            Mensaje mensaje = new Mensaje(FirebaseHelper.getInstance().getAuthUserEmail(),
                    String.valueOf(Calendar.getInstance().getTimeInMillis()),
                    "",
                    txtChatMensaje.getText().toString());

            reference.push().setValue(mensaje);

            txtChatMensaje.setText("");

        } catch (Exception e) {
            FirebaseCrash.report(e);
        }
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        // Convertir el datasnap a Mensaje y adicionar al arrayadapter
        Mensaje mensaje = dataSnapshot.getValue(Mensaje.class);

        arrayAdapter.add(mensaje.getPregunta());
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
