package info.anth.firebaseauth2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;

import info.anth.firebaseauth2.Login.LoginActivity;

// Note: FirebaseLoginBaseActivity extends AppCompatActivity the original extends
public class MainActivity extends AppCompatActivity {

    public static String TAG = "ajc";
    private Firebase mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRef = new Firebase(getResources().getString(R.string.FIREBASE_BASE_REF));
        mRef.addAuthStateListener(new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                if (authData != null) {
                    String provider = authData.getProvider();
                    String uid = authData.getUid();
                    String email = "Not provided by authData";
                    String profileImageUrl = "Not provided by authData";
                    String displayName = "Not provided by authData";

                    if(authData.getProviderData().containsKey("email")) email = authData.getProviderData().get("email").toString();
                    if(authData.getProviderData().containsKey("profileImageURL")) profileImageUrl = authData.getProviderData().get("profileImageURL").toString();
                    if(authData.getProviderData().containsKey("displayName")) displayName = authData.getProviderData().get("displayName").toString();

                    String message = "Logged In\n";
                    message += "\nProvider: " + provider;
                    message += "\nUID: " + uid;
                    message += "\nEmail: " + email;
                    message += "\nDisplay Name: " + displayName;
                    message += "\nprofileImageUrl: " + profileImageUrl;

                    // User Logged IN
                    Log.i("ajc", "AuthData: " + String.valueOf(authData));
                    Log.i("ajc", message);

                    TextView loginText = (TextView) findViewById(R.id.login_text);
                    loginText.setText(message);

                } else {
                    String message = "Logged Out";
                    // User Logged out
                    Log.i("ajc", message);

                    TextView loginText = (TextView) findViewById(R.id.login_text);
                    loginText.setText(message);
                }
            }
        });
    }

    public void callLogin (View view) {
        //Intent intent = new Intent(this, LoginActivity.class);
        //startActivity(intent);

        startActivity(new Intent(this, LoginActivity.class));
    }

    public void callLogout (View view) {
        mRef.unauth();
    }

    public void callTestActivity (View view) {
        //Intent intent = new Intent(this, LoginActivity.class);
        //startActivity(intent);

        startActivity(new Intent(this, TestActivity.class));
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("ajc", "MainActivity onResume");
    }

}
