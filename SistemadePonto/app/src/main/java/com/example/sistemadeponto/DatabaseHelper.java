package com.example.sistemadeponto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "sistema.db";
    private static final int DATABASE_VERSION = 1;

    // Tabela de status
    public static final String TABLE_STATUS = "StatusFuncionario";
    public static final String COL_ID = "id";
    public static final String COL_NOME = "nome_funcionario";
    public static final String COL_STATUS = "status";
    public static final String COL_DATA_HORA = "data_hora";
    public static final String TABLE_FUNCIONARIOS = "Funcionarios";
    public static final String TABLE_RONDAS = "RondasFuncionario";
    public static final String TABLE_NOTIFICACOES = "NotificacoesGerente";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createFuncionariosTable = "CREATE TABLE " + TABLE_FUNCIONARIOS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NOME + " TEXT NOT NULL, " +
                COL_STATUS + " TEXT NOT NULL" +
                ")";
        db.execSQL(createFuncionariosTable);

        // Criação da tabela de status
        String createStatusTable = "CREATE TABLE " + TABLE_STATUS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NOME + " TEXT, " +
                COL_STATUS + " TEXT, " +
                COL_DATA_HORA + " DATETIME DEFAULT CURRENT_TIMESTAMP" +
                ")";
        db.execSQL(createStatusTable);

        // Criação futura das outras tabelas (aqui deixamos só declarado para manter a estrutura)
        String createRondasTable = "CREATE TABLE " + TABLE_RONDAS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT, " +
                "horarioInicio TEXT, " +
                "horarioFim TEXT, " +
                "funcionario TEXT, " +  // funcionário designado (pode ser nome ou ID)
                "pontos TEXT" +         // string separada por vírgula
                ")";
        db.execSQL(createRondasTable);

        String NotificacoesGerente = "CREATE TABLE " + TABLE_NOTIFICACOES + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "titulo TEXT NOT NULL, " +
                "mensagem TEXT NOT NULL, " +
                "data TEXT NOT NULL" +
                ")";
        db.execSQL(NotificacoesGerente);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Atualização do banco se precisar no futuro
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATUS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RONDAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICACOES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FUNCIONARIOS);
        onCreate(db);
    }

    // Insere um novo status no histórico do funcionário
    public void inserirStatus(String nomeFuncionario, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NOME, nomeFuncionario);
        values.put(COL_STATUS, status);
        db.insert(TABLE_STATUS, null, values);
        db.close();
    }

    public void inserirRonda(Ronda ronda, String funcionario) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", ronda.getNome());
        values.put("horarioInicio", ronda.getHorarioInicio());
        values.put("horarioFim", ronda.getHorarioFim());
        values.put("funcionario", funcionario); // nome do funcionário
        values.put("pontos", TextUtils.join(",", ronda.getPontos()));
        db.insert(TABLE_RONDAS, null, values);
        db.close();
    }

    // Retorna o último status conhecido do funcionário
    public String buscarStatusAtual(String nomeFuncionario) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT " + COL_STATUS +
                        " FROM " + TABLE_STATUS +
                        " WHERE " + COL_NOME + " = ?" +
                        " ORDER BY " + COL_DATA_HORA + " DESC LIMIT 1",
                new String[]{nomeFuncionario}
        );

        String status = "offline"; // Padrão se não houver registros
        if (cursor.moveToFirst()) {
            status = cursor.getString(0);
        }

        cursor.close();
        db.close();
        return status;
    }
    public List<Funcionario> buscarTodosFuncionariosComStatus() {
        List<Funcionario> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT nome, status FROM funcionarios", null);

        if (cursor.moveToFirst()) {
            do {
                String nome = cursor.getString(0);
                String status = cursor.getString(1);
                Funcionario f = new Funcionario(nome, status);
                f.setStatus(status);
                lista.add(f);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return lista;
    }

    public List<Ronda> buscarRondasPorFuncionario(String funcionario) {
        List<Ronda> rondas = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_RONDAS, null, "funcionario = ?",
                new String[]{funcionario}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"));
                String inicio = cursor.getString(cursor.getColumnIndexOrThrow("horarioInicio"));
                String fim = cursor.getString(cursor.getColumnIndexOrThrow("horarioFim"));
                String pontosStr = cursor.getString(cursor.getColumnIndexOrThrow("pontos"));
                List<String> pontos = Arrays.asList(pontosStr.split(","));
                rondas.add(new Ronda(nome, inicio, fim, Collections.singletonList(funcionario), pontos));
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return rondas;
    }

    public void registrarNotificacao(String titulo, String mensagem, String data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("titulo", titulo);
        values.put("mensagem", mensagem);
        values.put("data", data);

        db.insert(TABLE_NOTIFICACOES, null, values);
        db.close();
    }
    public List<Notificacao> getTodasNotificacoes() {
        List<Notificacao> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NOTIFICACOES + " ORDER BY id DESC", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String titulo = cursor.getString(cursor.getColumnIndexOrThrow("titulo"));
                String mensagem = cursor.getString(cursor.getColumnIndexOrThrow("mensagem"));
                String data = cursor.getString(cursor.getColumnIndexOrThrow("data"));

                lista.add(new Notificacao(id, titulo, mensagem, data));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return lista;
    }
}
