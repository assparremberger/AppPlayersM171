package com.inf3m171.adalto.appplayersm171;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.inf3m171.adalto.appplayersm171.model.Jogador;

import java.util.ArrayList;
import java.util.List;

public class ListaPlayersActivity extends AppCompatActivity {

    private ListView lvJogadores;
    private List<Jogador> listaDeJogadores;
    private ArrayAdapter adapter;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private Query queryRef;
    private ChildEventListener childEventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_players);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lvJogadores = (ListView) findViewById(R.id.lvPlayers);
        listaDeJogadores = new ArrayList<>();
        adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, listaDeJogadores);
        lvJogadores.setAdapter(adapter);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListaPlayersActivity.this,
                        CadastraPlayersActivity.class);
                startActivity( i );
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        queryRef = reference.child("jogadores").orderByChild("nome");

        listaDeJogadores.clear();

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Jogador jogador = new Jogador();
                jogador.setId( dataSnapshot.getKey() );
                jogador.setNome( dataSnapshot.child("nome").getValue(String.class) );
                jogador.setIdade( dataSnapshot.child("idade").getValue(Integer.class));
                listaDeJogadores.add( jogador );
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

    }
}
