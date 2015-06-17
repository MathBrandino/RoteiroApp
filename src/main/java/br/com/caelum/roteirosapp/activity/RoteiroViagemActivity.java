package br.com.caelum.roteirosapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import br.com.caelum.roteirosapp.R;
import br.com.caelum.roteirosapp.activity.adapter.RoteiroViagemAdapter;
import br.com.caelum.roteirosapp.activity.dao.DatabaseHelperDao;
import br.com.caelum.roteirosapp.activity.dao.ParadaDao;
import br.com.caelum.roteirosapp.activity.modelo.Parada;
import br.com.caelum.roteirosapp.activity.modelo.Viagem;

/**
 * Created by matheus on 08/06/15.
 */
public class RoteiroViagemActivity extends AppCompatActivity {

    private ListView listParadas;
    private Viagem viagem;

    private DatabaseHelperDao daoHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roteiro_viagem);

        daoHelper = new DatabaseHelperDao(this);

        getViagem();

        listParadas = (ListView) findViewById(R.id.lista_paradas);

    }


    public Viagem getViagem() {
        Intent intent = getIntent();
        if (intent.hasExtra("viagem")) {
            viagem = (Viagem) intent.getSerializableExtra("viagem");
        }

        return viagem;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_roteiro_viagem, menu);


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_roteiro_viagem_add:
                Intent intent = new Intent(this, FormularioParadaActivity.class);
                intent.putExtra("viagem", viagem);
                startActivity(intent);

                return true;

        }

        return true;
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context_action_bar, menu);

    }

    public void carregaLista() {

        ParadaDao dao = new ParadaDao(daoHelper);
        List<Parada> paradas = dao.getLista(viagem);
        dao.close();

        RoteiroViagemAdapter adapter = new RoteiroViagemAdapter(paradas, this);

        listParadas.setAdapter(adapter);

    }


    @Override
    protected void onResume() {
        super.onResume();
        this.carregaLista();
    }
}
