package com.example.to_do;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;


public class MainActivity extends AppCompatActivity {

    List <String> items;

    Button button_add;
    EditText et_item;
    RecyclerView rv_item;
    ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_add = findViewById(R.id.button_add);
        et_item = findViewById(R.id.et_item);
        rv_item = findViewById(R.id.rv_item);

        loadItems();

        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                Log.d("TADA", String.valueOf(position));
                items.remove(position);
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "Item was removed", Toast.LENGTH_SHORT).show();
                savesItems();
            }
        };

        final ItemsAdapter itemsAdapter = new ItemsAdapter(items, onLongClickListener);
        rv_item.setAdapter(itemsAdapter);
        rv_item.setLayoutManager(new LinearLayoutManager(this));

        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  todo_list = et_item.getText().toString();
                items.add(todo_list);
                itemsAdapter.notifyItemInserted(items.size()-1);
                et_item.setText("");
                Toast.makeText(getApplicationContext(), "Item was added", Toast.LENGTH_SHORT).show();
                savesItems();

            }
        });

    }
    private File getDataFile() {
        return new File(getFilesDir(), "data.txt");
    }

    private void loadItems(){
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch(IOException e) {
            e.printStackTrace();
            Log.e("MainActivity", "Error reading items", e);
            items = new ArrayList<>();
        }
    }

    private void savesItems() {
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e){
            Log.e("MainActivity", "Error Writing items", e);
        }
    }
}
