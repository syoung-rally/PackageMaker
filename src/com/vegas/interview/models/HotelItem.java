package com.vegas.interview.models;

import java.math.BigDecimal;

public class HotelItem extends Item {

    public HotelItem(int id, BigDecimal price) {
        this.itemId = id;
        this.price = price;
        this.itemType = ItemType.HOTEL;
    }
}
