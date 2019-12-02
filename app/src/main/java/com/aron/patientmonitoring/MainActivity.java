package com.aron.patientmonitoring;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.core.view.GestureDetectorCompat;


import android.app.ActionBar;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.fang.medicinereminderapplication.AlarmMainActivity;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;


import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private final String CHANNEL_ID ="patient_notification";
    private final String CHANNEL_ID2 ="patien";
    private final String CHANNEL_ID3 ="patient";
    private final String CHANNEL_ID4 ="patient_n";
    private final String CHANNEL_ID5="patient_no";
    private final int NOTIFICATION_ID=001;

    private final String CHANNEL_NAME="PATIENT MONITORING";
    private final String CHANNEL_DESC="ALERT";
    LinearLayout linearLayout;
    Switch notification;
LottieAnimationView  lottieAnimationViewHumidity,lottieAnimationViewheart,lottieAnimationViewpulse,lottieAnimationViewtemp;
Button btn;
CardView cardViewhear;
ImageButton imageButton;
ImageView imageView;
ImageView medicinelay;
    Boolean notificationstate;


    TextView humidity,heart,pulse,temp;
DatabaseReference mRootref= FirebaseDatabase.getInstance().getReference();
DatabaseReference mHumidityref=mRootref.child("Humidity");
DatabaseReference mHeartrateref=mRootref.child("Heartrate");
DatabaseReference mTemperature=mRootref.child("Temp");
DatabaseReference mPulse = mRootref.child("Pulse");
DatabaseReference mFallen=mRootref.child("FALLEN");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        imageButton =(ImageButton)findViewById(R.id.calldoc);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+917976263241"));
                startActivity(intent);
            }
        });
        imageView=findViewById(R.id.medicinelay);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,MedicinActivity.class);
                startActivity(intent);

            }
        });
        notification = findViewById(R.id.notofication);

        lottieAnimationViewHumidity =(LottieAnimationView)findViewById(R.id.loadinghumidity);
        lottieAnimationViewheart=(LottieAnimationView)findViewById(R.id.loadinghr);
        lottieAnimationViewpulse=(LottieAnimationView)findViewById(R.id.loadingpulse);
        lottieAnimationViewtemp=(LottieAnimationView)findViewById(R.id.loadingtemperature);
        linearLayout=(LinearLayout)findViewById(R.id.moreoptions);
//        final BottomSheetBehavior bottomSheetBehavior =BottomSheetBehavior.from(linearLayout);
        cardViewhear=(CardView)findViewById(R.id.heartcardview);
        medicinelay=findViewById(R.id.medicinelay);
        medicinelay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent medicine =new Intent(MainActivity.this,AlarmMainActivity.class);
                startActivity(medicine);
            }
        });


        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel channel=new NotificationChannel(CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
            NotificationChannel channel2=new NotificationChannel(CHANNEL_ID2,CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager2=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel2);
            NotificationChannel channel3=new NotificationChannel(CHANNEL_ID3,CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager3=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel3);
            NotificationChannel channel4=new NotificationChannel(CHANNEL_ID4,CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager4=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel4);
            NotificationChannel channel5=new NotificationChannel(CHANNEL_ID5,CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager5=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel5);

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
        notificationManagerCompat.notify(001,builder.build());
    }
    public void displayNotificationheart(float heartrate)
    {


        Intent resultIntent = new Intent(this, MainActivity.class);
// Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
// Get the PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,CHANNEL_ID2);
        builder.setSmallIcon(R.drawable.notification_patient);
        builder.setContentTitle("ECG ALERT");
        builder.setContentText(String.valueOf(heartrate));
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setContentIntent(resultPendingIntent);
        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(002,builder.build());


    }
    public void displayNotificationpulse(float pulse)
    {
        if (pulse>100) {
            Intent resultIntent = new Intent(this, MainActivity.class);
// Create the TaskStackBuilder and add the intent, which inflates the back stack
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addNextIntentWithParentStack(resultIntent);
// Get the PendingIntent containing the entire back stack
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID3);
            builder.setSmallIcon(R.drawable.notification_patient);
            builder.setContentTitle("Pulse High Alert");
            builder.setContentText(String.valueOf(pulse));
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
            builder.setContentIntent(resultPendingIntent);
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
            notificationManagerCompat.notify(003, builder.build());
        }
        if (pulse<100) {
            Intent resultIntent = new Intent(this, MainActivity.class);
// Create the TaskStackBuilder and add the intent, which inflates the back stack
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addNextIntentWithParentStack(resultIntent);
// Get the PendingIntent containing the entire back stack
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID3);
            builder.setSmallIcon(R.drawable.notification_patient);
            builder.setContentTitle("Pulse Low Alert");
            builder.setContentText(String.valueOf(pulse));
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
            builder.setContentIntent(resultPendingIntent);
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
            notificationManagerCompat.notify(003, builder.build());
        }
    }
    public void displayNotificationtemperature(float temp)
    {

        if(temp>38) {
            Intent resultIntent = new Intent(this, MainActivity.class);
// Create the TaskStackBuilder and add the intent, which inflates the back stack
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addNextIntentWithParentStack(resultIntent);
// Get the PendingIntent containing the entire back stack
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID4);
            builder.setSmallIcon(R.drawable.notification_patient);
            builder.setContentTitle("Body High Temprature High");
            builder.setContentText(String.valueOf(temp));
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
            builder.setContentIntent(resultPendingIntent);
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
            notificationManagerCompat.notify(004, builder.build());
        }
        if(temp<36){

            Intent resultIntent = new Intent(this, MainActivity.class);
// Create the TaskStackBuilder and add the intent, which inflates the back stack
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addNextIntentWithParentStack(resultIntent);
// Get the PendingIntent containing the entire back stack
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID4);
            builder.setSmallIcon(R.drawable.notification_patient);
            builder.setContentTitle("Body Temprature Low");
            builder.setContentText(String.valueOf(temp));
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
            builder.setContentIntent(resultPendingIntent);
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
            notificationManagerCompat.notify(004, builder.build());

        }
    }
    public void displayNotificationfallen()
    {
        Intent resultIntent = new Intent(this, MainActivity.class);
// Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
// Get the PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,CHANNEL_ID5);
        builder.setSmallIcon(R.drawable.notification_patient);
        builder.setContentTitle("ALERT");
        builder.setContentText("Patien has fallen");
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setContentIntent(resultPendingIntent);
        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(005,builder.build());
    }


   public class sendmesage extends AsyncTask<String,Void,String>
   {
       private static final String TAG = "openurl";

       @Override
       protected String doInBackground(String... strings) {
           try
           {    String url1="https://intense-inlet-13826.herokuapp.com/?users=piyush"+strings[0];
               Log.d(TAG, url1);
               URL url =new URL(url1);
               HttpURLConnection connection= (HttpURLConnection) url.openConnection();
               connection.connect();
               if( connection.getResponseCode() == HttpURLConnection.HTTP_OK ){
                   InputStream is = connection.getInputStream();
               }else{
                   InputStream err = connection.getErrorStream();
               }
               Log.d(TAG, "doInBackground: done executing");
               connection.disconnect();
           }
           catch (Exception e)
           {
               e.printStackTrace();
           }
           return null;

       }
   }

    @Override
    protected void onStart() {
        super.onStart();
        mFallen.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String text=dataSnapshot.getValue(String.class);

                if(text.equals("YES"))
                {
                    displayNotificationfallen();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {



            }
        });
        mHumidityref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Float text=dataSnapshot.getValue(Float.class);
                lottieAnimationViewHumidity.setVisibility(View.GONE);
                humidity.setVisibility(View.VISIBLE);
                humidity.setText(String.valueOf(text)+" RH");
                notificationstate = notification.isChecked();



                if(text>80 && notificationstate)
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
                Float text=dataSnapshot.getValue(Float.class);
                lottieAnimationViewheart.setVisibility(View.GONE);
                heart.setVisibility(View.VISIBLE);
                heart.setText(String.valueOf(text));

                notificationstate = notification.isChecked();

                if((text<300||text>800) && notificationstate)
                {
                    new sendmesage().execute("ALERT ECG PROBLEM");
                    displayNotificationheart(text);
                }
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

                Float text=dataSnapshot.getValue(Float.class);
                lottieAnimationViewpulse.setVisibility(View.GONE);
                pulse.setVisibility(View.VISIBLE);
                pulse.setText(String.valueOf(text)+"BPM");
                notificationstate = notification.isChecked();
                if((text>100||text<60) && notificationstate)
                {   if(text>100)
                {
                    new sendmesage().execute("Alert High Pulse");
                }
                if(text<60 && notificationstate)
                {
                    new sendmesage().execute("Alert Low Pulse");
                }
                    displayNotificationpulse(text);
                }

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
                Float text=dataSnapshot.getValue(Float.class);
                lottieAnimationViewtemp.setVisibility(View.GONE);
                temp.setVisibility(View.VISIBLE);
                temp.setText(String.valueOf(text)+"℃");
                notificationstate = notification.isChecked();
                if(text>38 || text<36)
                {   if(text<36 && notificationstate)
                {
                    new sendmesage().execute("Body Low Temperature");
                }

                if(text>38 && notificationstate)
                {
                    new sendmesage().execute("Body High Temperature");
                }
                    displayNotificationtemperature(text);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                lottieAnimationViewHumidity.setVisibility(View.GONE);
                humidity.setVisibility(View.VISIBLE);
                humidity.setText("No Data");
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
       switch (item.getItemId()){
           case R.id.hospitals:
               Intent map =new Intent(MainActivity.this,mapactivity.class);
               startActivity(map);
               return  true;
           case R.id.medicine:
               Intent order =new Intent(MainActivity.this,ordermedicine.class);
               startActivity(order);
               return true;
           case R.id.logout:
               Intent intent =new Intent(MainActivity.this,LoginActivity.class);
               startActivity(intent);
               SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
               SharedPreferences.Editor edt = pref.edit();
               edt.putBoolean("activity_executed", false);
               edt.commit();
               return true;
           case R.id.ambulance:
               Intent call = new Intent(Intent.ACTION_DIAL);
               call.setData(Uri.parse("tel:09654232540"));
               startActivity(call);
               return true;
           case R.id.report:
               Intent report =new Intent(MainActivity.this,report.class);
               startActivity(report);
               return true;
           default:
               return false;
       }
    }

    public  void showmenu(View view)
    {
        PopupMenu popupMenu =new PopupMenu(this,view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();

    }
}

