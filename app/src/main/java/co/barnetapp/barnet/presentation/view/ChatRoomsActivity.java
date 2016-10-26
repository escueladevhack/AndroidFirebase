package co.barnetapp.barnet.presentation.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

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
    private DatabaseReference dbReference;
    private List<String> mensajes;

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

        dbReference =
                FirebaseDatabase.getInstance().getReference().child("questions");

        dbReference.addChildEventListener(this);
    }

    @OnClick(R.id.btnEnviar)
    public void clickEnviar() {

        try {

            // Crear un mensaje, y push a firebase
            Mensaje objMensaje = new Mensaje(FirebaseHelper.getInstance().getAuthUserEmail(),
                    String.valueOf(Calendar.getInstance().getTimeInMillis()), "",
                    String.valueOf(txtChatMensaje.getText()));

            dbReference.push().setValue(objMensaje);

            FirebaseCrash.log("Pregunta enviada => "+objMensaje.getPregunta());

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
