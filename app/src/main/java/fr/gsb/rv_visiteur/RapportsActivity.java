package fr.gsb.rv_visiteur;

import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import fr.gsb.rv_visiteur.adapter.DialogueRapport;
import fr.gsb.rv_visiteur.adapter.RapportsAdapter;
import fr.gsb.rv_visiteur.entites.RapportVisite;
import fr.gsb.rv_visiteur.techniques.DateFr;
import fr.gsb.rv_visiteur.techniques.Session;
import fr.gsb.rv_visiteur.R;

public class RapportsActivity extends AppCompatActivity {

    TextView nomText ;

    private List<RapportVisite> lesRapportsVisites = new ArrayList<RapportVisite>() ;
    private RapportsAdapter adapter ;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rapports);
        final ListView lvRapport = (ListView)findViewById(R.id.listeView_rapport);
        Bundle paquet = this.getIntent().getExtras();

        /*String string =Session.getSession().getLeVisiteur().getNom()+" "+Session.getSession().getLeVisiteur().getPrenom();
        nomText.setText(string);*/


        ArrayList<Integer> valeur = paquet.getIntegerArrayList("value");

        Integer mois = valeur.get(0);
        Integer annee = valeur.get(1);

        /*Toast.makeText(RapportsActivity.this, String.valueOf(mois), Toast.LENGTH_LONG).show();
        Toast.makeText(RapportsActivity.this, String.valueOf(annee), Toast.LENGTH_LONG).show();*/


        try {
            final String rap = Session.getSession().getLeVisiteur().getMatricule()+'.'+String.valueOf(mois) +'.'+ String.valueOf(annee);
            String visivisi = URLEncoder.encode(String.valueOf(rap), "UTF-8");
            final String url = String.format(getResources().getString(R.string.ip_recherche), visivisi);

            Toast.makeText(RapportsActivity.this, url, Toast.LENGTH_LONG).show();
            Response.Listener<JSONArray> ecouteur = new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    for(int i = 0; i < response.length(); i++){

                        try {

                            JSONObject jsonObject = response.getJSONObject(i);


                            /*int numero, String bilan, DateFr dateRedaction,
                            boolean vu, String praticien, String motif*/
                            RapportVisite unRapport = new RapportVisite(jsonObject.getInt("numRapport"), jsonObject.getString("rapBilan")," ",
                                    jsonObject.getString("rapDate"), jsonObject.getInt("estVu"), jsonObject.getString("nomPraticien"),
                                    jsonObject.getString("rapMotif"));

                            lesRapportsVisites.add(unRapport);

                            //lesRapportsVisites.add(new RapportVisite(jsonObject.getInt("numRapport"), jsonObject.getString("rapBilan")));

                            Toast.makeText(RapportsActivity.this, lesRapportsVisites.toString(), Toast.LENGTH_LONG).show();

                            RapportsAdapter rapportsAdapter = new RapportsAdapter(RapportsActivity.this, lesRapportsVisites);

                            lvRapport.setAdapter(rapportsAdapter);

                            lvRapport.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    /*Toast.makeText(RapportsActivity.this, String.valueOf(view.getTag()), Toast.LENGTH_SHORT).show();*/

                                    AlertDialog.Builder alert = new AlertDialog.Builder(RapportsActivity.this);

                                    alert.setTitle("Rap n°"+String.valueOf(view.getTag()));
                                    alert.setMessage(lesRapportsVisites.get(position).getBilan());

                                    alert.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {


                                        }
                                    });

                                    alert.setPositiveButton("Merci", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {



                                        }
                                    });
                                    alert.create().show();
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            Response.ErrorListener error = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(RapportsActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                }
            };

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,ecouteur, error);
            RequestQueue requestQueue = Volley.newRequestQueue(RapportsActivity.this);
            requestQueue.add(jsonArrayRequest);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }




}
