package com.everlapp.androidexamples.concurrency;

public class WaitNotifyExample {

    private static final Object monitor = new Object();

    public void sendData() {
        synchronized (monitor) {
            System.out.println("Waiting for data...");
            try {
                monitor.wait();                                     // Объект ждет, пока данные подготовятся и
            } catch (InterruptedException e) {                      // будет извещение notify() (notifyAll())
                e.printStackTrace();
            }
            // continue execution and sending data
            System.out.println("Sending data ...");
        }
    }


    public void prepareData() {
        synchronized (monitor) {
            System.out.println("Data prepared !");
            monitor.notifyAll();                                    // Извещает объект, что можно продолжать
        }
    }

}
