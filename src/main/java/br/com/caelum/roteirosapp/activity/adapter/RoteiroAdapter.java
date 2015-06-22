package br.com.caelum.roteirosapp.activity.adapter;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.caelum.roteirosapp.R;
import br.com.caelum.roteirosapp.activity.RoteirosActivity;
import br.com.caelum.roteirosapp.activity.modelo.Viagem;

/**
 * Created by matheus on 18/06/15.
 */
public class RoteiroAdapter extends BaseAdapter {

    private List<Viagem> viagens;
    private RoteirosActivity activity;

    public RoteiroAdapter(List<Viagem> viagens, RoteirosActivity activity) {
        this.viagens = viagens;
        this.activity = activity;

    }

    @Override
    public int getCount() {
        return viagens.size();
    }

    @Override
    public Object getItem(int position) {
        return viagens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return viagens.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Viagem viagem = viagens.get(position);

        View  view = activity.getLayoutInflater().inflate(R.layout.roteiro_item, parent, false);

        TextView nome = (TextView) view.findViewById(R.id.viagem_nome);
        nome.setText(viagem.getNome());

        TextView dataInicio = (TextView) view.findViewById(R.id.viagem_inicio);
        dataInicio.setText(viagem.getDataInicio().toString());

        TextView dataFinal = (TextView) view.findViewById(R.id.viagem_final);
        dataFinal.setText(viagem.getDataFinal().toString());

        return view;
    }
}
