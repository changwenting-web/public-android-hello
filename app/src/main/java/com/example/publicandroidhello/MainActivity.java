package com.example.publicandroidhello;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView view = new TextView(this);
        view.setText(getString(R.string.home_message));
        view.setGravity(Gravity.CENTER);
        view.setTextSize(24);
        setContentView(view);
    }
}
