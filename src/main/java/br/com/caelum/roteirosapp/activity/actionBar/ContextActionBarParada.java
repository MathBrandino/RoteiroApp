package br.com.caelum.roteirosapp.activity.actionBar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import br.com.caelum.roteirosapp.R;
import br.com.caelum.roteirosapp.activity.RoteiroViagemActivity;
import br.com.caelum.roteirosapp.activity.dao.DatabaseHelperDao;
import br.com.caelum.roteirosapp.activity.dao.ParadaDao;
import br.com.caelum.roteirosapp.activity.modelo.Parada;

/**
 * Created by matheus on 08/06/15.
 */
public class ContextActionBarParada implements ActionMode.Callback {

    private RoteiroViagemActivity activity;
    private Parada paradaSelecionada;
    private DatabaseHelperDao daoHelper;



    public ContextActionBarParada(RoteiroViagemActivity activity, Parada paradaSelecionada, DatabaseHelperDao daoHelper) {
        this.activity = activity;
        this.paradaSelecionada = paradaSelecionada;
        this.daoHelper = daoHelper;
    }

    @Override
    public boolean onCreateActionMode(final ActionMode mode, Menu menu) {

        activity.getMenuInflater().inflate(R.menu.context_action_bar, menu);

        MenuItem deletar = menu.findItem(R.id.menu_action_deletar);

        MenuItem mapa =  menu.findItem(R.id.menu_action_rota);
        mapa.setTitle("Rota até  Parada");

        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                new AlertDialog.Builder(activity)
                        .setTitle("Deletar")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setMessage("Deseja mesmo deletar parada ?")
                        .setNegativeButton("Não", null)
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int which) {


                                ParadaDao dao = new ParadaDao(daoHelper);

                                dao.deleta(paradaSelecionada);
                                dao.close();

                                activity.carregaLista();
                                mode.finish();
                            }
                        }).show()

                ;









                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }
}
