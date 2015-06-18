package br.com.caelum.roteirosapp.activity.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.roteirosapp.activity.modelo.Viagem;

/**
 * Created by matheus on 08/06/15.
 */
public class ViagemDao  {

    private final static String TABELA = "Viagens";
    private final static String TABELAPARADAS = "Paradas";

    private final DatabaseHelperDao dao;

    public ViagemDao(DatabaseHelperDao dao) {
        this.dao = dao;
    }


    public void insere(Viagem viagem) {
        ContentValues values = new ContentValues();

        values.put("nome", viagem.getNome());
        values.put("dataInicial", viagem.getDataInicio());
        values.put("dataFinal", viagem.getDataFinal());

        dao.getWritableDatabase().insert(TABELA, null, values);
    }


    public List<Viagem> getLista() {

        List<Viagem> viagens = new ArrayList<Viagem>();

        Cursor cursor = dao.getReadableDatabase().rawQuery(" Select * from  " + TABELA + " ;", null);

        while (cursor.moveToNext()) {
            Viagem viagem = new Viagem();

            viagem.setId(cursor.getLong(cursor.getColumnIndex("id")));
            viagem.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            viagem.setDataInicio(cursor.getString(cursor.getColumnIndex("dataInicial")));
            viagem.setDataFinal(cursor.getString(cursor.getColumnIndex("dataFinal")));

            viagens.add(viagem);
        }
        cursor.close();

        return viagens;
    }

    public void deleta(Viagem viagem) {

        String[] args = {viagem.getId().toString()};
        dao.getWritableDatabase().delete(TABELAPARADAS, "idViagem = ?", args);
        dao.getWritableDatabase().delete(TABELA, "id= ?", args);

    }

    public void altera(Viagem viagem){

        ContentValues values = new ContentValues();

        values.put("nome", viagem.getNome());
        values.put("dataInicial", viagem.getDataInicio());
        values.put("dataFinal", viagem.getDataFinal());

        String[] args= {viagem.getId().toString()};

        dao.getWritableDatabase().update(TABELA, values, "id=?" , args);
    }



    public void close() {
        dao.close();
    }
}
