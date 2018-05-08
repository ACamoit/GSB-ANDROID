package fr.gsb.rv_visiteur;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.gsb.rv_visiteur.techniques.DateFr;
import fr.gsb.rv_visiteur.techniques.Session;

public class ConsulterActivity extends AppCompatActivity {

    Spinner listeMois;
    Spinner listeAnnees;
    final List<Integer> dateAnnee = new ArrayList<Integer>();
    final List<String> dateMois = new ArrayList<String>();
    String mois;
    String annee;



    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter);

        listeMois = (Spinner) findViewById(R.id.spinner_mois);
        listeAnnees = (Spinner) findViewById(R.id.spinner_annee);

        dateMois.add("Janvier");
        dateMois.add("Février");
        dateMois.add("Mars");
        dateMois.add("Avril");
        dateMois.add("Mai");
        dateMois.add("Juin");
        dateMois.add("Juillet");
        dateMois.add("Août");
        dateMois.add("Séptembre");
        dateMois.add("Octobre");
        dateMois.add("Novembre");
        dateMois.add("Décembre");

        DateFr dateFr = new DateFr();
        for (int i = 0 ; i < 10 ; i++){

            dateAnnee.add(new Integer(dateFr.getAnnee() - i));
        }

        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(ConsulterActivity.this, R.layout.spinner_item, dateAnnee);
        ArrayAdapter<String> arrayAdapterMois = new ArrayAdapter<>(ConsulterActivity.this, R.layout.spinner_item, dateMois);

        listeAnnees.setAdapter(arrayAdapter);
        listeMois.setAdapter(arrayAdapterMois);

        listeAnnees.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                annee = dateAnnee.get(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                Toast.makeText(ConsulterActivity.this, "Rien", Toast.LENGTH_SHORT).show();

            }
        });

        listeMois.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                mois = String.valueOf(i+1) ;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                Toast.makeText(ConsulterActivity.this, "Rien", Toast.LENGTH_SHORT).show();
            }
        });




    }

    public void Retour(View v){
        Intent i = new Intent(ConsulterActivity.this, AccueilActivity.class);
        startActivity(i);
    }

    public void Rechercher(View v){

        Bundle donnees = new Bundle();
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        arrayList.add(Integer.parseInt(mois));
        arrayList.add(Integer.parseInt(annee));

        donnees.putIntegerArrayList("value", arrayList);

        Intent intention = new Intent(ConsulterActivity.this, RapportsActivity.class);
        intention.putExtras(donnees);
        startActivity(intention);

        /*Toast.makeText(ConsulterActivity.this, String.valueOf(mois), Toast.LENGTH_LONG).show();
        Toast.makeText(ConsulterActivity.this, String.valueOf(annee), Toast.LENGTH_LONG).show();*/

    }
}
