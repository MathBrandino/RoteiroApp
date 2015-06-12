package br.com.caelum.roteirosapp.activity.fragment;

import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import br.com.caelum.roteirosapp.activity.modelo.Parada;
import br.com.caelum.roteirosapp.activity.modelo.Viagem;


/**
 * Created by matheus on 10/06/15.
 */
public class MapaFragment extends SupportMapFragment {

    Parada parada;
    Viagem viagem;

    public MapaFragment() {
    }

    public MapaFragment(Parada parada) {
        this.parada = parada;
    }


    @Override
    public void onResume() {
        super.onResume();

        LatLng latLng = new LatLng(parada.getLatitude(), parada.getLongitude());
        getMap().addMarker(new MarkerOptions().title("Parada").position(latLng).visible(true));
        getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));


    }
}
