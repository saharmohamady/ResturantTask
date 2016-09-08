package com.quand.resturanttask.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.quand.resturanttask.AsyncTask.DataFetcherTask;
import com.quand.resturanttask.R;
import com.quand.resturanttask.model.Customer;
import com.quand.resturanttask.model.Table;
import com.quand.resturanttask.utilities.CleanAlarmReceiver;
import com.quand.resturanttask.utilities.DBHandler;
import com.quand.resturanttask.utilities.NetworkUtils;
import com.quand.resturanttask.utilities.Utility;

import java.util.Calendar;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        DBHandler helper = new DBHandler(this);

        if (!NetworkUtils.isConnectingToInternet(this) && helper.getCustomerCount() == 0) {
            // read from assets this is helpful if no internet connection at all then save it to db and open activity
            String fileDate = Utility.loadJSONFromAsset(this, "customer-list.json");
            Customer.loadAndStoreFromJson(this, fileDate);

            //for tables
            String tablesData = Utility.loadJSONFromAsset(this, "table-map.json");
            Table.parseAndStoreTablesData(this, tablesData);

            openCustomersActivity();
        } else if (NetworkUtils.isConnectingToInternet(this) && helper.getCustomerCount() == 0) {
            //if there is internet connection then load files from it
            //load customers data
            new DataFetcherTask(this, false).execute("https://s3-eu-west-1.amazonaws.com/quandoo-assessment/customer-list.json");
            //load tables data
            new DataFetcherTask(this, true).execute("https://s3-eu-west-1.amazonaws.com/quandoo-assessment/table-map.json");

        } else if (helper.getCustomerCount() >= 0) {
            openCustomersActivity();
        }

        //register clear every 10 mints
        Intent myIntent = new Intent(this, CleanAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, 0);

        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 60); // first time
        long frequency = 10 * 60 * 1000; // in ms
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), frequency, pendingIntent);

    }

    public void openCustomersActivity() {
        Intent mainClass = new Intent(this, CustomersActivity.class);
        startActivity(mainClass);
        this.finish();
    }
}
