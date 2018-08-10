package ka.com.showkoora.pushnotification

import android.preference.PreferenceManager
import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

class MyFirebaseInstanceIDService : FirebaseInstanceIdService() {

    private val TAG = "MyFirebaseIIDService"


    override fun onTokenRefresh() {
        // Get updated InstanceID token.
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.v(TAG, "Refreshed token: " + refreshedToken!!)

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        preferences.edit().putString(Constants.FIREBASE_TOKEN, refreshedToken).apply()
    }
}