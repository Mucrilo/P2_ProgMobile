package com.example.filmeflix;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "filmeflix.db";
    private static final String TABLE_CATEGORIA = "categoria";
    private static final String COL_CAT_ID = "categoriaId";
    private static final String COL_DESCRICAO = "descricao";

    private static final String TABLE_FILME = "filme";
    private static final String COL_FIL_ID = "filmeId";
    private static final String COL_ID_CATEGORIA = "idCategoria";
    private static final String COL_TITULO = "titulo";
    private static final String COL_ANO = "ano";
    private static final String COL_AVALIACAO = "avaliacao";
    private static final String COL_TEMPO = "tempo";

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }


    private static final String TABLE_CREATE="create table " + TABLE_CATEGORIA +
            "("+COL_CAT_ID+" integer primary key autoincrement, "+
            COL_DESCRICAO + " text not null);";
    private static final String TABLE_CREATE2="create table " + TABLE_FILME +
            "("+COL_FIL_ID+" integer primary key autoincrement, "+
            COL_ID_CATEGORIA + " integer references " + TABLE_CATEGORIA + "," +
            COL_TITULO + " text not null, " +
            COL_ANO + " integer not null, " +
            COL_AVALIACAO + " integer not null, " +
            COL_TEMPO + " text not null);";


    SQLiteDatabase db;
    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        db.execSQL(TABLE_CREATE2);
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_CATEGORIA;
        String query2 = "DROP TABLE IF EXISTS " + TABLE_FILME;
        db.execSQL(query);
        db.execSQL(query2);
        this.onCreate(db);
    }

    public void insereCategoria(Categoria c){
        db=this.getWritableDatabase();
        db.beginTransaction();

        try{
            ContentValues  values= new ContentValues();
            values.put(COL_DESCRICAO, c.getDescricao());
            db.insertOrThrow(TABLE_CATEGORIA, null, values);
            db.setTransactionSuccessful();
        }catch (Exception e) {
            Log.d(TAG, "Erro ao inserir uma categoria");
        } finally {
            db.endTransaction();
        }
    }

    public void insereFilme(Filme f){
        db=this.getWritableDatabase();
        db.beginTransaction();

        try{
            ContentValues  values= new ContentValues();
            values.put(COL_ID_CATEGORIA, f.getIdcategoria());
            values.put(COL_TITULO, f.getTitulo());
            values.put(COL_ANO, f.getAno());
            values.put(COL_AVALIACAO, f.getAvaliacao());
            values.put(COL_TEMPO, f.getTempo());
            db.insertOrThrow(TABLE_FILME, null, values);
            db.setTransactionSuccessful();
        }catch (Exception e) {
            Log.d(TAG, "Erro ao inserir um filme");
        } finally {
            db.endTransaction();
        }
    }


    public ArrayList<Categoria> buscarCategoria(){
        String[] colunas={COL_CAT_ID,COL_DESCRICAO};
        Cursor cursor = getReadableDatabase().query(TABLE_CATEGORIA, colunas,null, null, null,null,"upper(categoriaID)");
        ArrayList<Categoria> list = new ArrayList<Categoria>();
        while(cursor.moveToNext()){
            Categoria c = new Categoria();
            c.setIdcategoria(cursor.getInt(0));
            c.setDescricao(cursor.getString(1));
            list.add(c);
        }

        return list;
    }

    public ArrayList<Filme> buscarFilme(){
        String[] colunas={COL_FIL_ID,COL_ID_CATEGORIA,COL_TITULO, COL_ANO, COL_AVALIACAO, COL_TEMPO};
        Cursor cursor = getReadableDatabase().query(TABLE_FILME, colunas,null, null, null,null,"upper(ano)");
        ArrayList<Filme> list = new ArrayList<Filme>();
        while(cursor.moveToNext()){
            Filme f = new Filme();
            f.setIdFilme(cursor.getInt(0));
            f.setIdcategoria(cursor.getInt(1));
            f.setTitulo(cursor.getString(2));
            f.setAno(cursor.getInt(3));
            f.setAvaliacao(cursor.getInt(4));
            f.setTempo(cursor.getString(5));
            list.add(f);
        }

        return list;
    }

    public int verificarCategoria(int num){
        String select = "SELECT * FROM " + TABLE_FILME + " WHERE "+ COL_ID_CATEGORIA + " = " + num;
        ArrayList<Filme> list = new ArrayList<Filme>();
        SQLiteDatabase dbT = this.getWritableDatabase();
        Cursor cursor = dbT.rawQuery(select, null);
        return cursor.getCount();
    }

    public long excluirCategoria(Categoria categoria){
        if(verificarCategoria(categoria.idcategoria) == 0) {
            long retornaDB;
            db = this.getWritableDatabase();
            String[] args = {String.valueOf(categoria.getIdcategoria())};
            retornaDB = db.delete(TABLE_CATEGORIA, COL_CAT_ID + "=?", args);
            return retornaDB;
        }
        return 999;
    }

    public long excluirFilme(Filme filme){
        long retornaDB;
        db=this.getWritableDatabase();
        String[] args={String.valueOf(filme.getIdFilme())};
        retornaDB=db.delete(TABLE_FILME, COL_FIL_ID+"=?", args);
        return  retornaDB;
    }

    public long atualizarCategoria(Categoria categoria){
        long retornaDB;
        db=this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_DESCRICAO, categoria.getDescricao());
        String[] args={String.valueOf(categoria.getIdcategoria())};
        retornaDB=db.update(TABLE_CATEGORIA, values,COL_CAT_ID+"=?", args);
        db.close();
        return  retornaDB;
    }

    public long atualizarFilme(Filme filme){
        long retornaDB;
        db=this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_ID_CATEGORIA, filme.getIdcategoria());
        values.put(COL_TITULO, filme.getTitulo());
        values.put(COL_ANO, filme.getAno());
        values.put(COL_AVALIACAO, filme.getAvaliacao());
        values.put(COL_TEMPO, filme.getTempo());

        String[] args={String.valueOf(filme.getIdFilme())};
        retornaDB=db.update(TABLE_FILME, values,COL_FIL_ID+"=?", args);
        db.close();
        return  retornaDB;
    }


}
