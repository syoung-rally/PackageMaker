package com.vegas.interview.models;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public abstract class Item {
    protected BigDecimal price;
    protected int itemId;
    protected ItemType itemType;

    public BigDecimal getPrice() {
        return price;
    }

    public ItemType getItemType() { return itemType; };

    private NumberFormat formatter = NumberFormat.getNumberInstance(Locale.ROOT);

    public String toString() {
        formatter.setMinimumFractionDigits(1);
        formatter.setMaximumFractionDigits(2);
        return String.format("%s\t%d\t%s",
                this.itemType.toString(),
                this.itemId,
                formatter.format(this.price.doubleValue()));
    }

    public static Item newItem(BigDecimal price, int itemId, ItemType itemType) {
        Item i = switch (itemType) {
            case HOTEL -> new HotelItem(itemId, price);
            case TOUR -> new TourItem(itemId, price);
            case SHOW -> new ShowItem(itemId, price);
        };
        return i;
    }

    @Override
    public int hashCode() {
        return itemId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Item)) {
            return false;
        }
        return ((Item) o).itemId == this.itemId;
    }

}
