package br.com.caelum.roteirosapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.caelum.roteirosapp.R;
import br.com.caelum.roteirosapp.activity.actionBar.ContextActionBar;
import br.com.caelum.roteirosapp.activity.dao.DatabaseHelperDao;
import br.com.caelum.roteirosapp.activity.dao.ViagemDao;
import br.com.caelum.roteirosapp.activity.modelo.Viagem;


public class RoteirosActivity extends AppCompatActivity {

    ListView listRoteiro;
    List<Viagem> viagens;
    DatabaseHelperDao daoHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roteiros);


        daoHelper = new DatabaseHelperDao(this);

        listRoteiro = (ListView) findViewById(R.id.lista_roteiros);

        ViagemDao dao = new ViagemDao(daoHelper);
        viagens =  dao.getLista();

        if(viagens.size() < 1) {
            Toast.makeText(this, "Adicione uma nova viagem selecionando o botão '+' ", Toast.LENGTH_LONG).show();
        }

        ArrayAdapter<Viagem> adapter = new ArrayAdapter<Viagem>(this, android.R.layout.simple_list_item_1, viagens);

        listRoteiro.setAdapter(adapter);

        registerForContextMenu(listRoteiro);



        listRoteiro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Viagem viagem = (Viagem) listRoteiro.getItemAtPosition(position);

                Intent intent = new Intent(RoteirosActivity.this, RoteiroViagemActivity.class);
                intent.putExtra("viagem" ,viagem);
                startActivity(intent);
            }
        });



        listRoteiro.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Viagem viagemSelecionada = (Viagem) parent.getItemAtPosition(position);


                ContextActionBar actionBar = new ContextActionBar(RoteirosActivity.this, viagemSelecionada, daoHelper);

                startSupportActionMode(actionBar);

                return true;
            }
        });


        Button buttonAdd = (Button) findViewById(R.id.roteiros_botao_add);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RoteirosActivity.this, FormularioViagemActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_roteiros, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.carregaLista();
    }

    public void carregaLista(){
        ViagemDao dao = new ViagemDao(daoHelper);
        List<Viagem> viagens = dao.getLista();
        dao.close();

        ArrayAdapter<Viagem> adapter = new ArrayAdapter<Viagem>(this, android.R.layout.simple_list_item_1, viagens);

        this.listRoteiro.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context_action_bar, menu);

    }
}
