package com.vegas.interview.models;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Optional;

public class ItemPackage {
    private Optional<HotelItem> hotel;
    private Optional<TourItem> tour;
    private Optional<ShowItem> show;

    private static ItemPackage emptyItemPackage = new ItemPackage(Optional.empty(), Optional.empty(), Optional.empty());
    private NumberFormat formatter = NumberFormat.getNumberInstance(Locale.ROOT);

    ItemPackage(Optional<HotelItem> hotel, Optional<TourItem> tour, Optional<ShowItem> show) {
        this.hotel = hotel;
        this.show = show;
        this.tour = tour;
    }

    public static ItemPackage empty() {
        return emptyItemPackage;
    }

    private BigDecimal getOptionalPrice(Optional<Item> item) {
        return item.map(e -> e.price).orElse(BigDecimal.ZERO);
    }

    public BigDecimal getPrice() {
        return getOptionalPrice(hotel.map(i -> (Item) i))
                .add(getOptionalPrice(tour.map(i -> (Item) i)))
                .add(getOptionalPrice(show.map(i -> (Item) i)));
    }

    public ItemPackage copyWithAddedItem(Item item) {
        ItemPackage newItemPackage = new ItemPackage(this.hotel, this.tour, this.show);
        switch (item.getItemType()) {
            case HOTEL -> newItemPackage.addHotel((HotelItem) item);
            case SHOW -> newItemPackage.addShow((ShowItem) item);
            case TOUR -> newItemPackage.addTour((TourItem) item);
        }
        return newItemPackage;
    }

    public BigDecimal getPriceByPriceType(PriceType priceType) {
        BigDecimal price = switch (priceType) {
            case HOTEL -> getOptionalPrice(this.hotel.map(i -> (Item) i));
            case SHOW -> getOptionalPrice(this.show.map(i -> (Item) i));
            case TOUR -> getOptionalPrice(this.tour.map(i -> (Item) i));
            case PACKAGE -> getPrice();
        };
        return price;
    }

    private void addHotel(HotelItem hotel) {
        if (this.hotel.isPresent()) {
            // TODO fix error string
            throw new RuntimeException("Attempting to add a item=% to package ");
        }
        this.hotel = Optional.of(hotel);
     }

    private void addShow(ShowItem show) {
        if (this.show.isPresent()) {
            // TODO fix error string
            throw new RuntimeException("Attempting to add a item=% to package ");
        }
        this.show = Optional.of(show);
    }

    private void addTour(TourItem tour) {
        if (this.tour.isPresent()) {
            // TODO fix error string
            throw new RuntimeException("Attempting to add a item=% to package ");
        }
        this.tour = Optional.of(tour);
    }

    public String toString() {
        formatter.setMinimumFractionDigits(1);
        formatter.setMaximumFractionDigits(2);
        return String.format(
                "PACKAGE\t%s%s%s%s\n",
                formatter.format(getPrice().doubleValue()),
                hotel.map(e -> String.format("\t%s",e.toString())).orElse(""),
                show.map(e -> String.format("\t%s",e.toString())).orElse(""),
                tour.map(e -> String.format("\t%s",e.toString())).orElse(""));
    }
}


