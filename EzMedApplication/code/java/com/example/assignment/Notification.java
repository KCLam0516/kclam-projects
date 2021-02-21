package com.example.assignment;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Calendar;



public class Notification extends AppCompatActivity implements View.OnClickListener {
    // Notification ID.
    private static final int NOTIFICATION_ID = 0;
    // Notification channel ID.
    private static final String PRIMARY_CHANNEL_ID =
            "primary_notification_channel";
    private NotificationManager mNotificationManager;

    DatabaseHelper myDb;
    Button second, third, forth, fifth;
    TimePicker reminderTime;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder);

        Intent intent = getIntent();

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        ToggleButton alarmToggle = findViewById(R.id.alarmToggle);

        // Set up the Notification Broadcast Intent.
        Intent notifyIntent = new Intent(this, AlarmReceiver.class);

        boolean alarmUp = (PendingIntent.getBroadcast(this, NOTIFICATION_ID,
                notifyIntent, PendingIntent.FLAG_NO_CREATE) != null);
        alarmToggle.setChecked(alarmUp);

        final PendingIntent notifyPendingIntent = PendingIntent.getBroadcast
                (this, NOTIFICATION_ID, notifyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        final AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // Set the click listener for the toggle button.
        alarmToggle.setOnCheckedChangeListener
                (new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        String toastMessage;

                        if (isChecked) {

                            long repeatInterval = 1000L;
                            long triggerTime = setTime().getTimeInMillis();

                            if (alarmManager != null) { alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggerTime, repeatInterval, notifyPendingIntent); }
                            toastMessage = "Alarm on";

                        } else {
                            // Cancel notification if the alarm is turned off.
                            mNotificationManager.cancelAll();

                            if (alarmManager != null) {
                                alarmManager.cancel(notifyPendingIntent);
                            }
                            toastMessage = "Alarm off";

                        }

                        Toast.makeText(Notification.this, toastMessage, Toast.LENGTH_SHORT).show();
                    }
                });

        second = (Button) findViewById(R.id.second);
        third = (Button) findViewById(R.id.third);
        forth = (Button) findViewById(R.id.forth);
        fifth = (Button) findViewById(R.id.fifth);
        reminderTime = (TimePicker) findViewById(R.id.editReminderTime);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        second.setOnClickListener(this);
        third.setOnClickListener(this);
        forth.setOnClickListener(this);
        fifth.setOnClickListener(this);

        createNotificationChannel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                startActivity(intent);
                Toast.makeText(this, "Log Out Successfully", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.second) {
            Intent intent = new Intent(getApplicationContext(), CovidInfo.class);
            startActivity(intent);
        } else if (id == R.id.third) {
            Intent intent = new Intent(getApplicationContext(), CovidChecking.class);
            startActivity(intent);
        } else if (id == R.id.forth) {
            Intent intent = new Intent(getApplicationContext(), Notification.class);
            startActivity(intent);
        } else if (id == R.id.fifth) {
            Intent intent = new Intent(getApplicationContext(), LocationRecorder.class);
            startActivity(intent);
        }
    }
    public Calendar setTime() {
        Calendar calNow = Calendar.getInstance();
        Calendar calSet = (Calendar) calNow.clone();

        calSet.set(Calendar.HOUR_OF_DAY, reminderTime.getCurrentHour());
        calSet.set(Calendar.MINUTE, reminderTime.getCurrentMinute());
        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);
        return calSet;
    }

    /**
     * Creates a Notification channel, for OREO and higher.
     */
    public void createNotificationChannel() {

        // Create a notification manager object.
        mNotificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel = new NotificationChannel
                    (PRIMARY_CHANNEL_ID, "Reminder", NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("CsGo Time");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private boolean titleCheck(EditText textBox) {
        if (textBox.getText().toString().isEmpty()) {
            textBox.setError("Please fill in the title");
            return false;
        } else {
            textBox.setError(null);
            return true;
        }
    }
}