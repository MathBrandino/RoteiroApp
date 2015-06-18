package br.com.caelum.roteirosapp.activity.helper;

import android.app.Activity;
import android.widget.EditText;

import br.com.caelum.roteirosapp.R;
import br.com.caelum.roteirosapp.activity.modelo.Viagem;

/**
 * Created by matheus on 08/06/15.
 */
public class FormularioViagemHelper {

    private EditText nome;

    private Viagem viagem;

    private EditText dataInicio;
    private EditText dataFinal;

    public FormularioViagemHelper(Activity activity) {
        nome = (EditText) activity.findViewById(R.id.formulario_descricao_viagem);
        dataInicio = (EditText) activity.findViewById(R.id.formulario_viagem_data_inicial);
        dataFinal = (EditText) activity.findViewById(R.id.formulario_viagem_data_final);

        viagem = new Viagem();
    }

    public void colocaViagemFormulario(Viagem viagem){

        nome.setText(viagem.getNome());
        dataInicio.setText(viagem.getDataInicio());
        dataFinal.setText(viagem.getDataFinal());

        this.viagem = viagem;
    }

    public Viagem pegaViagemDoFormulario() {
        viagem.setNome(nome.getText().toString());
        viagem.setDataInicio(dataInicio.getText().toString());
        viagem.setDataFinal(dataFinal.getText().toString());


        return viagem;
    }

    public boolean temNome() {
        return !nome.getText().toString().trim().isEmpty();
    }

    public void mostraErroNome() {
        nome.setError("Nome da viagem não pode ser vazio");

    }

    public boolean temDataInicio() {
        return  !dataInicio.getText().toString().trim().isEmpty();
    }

    public void mostraErroDataInicio() {
        dataInicio.setError("Data inicial não pode ser vazia");
    }

    public boolean validaDataInicial(){
        if(!temDataInicio()){
            mostraErroDataInicio();
            return false;
        }
        return true;
    }

    public boolean temDataFinal() {
        return  !dataFinal.getText().toString().trim().isEmpty();
    }

    public void mostraErroDataFinal(){
        dataFinal.setError("Data final não pode ser nula");
    }

    public boolean validaDataFinal(){
        if(!temDataFinal()){
            mostraErroDataFinal();
            return false;
        }
        return true;
    }


    public boolean validaNome(){
        if(!temNome()){
            mostraErroNome();
            return false;
        }
        return true;
    }

    public boolean validaViagem(){
       if(validaNome() && validaDataInicial()&& validaDataFinal() ){
           return true;
       }
        return false;
    }

}
