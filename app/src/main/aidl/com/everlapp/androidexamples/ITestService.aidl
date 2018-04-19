// ITestService.aidl
package com.everlapp.androidexamples;

// Declare any non-default types here with import statements

interface ITestService {

    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    /*void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);*/

    // Описываем методы, которые предоставляет сервис
    // oneway - не должны ждать ответа от сервиса

    String getString();
    oneway void getStringAsync();
    void bindActivity(IBinder callback);

}
