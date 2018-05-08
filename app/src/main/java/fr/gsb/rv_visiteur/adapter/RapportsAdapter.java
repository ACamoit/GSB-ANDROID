package fr.gsb.rv_visiteur.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.List;

import fr.gsb.rv_visiteur.entites.RapportVisite;
import fr.gsb.rv_visiteur.R;


public class RapportsAdapter extends BaseAdapter {

    private Context context;
    private List<RapportVisite> lesRapportsVisite ;

    //Constructeur


    public RapportsAdapter(Context context, List<RapportVisite> lesRapportsVisite) {
        this.context = context;
        this.lesRapportsVisite = lesRapportsVisite;
    }

    @Override
    public int getCount() {
        return lesRapportsVisite.size();
    }

    @Override
    public Object getItem(int i) {
        return lesRapportsVisite.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.list_item, null);
        TextView tvRap = (TextView) v.findViewById(R.id.tv_nomRap);
        TextView tvDate = (TextView) v.findViewById(R.id.tv_date);
        TextView tvPar = (TextView) v.findViewById(R.id.tv_pra);
        TextView tvVu = (TextView)v.findViewById(R.id.vu);

        //Set Text for TextView


        String date = lesRapportsVisite.get(i).getDateRedaction();

        tvRap.setText(String.valueOf(lesRapportsVisite.get(i).getNumero()));
        tvDate.setText(date);
        tvPar.setText(lesRapportsVisite.get(i).getPraticien());
        v.setTag(lesRapportsVisite.get(i).getNumero());

        return v;
    }
}
