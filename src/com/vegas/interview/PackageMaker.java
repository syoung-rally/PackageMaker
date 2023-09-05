package com.vegas.interview;

import java.math.BigDecimal;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class PackageMaker {
    static int NUM_ARGS = 5;
    static String useageString =
            "Commandline arguments must be of the form [input filename] [minimum package price]" +
                    " [maximum price] [hstp] [output filename]";


    public static void main(String[] args) {
        String inputFilename;
        String outputFilename;
        String typeSpecifiers;
        BigDecimal minimumPrice;
        BigDecimal maximumPrice;

        if (args.length == NUM_ARGS) {
            inputFilename = args[0];
            minimumPrice = parseAsBigDecimal(args[1]);
            maximumPrice = parseAsBigDecimal(args[2]);
            typeSpecifiers = args[3];
            outputFilename = args[4];

        } else {
            throw new RuntimeException(useageString);
        }

        System.out.printf(
                "inputFile=%s, outputFile=%s, typeSpecifiers=%s, minimumPrice=%.2f, maximumPrice=%.2f\n",
                inputFilename, outputFilename, typeSpecifiers, minimumPrice.floatValue(), maximumPrice.floatValue());
    }

    private static BigDecimal parseAsBigDecimal(String doubleString) {
        try {
            return new BigDecimal(doubleString);
        } catch (Exception e) {
            throw new RuntimeException(useageString, e);
        }
    }
}