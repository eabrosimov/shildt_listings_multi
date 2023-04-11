package main.java.com.egor_abrosimov.javacore.chapter28;

import java.util.concurrent.Phaser;

public class PhaserDemo2 {
    public static void main(String[] args) {
        MyPhaser phaser = new MyPhaser(1, 4);
        new MyThread3(phaser, "A");
        new MyThread3(phaser, "B");
        new MyThread3(phaser, "C");

        while (!phaser.isTerminated()){
            phaser.arriveAndAwaitAdvance();
        }
        System.out.println("Синхронизатор фаз завершен");
    }
}

class MyPhaser extends Phaser{
    int numPhases;

    MyPhaser(int parties, int phaseCount){
        super(parties);
        numPhases = phaseCount - 1;
    }

    @Override
    protected boolean onAdvance(int p, int regParties){
        System.out.println("Фаза " + p + " завершена\n");
        if(p == numPhases || regParties == 0)
            return true;
        return false;
    }
}

class MyThread3 implements  Runnable{
    Phaser phaser;
    String name;

    MyThread3(Phaser p, String n){
        phaser = p;
        name = n;
        phaser.register();
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (!phaser.isTerminated()){
            System.out.println("Поток " + name + " начинает фазу " + phaser.getPhase());
            phaser.arriveAndAwaitAdvance();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }

}
