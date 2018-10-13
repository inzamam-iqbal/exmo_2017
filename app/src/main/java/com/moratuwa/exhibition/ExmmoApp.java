package com.moratuwa.exhibition;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Inzimam on 4/14/2017.
 */
public class ExmmoApp extends android.app.Application {

    FirebaseDatabase data;

    @Override
    public void onCreate() {
        super.onCreate();

        if (FirebaseApp.getApps(getApplicationContext()).size()!=0){
            data = FirebaseDatabase.getInstance();
            data.setPersistenceEnabled(true);
        }
    }
}
