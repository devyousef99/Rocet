package com.example.yousefbukhari.rocketreader;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {
    Button Button;
    TextView text , text2;
    ImageView image;
    Button dialogButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button = findViewById(R.id.button);
        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator object = new IntentIntegrator(MainActivity.this);
                object.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                object.setPrompt("Scan");
                object.setCameraId(0);
                object.setBeepEnabled(false);
                object.setBarcodeImageEnabled(false);
                object.initiateScan();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult Myresult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(Myresult != null) {
            if(Myresult.getContents() == null) {
                Log.d("MainActivity", "Fail To Scan");
                Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show();
            } else {
                Log.d("MainActivity", "Successful To Scan");
                final Dialog d = new Dialog(this);
                d.setContentView(R.layout.dialog);
                text = d.findViewById(R.id.text);
                image = d.findViewById(R.id.image);
                text2 = d.findViewById(R.id.text2);
                text2.setText(Myresult.getContents());
                dialogButton = d.findViewById(R.id.dialogButton);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                    }
                });
                d.show();
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}