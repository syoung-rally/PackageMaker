package com.vegas.interview;

import com.vegas.interview.models.Item;
import com.vegas.interview.models.ItemPackage;
import com.vegas.interview.models.ItemType;
import com.vegas.interview.models.PriceType;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class PackageMaker {
    static int NUM_ARGS = 5;
    static String useageString =
            "Commandline arguments must be of the form [input filename] [minimum package price]" +
                    " [maximum price] [hstp] [output filename]";

    private final static Logger logger = Logger.getLogger("PackageMaker");


    public static void main(String[] args) {
        String inputFilename;
        String outputFilename;
        List<PriceType> priceTypeList;
        Set<ItemType> itemTypeSet;
        BigDecimal minimumPrice;
        BigDecimal maximumPrice;

        BufferedReader iFileReader;


        ItemDataManager itemDataManager = new ItemDataManager();

        if (args.length == NUM_ARGS) {
            inputFilename = args[0];
            minimumPrice = parseAsBigDecimal(args[1]);
            maximumPrice = parseAsBigDecimal(args[2]);
            String productTypeString = args[3];
            try {
                priceTypeList = ItemPackageHelper.parsePriceTypeString(productTypeString);
                itemTypeSet = ItemPackageHelper.createItemTypeSet(priceTypeList);
            } catch (Exception e) {
                throw new RuntimeException(
                    String.format("Product type string contains unknown characters: %s",  productTypeString));
            }
            outputFilename = args[4];

        } else {
            throw new RuntimeException(useageString);
        }

        System.out.printf(
            "inputFile=%s, outputFile=%s, priceTypeList=%s, minimumPrice=%.2f, maximumPrice=%.2f\n",
            inputFilename, outputFilename, priceTypeList, minimumPrice.floatValue(), maximumPrice.floatValue());

        try {
            iFileReader = new BufferedReader(new FileReader(inputFilename));
            String currLine = "";
            int lineNum = 0;
            while ((currLine = iFileReader.readLine()) != null) {
                try {
                    lineNum += 1;
                    String[] tokens = currLine.split("\t");
                    ItemType itemType = ItemType.valueOf(tokens[0]);
                    int itemId = Integer.valueOf(tokens[1]);
                    BigDecimal itemPrice = parseAsBigDecimal(tokens[2]);
                    Item newItem = Item.newItem(itemPrice, itemId, itemType);
                    itemDataManager.addItem(newItem);

                } catch (Exception e) {
                    logger.warning(String.format("Unable to parse line %d, ignoring: %s", lineNum, currLine));
                }
            }
        } catch (FileNotFoundException e) {
            // TODO
        } catch (IOException e) {
            // TODO
        }

        List<ItemPackage> packages = itemDataManager.getPackagesInRange(minimumPrice, maximumPrice, itemTypeSet);
        packages.sort(ItemPackageHelper.createComparator(priceTypeList));

        
    }

    private static BigDecimal parseAsBigDecimal(String doubleString) {
        try {
            return new BigDecimal(doubleString);
        } catch (Exception e) {
            throw new RuntimeException(useageString, e);
        }
    }
}