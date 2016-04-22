package info.anth.firebaseauth;

import android.util.Log;

import com.firebase.client.AuthData;
import com.firebase.client.Config;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

/**
 * Created by Primary on 4/19/2016.
 */
public class FirebaseAuthApplication extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);

        // update the default config to save all data locally.
        Config config = Firebase.getDefaultConfig();
        config.setPersistenceEnabled(true);
        Firebase.setDefaultConfig(config);
/*
        Log.i("ajc", "in application code");

        Firebase test = new Firebase(getResources().getString(R.string.FIREBASE_BASE_REF));

        String auth1 = test.getAuth().toString();
        Log.i("ajc", "auth1: " + auth1);

        test.authAnonymously(new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                // we've authenticated this session with your Firebase app
                Log.i("ajc", "Authenticated Anonymously");
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                // there was an error
                Log.i("ajc", "Error: " + firebaseError.toString());
            }
        });

        String auth2 = test.getAuth().toString();
        Log.i("ajc", "auth2: " + auth2);
*/
        /*.authWithOAuthToken("google", "<OAuth Token>", new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                // the Google user is now authenticated with your Firebase app
                Log.i("ajc", "Authenticated");
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                // there was an error
                Log.i("ajc", "Error: " + firebaseError.toString());
            }
        });*/

    }
}
