package com.example.filmeflix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activity2 extends AppCompatActivity {
    DBHelper helper = new DBHelper(this);
    private EditText edtDescricao;
    private Button btnSalvar;
    private Categoria categoria;
    private Categoria altCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        edtDescricao = findViewById(R.id.edtDescricao);
        btnSalvar = findViewById(R.id.btnSalvar);

        Intent it=getIntent();
        altCategoria = (Categoria) it.getSerializableExtra("chave_categoria");
        categoria = new Categoria();

        if (altCategoria!= null){
            btnSalvar.setText("ALTERAR");
            edtDescricao.setText(altCategoria.getDescricao());
            categoria.setIdcategoria(altCategoria.getIdcategoria());
        }else{
            btnSalvar.setText("SALVAR");
        }
    }

    public void cadastrar(View view){
        String descricao = edtDescricao.getText().toString();
        categoria.setDescricao(descricao);
        if(btnSalvar.getText().toString().equals("SALVAR")) {
            helper.insereCategoria(categoria);
            Toast toast = Toast.makeText(Activity2.this,
                    "Categoria cadastrada com sucesso!", Toast.LENGTH_SHORT);
            toast.show();
        }else{
            helper.atualizarCategoria(categoria);
            helper.close();
        }
        limpar();
        finish();
    }

    public void limpar(){
        edtDescricao.setText("");
    }
    public void cancelar(View view) {
        finish();
    }
}