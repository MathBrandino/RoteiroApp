package br.com.caelum.roteirosapp.activity.fragment;


import android.content.Intent;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.List;

import br.com.caelum.roteirosapp.activity.dao.DatabaseHelperDao;
import br.com.caelum.roteirosapp.activity.dao.ParadaDao;
import br.com.caelum.roteirosapp.activity.modelo.Parada;
import br.com.caelum.roteirosapp.activity.modelo.Viagem;


/**
 * Created by matheus on 10/06/15.
 */
public class MapaFragment extends SupportMapFragment {

    private Parada parada;


    public MapaFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();

        Intent intent = getActivity().getIntent();
        Viagem viagem = (Viagem) intent.getSerializableExtra("viagem");

        DatabaseHelperDao databaseHelperDao = new DatabaseHelperDao(getActivity());
        ParadaDao dao = new ParadaDao(databaseHelperDao);
        List<Parada> paradas = dao.getLista(viagem);

        for(Parada parada : paradas) {

            GoogleMap map = getMap();
            map.clear();
            LatLng latLng = new LatLng(parada.getLatitude(), parada.getLongitude());
            map.addMarker(new MarkerOptions().title("Parada").snippet(parada.getDescricao()).position(latLng).visible(true));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));

        }
    }
}
