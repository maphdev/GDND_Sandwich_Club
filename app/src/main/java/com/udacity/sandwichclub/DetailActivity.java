package com.udacity.sandwichclub;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView alsoKnowAsTextView;
    private TextView placeOfOriginTextView;
    private TextView descriptionTextView;
    private ImageView imageTextView;
    private TextView ingredientsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //ImageView ingredientsIv = findViewById(R.id.image_iv);

        alsoKnowAsTextView = (TextView) findViewById(R.id.also_known_tv);
        placeOfOriginTextView = (TextView) findViewById(R.id.origin_tv);
        descriptionTextView = (TextView) findViewById(R.id.description_tv);
        imageTextView = (ImageView) findViewById(R.id.image_iv);
        ingredientsTextView = (TextView) findViewById(R.id.ingredients_tv) ;

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(imageTextView);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        setTitle(sandwich.getMainName());

        int alsoKnownAsSize = sandwich.getAlsoKnownAs().size();
        if (alsoKnownAsSize == 0) {
            //TextView alsoKnownAsHardText = (TextView) findViewById(R.id.also_known_as_hard);
            //alsoKnownAsHardText.setVisibility(View.GONE);
            //alsoKnowAsTextView.setVisibility(View.GONE);
            LinearLayout alsoKownAsLayout = (LinearLayout) findViewById(R.id.also_known_as_layout);
            alsoKownAsLayout.setVisibility(View.GONE);
        } else {
            for (int i = 0; i < alsoKnownAsSize; i++) {
                alsoKnowAsTextView.append(sandwich.getAlsoKnownAs().get(i));
                if (i < alsoKnownAsSize -1)
                    alsoKnowAsTextView.append(", ");
            }
        }

        if (sandwich.getPlaceOfOrigin().equals("")) {
            //TextView placeOfOriginHard = (TextView) findViewById(R.id.origin_hard);
            //placeOfOriginHard.setVisibility(View.GONE);
            //placeOfOriginTextView.setVisibility(View.GONE);
            LinearLayout originLayout = (LinearLayout) findViewById(R.id.origin_layout);
            originLayout.setVisibility(View.GONE);
        } else {
            placeOfOriginTextView.setText(sandwich.getPlaceOfOrigin());
        }

        if (sandwich.getDescription().equals("")) {
            TextView descriptionHard = (TextView) findViewById(R.id.description_hard);
            descriptionHard.setVisibility(View.GONE);
            descriptionTextView.setVisibility(View.GONE);
        } else {
            descriptionTextView.setText(sandwich.getDescription());
        }

        int ingredientsSize = sandwich.getIngredients().size();
        if (ingredientsSize == 0) {
            TextView ingredientsHard = (TextView) findViewById(R.id.ingredients_hard);
            ingredientsHard.setVisibility(View.GONE);
            ingredientsTextView.setVisibility(View.GONE);
        } else {
            for (int i = 0; i < ingredientsSize; i++) {
                ingredientsTextView.append("- ");
                ingredientsTextView.append(sandwich.getIngredients().get(i));
                if (i < ingredientsSize -1) {
                    ingredientsTextView.append("\n");
                }
            }
        }
    }
}
