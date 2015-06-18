package br.com.caelum.roteirosapp.activity.camera;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

/**
 * Created by matheus on 18/06/15.
 */
public class IniciadorDeCamera  {

    Activity activity;

    public IniciadorDeCamera(Activity activity) {
        this.activity = activity;
    }

    public String caminhoFoto (){

        return activity.getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
    }

    public void iniciCamera(String caminhoDaFoto, int CODE){

        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        Uri localFoto = Uri.fromFile(new File(caminhoDaFoto));

        camera.putExtra(MediaStore.EXTRA_OUTPUT, localFoto);

        activity.startActivityForResult(camera, CODE);
    }

}
