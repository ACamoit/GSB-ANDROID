package fr.gsb.rv_visiteur.entites;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import fr.gsb.rv_visiteur.techniques.DateFr;

public class RapportVisite {

    private int numero ;
    private String bilan ;
    private String coefConfiance ;
    private DateFr dateRedaction ;
    private boolean vu ;

    private String praticien ;
    private Visiteur leVisiteur ;
    private String motif ;

    public RapportVisite() {
        super();
    }

    public RapportVisite(int numero, String bilan, String coefConfiance, DateFr dateRedaction,
                         boolean vu, String praticien, String motif) {
        super();
        this.numero = numero;
        this.bilan = bilan;
        this.coefConfiance = coefConfiance;
        this.dateRedaction = dateRedaction;
        this.vu = vu;
        this.praticien = praticien;
        this.motif = motif;
    }



    public RapportVisite(int numero, String bilan, String coefConfiance, boolean vu) {
        super();
        this.numero = numero;
        this.bilan = bilan;
        this.coefConfiance = coefConfiance;
        this.vu = vu;
    }

    public RapportVisite(int numero, String bilan, String coefConfiance,
                         DateFr dateVisite, DateFr dateRedaction,
                         boolean vu, Visiteur leVisiteur) {
        super();
        this.numero = numero;
        this.bilan = bilan;
        this.coefConfiance = coefConfiance;
        this.dateRedaction = dateRedaction;
        this.vu = vu;
        this.leVisiteur = leVisiteur;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getBilan() {
        return bilan;
    }

    public void setBilan(String bilan) {
        this.bilan = bilan;
    }

    public String getCoefConfiance() {
        return coefConfiance;
    }

    public void setCoefConfiance(String coefConfiance) {
        this.coefConfiance = coefConfiance;
    }


    public GregorianCalendar getDateRedaction() {
        return dateRedaction;
    }

    public void setDateRedaction(DateFr dateRedaction) {
        this.dateRedaction = dateRedaction;
    }


    public Visiteur getLeVisiteur() {
        return leVisiteur;
    }

    public void setLeVisiteur(Visiteur leVisiteur) {
        this.leVisiteur = leVisiteur;
    }

    public String getPraticien(){
        return praticien;
    }
    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }


    @Override
    public String toString() {
        return "RapportVisite [numero=" + numero + ", bilan=" + bilan
                + ", coefConfiance=" + coefConfiance + ", dateRedaction=" + dateRedaction + ", leVisiteur="
                + leVisiteur + "]";
    }

}

