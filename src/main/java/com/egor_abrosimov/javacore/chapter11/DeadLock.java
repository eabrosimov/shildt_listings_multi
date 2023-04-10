package main.java.com.egor_abrosimov.javacore.chapter11;

public class DeadLock implements Runnable{
    A a = new A();
    B b = new B();

    public static void main(String[] args) {
        new DeadLock();
    }

    DeadLock(){
        Thread.currentThread().setName("Главный поток");
        Thread t = new Thread(this, "Соперничающий поток");
        t.start();

        a.foo(b);
        System.out.println("Назад в главный поток");
    }

    @Override
    public void run() {
        b.bar(a);
        System.out.println("Назад в главный поток");
    }
}

class A{
    synchronized  void foo(B b){
        String name = Thread.currentThread().getName();
        System.out.println(name + " вошел в метод A.foo()");

        try {
            Thread.sleep(1000);
        } catch (Exception e){
            System.out.println("Класс А прерван");
        }
        System.out.println(name + " пытается вызвать метод B.last()");
        b.last();
    }

    synchronized void last(){
        System.out.println("В методе A.last()");
    }
}

class B{
    synchronized void bar(A a){
        String name = Thread.currentThread().getName();
        System.out.println(name + " вошел в метод B.bar()");

        try {
            Thread.sleep(1000);
        } catch (Exception e){
            System.out.println("Класс В прерван");
        }
        System.out.println(name + " пытается вызвать метод A.last()");
        a.last();
    }

    synchronized void last(){
        System.out.println("В методе B.last()");
    }
}
