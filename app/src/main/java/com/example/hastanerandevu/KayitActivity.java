package com.example.hastanerandevu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class KayitActivity extends AppCompatActivity {
    private EditText adsoyad,sifre,telefon;
    private Button kayitBTN,girisBTN;
    private DatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit);

        adsoyad=findViewById(R.id.editTextKayitAdSoyad);
        sifre=findViewById(R.id.editTextKayitSifre);
        telefon=findViewById(R.id.editTextKayitTelefon);

        kayitBTN=findViewById(R.id.buttonKayitKayitBTN);
        girisBTN=findViewById(R.id.buttonKayitGiriseDon);

        database=new DatabaseHelper(this);

        kayitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean register=database.registerUser(adsoyad.getText().toString(),telefon.getText().toString(),sifre.getText().toString());
                if(register){
                    Toast.makeText(KayitActivity.this, "Kayıt işlemi başarılı.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(KayitActivity.this, "Kayıt işlemi başarısız.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        girisBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(KayitActivity.this,MainActivity.class));
                finish();
            }
        });
    }
}