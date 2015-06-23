package br.com.caelum.roteirosapp.activity.fragment;


import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import br.com.caelum.roteirosapp.activity.modelo.Parada;


/**
 * Created by matheus on 10/06/15.
 */
public class MapaFragment extends SupportMapFragment {



    @Override
    public void onResume() {
        super.onResume();

        Bundle bundle = getArguments();

        Parada parada = (Parada) bundle.getSerializable("parada");

        GoogleMap map = getMap();
        map.clear();
        LatLng latLng = new LatLng(parada.getLatitude(), parada.getLongitude());
        map.addMarker(new MarkerOptions().title("Parada").snippet(parada.getDescricao()).position(latLng).visible(true));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));

    }
}
