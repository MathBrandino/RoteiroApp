package br.com.caelum.roteirosapp.activity.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by matheus on 10/06/15.
 */
public class DatabaseHelperDao extends SQLiteOpenHelper {


    private final static String DATABASE = "RoteirosApp" ;
    private final static int VERSAO =  4;
    private final static String TABELAParadas = "Paradas";
    private final static String TABELAViagens = "Viagens";


    public DatabaseHelperDao(Context context) {
        super(context, DATABASE , null, VERSAO);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE  "+ TABELAViagens +
                " (id INTEGER PRIMARY KEY, " +
                " nome TEXT NOT NULL );";


        String ddl =
                "CREATE TABLE  "+ TABELAParadas +
                        " (id INTEGER PRIMARY KEY, " +
                        " descricao TEXT NOT NULL ," +
                        " caminhoDaFoto TEXT ," +
                        " longitude TEXT,   " +
                        " latitude TEXT,  "+
                        " idViagem INTEGER NOT NULL, " +
                        "  FOREIGN KEY(idViagem) REFERENCES  "+ TABELAViagens + " (id) ); ";




        db.execSQL(sql);
        db.execSQL(ddl);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql =
                "DROP TABLE IF EXISTS  " + TABELAViagens ;
        String ddl =
                "DROP TABLE IF EXISTS " + TABELAParadas;

        db.execSQL(sql);
        db.execSQL(ddl);
        onCreate(db);
    }
}
