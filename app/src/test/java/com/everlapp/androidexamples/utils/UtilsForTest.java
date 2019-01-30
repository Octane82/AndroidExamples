package com.everlapp.androidexamples.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class UtilsForTest {

    public static String getStringFromAssets(Class clazz, String filePath) throws IOException {
        InputStream is = clazz.getClassLoader().getResourceAsStream(filePath);
        Scanner scanner = new Scanner(is).useDelimiter("\\A");
        String result = scanner.hasNext() ? scanner.next() : "";
        is.close();
        return result;
    }

}