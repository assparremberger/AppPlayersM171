package com.inf3m171.adalto.appplayersm171;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inf3m171.adalto.appplayersm171.model.Jogador;

public class CadastraPlayersActivity extends AppCompatActivity {

    private EditText etNome, etIdade;
    private Button btnSalvar;

    private FirebaseDatabase database;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastra_players);

        etNome = (EditText) findViewById(R.id.etNomePlayer);
        etIdade = (EditText) findViewById(R.id.etIdade);
        btnSalvar = (Button) findViewById(R.id.btnSalvarPlayer);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrarJogador();
            }
        });

    }

    private void cadastrarJogador(){
        String nome = etNome.getText().toString();
        String idade = etIdade.getText().toString();

        if ( !nome.isEmpty() ){
            Jogador jogador = new Jogador();
            jogador.setNome( nome );

            if ( idade.isEmpty() )
                jogador.setIdade( 0 );
            else
                jogador.setIdade( Integer.valueOf(idade) );

            reference.child("jogadores").push().setValue( jogador );

            Toast.makeText(this, "Jogador salvo com sucesso!",
                    Toast.LENGTH_LONG );

            etNome.setText("");
            etIdade.setText("");


        }

    }

}
