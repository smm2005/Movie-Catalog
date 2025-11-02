package com.moviecatalog.catalog.recommender;

public class FeatureVector {

    private double genreScale = 2.0;
    private double ratingScale = 1.0;

    private double genreScore;
    private double rating;

    public FeatureVector(double genreScore, double rating){
        this.genreScore = genreScale * genreScore;
        this.rating = ratingScale * rating;
    }

    public double dotProduct(Object other){
        FeatureVector otherVector = (FeatureVector) other;
        return (this.genreScore * otherVector.genreScore) + (this.rating * rating);
    }

    public double magnitude(){
        return Math.sqrt(
            Math.pow(this.genreScore, 2.0) +
            Math.pow(this.rating, 2.0)
        );
    }

    public double angleBetween(Object other){
        return Math.acos(
            dotProduct(other) / Math.pow(magnitude(), 2)
        );
    }

}
