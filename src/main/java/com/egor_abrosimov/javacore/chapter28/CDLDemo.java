package main.java.com.egor_abrosimov.javacore.chapter28;

import java.util.concurrent.CountDownLatch;

public class CDLDemo {
    public static void main(String[] args) {
        CountDownLatch cd1 = new CountDownLatch(5);
        System.out.println("Запуск потока исполнения");

        new MyThread(cd1);

        try {
            cd1.await();
        } catch (InterruptedException e){
            System.out.println(e);
        }
        System.out.println("Завершение потока исполнения");
    }
}

class MyThread implements Runnable{
    CountDownLatch latch;

    MyThread(CountDownLatch c){
        latch = c;
        new Thread(this).start();
    }
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(i);
            try {
                Thread.sleep(1000);
                latch.countDown();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
