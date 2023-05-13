package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    private TextView nameText;
    private TextView numberText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getData();
        makeCall();
        sendSms();
        sendMail();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detailpage_menu, menu);

        MenuItem help = menu.findItem(R.id.moreBarHelpDetail);
        MenuItem delete = menu.findItem(R.id.moreBarDeleteDetail);

        delete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return false;
            }
        });

        help.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                Toast.makeText(getApplicationContext(), "Please describe your issue", Toast.LENGTH_LONG).show();

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + "@.com"));
                String subject = "Please describe your issue here";
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                startActivity(Intent.createChooser(emailIntent, "Chooser Title"));

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void getData() {

        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        String number = intent.getStringExtra("number");

        nameText = findViewById(R.id.nameDetail);
        numberText = findViewById(R.id.numberDetail);

        nameText.setText(name);
        numberText.setText(number);

    }


    public void makeCall() {

        ImageButton call = findViewById(R.id.callDeltail);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(DetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(DetailActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 100);
                    Toast.makeText(getApplicationContext(), "Permission Allowed", Toast.LENGTH_SHORT).show();
                } else {
                    String number = numberText.getText().toString();
                    String prepare = "tel:" + number;

                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse(prepare));
                    startActivity(intent);
                }
            }
        });
    }


    public void sendSms() {

        ImageButton button = findViewById(R.id.smsDeltail);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(DetailActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Please Allow Sms Permission", Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(DetailActivity.this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS}, 100);
                } else {
                    Intent inten = getIntent();
                    String name = inten.getStringExtra("name");
                    String number = inten.getStringExtra("number");

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + number));
                    intent.putExtra("sms_body", "Hello " + name);
                    startActivity(intent);

                }
            }
        });
    }


    public void sendMail() {

        ImageButton button = findViewById(R.id.mailDeltail);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));

                Intent inten = getIntent();
                String name = inten.getStringExtra("name");

                String subject = "Hello " + name;
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                startActivity(Intent.createChooser(emailIntent, "Chooser Title"));
            }
        });

    }

}



