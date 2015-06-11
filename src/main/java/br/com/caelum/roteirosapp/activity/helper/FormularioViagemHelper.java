package br.com.caelum.roteirosapp.activity.helper;

import android.app.Activity;
import android.widget.EditText;

import br.com.caelum.roteirosapp.R;
import br.com.caelum.roteirosapp.activity.modelo.Viagem;

/**
 * Created by matheus on 08/06/15.
 */
public class FormularioViagemHelper  {

    private EditText nome;

    private Viagem viagem;

    public FormularioViagemHelper(Activity activity) {
        nome = (EditText) activity.findViewById(R.id.formulario_descricao_viagem);

        viagem = new Viagem();
    }

    public Viagem pegaViagemDoFormulario(){
        viagem.setNome(nome.getText().toString());

        return viagem;
    }

    public boolean temNome(){
        return !nome.getText().toString().isEmpty();
    }

    public void mostraErro(){
        nome.setError("Nome da viagem não pode ser vazio");
    }
}
