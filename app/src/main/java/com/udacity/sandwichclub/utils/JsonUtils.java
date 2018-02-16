package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson (String json) {
        Sandwich sandwich = null;

        JSONObject sandwichObject = null;
        JSONObject nameObject = null;
        String mainName = null;
        JSONArray alsoKnownAsJArray = null;
        List<String> alsoKnownAs = null;
        String placeOfOrigin = null;
        String description = null;
        String image = null;
        JSONArray ingredientsJArray = null;
        List<String> ingredients = null;

        try {
            sandwichObject = new JSONObject(json);

            nameObject = sandwichObject.getJSONObject("name");
            mainName = nameObject.getString("mainName");
            alsoKnownAsJArray = nameObject.getJSONArray("alsoKnownAs");
            alsoKnownAs = new ArrayList<>();
            for (int i = 0; i < alsoKnownAsJArray.length(); i++)
                alsoKnownAs.add(alsoKnownAsJArray.getString(i));

            placeOfOrigin = sandwichObject.getString("placeOfOrigin");

            description = sandwichObject.getString("description");

            image = sandwichObject.getString("image");

            ingredientsJArray = sandwichObject.getJSONArray("ingredients");
            ingredients = new ArrayList<>();
            for (int i = 0; i < ingredientsJArray.length(); i++)
                ingredients.add(ingredientsJArray.getString(i));

        } catch (final JSONException e) {
            e.printStackTrace();
        }

        sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        return sandwich;
    }
}
