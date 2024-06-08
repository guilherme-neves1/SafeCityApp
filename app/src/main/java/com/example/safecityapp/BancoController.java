package com.example.safecityapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class BancoController {
    private SQLiteDatabase db;
    private CriaBanco banco;

    public BancoController(Context context) {
        banco = new CriaBanco(context);
    }

    public String insereDadosUsuarios(String nome, String dtnascimento, String sexo, long cep, String endereco, long numero, String complemento, String bairro, String cidade, String estado, long telefone, String email, String senha) {
        ContentValues valores;
        long resultado;

        try {
            db = banco.getWritableDatabase(); // Corrigido: Obter instância do banco de dados a partir do objeto CriaBanco
            valores = new ContentValues();
            valores.put("nome", nome);
            valores.put("dtnascimento", dtnascimento);
            valores.put("sexo", sexo);
            valores.put("cep", cep);
            valores.put("endereco", endereco);
            valores.put("numero", numero);
            valores.put("complemento", complemento);
            valores.put("bairro", bairro);
            valores.put("cidade", cidade);
            valores.put("estado", estado);
            valores.put("telefone", telefone);
            valores.put("email", email);
            valores.put("senha", senha);

            resultado = db.insert("usuarios", null, valores);
            db.close();

            if (resultado == -1) {
                return "Erro ao inserir registro";
            } else {
                return "Registro inserido com sucesso";
            }
        } catch (Exception e) {
            Log.e("BancoController", "Erro ao inserir dados: " + e.getMessage(), e);
            return "Erro ao inserir registro";
        }
    }

    public boolean validarLogin(String email, String senha) {
        boolean isValid = false;
        Cursor cursor = null;

        try {
            db = banco.getReadableDatabase(); // Corrigido: Obter instância do banco de dados a partir do objeto CriaBanco
            String[] columns = {"idUser"};
            String selection = "email = ? AND senha = ?";
            String[] selectionArgs = {email, senha};

            cursor = db.query("usuarios", columns, selection, selectionArgs, null, null, null);

            isValid = cursor.getCount() > 0;
        } catch (Exception e) {
            Log.e("BancoController", "Erro ao validar login: " + e.getMessage(), e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return isValid;
    }
}
