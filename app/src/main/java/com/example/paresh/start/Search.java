package com.example.paresh.start;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Search extends AppCompatActivity{

    // Search EditText
    private EditText inputSearch;
    private RecyclerView recyclerView;
    private RecyclerAdapterSearch adapterSearch;
    private FirebaseDatabase database;
    private DatabaseReference myRef;


    // ArrayList for Listview
    ArrayList<UserDetails> userList;
    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        inputSearch = (EditText) findViewById(R.id.inputSearch);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        userList = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(Search.this);
        recyclerView.setLayoutManager(layoutManager);
        adapterSearch = new RecyclerAdapterSearch(Search.this,userList);
        recyclerView.setAdapter(adapterSearch);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                    String name = postSnapshot.child("name").getValue().toString();
                    String email = postSnapshot.child("email").getValue().toString();
                    String phone = postSnapshot.child("phone").getValue().toString();
                    String room = postSnapshot.child("room").getValue().toString();
                    userList.add(new UserDetails(name,email,phone,room));
                    adapterSearch.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        adapterSearch.notifyDataSetChanged();
        /**
         * Enabling Search Filter
         * */
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                //Toast.makeText(Search.this,cs,Toast.LENGTH_SHORT).show();
                final ArrayList<UserDetails> filteredList = filter(userList,cs.toString());
                adapterSearch.setFilter(filteredList);
                adapterSearch.notifyDataSetChanged();
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
    }


    ArrayList<UserDetails> filter(ArrayList<UserDetails> listUsers,String query){

        if(query.contentEquals("")){
            final ArrayList<UserDetails> filteredList = new ArrayList<>();
            filteredList.addAll(listUsers);
            adapterSearch.notifyDataSetChanged();
            return filteredList;
        }

        query = query.toLowerCase();

        final ArrayList<UserDetails> filtered = new ArrayList<>();

        for(UserDetails details : listUsers){
            String name = details.getName().toLowerCase();
            String phone = details.getPhone_number();
            String email = details.getEmail().toLowerCase();
            String room_no = details.getRoom_no().toLowerCase();

            if(name.contains(query)||phone.contains(query)||email.contains(query)||room_no.contains(query)){
                filtered.add(details);
            }
        }
        return filtered;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
