package com.example.filmeflix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Activity4 extends AppCompatActivity {

    DBHelper helper = new DBHelper(this);
    private EditText edtTitulo, edtAno, edtAvaliacao, edtTempo, edtIdCategoria;
    private Button btnSalvar;
    private Filme filme;
    private Filme altFilme;

    private ListView listCategorias;
    ArrayList<Categoria> categoriaArrayList;
    ArrayAdapter<Categoria> categoriaArrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4);

        EditText et = (EditText) findViewById(R.id.edtNota);
        et.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "100")});

        edtTitulo = findViewById(R.id.edtTitulo);
        edtAno = findViewById(R.id.edtAno);
        edtAvaliacao = findViewById(R.id.edtNota);
        edtTempo = findViewById(R.id.edtTempo);
        edtIdCategoria = findViewById(R.id.edtIdCategoria);
        btnSalvar = findViewById(R.id.btnSalvar);

        Intent it= getIntent();
        altFilme = (Filme) it.getSerializableExtra("chave_filme");
        filme = new Filme();



       if (altFilme != null){
            btnSalvar.setText("ALTERAR");
            edtTitulo.setText(altFilme.getTitulo());
            edtAno.setText(Integer.toString( altFilme.getAno()));
            edtAvaliacao.setText(Integer.toString(altFilme.getAvaliacao()));
            edtTempo.setText(altFilme.getTempo());
            edtIdCategoria.setText(Integer.toString(altFilme.getIdcategoria()));
            filme.setIdFilme(altFilme.getIdFilme());
        }else{
            btnSalvar.setText("SALVAR");
        }

        listCategorias=findViewById(R.id.listCategoriaCad);
        registerForContextMenu(listCategorias);


    }


    public void cadastrar(View view){


        String titulo  = edtTitulo.getText().toString();
        int ano  = Integer.parseInt(edtAno.getText().toString());
        int avaliacao  = Integer.parseInt(edtAvaliacao.getText().toString());
        String tempo  = edtTempo.getText().toString();
        int idCategoria = Integer.parseInt(edtIdCategoria.getText().toString());
        filme.setTitulo(titulo);
        filme.setAno(ano);
        filme.setAvaliacao(avaliacao);
        filme.setTempo(tempo);
        filme.setIdcategoria(idCategoria);


        if(btnSalvar.getText().toString().equals("SALVAR")) {
            helper.insereFilme(filme);
            Toast toast = Toast.makeText(Activity4.this,
                    "Filme cadastrado com sucesso!", Toast.LENGTH_SHORT);
            toast.show();
        }else{
            helper.atualizarFilme(filme);
            helper.close();
        }
        limpar();
        finish();
    }

    public void limpar(){

        edtTitulo.setText("");
        edtAno.setText("");
        edtAvaliacao.setText("");
        edtTempo.setText("");
        edtIdCategoria.setText("");
    }
    public void cancelar(View view) {
        finish();
    }

    public void preencheLista(){
        helper= new DBHelper(Activity4.this);
        categoriaArrayList = helper.buscarCategoria();
        helper.close();
        if (listCategorias!=null){
            categoriaArrayAdapter = new ArrayAdapter<Categoria>(
                    Activity4.this,
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

    private void alert(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }
}