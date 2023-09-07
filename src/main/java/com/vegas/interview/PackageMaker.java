package com.vegas.interview;

import com.vegas.interview.models.ItemPackage;
import com.vegas.interview.models.Item;
import com.vegas.interview.models.ItemType;
import com.vegas.interview.models.PriceType;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class PackageMaker {
    static int NUM_ARGS = 5;
    static String useageString =
            "Commandline arguments must be of the form [input filename] [minimum package price]" +
                    " [maximum price] [hstp] [output filename]";

    private final static Logger logger = Logger.getLogger("PackageMaker");


    public static void main(String[] args) {
        if (args.length == NUM_ARGS) {
            makePackages(args[0], args[1], args[2], args[3], args[4]);
        } else {
            throw new RuntimeException(useageString);
        }
    }

    public static void makePackages(
            String inputFilename,
            String minimumPriceStr,
            String maximumPriceStr,
            String productTypeString,
            String outputFilename
    ) {

        List<PriceType> priceTypeList;
        Set<ItemType> itemTypeSet;

        BigDecimal minimumPrice = parseAsBigDecimal(minimumPriceStr);
        BigDecimal maximumPrice = parseAsBigDecimal(maximumPriceStr);

        try {
            priceTypeList = ItemPackageHelper.parsePriceTypeString(productTypeString);
            itemTypeSet = ItemPackageHelper.createItemTypeSet(priceTypeList);
        } catch (Exception e) {
            throw new RuntimeException(
                    String.format("Product type string contains unknown characters: %s",  productTypeString));
        }


        BufferedReader iFileReader;
        FileWriter oFileWriter;

        ItemDataManager itemDataManager = new ItemDataManager();

        try {
            iFileReader = new BufferedReader(new FileReader(inputFilename));
            String currLine = "";
            int lineNum = 0;
            while ((currLine = iFileReader.readLine()) != null) {
                try {
                    lineNum += 1;
                    String[] tokens = currLine.split("\t");
                    ItemType itemType = ItemType.valueOf(tokens[0]);
                    int itemId = Integer.parseInt(tokens[1]);
                    BigDecimal itemPrice = parseAsBigDecimal(tokens[2]);
                    Item newItem = Item.newItem(itemPrice, itemId, itemType);
                    itemDataManager.addItem(newItem);

                } catch (Exception e) {
                    logger.warning(String.format("Unable to parse line %d, ignoring: %s", lineNum, currLine));
                }
            }
            iFileReader.close();
        } catch (IOException e) {
            throw new RuntimeException("Unable to read file due to IOException. Aborting.", e);
        }

        List<ItemPackage> packages = itemDataManager.getPackagesInRange(minimumPrice, maximumPrice, itemTypeSet);
        packages.sort(ItemPackageHelper.createComparator(priceTypeList));

        try {
            oFileWriter = new FileWriter(outputFilename);
            for (ItemPackage p : packages) {
                oFileWriter.write(p.toString());
            }
            oFileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException("Unable to write file due to IOException. Aborting", e);
        }

    }

    private static BigDecimal parseAsBigDecimal(String doubleString) {
        try {
            BigDecimal num = new BigDecimal(doubleString);
            return num.setScale(2, RoundingMode.HALF_UP);
        } catch (Exception e) {
            throw new RuntimeException(useageString, e);
        }
    }

}