package com.moviecatalog.catalog;

import java.util.ArrayList;
import lombok.Data;

@Data
public class Wishlist {
    private Long id;
    private Long userId;
    private ArrayList<Long> movieIds;
}
