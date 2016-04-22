package info.anth.firebaseauth2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.ui.auth.core.AuthProviderType;
import com.firebase.ui.auth.core.FirebaseLoginBaseActivity;
import com.firebase.ui.auth.core.FirebaseLoginError;

public class LoginActivity extends FirebaseLoginBaseActivity {

    public static String TAG = "ajc";
    private Firebase mRef;
    private String mName;
    private LoginRegisterDialog loginRegisterDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mRef = new Firebase(getResources().getString(R.string.FIREBASE_BASE_REF));

        // set login button listener
        Button buttonLogin = (Button) findViewById(R.id.activity_login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clickLogin();
            }
        });

        // set cancel button listener
        Button buttonCancel = (Button) findViewById(R.id.activity_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clickCancel();
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        logout();
        //setEnabledAuthProvider(AuthProviderType.FACEBOOK);
        //setEnabledAuthProvider(AuthProviderType.TWITTER);
        setEnabledAuthProvider(AuthProviderType.GOOGLE);
        setEnabledAuthProvider(AuthProviderType.PASSWORD);

        if (mRef.getAuth() == null) showFirebaseLoginPrompt();
        Log.i(TAG, "Auth: " + String.valueOf(mRef.getAuth()));

        loginRegisterDialog = new LoginRegisterDialog();
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

        finish();
    }

    @Override
    public void onFirebaseLoggedOut() {
        Log.i(TAG, "Logged out: ");
        mName = "";
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

    // Create a new account
    public void createAccount(View view){
        dismissFirebaseLoginPrompt();
        loginRegisterDialog.show(getFragmentManager(),"");
    }

    // Return to login dialog when back button is pressed
    public void clickLogin(){
        showFirebaseLoginPrompt();
    }

    // Cancel activity - no login
    public void clickCancel(){
        finish();
    }

}
