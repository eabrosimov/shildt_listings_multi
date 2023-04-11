package main.java.com.egor_abrosimov.javacore.chapter28;

import java.util.concurrent.Semaphore;

public class ProdCon {
    public static void main(String[] args) {
        Q q = new Q();
        new Consumer(q);
        new Producer(q);
    }
}

class Q{
    int n;

    static Semaphore semCon = new Semaphore(0);
    static Semaphore semProd = new Semaphore(1);

    void get(){
        try{
            semCon.acquire();
        } catch (InterruptedException e){
            System.out.println(e);
        }

        System.out.println("Получено: " + n);
        semProd.release();
    }

    void put(int n){
        try {
            semProd.acquire();
        } catch (InterruptedException e){
            System.out.println(e);
        }

        this.n = n;
        System.out.println("Отправлено: " + n);
        semCon.release();
    }
}

class Producer implements Runnable{
    Q q;

    Producer(Q q){
        this.q = q;
        new Thread(this, "Producer").start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            q.put(i);
        }
    }
}

class Consumer implements Runnable{
    Q q;

    Consumer(Q q){
        this.q = q;
        new Thread(this, "Consumer").start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            q.get();
        }
    }
}