package org.artisan.hiraafood.model;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Recommendations {
    private final Map<String, Recommendation> recommendations = new HashMap<>();

    /**
     * Gets skus of recommended items from given sku.
     *
     * @param sku sku of an item
     * @return a recommendation. null if not recommendations
     */
    public Recommendation getRecommendations(String sku) {
        return recommendations.get(sku);
    }

    public void addRecommendation(String sku, List<String> recos) {
        Recommendation existing = recommendations.get(sku);
        Recommendation r = new Recommendation();
        r.sku = sku;
        r.others = recos;
        if (existing == null) {
            recommendations.put(sku, r);
        } else {
            existing.merge(r);
        }
    }

    public void addRecommendation(Recommendation r) {
        recommendations.put(r.sku, r);
    }
}