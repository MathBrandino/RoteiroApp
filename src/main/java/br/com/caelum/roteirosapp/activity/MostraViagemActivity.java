package br.com.caelum.roteirosapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import br.com.caelum.roteirosapp.R;
import br.com.caelum.roteirosapp.activity.fragment.MapaFragment;
import br.com.caelum.roteirosapp.activity.fragment.MapaRoteiroFragment;
import br.com.caelum.roteirosapp.activity.modelo.Viagem;

/**
 * Created by matheus on 12/06/15.
 */
public class MostraViagemActivity extends AppCompatActivity {

    private Viagem viagem;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_mostra_viagem);

        Intent intent = getIntent();
        viagem = (Viagem) intent.getSerializableExtra("viagem");

        MapaRoteiroFragment mapaFragment = new MapaRoteiroFragment(viagem);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.framelayout_viagem, mapaFragment);

        transaction.commit();
    }
}
