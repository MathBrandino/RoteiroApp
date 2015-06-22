package br.com.caelum.roteirosapp.activity.actionBar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;

import br.com.caelum.roteirosapp.R;
import br.com.caelum.roteirosapp.activity.RoteiroViagemActivity;
import br.com.caelum.roteirosapp.activity.dao.DatabaseHelperDao;
import br.com.caelum.roteirosapp.activity.dao.ParadaDao;
import br.com.caelum.roteirosapp.activity.helper.FormularioParadaHelper;
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

        final MenuItem alterar = menu.findItem(R.id.menu_action_alterar);
        alterar.setVisible(false);

        MenuItem compartilhar = menu.add("Compartilhar");
        compartilhar.setIcon(R.drawable.compartilhar);

        compartilhar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/*");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Estou fazendo uma viagem : " + activity.getViagem().getNome());
                intent.putExtra(Intent.EXTRA_TEXT, "Minha parada é : " + paradaSelecionada.getDescricao());

                if(paradaSelecionada.getCaminhoDaFoto() != null) {

                    intent.putExtra(Intent.EXTRA_TEXT,"Minha viagem é : "+ activity.getViagem().getNome() +
                            " e minha parada é : " + paradaSelecionada.getDescricao());
                    intent.setType("image/*");
                    File file = new File(paradaSelecionada.getCaminhoDaFoto());
                    Uri uri = Uri.fromFile(file);
                    intent.putExtra(Intent.EXTRA_STREAM, uri);

                }
                activity.startActivity(Intent.createChooser(intent, "Escolha "));

                return false;
            }
        });

        MenuItem deletar = menu.findItem(R.id.menu_action_deletar);

        MenuItem mapa = menu.findItem(R.id.menu_action_rota);
        mapa.setTitle("Rota até  Parada");
        mapa.setVisible(false);


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
