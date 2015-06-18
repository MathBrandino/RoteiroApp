package br.com.caelum.roteirosapp.activity.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

import br.com.caelum.roteirosapp.R;
import br.com.caelum.roteirosapp.activity.FormularioParadaActivity;
import br.com.caelum.roteirosapp.activity.RoteiroViagemActivity;
import br.com.caelum.roteirosapp.activity.actionBar.ContextActionBarParada;
import br.com.caelum.roteirosapp.activity.dao.DatabaseHelperDao;
import br.com.caelum.roteirosapp.activity.fragment.MapaFragment;
import br.com.caelum.roteirosapp.activity.modelo.Parada;
import br.com.caelum.roteirosapp.activity.modelo.Viagem;

/**
 * Created by matheus on 09/06/15.
 */
public class RoteiroViagemAdapter extends BaseAdapter {

    private List<Parada> paradas;
    private RoteiroViagemActivity activity;
    private Viagem viagem;

    private DatabaseHelperDao daoHelper;


    public RoteiroViagemAdapter(List<Parada> paradas, RoteiroViagemActivity activity) {
        this.paradas = paradas;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return paradas.size();
    }

    @Override
    public Object getItem(int position) {
        return paradas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return paradas.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = activity.getLayoutInflater().inflate(R.layout.roteiro_parada_item, parent, false);

        final Parada parada = paradas.get(position);

        daoHelper = new DatabaseHelperDao(activity);

        Intent intent = activity.getIntent();
        viagem = (Viagem) intent.getSerializableExtra("viagem");

        TextView descricao = (TextView) view.findViewById(R.id.roteiro_parada_item_descricao);
        descricao.setText(parada.getDescricao());
        descricao.setMinHeight(150);

        Button buttonEditar = (Button) view.findViewById(R.id.roteiro_parada_item_editar);

        buttonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, FormularioParadaActivity.class);
                intent.putExtra("Editar", parada);
                activity.startActivity(intent);
            }
        });

        Bitmap bm;

        if (parada.getCaminhoDaFoto() != null) {
            bm = BitmapFactory.decodeFile(parada.getCaminhoDaFoto());
        } else {
            bm = BitmapFactory.decodeResource(activity.getResources(), R.drawable.person);
        }
        bm = Bitmap.createScaledBitmap(bm, parent.getWidth(), 250, true);

        ImageView imageView = (ImageView) view.findViewById(R.id.roteiro_parada_item_foto);
        imageView.setImageBitmap(bm);

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                ContextActionBarParada contextActionBarParada = new ContextActionBarParada(activity, parada, daoHelper);

                activity.startSupportActionMode(contextActionBarParada);

                return true;

            }
        });

        return view;
    }

}


