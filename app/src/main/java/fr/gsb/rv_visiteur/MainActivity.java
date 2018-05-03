package fr.gsb.rv_visiteur;


import fr.gsb.rv_visiteur.techniques.*;
import fr.gsb.rv_visiteur.entites.*;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    Button valider;
    TextView message;
    EditText matricule;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        valider = findViewById(R.id.valider);

        message = findViewById(R.id.tVMessage);
        matricule = findViewById(R.id.eTMatricule);
        password = findViewById(R.id.eTPassword);

    }

    public void Valider(View v) throws UnsupportedEncodingException {
        String authentification = matricule.getText().toString().trim() +'.'+password.getText().toString().trim();
        final String visiteur = URLEncoder.encode(authentification, "UTF-8");
        String url = String.format(getResources().getString(R.string.ip_connexion), visiteur);

        Response.Listener<JSONObject> ecouteur = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    Visiteur visiteur = new Visiteur();
                    visiteur.setNom(response.getString("nom"));
                    visiteur.setPrenom(response.getString("prenom"));
                    visiteur.setMatricule(response.getString("matricule"));
                    visiteur.setMdp(response.getString("mdp"));
                    password.setText("");
                    Session.ouvrir(visiteur);
                    Intent i = new Intent(MainActivity.this, AccueilActivity.class);
                    startActivity(i);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                /*new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Connexion refusée")
                        .setMessage("Le login et ou le mot de passe est faux." +
                                " Veuillez réessayer...")
                        .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                //Toast.makeText(MainActivity.this, "Button cliqué", Toast.LENGTH_LONG).show();
                            }
                        })
                        .show();*/
                message.setText("Echec de connexion, veuillez réessayer.");
                password.setText("");
            }
        };

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, ecouteur,errorListener);
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(jsonObjectRequest);
    }
}
