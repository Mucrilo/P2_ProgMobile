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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Activity3 extends AppCompatActivity {
    private TextView txtDescricao;
    private ListView listCategorias;
    DBHelper helper;
    Categoria categoria;
    ArrayList<Categoria> categoriaArrayList;
    ArrayAdapter<Categoria> categoriaArrayAdapter;
    private int id1, id2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        listCategorias=findViewById(R.id.listCategorias);
        registerForContextMenu(listCategorias);

        listCategorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Categoria categoriaEnviada = (Categoria) categoriaArrayAdapter.getItem(position);
                Intent it= new Intent(Activity3.this, Activity2.class);
                it.putExtra("chave_categoria", categoriaEnviada);
                startActivity(it);
            }
        });

        listCategorias.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                categoria = categoriaArrayAdapter.getItem(position);
                return false;
            }
        });

    }

    public void preencheLista(){
        helper= new DBHelper(Activity3.this);
        categoriaArrayList = helper.buscarCategoria();
        helper.close();
        if (listCategorias!=null){
            categoriaArrayAdapter = new ArrayAdapter<Categoria>(
                    Activity3.this,
                    android.R.layout.simple_list_item_1,
                    categoriaArrayList
            );
            listCategorias.setAdapter(categoriaArrayAdapter);
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
                helper=new DBHelper(Activity3.this);
                retornoBD = helper.excluirCategoria(categoria);
                helper.close();
                if(retornoBD==-1){
                    alert("Erro de exclusão!");
                }
                else if(retornoBD == 999){
                    alert("Erro, categoria possui filmes registrados!");
                }else{
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