package info.anth.firebaseauth;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.ui.auth.core.AuthProviderType;
import com.firebase.ui.auth.core.FirebaseLoginBaseActivity;
import com.firebase.ui.auth.core.FirebaseLoginError;

// Note: FirebaseLoginBaseActivity extends AppCompatActivity the original extends
public class MainActivity extends FirebaseLoginBaseActivity {

    public static String TAG = "ajc";
    private Firebase mRef;
    private String mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRef = new Firebase(getResources().getString(R.string.FIREBASE_BASE_REF));
    }

    @Override
    protected void onStart() {
        super.onStart();
        logout();
        //mRef.unauth();
        //setEnabledAuthProvider(AuthProviderType.FACEBOOK);
        //setEnabledAuthProvider(AuthProviderType.TWITTER);
        setEnabledAuthProvider(AuthProviderType.GOOGLE);
        setEnabledAuthProvider(AuthProviderType.PASSWORD);

        if (mRef.getAuth() == null) showFirebaseLoginPrompt();
        Log.i(TAG, "Auth: " + String.valueOf(mRef.getAuth()));
    }

    @Override
    public void onFirebaseLoggedIn(AuthData authData) {
        Log.i(TAG, "Logged in to " + authData.getProvider());

        switch (authData.getProvider()) {
            case "password":
                mName = (String) authData.getProviderData().get("email");
                break;
            default:
                mName = (String) authData.getProviderData().get("displayName");
                break;
        }

        //invalidateOptionsMenu();
        //mRecycleViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFirebaseLoggedOut() {
        Log.i(TAG, "Logged out: " + mName);
        mName = "";
        //invalidateOptionsMenu();
        //mRecycleViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFirebaseLoginProviderError(FirebaseLoginError firebaseError) {
        Log.e(TAG, "Login provider error: " + firebaseError.toString());
        resetFirebaseLoginPrompt();
    }

    @Override
    public void onFirebaseLoginUserError(FirebaseLoginError firebaseError) {
        Log.e(TAG, "Login user error: " + firebaseError.toString() + " error: " + firebaseError.error.toString() + " message: " + firebaseError.message);


        Context context = getApplicationContext();
        CharSequence text = firebaseError.message;
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();

        //Toast.makeText(getApplicationContext(), firebaseError.message, Toast.LENGTH_LONG).show();

        resetFirebaseLoginPrompt();
    }

    @Override
    public Firebase getFirebaseRef() {
        return mRef;
    }

    public void createAccount(View view){
        Log.i(TAG, "Create Account");
    }
}
