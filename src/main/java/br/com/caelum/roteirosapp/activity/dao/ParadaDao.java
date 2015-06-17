package br.com.caelum.roteirosapp.activity.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.roteirosapp.activity.modelo.Parada;
import br.com.caelum.roteirosapp.activity.modelo.Viagem;

/**
 * Created by matheus on 08/06/15.
 */
public class ParadaDao {

    private final static String TABELA = "Paradas";


    private final DatabaseHelperDao dao;

    public ParadaDao(DatabaseHelperDao dao) {
        this.dao = dao;
    }

    public void insere(Parada parada, Long id) {
        ContentValues values = new ContentValues();

        values.put("descricao", parada.getDescricao());
        values.put("caminhoDaFoto", parada.getCaminhoDaFoto());
        values.put("idViagem", id);
        values.put("latitude", parada.getLatitude());
        values.put("longitude", parada.getLongitude());

        dao.getWritableDatabase().insert(TABELA, null, values);
    }

    public List<Parada> getLista(Viagem viagem) {

        List<Parada> paradas = new ArrayList<Parada>();

        Cursor cursor = dao.getReadableDatabase().rawQuery(" Select * from  " + TABELA + " where idViagem = " + viagem.getId() + " ;", null);

        while (cursor.moveToNext()) {
            Parada parada = new Parada();

            parada.setId(cursor.getLong(cursor.getColumnIndex("id")));
            parada.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
            parada.setCaminhoDaFoto(cursor.getString(cursor.getColumnIndex("caminhoDaFoto")));
            parada.setLongitude(cursor.getDouble(cursor.getColumnIndex("longitude")));
            parada.setLatitude(cursor.getDouble(cursor.getColumnIndex("latitude")));

            paradas.add(parada);
        }
        cursor.close();

        return paradas;
    }


    public void deleta(Parada parada) {

        String[] args = {parada.getId().toString()};
        dao.getWritableDatabase().delete(TABELA, "id= ?", args);


    }


    public void altera(Parada parada) {

        ContentValues values = new ContentValues();

        values.put("descricao", parada.getDescricao());
        values.put("caminhoDaFoto", parada.getCaminhoDaFoto());

        String[] args = {String.valueOf(parada.getId())};

        dao.getWritableDatabase().update(TABELA, values, "id=?", args);

    }

    public void close() {
        dao.close();
    }
}
