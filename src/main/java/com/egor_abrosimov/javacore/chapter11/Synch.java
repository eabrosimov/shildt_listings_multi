package main.java.com.egor_abrosimov.javacore.chapter11;

public class Synch {
    public static void main(String[] args) {
        Callme target = new Callme();
        Caller obj1 = new Caller(target, "Добро пожаловать");
        Caller obj2 = new Caller(target, "в синхронизированный");
        Caller obj3 = new Caller(target, "мир!");

        try{
            obj1.t.join();
            obj2.t.join();
            obj3.t.join();
        } catch (InterruptedException e){
            System.out.println("Прервано");
        }

    }
}

class Callme{
    synchronized void call(String msg){
        System.out.print("[" + msg);
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            System.out.println("Прервано");
        }
        System.out.println("]");
    }
}

class Caller implements Runnable{
    String msg;
    Callme target;
    Thread t;

    public Caller(Callme targ, String s){
        target = targ;
        msg = s;
        t = new Thread(this);
        t.start();
    }

    public void run(){
        target.call(msg);
    }
}