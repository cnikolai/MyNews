package com.nikolai.mynews.Controllers.Views;

import android.os.Bundle;
import android.app.Activity;

import com.nikolai.mynews.R;

/**
 * creates the view for notifications
 */
public class NotificationView extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);
    }
}