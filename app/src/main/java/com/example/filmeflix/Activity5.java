package com.example.filmeflix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Activity5 extends AppCompatActivity {
    private ListView listaFilmes;
    DBHelper helper;
    Filme filme;
    ArrayList<Filme> filmeArrayList;
    ArrayAdapter<Filme> filmeArrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_5);

        listaFilmes=findViewById(R.id.listFilmes);
        registerForContextMenu(listaFilmes);

        listaFilmes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Filme filmeEnviado = (Filme) filmeArrayAdapter.getItem(position);
                Intent it= new Intent(Activity5.this, Activity4.class);
                it.putExtra("chave_filme", filmeEnviado);
                startActivity(it);
            }
        });

        listaFilmes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                filme = filmeArrayAdapter.getItem(position);
                return false;
            }
        });
    }

    public void preencheLista(){
        helper= new DBHelper(Activity5.this);
        filmeArrayList = helper.buscarFilme();
        helper.close();
        if (listaFilmes!=null){
            filmeArrayAdapter = new ArrayAdapter<Filme>(
                    Activity5.this,
                    android.R.layout.simple_list_item_1,
                    filmeArrayList
            );
            listaFilmes.setAdapter(filmeArrayAdapter);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        preencheLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View
            v, ContextMenu.ContextMenuInfo menuInfo){
        MenuItem mDelete = menu.add(Menu.NONE, 1, 1,"Deleta Registro");
        MenuItem mSair = menu.add(Menu.NONE, 2, 2,"Cancela");
        mDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                long retornoBD=1;
                helper=new DBHelper(Activity5.this);
                retornoBD = helper.excluirFilme(filme);
                helper.close();
                if(retornoBD==-1){
                    alert("Erro de exclusão!");
                }
                else{
                    alert("Registro excluído com sucesso!");
                }
                preencheLista();
                return false; }
        });
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    private void alert(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }

}