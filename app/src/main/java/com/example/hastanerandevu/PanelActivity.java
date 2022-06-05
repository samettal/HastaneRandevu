package com.example.hastanerandevu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PanelActivity extends AppCompatActivity {
    private TextView hosgeldiniz;

    private Button randevuAl,randevularim,hesaptanCik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);

        hosgeldiniz=findViewById(R.id.textViewHosgeldiniz);

        randevuAl=findViewById(R.id.buttonRANDEVUAL);
        randevularim=findViewById(R.id.buttonRANDEVULAR);
        hesaptanCik=findViewById(R.id.buttonCikisYap);

        hosgeldiniz.setText("HOŞGELDİNİZ Sn. "+ MainActivity.adsoyad_static);

        randevuAl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PanelActivity.this,RandevuAlActivity.class));
                finish();
            }
        });

        randevularim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PanelActivity.this,RandevularActivity.class));
                finish();
            }
        });

        hesaptanCik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PanelActivity.this,MainActivity.class));
                finish();
            }
        });
    }
}