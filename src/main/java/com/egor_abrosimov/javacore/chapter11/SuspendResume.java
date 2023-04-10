package main.java.com.egor_abrosimov.javacore.chapter11;

public class SuspendResume {
    public static void main(String[] args) {
        NewThread4 obj1 = new NewThread4("Один");
        NewThread4 obj2 = new NewThread4("Два");

        try{
            Thread.sleep(1000);
            obj1.mySuspend();
            System.out.println("Приостановка потока Один");
            Thread.sleep(1000);
            obj1.myResume();
            System.out.println("Возобновление потока Один");
            obj2.mySuspend();
            System.out.println("Приостановка потока Два");
            Thread.sleep(1000);
            obj2.myResume();
            System.out.println("Возобновление потока Два");
        } catch (InterruptedException e){
            System.out.println("Главный поток прерван");
        }

        try{
            System.out.println("Ожидание завершения потоков");
            obj1.t.join();
            obj2.t.join();
        } catch (InterruptedException e){
            System.out.println("Главный поток прерван");
        }

        System.out.println("Главный поток завершен");
    }
}

class NewThread4 implements Runnable{
    String name;
    Thread t;
    boolean suspendFlag;

    NewThread4(String threadName){
        name = threadName;
        t = new Thread(this, name);
        System.out.println("Новый поток: " + t);
        suspendFlag = false;
        t.start();
    }

    @Override
    public void run() {
        try{
            for (int i = 15; i > 0; i--) {
                System.out.println(name + ": " + i);
                Thread.sleep(200);
                synchronized (this){
                    while (suspendFlag){
                        wait();
                    }
                }
            }
        } catch (InterruptedException e){
            System.out.println(name + " прерван");
        }

        System.out.println(name + " завершен");
    }

    synchronized void mySuspend(){
        suspendFlag = true;
    }

    synchronized void myResume(){
        suspendFlag = false;
        notify();
    }
}
