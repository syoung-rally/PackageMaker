package com.vegas.interview;

import com.vegas.interview.models.*;

import java.math.BigDecimal;
import java.util.*;

import static java.util.Map.entry;

public class ItemDataManager {
    private NavigableMap<BigDecimal, Set<Item>> hotels = new TreeMap<BigDecimal, Set<Item>>();
    private NavigableMap<BigDecimal, Set<Item>> shows = new TreeMap<BigDecimal, Set<Item>>();
    private NavigableMap<BigDecimal, Set<Item>> tours = new TreeMap<BigDecimal, Set<Item>>();

    private Map<ItemType, NavigableMap<BigDecimal, Set<Item>>> items = Map.ofEntries(
        entry(ItemType.SHOW, shows),
        entry(ItemType.HOTEL, hotels),
        entry(ItemType.TOUR, tours)
    );

    public void addItem(Item item) {
        Map<BigDecimal, Set<Item>> itemMap = items.get(item.getItemType());

        if (itemMap.containsKey(item.getPrice())) {
             itemMap.get(item.getPrice()).add(item);
        } else {
            Set<Item> set =  new HashSet<Item>();
            set.add(item);
            itemMap.put(item.getPrice(), set);
        }
    }

    public List<ItemPackage> getPackagesInRange(List<ItemType> itemTypes, BigDecimal minPrice, BigDecimal maxPrice) {
        // Get all packages less than max price


        // filter out any that are below min price and return
    }
}

