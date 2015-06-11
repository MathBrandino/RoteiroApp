package br.com.caelum.roteirosapp.activity.fragment;

import android.util.Log;

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

    Parada parada;

    public MapaFragment(Parada parada) {
        this.parada = parada;
    }

    @Override
    public void onResume() {
        super.onResume();





        GoogleMap map =  getMap();
        LatLng latLng = new LatLng(23.588305, 46.632395);
        map.addMarker(new MarkerOptions().title("Parada").snippet(parada.getDescricao()).position(latLng).visible(true));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));

    }
}
