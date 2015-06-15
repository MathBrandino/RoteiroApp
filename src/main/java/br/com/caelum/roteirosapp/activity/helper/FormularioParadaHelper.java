package br.com.caelum.roteirosapp.activity.helper;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.caelum.roteirosapp.R;
import br.com.caelum.roteirosapp.activity.modelo.Parada;

/**
 * Created by matheus on 08/06/15.
 */
public class FormularioParadaHelper {

    private Parada parada;

    private ImageView fotoParada;
    private Button buttonFoto;

    private EditText descricao;

    private TextView latitude;
    private TextView longitude;

    private Button buttonCoordenadas;

    public FormularioParadaHelper(Activity activity) {
        fotoParada = (ImageView) activity.findViewById(R.id.formulario_imagem);
        buttonFoto = (Button) activity.findViewById(R.id.formulario_botao_foto);
        descricao = (EditText) activity.findViewById(R.id.formulario_descricao_parada);
        latitude = (TextView) activity.findViewById(R.id.formulario_latitude);
        longitude = (TextView) activity.findViewById(R.id.formulario_longitude );
        buttonCoordenadas = (Button) activity.findViewById(R.id.formulario_botao_coordenadas);


        parada = new Parada();

    }

    public Parada pegaParadaDoFormulario() {
        parada.setDescricao(descricao.getText().toString());
        parada.setCaminhoDaFoto((String) fotoParada.getTag());
        parada.setLatitude(Double.parseDouble(latitude.getText().toString()));
        parada.setLongitude(Double.parseDouble(longitude.getText().toString()));



        return parada;
    }


    public void carregaImagem(String caminhoDaFoto) {

        Bitmap imagemFoto = BitmapFactory.decodeFile(caminhoDaFoto);

        Bitmap imagemFotoReduzida = Bitmap.createScaledBitmap(imagemFoto, imagemFoto.getWidth(), 300, true);

        fotoParada.setScaleType(ImageView.ScaleType.FIT_XY);
        fotoParada.setImageBitmap(imagemFotoReduzida);
        fotoParada.setTag(caminhoDaFoto);
    }

    public void colocaParadaFormulario(Parada parada) {

        descricao.setText(parada.getDescricao());
        if (parada.getCaminhoDaFoto() != null) {
            carregaImagem(parada.getCaminhoDaFoto());
        }
        latitude.setText(String.valueOf(parada.getLatitude()));
        longitude.setText(String.valueOf(parada.getLongitude()));


        this.parada = parada;
    }


    public boolean temDescricao() {
        return !descricao.getText().toString().trim().isEmpty();
    }

    public void mostraErro() {
        descricao.setError("Descricao da parada não pode ser vazio");
    }

    public boolean valida() {
        if (!temDescricao()) {
            mostraErro();
            return false;
        }
        return true;

    }

    public void setaCoordenadas(String lat, String lon){
        this.latitude.setText(lat);
        this.longitude.setText(lon);
    }



    public Button getButtonFoto() {
        return buttonFoto;
    }

    public Button getButtonCoordenadas() {
        return buttonCoordenadas;
    }

}