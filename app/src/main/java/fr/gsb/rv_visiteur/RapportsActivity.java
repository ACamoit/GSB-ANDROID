package fr.gsb.rv_visiteur;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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
    private DrawerLayout drawerLayout ;
    private List<RapportVisite> lesRapportsVisites = new ArrayList<RapportVisite>() ;
    private RapportsAdapter adapter ;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rapports);
        final ListView lvRapport = (ListView)findViewById(R.id.listeView_rapport);
        Bundle paquet = this.getIntent().getExtras();

        String string =Session.getSession().getLeVisiteur().getNom()+" "+Session.getSession().getLeVisiteur().getPrenom();
        nomText.setText(string);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<Integer> valeur = paquet.getIntegerArrayList("value");

        Integer mois = valeur.get(0);
        Integer annee = valeur.get(1);
        try {

            final String rap = Session.getSession().getLeVisiteur().getMatricule()+'.'+String.valueOf(mois) +'.'+ String.valueOf(annee);
            String visivisi = URLEncoder.encode(String.valueOf(rap), "UTF-8");
            final String url = String.format(getResources().getString(R.string.ip_recherche), rap);

            Response.Listener<JSONArray> ecouteur = new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(final JSONArray response) {

                    for (int i = 0; i < response.length(); i++){

                        try {

                            JSONObject jsonObject = response.getJSONObject(i);
                            String[] dateRedac = jsonObject.getString("rapDate").split("-");

                            int anneeRedac, moisRedac, jourRedac ;

                            anneeRedac = Integer.parseInt(dateRedac[0]);
                            moisRedac = Integer.parseInt(dateRedac[1]);
                            jourRedac = Integer.parseInt(dateRedac[2]);

                            boolean vu ;
                            if(jsonObject.getInt("estVu") == 0){

                                vu = false ;
                            }else{
                                vu = true ;
                            }
                            lesRapportsVisites.add(new RapportVisite(jsonObject.getInt("numRapport"),jsonObject.getString("rapBilan"),jsonObject.getString("rapEval"),new DateFr(jourRedac, moisRedac, anneeRedac),vu,jsonObject.getString("nomPraticien"),jsonObject.getString("rapMotif")));
                            //t1.setText(lesRapportsVisites.get(i).toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    adapter = new RapportsAdapter(getApplicationContext(), lesRapportsVisites);
                    lvRapport.setAdapter(adapter);
                    lvRapport.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Integer intt = (Integer) view.getTag();
                            if(intt == 0){

                                intt = (Integer) view.getTag() + 1;
                            }

                            /**int numRap, String bilan, String coef, String dateV, String dateR, String praticien, String motif**/
                            DialogueRapport dialogueRapport = new DialogueRapport(lesRapportsVisites.get(i).getNumero(),
                                    lesRapportsVisites.get(i).getBilan(),
                                    lesRapportsVisites.get(i).getCoefConfiance(),
                                    lesRapportsVisites.get(i).getDateRedaction(),
                                    lesRapportsVisites.get(i).getPraticien(),
                                    lesRapportsVisites.get(i).getMotif());
                            dialogueRapport.show(getSupportFragmentManager(), "Dialogue");

                        }
                    });
                }
            };
            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(RapportsActivity.this, "JE suis vide", Toast.LENGTH_LONG).show();
                }
            };
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,ecouteur, errorListener);
            RequestQueue requestQueue = Volley.newRequestQueue(RapportsActivity.this);
            requestQueue.add(jsonArrayRequest);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }




}
