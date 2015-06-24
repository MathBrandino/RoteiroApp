package br.com.caelum.roteirosapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.Toast;

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


        Intent intent = getIntent();


        daoHelper = new DatabaseHelperDao(this);

        viagemHelper = new FormularioViagemHelper(this);


        if (intent.hasExtra("Editar")){
            viagemHelper.colocaViagemFormulario((Viagem) intent.getSerializableExtra("Editar"));


        }

        viagemHelper.getCalendarFinal().setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                viagemHelper.setDataFinal(viagemHelper.getCalendarFinal().getDate());
            }
        });

        viagemHelper.getCalendarInicial().setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                viagemHelper.setDataInicial(viagemHelper.getCalendarInicial().getDate());
            }
        });

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
                if (viagemHelper.validaViagem()) {
                    if (viagem.getId() == null) {
                        dao.insere(viagem);
                        dao.close();
                        finish();

                    } else {
                        dao.altera(viagem);
                        dao.close();
                        finish();

                    }
                }

                return true;
        }
        return false;
    }
}
