package com.aron.patientmonitoring;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private final String CHANNEL_ID ="patient_notification";
    private final int NOTIFICATION_ID=001;
    private final String CHANNEL_NAME="PATIENT MONITORING";
    private final String CHANNEL_DESC="ALERT";
LottieAnimationView  lottieAnimationViewHumidity,lottieAnimationViewheart,lottieAnimationViewpulse,lottieAnimationViewtemp;
Button btn;
TextView humidity,heart,pulse,temp;
DatabaseReference mRootref= FirebaseDatabase.getInstance().getReference();
DatabaseReference mHumidityref=mRootref.child("Humidity");
DatabaseReference mHeartrateref=mRootref.child("Heartrate");
DatabaseReference mTemperature=mRootref.child("Temperature");
DatabaseReference mPulse = mRootref.child("Pulse");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lottieAnimationViewHumidity =(LottieAnimationView)findViewById(R.id.loadinghumidity);
        lottieAnimationViewheart=(LottieAnimationView)findViewById(R.id.loadinghr);
        lottieAnimationViewpulse=(LottieAnimationView)findViewById(R.id.loadingpulse);
        lottieAnimationViewtemp=(LottieAnimationView)findViewById(R.id.loadingtemperature);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel channel=new NotificationChannel(CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }


        humidity=(TextView)findViewById(R.id.humidity);
        heart=(TextView)findViewById(R.id.heartrate);
        pulse=(TextView)findViewById(R.id.pulse);
        temp=(TextView)findViewById(R.id.temperature);



    }
    public void displayNotificationhumidity(float humidityValue)
    {
        Intent resultIntent = new Intent(this, MainActivity.class);
// Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
// Get the PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,CHANNEL_ID);
        builder.setSmallIcon(R.drawable.notification_patient);
        builder.setContentTitle("Humidity Alert");
        builder.setContentText(String.valueOf(humidityValue));
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setContentIntent(resultPendingIntent);
        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());
    }
    public void displayNotificationheart(String heartrate)
    {
        Intent resultIntent = new Intent(this, MainActivity.class);
// Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
// Get the PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,CHANNEL_ID);
        builder.setSmallIcon(R.drawable.notification_patient);
        builder.setContentTitle("Heart Rate High Alert");
        builder.setContentText(heartrate);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setContentIntent(resultPendingIntent);
        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());
    }
    public void displayNotificationpulse(String pulse)
    {
        Intent resultIntent = new Intent(this, MainActivity.class);
// Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
// Get the PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,CHANNEL_ID);
        builder.setSmallIcon(R.drawable.notification_patient);
        builder.setContentTitle("Pulse High Alert");
        builder.setContentText(pulse);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setContentIntent(resultPendingIntent);
        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());
    }
    public void displayNotificationtemperature(String temp)
    {
        Intent resultIntent = new Intent(this, MainActivity.class);
// Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
// Get the PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,CHANNEL_ID);
        builder.setSmallIcon(R.drawable.notification_patient);
        builder.setContentTitle("Body High Temprature High");
        builder.setContentText(temp);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setContentIntent(resultPendingIntent);
        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mHumidityref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Float text=dataSnapshot.getValue(Float.class);
                lottieAnimationViewHumidity.setVisibility(View.GONE);
                humidity.setVisibility(View.VISIBLE);
                humidity.setText(String.valueOf(text));

                if(text>300)
                {
                    displayNotificationhumidity(text);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                lottieAnimationViewHumidity.setVisibility(View.GONE);
                humidity.setVisibility(View.VISIBLE);
                humidity.setText("No Data");
            }
        });
        mHeartrateref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String text=dataSnapshot.getValue(String.class);
                lottieAnimationViewheart.setVisibility(View.GONE);
                heart.setVisibility(View.VISIBLE);
                heart.setText(String.valueOf(text));
//                if(Integer.parseInt(text)>300)
//                {
//                    displayNotificationheart(text);
//                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                lottieAnimationViewHumidity.setVisibility(View.GONE);
                humidity.setVisibility(View.VISIBLE);
                humidity.setText("No Data");
            }
        });
        mPulse.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String text=dataSnapshot.getValue(String.class);
                lottieAnimationViewpulse.setVisibility(View.GONE);
                pulse.setVisibility(View.VISIBLE);
                pulse.setText(String.valueOf(text));
//                if(Integer.parseInt(text)>300)
//                {
//                    displayNotificationpulse(text);
//                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                lottieAnimationViewHumidity.setVisibility(View.GONE);
                humidity.setVisibility(View.VISIBLE);
                humidity.setText("No Data");
            }
        });
        mTemperature.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String text=dataSnapshot.getValue(String.class);
                lottieAnimationViewtemp.setVisibility(View.GONE);
                temp.setVisibility(View.VISIBLE);
                temp.setText(String.valueOf(text));
//                if(Integer.parseInt(text)>300)
//                {
//                    displayNotificationtemperature(text);
//                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                lottieAnimationViewHumidity.setVisibility(View.GONE);
                humidity.setVisibility(View.VISIBLE);
                humidity.setText("No Data");
            }
        });
    }

}
