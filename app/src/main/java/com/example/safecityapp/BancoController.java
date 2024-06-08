package com.example.safecityapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Date;

public class BancoController {
    private SQLiteDatabase db;
    private CriaBanco banco;

    public BancoController(Context context) {
        banco = new CriaBanco(context);
    }


    public String insereDados(String txtNome, String txtEmail) {
        ContentValues valores;
        long resultado;
        db = banco.getWritableDatabase();

        valores = new ContentValues();
        valores.put("nome", txtNome);
        valores.put("email", txtEmail);

        resultado = db.insert("contatos", null, valores);
        db.close();

        if (resultado == -1)
            return "Erro ao inserir registro";
        else
            return "Registro Inserido com sucesso";
    }

    public String insereDadosUsuarios(String txtNome, String txtDtnascimento, String txtSexo, Integer IntegerCep, String txtEndereco, Integer IntegerNumero, String txtComplemento, String txtBairro, String txtCidade, String txtEstado, Integer IntegerTelefone, String txtEmail, String txtSenha) {
        ContentValues valores;
        long resultado;
        db = banco.getWritableDatabase();

        valores = new ContentValues();
        valores.put("nome", txtNome);
        valores.put("dtnascimento", txtDtnascimento);
        valores.put("sexo", txtSexo);
        valores.put("cep", IntegerCep);
        valores.put("endereco", txtEndereco);
        valores.put("numero", IntegerNumero);
        valores.put("complemento", txtComplemento);
        valores.put("bairro", txtBairro);
        valores.put("cidade", txtCidade);
        valores.put("estado", txtEstado);
        valores.put("telefone", IntegerTelefone);
        valores.put("email", txtEmail);
        valores.put("senha", txtSenha);

        try {
            resultado = db.insert("usuarios", null, valores);
            Log.d("BancoController", "Dados inseridos com sucesso: " + valores.toString());
        } catch (Exception e) {
            Log.e("BancoController", "Erro ao inserir dados no banco: " + e.getMessage(), e);
            resultado = -1;
        } finally {
            db.close();
        }

        if (resultado == -1)
            return "Erro ao inserir registro";
        else
            return "Registro Inserido com sucesso";
    }



    public Cursor carregaDadosPeloCodigo(int id) {
        Cursor cursor;
        String[] campos = { "codigo", "nome", "email" };
        String where = "codigo=" + id;
        db = banco.getReadableDatabase();
        cursor = db.query("contatos", campos, where, null, null, null,
                null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        db.close();
        return cursor;
    }

    public String alteraDados(int id, String nome, String email){

        String msg = "Dados alterados com sucesso!!!" ;

        db = banco.getReadableDatabase();

        ContentValues valores = new ContentValues() ;
        valores.put("nome" , nome ) ;
        valores.put("email", email) ;

        String condicao = "codigo = " + id;

        int linha ;
        linha = db.update("contatos", valores, condicao, null) ;

        if (linha < 1){
            msg = "Erro ao alterar os dados" ;
        }

        db.close();
        return msg;
    }

    public String excluirDados(int id){
        String msg = "Registro Excluído" ;

        db = banco.getReadableDatabase();

        String condicao = "codigo = " + id ;

        int linhas ;
        linhas = db.delete("contatos", condicao, null) ;

        if ( linhas < 1) {
            msg = "Erro ao Excluir" ;
        }

        db.close();
        return msg;
    }

}

