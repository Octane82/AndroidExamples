package com.everlapp.androidexamples;

import java.util.HashMap;
import java.util.Map;

public class ClassForTest {

    {
        System.out.println("Это не статический блок инициализации");
    }

    // Динамический блок инициализации
    Map<String, String> map = new HashMap<String, String>() {{
        put("паук",  "арахнид");
        put("птица", "архозавр");
        put("кит",   "зверь");
    }};


    public void testMethodOne() {

    }


}
