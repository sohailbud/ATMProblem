package cscie55.hw6;

import java.util.LinkedList;

/**
 * Created by Sohail on 4/22/14.
 */
public class ATMThread extends Thread{

    private LinkedList taskQueue= null;

    public ATMThread() {
        this.taskQueue = Server.taskQueue;
    }

    public void run() {
        ATMRunnable runnable;
        while(true) {
            synchronized (taskQueue) {
                while(taskQueue.isEmpty()) {
                    try {
                        taskQueue.wait();
                    } catch (InterruptedException e) {}
                }
                runnable = (ATMRunnable) taskQueue.removeFirst();
                try {
                    runnable.run();
                } catch (ATMException e) {}
            }

            //prints current thread
            String thrd = Thread.currentThread().getName();
            System.out.println("Running request in thread: " + thrd);

        }
    }
}
