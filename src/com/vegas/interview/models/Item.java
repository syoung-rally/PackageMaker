package com.vegas.interview.models;

import java.math.BigDecimal;

public abstract class Item {
    protected BigDecimal price;
    protected int itemId;
    protected ItemType itemType;

    public BigDecimal getPrice() {
        return price;
    }

    public ItemType getItemType() { return itemType; };

    public String toString() {
        return String.format("%s\t%d%\t%.2f", this.itemType.toString(), this.itemId, this.price.doubleValue());
    }

    public static Item newItem(BigDecimal price, int itemId, ItemType itemType) {
        Item i = null;
        switch (itemType) {
            case HOTEL:
                i = new HotelItem(itemId, price);
                break;
            case TOUR:
                i = new TourItem(itemId, price);
                break;
            case SHOW:
                i = new ShowItem(itemId, price);
                break;
        }
        return i;
    }
}
