package info.anth.firebaseauth2;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.firebase.client.Firebase;

import info.anth.firebaseauth2.Login.LoginActivity;

public class TestActivity extends AppCompatActivity {

    private Firebase mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mRef = new Firebase(getResources().getString(R.string.FIREBASE_BASE_REF));
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("ajc", "TestActivity onResume");
        if (mRef.getAuth()==null) startActivityForResult(new Intent(this, LoginActivity.class), LoginActivity.RESULT_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == LoginActivity.RESULT_REQUEST_CODE) {
            /*
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
            }
            */
            if (resultCode == Activity.RESULT_CANCELED) {
                // Login was cancelled therefor cancel this activity
                finish();
            }
        }
    }

}
