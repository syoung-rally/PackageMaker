package com.vegas.interview.models;

import java.math.BigDecimal;

public class TourItem extends Item {

    public TourItem(int id, BigDecimal price) {
        this.itemId = id;
        this.price = price;
        this.itemType = ItemType.TOUR;
    }
}
