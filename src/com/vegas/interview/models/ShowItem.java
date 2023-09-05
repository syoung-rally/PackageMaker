package com.vegas.interview.models;

import java.math.BigDecimal;

public class ShowItem extends Item {

    public ShowItem(int id, BigDecimal price) {
        this.itemId = id;
        this.price = price;
        this.itemType = ItemType.SHOW;
    }
}
