package br.com.caelum.roteirosapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.Serializable;
import java.security.Provider;
import java.util.List;

import br.com.caelum.roteirosapp.R;
import br.com.caelum.roteirosapp.activity.dao.DatabaseHelperDao;
import br.com.caelum.roteirosapp.activity.dao.ParadaDao;
import br.com.caelum.roteirosapp.activity.helper.FormularioParadaHelper;
import br.com.caelum.roteirosapp.activity.modelo.Parada;
import br.com.caelum.roteirosapp.activity.modelo.Viagem;

/**
 * Created by matheus on 08/06/15.
 */
public class FormularioParadaActivity extends AppCompatActivity {

    FormularioParadaHelper helper;
    String caminhoDaFoto;
    DatabaseHelperDao daoHelper;
    final int CODE = 123;
    Viagem viagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_parada);

        daoHelper = new DatabaseHelperDao(this);

        Intent intent = getIntent();

        helper = new FormularioParadaHelper(this);

        if(intent.hasExtra("viagem")){
            viagem = (Viagem) intent.getSerializableExtra("viagem");
        }

        if (intent.hasExtra("Editar")){

            helper.colocaParadaFormulario((Parada) intent.getSerializableExtra("Editar"));

        }



        Button button = helper.getButtonFoto();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                caminhoDaFoto  = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";


                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                Uri localFoto = Uri.fromFile(new File(caminhoDaFoto));

                camera.putExtra(MediaStore.EXTRA_OUTPUT, localFoto);

                startActivityForResult(camera, CODE);
            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario_parada, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.menu_formulario_parada_salvar :
                Parada parada = helper.pegaParadaDoFormulario();

                ParadaDao dao = new ParadaDao(daoHelper);

                if(helper.valida()){

                    if(parada.getId() == null){

                        dao.insere(parada, viagem.getId());
                        finish();


                        return false;

                    }else {

                        dao.altera(parada);
                        finish();

                    }
                }
                dao.close();



            default:
                    return super.onOptionsItemSelected(item);

        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == CODE){
            if( resultCode == Activity.RESULT_OK){
                helper.carregaImagem(this.caminhoDaFoto);
            }else {
                this.caminhoDaFoto = null;
            }
        }
    }
}
