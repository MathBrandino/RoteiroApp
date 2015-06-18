package br.com.caelum.roteirosapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import java.io.File;

import br.com.caelum.roteirosapp.R;
import br.com.caelum.roteirosapp.activity.camera.IniciadorDeCamera;
import br.com.caelum.roteirosapp.activity.dao.DatabaseHelperDao;
import br.com.caelum.roteirosapp.activity.dao.ParadaDao;
import br.com.caelum.roteirosapp.activity.helper.FormularioParadaHelper;
import br.com.caelum.roteirosapp.activity.modelo.Parada;
import br.com.caelum.roteirosapp.activity.modelo.Viagem;

/**
 * Created by matheus on 08/06/15.
 */
public class FormularioParadaActivity extends AppCompatActivity {

    private FormularioParadaHelper formularioParadaHelper;
    private String caminhoDaFoto;
    private DatabaseHelperDao daoHelper;
    final int CODE = 123;
    private Viagem viagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_parada);

        daoHelper = new DatabaseHelperDao(this);

        Intent intent = getIntent();

        formularioParadaHelper = new FormularioParadaHelper(this);

        if (intent.hasExtra("viagem")) {
            viagem = (Viagem) intent.getSerializableExtra("viagem");

            Toast.makeText(this, "Clique no botao para definir as coordenadas", Toast.LENGTH_LONG).show();

        }

        if (intent.hasExtra("Editar")) {

            formularioParadaHelper.colocaParadaFormulario((Parada) intent.getSerializableExtra("Editar"));

        }

        Button button = formularioParadaHelper.getButtonFoto();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IniciadorDeCamera iniciadorDeCamera = new IniciadorDeCamera(FormularioParadaActivity.this);

                caminhoDaFoto = iniciadorDeCamera.caminhoFoto();

                iniciadorDeCamera.iniciCamera(caminhoDaFoto, CODE);


            }
        });


        Button buttonCoordenada = formularioParadaHelper.getButtonCoordenadas();

        buttonCoordenada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                LocationListener locationListener = new LocationListener() {
                    public void onLocationChanged(Location location) {
                        Double latitude = location.getLatitude();
                        Double longitude = location.getLongitude();

                        formularioParadaHelper.setaCoordenadas(latitude.toString(), longitude.toString());

                    }

                    public void onStatusChanged(String provider, int status, Bundle extras) {
                    }

                    public void onProviderEnabled(String provider) {
                    }

                    public void onProviderDisabled(String provider) {
                    }


                };

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

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

        switch (item.getItemId()) {

            case R.id.menu_formulario_parada_salvar:
                Parada parada = formularioParadaHelper.pegaParadaDoFormulario();

                ParadaDao dao = new ParadaDao(daoHelper);

                if (formularioParadaHelper.validaParada()) {

                    if (parada.getId() == null) {

                        dao.insere(parada, viagem.getId());
                        finish();

                        return false;

                    } else {

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

        if (requestCode == CODE) {
            if (resultCode == Activity.RESULT_OK) {
                formularioParadaHelper.carregaImagem(this.caminhoDaFoto);
            } else {
                this.caminhoDaFoto = null;
            }
        }
    }
}
