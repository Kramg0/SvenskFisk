package com.example.jesper.svenskfisk.search;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.SearchView;

import com.example.jesper.svenskfisk.R;
import com.example.jesper.svenskfisk.model.Fish;
import com.example.jesper.svenskfisk.model.FishContainer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Activity for searching for fish. A list of matching results will be displayed.
 * @author Jesper Bergstrom
 */
public class SearchActivity extends ListActivity {

    private SearchView searchView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> listItems = new ArrayList<String>();
    private FishContainer container = new FishContainer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        setListAdapter(adapter);
        search();
    }

    /**
     * Search method that searches through the fish database to see if
     * there are any matches with the search word. All matching fish will
     * get added to the FishContainer.
     */
    public void search(){
        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                container.getList().clear();
                listItems.clear();
                InputStream iS = null;
                try {
                    iS = getAssets().open("fish.txt");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(iS, "ISO-8859-1"));
                    String temp = reader.readLine();
                    // Read the fish file
                    while(!temp.equals("end")) {
                        String[] parts = temp.split(":");
                        temp = parts[1];
                        if (parts[0].equals("Name") && temp.toLowerCase().contains(query.toLowerCase())) {
                            Fish fish = new Fish();
                            fish.setName(temp);
                            //TODO: Set all other properties

                            container.add(fish);
                        }
                        temp = reader.readLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for(int i=0; i<container.getList().size(); i++){
                    listItems.add(container.getList().get(i).getName());
                }
                adapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
}