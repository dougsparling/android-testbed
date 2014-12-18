package com.protegra.dsparling.testbed;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.protegra.dsparling.testbed.time.TimeService;

import java.text.SimpleDateFormat;

import javax.inject.Inject;


public class MainActivity extends ActionBarActivity {

    @Inject TimeService timeService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.inject(this, this);

        String formattedDate = new SimpleDateFormat("yy/MM/dd").format(timeService.getTime().getTime());
        ((TextView) findViewById(R.id.date_label)).setText("Date: " + formattedDate);

        final TextView helloWorld = (TextView) findViewById(R.id.hello_world);
        helloWorld.setOnClickListener(new View.OnClickListener() {
            int i = 1;
            @Override
            public void onClick(View v) {
                helloWorld.setText(String.format("Was Clicked %d Times!", i));
                helloWorld.setTextSize(helloWorld.getTextSize() + 2.0f);
                i++;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}