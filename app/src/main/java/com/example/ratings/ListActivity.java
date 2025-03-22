package com.example.ratings;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.MenuItemCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ratings.adapter.StarAdapter;
import com.example.ratings.beans.Star;
import com.example.ratings.service.StarService;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private StarAdapter starAdapter = null;
    private StarService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if(getSupportActionBar() != null){
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFD05B")));
        }

        recyclerView = findViewById(R.id.recycleView);

        service = StarService.getInstance();

        if(service.findAll().isEmpty())
            init();

        for(Star s : service.findAll()){
            Log.d("ListActivity", "Actor's name : " + s.getName());
        }
        starAdapter = new StarAdapter(service.findAll(), this);
        recyclerView.setAdapter(starAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView)
                MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new
              SearchView.OnQueryTextListener() {
                  @Override
                  public boolean onQueryTextSubmit(String query) {
                      return true;
                  }
                  @Override
                  public boolean onQueryTextChange(String newText) {
                      Log.d("ListActivity", newText);
                      if(starAdapter != null){
                          starAdapter.getFilter().filter(newText);
                      }
                      return true;
                  }
              }
        );

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.share) {
            Toast.makeText(this, "share app", Toast.LENGTH_SHORT).show();

            ShareCompat.IntentBuilder.from(this)
                    .setType("text/plain")
                    .setChooserTitle("Stars")
                    .setText("Stars")
                    .startChooser();
        }
        return super.onOptionsItemSelected(item);
    }

    public void init(){
        service.create(new Star("Daniel Craig", "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcTbkMsetGcYG2d0j1DW3KWls7epHuC54ccQ5U9PRujTDRh-vglebSgkTivxAUuzPZ1nX__gC9lYYuv9gnnZfkO9qg", 3));
        service.create(new Star("Emma Watson", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT7OWYUXaj_sNSEtrjD8nmg0EGUrvWZq80GSr6p23NBgJihaOm-uBtk61KBLnd1fuWwJbiiqfZR2Miu4GpFvNpFJg", 3));
        service.create(new Star("Chiwetel Ejiofor", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRmfn2AbCmEeNYRNIAkQOhSAJxz-pHQB9-7tg&s", 5));
        service.create(new Star("Benedict Cumberbatch", "https://m.media-amazon.com/images/M/MV5BMjE0MDkzMDQwOF5BMl5BanBnXkFtZTgwOTE1Mjg1MzE@._V1_.jpg", 1));
        service.create(new Star("Ian McKellen", "https://upload.wikimedia.org/wikipedia/commons/thumb/1/15/SDCC13_-_Ian_McKellen.jpg/1200px-SDCC13_-_Ian_McKellen.jpg", 5));
        service.create(new Star("Patrick Stewart", "https://upload.wikimedia.org/wikipedia/commons/thumb/8/87/Patrick_Stewart_by_Gage_Skidmore_2.jpg/1200px-Patrick_Stewart_by_Gage_Skidmore_2.jpg", 1));

    }
}