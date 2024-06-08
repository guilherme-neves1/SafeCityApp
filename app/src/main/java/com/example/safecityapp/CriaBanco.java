package com.example.safecityapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CriaBanco extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "banco_safecity.db";
    private static final int VERSAO = 12;
    public CriaBanco(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE contatos ("
                + "codigo integer primary key autoincrement,"
                + "nome text,"
                + "email text)";
        db.execSQL(sql);
        sql = "CREATE TABLE usuarios ("
                + "idUser integer primary key autoincrement,"
                + "nome text,"
                + "dtnascimento text,"
                + "sexo text,"
                + "cep integer,"
                + "endereco text,"
                + "numero integer,"
                + "complemento text,"
                + "bairro text,"
                + "cidade text,"
                + "estado text,"
                + "telefone integer,"
                + "email text,"
                + "senha text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS contatos");
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        onCreate(db);
    }
}
