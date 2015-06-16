package br.com.caelum.roteirosapp.activity.fragment;

import android.graphics.Color;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.roteirosapp.activity.dao.DatabaseHelperDao;
import br.com.caelum.roteirosapp.activity.dao.ParadaDao;
import br.com.caelum.roteirosapp.activity.modelo.Parada;
import br.com.caelum.roteirosapp.activity.modelo.Viagem;

/**
 * Created by matheus on 15/06/15.
 */
public class MapaRoteiroFragment extends SupportMapFragment {

    Viagem viagem;
    DatabaseHelperDao helperDao;


    public MapaRoteiroFragment(Viagem viagem) {
        this.viagem = viagem;
    }

    @Override
    public void onResume() {
        super.onResume();

        helperDao = new DatabaseHelperDao(getActivity());
        ParadaDao paradaDao = new ParadaDao(helperDao);
        List<Parada> paradas = paradaDao.getLista(viagem);

        LatLng latLng;

        if (paradas.size() != 0) {
           latLng = new LatLng(paradas.get(0).getLatitude(), paradas.get(0).getLongitude());
        } else {
            latLng = new LatLng(0,0);
        }

        for(Parada parada : paradas){

            GoogleMap map = getMap();

            LatLng latLngNovo = new LatLng(parada.getLatitude(), parada.getLongitude());

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.title(viagem.getNome()).snippet(parada.getDescricao()).visible(true).position(latLngNovo);

            PolylineOptions polylineOptions = new PolylineOptions();
            polylineOptions.add(latLng, latLngNovo).visible(true).width(6).color(Color.RED);

            map.addMarker(markerOptions);
            map.addPolyline(polylineOptions);

            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));

            latLng = latLngNovo;

        }
    }
}
