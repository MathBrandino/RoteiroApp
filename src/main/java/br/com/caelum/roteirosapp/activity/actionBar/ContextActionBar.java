package br.com.caelum.roteirosapp.activity.actionBar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.caelum.roteirosapp.R;
import br.com.caelum.roteirosapp.activity.FormularioViagemActivity;
import br.com.caelum.roteirosapp.activity.MostraViagemActivity;
import br.com.caelum.roteirosapp.activity.RoteirosActivity;
import br.com.caelum.roteirosapp.activity.dao.DatabaseHelperDao;
import br.com.caelum.roteirosapp.activity.dao.ParadaDao;
import br.com.caelum.roteirosapp.activity.dao.ViagemDao;
import br.com.caelum.roteirosapp.activity.modelo.Parada;
import br.com.caelum.roteirosapp.activity.modelo.Viagem;

/**
 * Created by matheus on 08/06/15.
 */
public class ContextActionBar implements ActionMode.Callback {

    private RoteirosActivity activity;
    private Viagem viagemSelecionada;
    private DatabaseHelperDao daoHelper;
    private List<Parada> paradas;

    public ContextActionBar(RoteirosActivity activity, Viagem viagemSelecionada, DatabaseHelperDao daoHelper) {
        this.activity = activity;
        this.viagemSelecionada = viagemSelecionada;
        this.daoHelper = daoHelper;
    }

    @Override
    public boolean onCreateActionMode(final ActionMode mode, Menu menu) {

        activity.getMenuInflater().inflate(R.menu.context_action_bar, menu);

        ParadaDao dao = new ParadaDao(daoHelper);
        paradas = dao.getLista(viagemSelecionada);


        MenuItem compartilhar = menu.add("Compartilhar");
        compartilhar.setIcon(R.drawable.compartilhar);

        compartilhar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                ParadaDao dao = new ParadaDao(daoHelper);
                List<Parada> paradas = dao.getLista(viagemSelecionada);

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/*");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Minha viagem foi : " + viagemSelecionada.getNome());
                if(paradas.size() > 1) {
                    intent.putExtra(Intent.EXTRA_TEXT, "Fiz até agora " + paradas.size() + " paradas");
                } else  {
                    intent.putExtra(Intent.EXTRA_TEXT, "Fiz até agora " + paradas.size() + " parada ");
                }

                activity.startActivity(Intent.createChooser(intent, "Escolha onde compartilhar"));

                return false;
            }
        });


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

        MenuItem alterar = menu.findItem(R.id.menu_action_alterar);

        alterar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(activity, FormularioViagemActivity.class);
                intent.putExtra("Editar", viagemSelecionada);
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
