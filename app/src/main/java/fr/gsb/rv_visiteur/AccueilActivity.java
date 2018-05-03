package fr.gsb.rv_visiteur;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import fr.gsb.rv_visiteur.techniques.Session;

public class AccueilActivity extends AppCompatActivity {

    TextView user;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        user = findViewById(R.id.tVUser);
        user.setText("Bonjour "+ Session.getSession().getLeVisiteur().getPrenom() + " " + Session.getSession().getLeVisiteur().getNom() +".");

    }

    public void Rapports(View v) {
        Intent i = new Intent(AccueilActivity.this, ConsulterActivity.class);
        startActivity(i);
    }

    public void Deconnexion(View v) {
        Session.fermer();
        Intent i = new Intent(AccueilActivity.this, MainActivity.class);
        startActivity(i);

    }
}
