package main.java.com.egor_abrosimov.javacore.chapter28;

import java.util.concurrent.Phaser;

public class PhaserDemo {
    public static void main(String[] args) {
        Phaser phaser = new Phaser(1);
        int curPhase;

        System.out.println("Запуск потоков");
        new MyThread2(phaser, "A");
        new MyThread2(phaser, "B");
        new MyThread2(phaser, "C");

        curPhase = phaser.getPhase();
        phaser.arriveAndAwaitAdvance();
        System.out.println("Фаза " + curPhase + " завершена");

        curPhase = phaser.getPhase();
        phaser.arriveAndAwaitAdvance();
        System.out.println("Фаза " + curPhase + " завершена");

        curPhase = phaser.getPhase();
        phaser.arriveAndAwaitAdvance();
        System.out.println("Фаза " + curPhase + " завершена");

        phaser.arriveAndDeregister();

        if(phaser.isTerminated())
            System.out.println("Синхронизатор фаз завершен");

    }
}

class MyThread2 implements Runnable{
    Phaser phaser;
    String name;

    MyThread2(Phaser p, String n){
        phaser = p;
        name = n;
        phaser.register();
        new Thread(this).start();
    }

    @Override
    public void run() {
        System.out.println("Поток " + name + " начинает первую фазу");
        phaser.arriveAndAwaitAdvance();
        try{
            Thread.sleep(10);
        } catch (InterruptedException e) {
            System.out.println(e);
        }

        System.out.println("Поток " + name + " начинает вторую фазу");
        phaser.arriveAndAwaitAdvance();

        try{
            Thread.sleep(10);
        } catch (InterruptedException e) {
            System.out.println(e);
        }

        System.out.println("Поток " + name + " начинает третью фазу");

        phaser.arriveAndDeregister();
    }
}
