package br.com.caelum.roteirosapp.activity.actionBar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import br.com.caelum.roteirosapp.R;
import br.com.caelum.roteirosapp.activity.MostraViagemActivity;
import br.com.caelum.roteirosapp.activity.RoteirosActivity;
import br.com.caelum.roteirosapp.activity.dao.DatabaseHelperDao;
import br.com.caelum.roteirosapp.activity.dao.ViagemDao;
import br.com.caelum.roteirosapp.activity.modelo.Viagem;

/**
 * Created by matheus on 08/06/15.
 */
public class ContextActionBar implements ActionMode.Callback {

    private RoteirosActivity activity;
    private Viagem viagemSelecionada;
    private DatabaseHelperDao daoHelper;

    public ContextActionBar(RoteirosActivity activity, Viagem viagemSelecionada, DatabaseHelperDao daoHelper) {
        this.activity = activity;
        this.viagemSelecionada = viagemSelecionada;
        this.daoHelper = daoHelper;
    }

    @Override
    public boolean onCreateActionMode(final ActionMode mode, Menu menu) {

        activity.getMenuInflater().inflate(R.menu.context_action_bar, menu);


        MenuItem mapa = menu.findItem(R.id.menu_action_rota);

        mapa.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(activity, MostraViagemActivity.class);
                intent.putExtra("viagem", viagemSelecionada);
                activity.startActivity(intent);


                return false;
            }
        });

        MenuItem deletar = menu.findItem(R.id.menu_action_deletar);

        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                new AlertDialog.Builder(activity)
                        .setTitle("Deletar")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setMessage("Deseja mesmo deletar " + viagemSelecionada.getNome() + " ?")
                        .setNegativeButton("Não", null)
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int which) {

                                ViagemDao dao = new ViagemDao(daoHelper);

                                dao.deleta(viagemSelecionada);
                                dao.close();

                                activity.carregaLista();
                                mode.finish();
                            }
                        }).show();

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
