package fr.nrw.free.commons.modifications;

import android.os.Bundle;
import android.os.Parcel;
import android.text.TextUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class CategoryModifier extends PageModifier {


    public static String PARAM_CATEGORIES = "categories";

    public static String MODIFIER_NAME = "CategoriesModifier";

    public CategoryModifier(String... categories) {
        super(MODIFIER_NAME);
        JSONArray categoriesArray = new JSONArray();
        for(String category: categories) {
            categoriesArray.put(category);
        }
        try {
            params.putOpt(PARAM_CATEGORIES, categoriesArray);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public CategoryModifier(JSONObject data) {
        super(MODIFIER_NAME);
        this.params = data;
    }

    @Override
    public String doModification(String pageName, String pageContents) {
        JSONArray categories;
        categories = params.optJSONArray(PARAM_CATEGORIES);

        StringBuffer categoriesString = new StringBuffer();
        for(int i=0; i < categories.length(); i++) {
            String category = categories.optString(i);
            categoriesString.append("\n[[Category:").append(category).append("]]");
        }
        return pageContents + categoriesString.toString();
    }

    @Override
    public String getEditSumary() {
        return String.format("Added " + params.optJSONArray(PARAM_CATEGORIES).length() + " categories.");
    }
}
