package com.vegas.interview;

import com.vegas.interview.models.ItemPackage;
import com.vegas.interview.models.ItemType;
import com.vegas.interview.models.PriceType;

import java.util.*;
import java.util.stream.Collectors;

public class ItemPackageHelper {

    private static Map<Character, PriceType> priceTypeMap = Map.ofEntries(
            Map.entry('p', PriceType.PACKAGE),
            Map.entry('h', PriceType.HOTEL),
            Map.entry('t', PriceType.TOUR),
            Map.entry('s', PriceType.SHOW)
    );

    public static List<PriceType> parsePriceTypeString(String priceTypeString) {
        List<PriceType> priceTypeList = new LinkedList<>();
        char[] chars = priceTypeString.toCharArray();
        for (char ch : chars) {
            priceTypeList.add(priceTypeMap.get(ch));
        }
        return priceTypeList;
    }

    public static Set<ItemType> createItemTypeSet(List<PriceType> priceTypeList) {
        Set<PriceType> priceSet = new HashSet<>(priceTypeList);
        priceSet.remove(PriceType.PACKAGE);
        return priceSet.stream().map(p -> ItemType.valueOf(p.name())).collect(Collectors.toSet());
    }

    public static Comparator<ItemPackage> createComparator(List<PriceType> priceTypeList) {
        return (o1, o2) -> {
            for (PriceType p : priceTypeList) {
                if (!o1.getPriceByPriceType(p).equals(o2.getPriceByPriceType(p))) {
                    return o1.getPriceByPriceType(p).compareTo(o2.getPriceByPriceType(p));
                }
            }
            return 0;
        };
    }
}
