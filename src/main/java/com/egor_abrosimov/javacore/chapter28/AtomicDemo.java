package main.java.com.egor_abrosimov.javacore.chapter28;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicDemo {
    public static void main(String[] args) {
        new AtomThread("A");
        new AtomThread("B");
        new AtomThread("C");
    }
}

class Shared2{
    static AtomicInteger ai = new AtomicInteger(0);
}

class AtomThread implements Runnable{
    String name;
    AtomThread(String n){
        name = n;
        new Thread(this).start();
    }

    @Override
    public void run() {
        System.out.println("Запуск потока " + name);
        for (int i = 1; i <= 3; i++) {
            System.out.println("Поток " + name + " получено: " + Shared2.ai.getAndSet(i));
        }
    }
}
