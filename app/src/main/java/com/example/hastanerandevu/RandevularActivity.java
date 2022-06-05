package com.example.hastanerandevu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RandevularActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private TextView randevularText;
    private Spinner randevuNoSpinner;

    private Button randevuSilBTN, giriseDonBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randevular);
        db=new DatabaseHelper(this);
        randevularText=findViewById(R.id.randevuListesi);
        randevuNoSpinner=findViewById(R.id.spinnerRandevuNo);

        RandevulariGor();

        randevuSilBTN=findViewById(R.id.randevuSilBTN);
        giriseDonBTN=findViewById(R.id.buttonRandevularGiriseDon);

        giriseDonBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RandevularActivity.this,PanelActivity.class));
                finish();
            }
        });

        randevuSilBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.RandevuSil(Integer.parseInt(randevuNoSpinner.getSelectedItem().toString()));
                Toast.makeText(RandevularActivity.this,"Randevunuz Silinmiştir.",Toast.LENGTH_SHORT).show();
                RandevulariGor();
            }
        });
    }

    private void RandevulariGor(){
        String[] randevuNolar = db.RandevuListele(randevularText,MainActivity.adsoyad_static,randevuNoSpinner);
        ArrayAdapter<String> adp;
        adp=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,randevuNolar);
        randevuNoSpinner.setAdapter(adp);
    }
}