package com.vegas.interview.models;

import java.util.Optional;

public class ItemPackage {
    Optional<HotelItem> hotel;
    Optional<TourItem> tour;
    Optional<ShowItem> show;

    ItemPackage(Optional<HotelItem> hotel, Optional<TourItem> tour, Optional<ShowItem> show) {
        if (hotel.isEmpty() && tour.isEmpty() && show.isEmpty()) {
            throw new RuntimeException("Package must have a least one of hotel, tour, show!");
        }

        this.hotel = hotel;
        this.show = show;
        this.tour = tour;
    }
}


