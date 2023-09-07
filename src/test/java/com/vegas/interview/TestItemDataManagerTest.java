package com.vegas.interview;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestItemDataManagerTest {

    @Test
    public void testPackageMaker() throws IOException {
        List<String> options = new LinkedList<>(Arrays.asList(
                "hst",
                "phst",
                "pth",
                "s",
                "th"
        ));

        for (String testName : options) {
            String outputFileName = String.format("%s.dat", testName);
            createAndCompareFiles(
                    "src/test/resources/test-items.dat",
                    "50",
                    "200",
                    testName,
                    outputFileName);
        }
    }

    @Test
    public void testBadPriceTypeString() throws IOException {
        createAndCompareFiles(
                "src/test/resources/test-bad-price.dat",
                "50",
                "200",
                "h",
                "bad-price-expected-result.dat");
    }

    @Test
    public void testFileNotFound()  {
        assertThrows(RuntimeException.class, () ->
            createAndCompareFiles(
                    "src/test/resources/not-present.dat",
                    "50",
                    "200",
                    "h",
                    "never-made.dat")
        );
    }

    @Test
    public void badProductTypeString()  {
        assertThrows(RuntimeException.class, () ->
                createAndCompareFiles(
                        "src/test/resources/test-items.dat",
                        "50",
                        "200",
                        "hat",
                        "never-made.dat")
        );
    }

    private void createAndCompareFiles(
        String inputFileName,
        String minimum,
        String maximum,
        String productArgs,
        String outputFileName
    ) throws IOException {
        String expectedFileName = String.format("src/test/resources/test-%s", outputFileName);
        PackageMaker.makePackages(
                inputFileName,
                minimum,
                maximum,
                productArgs,
                outputFileName
        );

        String expected = readContentsToString(expectedFileName);
        String actual = readContentsToString(outputFileName);
        assertEquals(expected, actual);

        File actualFile = new File(outputFileName);
        actualFile.delete();

    }

    private String readContentsToString(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        String ls = System.getProperty("line.separator");
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append(ls);
        } reader.close();

        return stringBuilder.toString();
    }
}
