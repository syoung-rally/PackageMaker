package com.vegas.interview;

import com.vegas.interview.models.Item;
import com.vegas.interview.models.ItemPackage;
import com.vegas.interview.models.ItemType;
import com.vegas.interview.models.PriceType;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class ItemDataManager {
    private NavigableMap<BigDecimal, Set<Item>> items = new TreeMap<>();

    public void addItem(Item item) {

        if (items.containsKey(item.getPrice())) {
             items.get(item.getPrice()).add(item);
        } else {
            Set<Item> set =  new HashSet<>();
            set.add(item);
            items.put(item.getPrice(), set);
        }
    }

    public List<ItemPackage> getPackagesInRange(
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Set<ItemType> itemTypes) {
        // Get all packages less than max price

        Collection<ItemPackage> packages = new LinkedList<>();
        packages.add(ItemPackage.empty());

        for (ItemType itemType: itemTypes) {
            packages = findItemsOfTypeWithinPrice(maxPrice, itemType, packages);
        }

        // filter out any that are below min price and return
        return packages.stream()
                .filter(p -> p.getPriceByPriceType(PriceType.PACKAGE).compareTo(minPrice) > 0)
                .collect(Collectors.toList());
    }

    private Collection<ItemPackage> findItemsOfTypeWithinPrice(
            BigDecimal maxPrice,
            ItemType itemType,
            Collection<ItemPackage> packages) {
        Collection<ItemPackage> newPackages = new HashSet<>();

        for (ItemPackage itemPackage:  packages) {
            BigDecimal maxPriceForNewItem = maxPrice.subtract(itemPackage.getPrice());
            Collection<Set<Item>> affordableItems =
                    items
                        .subMap(BigDecimal.ZERO, true, maxPriceForNewItem, true)
                        .values();

            Set<Item> affordableItemsByType =
                    affordableItems.stream().flatMap(Set::stream)
                            .filter(e -> e.getItemType() == itemType)
                            .collect(Collectors.toSet());

            for (Item newItem : affordableItemsByType) {
                newPackages.add(itemPackage.copyWithAddedItem(newItem));
            }
        }
        return newPackages;
    }
}

