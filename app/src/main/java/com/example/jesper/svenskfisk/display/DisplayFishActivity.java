package com.example.jesper.svenskfisk.display;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jesper.svenskfisk.R;
import com.example.jesper.svenskfisk.model.Fish;

/**
 * Activity that displays a fish and it's information that has been
 * selected from a list in the parent activity.
 * @author Jesper Bergstrom
 * @name DisplayFishActivity.java
 */
public class DisplayFishActivity extends AppCompatActivity {

    private TextView fishText;
    private ImageView fishImage;
    private TextView sciText;
    private TextView locationText;
    private TextView waterText;
    private TextView methodText;
    private TextView weightText;
    private TextView foodText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_fish);

        // Set fish name
        fishText = (TextView) findViewById(R.id.fishText);
        Bundle b = this.getIntent().getExtras();
        Fish fish = (Fish) b.getSerializable("fish");
        fishText.setText(fish.getName());

        // Set scientific name
        sciText = (TextView) findViewById(R.id.sciText);
        sciText.setText(fish.getSciName());

        fishImage = (ImageView) findViewById(R.id.fishImage);

        String name = fish.getName().toLowerCase();
        String result = "";

        for(int i=0; i<name.length(); i++){
            if(name.charAt(i) == 'ö')
                result += "o";
            else if(name.charAt(i) == 'å' || name.charAt(i) == 'ä')
                result += "a";
            else
                result += name.charAt(i);
        }
        name = result;

        // Set fish image
        int reIm = this.getResources().getIdentifier(name, "drawable", this.getPackageName());
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), reIm);
        fishImage.setImageBitmap(bitmap);

        // Set location
        locationText = (TextView) findViewById(R.id.locationText);
        locationText.setText(fish.getLocations());

        // Set waters
        waterText = (TextView) findViewById(R.id.waterText);
        result = "";
        for(int i=0; i<fish.getWaters().length - 1; i++){
            result += fish.getWaters()[i] + ", ";
        }
        result += fish.getWaters()[fish.getWaters().length-1];
        waterText.setText(result);

        // Set methods
        methodText = (TextView) findViewById(R.id.methodText);
        methodText.setText(fish.getMethod());

        // Set Weight
        weightText = (TextView) findViewById(R.id.weightText);
        result = "Normal vikt ligger runt " + fish.getNormalWeight() + "g och de kan väga upp till " + fish.getMaxWeight() + "g.";
        weightText.setText(result);

        // Set food
        foodText = (TextView) findViewById(R.id.foodText);
        foodText.setText(fish.getFood());
    }
}
