package com.example.hastanerandevu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText adsoyad,sifre;
    private Button girisBTN, kayitBTN;
    private DatabaseHelper database;

    public static String adsoyad_static;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        girisBTN=findViewById(R.id.buttonGIRIS);
        kayitBTN=findViewById(R.id.buttonKAYIT);

        adsoyad=findViewById(R.id.editTextADSOYAD);
        sifre=findViewById(R.id.editTextSIFRE);

        database=new DatabaseHelper(this);

        girisBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean login=database.checkUser(adsoyad.getText().toString(),sifre.getText().toString());
                if(login){
                    adsoyad_static = adsoyad.getText().toString();
                    Toast.makeText(MainActivity.this,"Giriş Yapıldı",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,PanelActivity.class));
                    finish();
                }
                else{
                    Toast.makeText(MainActivity.this,"Giriş Yapılamadı",Toast.LENGTH_SHORT).show();
                }
            }
        });

        kayitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,KayitActivity.class));
                finish();
            }
        });
    }
}