package com.aron.patientmonitoring;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class report extends AppCompatActivity {
ImageView imageView;
CardView download;
CardView share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        imageView=findViewById(R.id.reportd);
        download=findViewById(R.id.download);
        share=findViewById(R.id.share);

        download.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Downloading Started",Toast.LENGTH_SHORT).show();
                BitmapDrawable draw = (BitmapDrawable) imageView.getDrawable();
                Bitmap bitmap = draw.getBitmap();

                FileOutputStream outStream = null;
                File sdCard = Environment.getExternalStorageDirectory();
                File dir = new File(sdCard.getAbsolutePath() + "/Download");
                dir.mkdirs();
                String fileName = String.format("%d.jpg", 123456);
                File outFile = new File(dir, fileName);
                try {
                    outStream = new FileOutputStream(outFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                try {
                    outStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(),"Downloading Finished",Toast.LENGTH_SHORT).show();
            }


        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/*");
                share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/123456.jpg")));
                startActivity(Intent.createChooser(share,"Share via"));
            }
        });

    }
}
