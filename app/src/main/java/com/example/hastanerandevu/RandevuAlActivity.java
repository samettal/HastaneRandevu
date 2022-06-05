package com.example.hastanerandevu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class RandevuAlActivity extends AppCompatActivity {
    private String[] iller={"İSTANBUL","ANKARA"};
    private String[][] ilceler= {{"AVCILAR", "KADIKÖY"},{"ÇANKAYA","YENİMAHALLE"}};
    private String[] hastaneler={"AVCILAR Devlet Hastanesi"};
    private String[] bolumler={"Cildiye","Çocuk","Ortopedi","Kadın Doğum","Dahiliye","Göz","Diş"};
    private String[] doktorlar={"Ahmet Güneş","Gamze Önal", "Mehmet Öz", "Furkan Sekmez","Sualp Kaçmaz","Ayşe Görmez","Mansur Görür","Hamit Altıntop","Tansu Giller","Sedat Sevgi","Ekrem Coşkun","Nusret Etçiloğlu"};

    private Spinner il,ilce,hastane,bolum,doktor;

    private int ilIndex,ilceIndex;

    private ArrayAdapter<String> dataAdapterForIller;
    private ArrayAdapter<String> dataAdapterForIlceler;
    private ArrayAdapter<String> dataAdapterForHastane;
    private ArrayAdapter<String> dataAdapterForBolum;
    private ArrayAdapter<String> dataAdapterForDoktor;

    private Button randevuAlBTN, giriseDonBTN;
    private RadioGroup gunRadioGroup, saatRadioGroup;
    private RadioButton secilenGunButton, secilenSaatButton;
    private RadioButton gun1RadioButton, gun2RadioButton, gun3RadioButton;

    private DatabaseHelper randevuDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randevu_al);

        randevuDB=new DatabaseHelper(this);

        randevuAlBTN=findViewById(R.id.buttonRandevuKaydet);
        giriseDonBTN=findViewById(R.id.buttonRandevuAlGiriseDon);

        gunRadioGroup=findViewById(R.id.gunRadioGroup);
        saatRadioGroup=findViewById(R.id.saatRadioGrup);

        gun1RadioButton=findViewById(R.id.gunRadio1gun);
        gun2RadioButton=findViewById(R.id.gunRadio2gun);
        gun3RadioButton=findViewById(R.id.gunRadio3gun);

        gun1RadioButton.setText(DateAfterDays(1));
        gun2RadioButton.setText(DateAfterDays(2));
        gun3RadioButton.setText(DateAfterDays(3));

        il=findViewById(R.id.spinnerSehir);
        ilce=findViewById(R.id.spinnerIlce);
        hastane=findViewById(R.id.spinnerHastane);
        bolum=findViewById(R.id.spinnerBolum);
        doktor=findViewById(R.id.spinnerDoktor);

        dataAdapterForIller=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,iller);
        dataAdapterForIlceler=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,ilceler[0]);
        dataAdapterForHastane=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,hastaneler);
        dataAdapterForBolum=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,bolumler);
        dataAdapterForDoktor=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,RandomRangeDoktor(0,doktorlar.length));

        dataAdapterForIller.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterForIlceler.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterForHastane.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterForBolum.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterForDoktor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        il.setAdapter(dataAdapterForIller);
        ilce.setAdapter(dataAdapterForIlceler);
        hastane.setAdapter(dataAdapterForHastane);
        bolum.setAdapter(dataAdapterForBolum);
        doktor.setAdapter(dataAdapterForDoktor);

        il.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int i=0;
                while(!parent.getSelectedItem().toString().equals(iller[i])){
                    i++;
                }
                //Log.d("i", String.valueOf(i));
                ilIndex=i;
                dataAdapterForIlceler=new ArrayAdapter<String>(RandevuAlActivity.this, android.R.layout.simple_spinner_item,ilceler[i]);
                dataAdapterForIlceler.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                ilce.setAdapter(dataAdapterForIlceler);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ilce.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int i=0;
                while(!parent.getSelectedItem().toString().equals(ilceler[ilIndex][i])){
                    i++;
                }
                ilceIndex=i;
                hastaneler[0]=ilceler[ilIndex][ilceIndex]+" Devlet Hastanesi";
                dataAdapterForHastane=new ArrayAdapter<String>(RandevuAlActivity.this,android.R.layout.simple_spinner_item,hastaneler);
                hastane.setAdapter(dataAdapterForHastane);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        bolum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dataAdapterForDoktor=new ArrayAdapter<String>(RandevuAlActivity.this,android.R.layout.simple_spinner_item,RandomRangeDoktor(0,doktorlar.length));
                dataAdapterForDoktor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                doktor.setAdapter(dataAdapterForDoktor);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        randevuAlBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secilenGunButton=findViewById(gunRadioGroup.getCheckedRadioButtonId());
                secilenSaatButton=findViewById(saatRadioGroup.getCheckedRadioButtonId());

                boolean randevu = randevuDB.RandevuAl(il.getSelectedItem().toString(),ilce.getSelectedItem().toString(),hastane.getSelectedItem().toString(),bolum.getSelectedItem().toString(),doktor.getSelectedItem().toString(),secilenGunButton.getText()+" "+secilenSaatButton.getText(),MainActivity.adsoyad_static);
                //boolean randevu=randevuDB.RandevuAl("deneme","deneme","deneme","deneme","deneme","deneme","deneme");
                if(randevu){
                    Toast.makeText(RandevuAlActivity.this,"Randevunuz Başarıyla Alınmıştır.",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(RandevuAlActivity.this,"Randevu Alınamadı.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        giriseDonBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RandevuAlActivity.this,PanelActivity.class));
                finish();
            }
        });
    }

    private String[] RandomRangeDoktor(int min,int max){
        String[] result=new String[3];
        for (int i=0;i<3;i++){
            int index=new Random().nextInt(max-min)+min;
            result[i]=doktorlar[index];
        }
        return result;
    }

    private String DateAfterDays(int days){
        Date date=Calendar.getInstance().getTime();
        DateFormat currentDate=new SimpleDateFormat("dd-MM-yyyy");
        date.setTime(date.getTime()+days*1000*60*60*24);
        return currentDate.format(date);
    }
}