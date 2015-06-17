package br.com.caelum.roteirosapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import br.com.caelum.roteirosapp.R;
import br.com.caelum.roteirosapp.activity.dao.DatabaseHelperDao;
import br.com.caelum.roteirosapp.activity.dao.ViagemDao;
import br.com.caelum.roteirosapp.activity.helper.FormularioViagemHelper;
import br.com.caelum.roteirosapp.activity.modelo.Viagem;

/**
 * Created by matheus on 08/06/15.
 */
public class FormularioViagemActivity extends AppCompatActivity {

    private DatabaseHelperDao daoHelper;
    private FormularioViagemHelper viagemHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_viagem);

        daoHelper = new DatabaseHelperDao(this);

        viagemHelper = new FormularioViagemHelper(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario_viagem, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_formulario_viagem_salvar:
                Viagem viagem = viagemHelper.pegaViagemDoFormulario();

                ViagemDao dao = new ViagemDao(daoHelper);
                if (viagemHelper.temNome()) {
                    dao.insere(viagem);
                    dao.close();
                    finish();
                } else {
                    viagemHelper.mostraErro();
                }
                return true;
        }
        return false;
    }
}
