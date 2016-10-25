package co.barnetapp.barnet;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jggomezt on 09/09/2016.
 */
public class FirebaseHelper {

    private FirebaseDatabase firebaseDatabase;
    private final static String SEPARATOR = "__";
    private final static String CHATS_PATH = "chats";
    private final static String USERS_PATH = "users";

    private static class SingletonHolder {
        private static final FirebaseHelper instance = new FirebaseHelper();
    }

    private FirebaseHelper() {

    }

    public static FirebaseHelper getInstance() {
        return SingletonHolder.instance;
    }

    public DatabaseReference getFirebaseDatabase() {
        return firebaseDatabase.getInstance().getReference();
    }

    public void setFirebaseDatabase(FirebaseDatabase firebaseDatabase) {
        this.firebaseDatabase = firebaseDatabase;
    }

    public String getAuthUserEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            return user.getEmail();
        }

        return null;
    }

    public String getAuthUserID() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            return user.getUid();
        }

        return null;
    }

    public DatabaseReference getUserReference(String email) {
        DatabaseReference userReference = null;
        if (email != null) {
            String emailKey = email.replace(".", "_");
            userReference = getFirebaseDatabase().child(USERS_PATH).child(emailKey);
        }
        return userReference;
    }

    public DatabaseReference getMyUserReference() {
        return getUserReference(getAuthUserEmail());
    }

    public DatabaseReference getChatsReference(String receiver){

        String keySender = getAuthUserEmail().replace(",", "_");
        String keyReceiver = receiver.replace(",", "_");

        String keyChat = keySender + SEPARATOR + keyReceiver;

        if (keySender.compareTo(keyReceiver) > 0){
            keyChat = keyReceiver + SEPARATOR + keySender;
        }

        return  getFirebaseDatabase().getRoot().child(CHATS_PATH).child(keyChat);

    }

    public void changeUserConnectionStatus(boolean online){
        if (getMyUserReference() != null){
            Map<String, Object> map = new HashMap<>();
            map.put("online", online);
            getMyUserReference().updateChildren(map);
            notifyContactsConnectionChange(online);
        }
    }

    public void signOff(boolean online){
        notifyContactsConnectionChange(online, true);
        FirebaseAuth.getInstance().signOut();
    }

    public void notifyContactsConnectionChange(boolean online) {
        notifyContactsConnectionChange(online, false);
    }

    private void notifyContactsConnectionChange(boolean online, boolean singoff) {

    }


}
