package br.com.caelum.roteirosapp.activity.helper;

import android.app.Activity;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.caelum.roteirosapp.R;
import br.com.caelum.roteirosapp.activity.modelo.Viagem;

/**
 * Created by matheus on 08/06/15.
 */
public class FormularioViagemHelper {

    private EditText nome;

    private Viagem viagem;

    private TextView dataInicio;
    private TextView dataFinal;

    private CalendarView calendarInicial;
    private CalendarView calendarFinal;


    public FormularioViagemHelper(Activity activity) {
        nome = (EditText) activity.findViewById(R.id.formulario_descricao_viagem);
        dataInicio = (TextView) activity.findViewById(R.id.formulario_viagem_data_inicial);
        dataFinal =  (TextView) activity.findViewById(R.id.formulario_viagem_data_final);
        calendarInicial = (CalendarView) activity.findViewById(R.id.calendario_inicial);
        calendarFinal = (CalendarView) activity.findViewById(R.id.calendario_final);
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


    public CalendarView getCalendarInicial() {
        return calendarInicial;
    }

    public CalendarView getCalendarFinal() {
        return calendarFinal;
    }

    public void setDataFinal(long dataFinalCalendario) {
        String data = converteData(dataFinalCalendario);
        this.dataFinal.setText(data);
    }

    public void  setDataInicial(long dataInicialCalendario){
        String data = converteData(dataInicialCalendario);
        this.dataInicio.setText(data);
    }

    public String converteData(long data){
        Date date = new Date(data);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataText = dateFormat.format(date).toString();
        return dataText;
    }
}
