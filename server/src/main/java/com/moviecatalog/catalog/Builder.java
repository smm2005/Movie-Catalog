package com.moviecatalog.catalog;

public interface Builder {
    Builder popularity(float popularity);
    Builder vote_count(int vote_count);
    Builder rating(float rating);
    Builder language(String language);
    Builder genre(String genre);
    Object build();
}
