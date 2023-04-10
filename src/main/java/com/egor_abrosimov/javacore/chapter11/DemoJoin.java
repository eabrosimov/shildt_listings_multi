package main.java.com.egor_abrosimov.javacore.chapter11;

public class DemoJoin {
    public static void main(String[] args) {
        NewThread3 obj1 = new NewThread3("один");
        NewThread3 obj2 = new NewThread3("два");
        NewThread3 obj3 = new NewThread3("три");

        System.out.println("Поток один запущен:" + obj1.t.isAlive());
        System.out.println("Поток один запущен:" + obj2.t.isAlive());
        System.out.println("Поток один запущен:" + obj3.t.isAlive());

        try {
            System.out.println("Ожидание завершения потоков");
            obj1.t.join();
            obj2.t.join();
            obj3.t.join();
        } catch (InterruptedException e){
            System.out.println("Главный поток прерван");
        }

        System.out.println("Поток один запущен:" + obj1.t.isAlive());
        System.out.println("Поток один запущен:" + obj2.t.isAlive());
        System.out.println("Поток один запущен:" + obj3.t.isAlive());

        System.out.println("Главный поток завершен");
    }
}

class NewThread3 implements Runnable{
    String name;
    Thread t;

    NewThread3(String threadName){
        name = threadName;
        t = new Thread(this, name);
        System.out.println("Новый поток: " + t);
        t.start();
    }

    @Override
    public void run() {
        try{
            for (int i = 5; i > 0; i--) {
                System.out.println(name + ": " + i);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e){
            System.out.println(name + " прерван");
        }
        System.out.println(name + " завершен");
    }
}
