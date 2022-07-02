package com.example.filmeflix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    DBHelper helper = new DBHelper(this);
    private EditText edtDiscricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void cadastrar(View view) {
        Intent it = new Intent(this, Activity3.class);
        startActivity(it);
    }

    public void listar(View view){
        Intent it = new Intent(this, Activity2.class);
        startActivity(it);
    }

    public void cadastrarFilme(View view) {
        Intent it = new Intent(this, Activity4.class);
        startActivity(it);
    }

    public void listarFilmes(View view) {
        Intent it = new Intent(this, Activity5.class);
        startActivity(it);
    }
}