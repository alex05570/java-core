package org.example;

import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Runnable> taskQueue = new BlockingQueue<>(10);
        AtomicInteger completed = new AtomicInteger(0);

        int workers = 3;
        for (int i = 0; i < workers; i++) {
            final int id = i;
            Thread worker = new Thread(() -> {
                try {
                    while (true) {
                        Runnable task = taskQueue.dequeue();
                        if (task == null) break;
                        System.out.println("Worker-" + id + " взял задачу");
                        task.run();
                        completed.incrementAndGet();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            worker.setDaemon(true);
            worker.start();
        }

        for (int i = 1; i <= 20; i++) {
            final int num = i;
            taskQueue.enqueue(() -> {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ignored) {}
                System.out.println("  задача #" + num + " выполнена");
            });
        }

        Thread.sleep(2000);
        for (int i = 0; i < workers; i++) {
            taskQueue.enqueue(null);
        }

        System.out.println("Всего выполнено: " + completed.get());
    }
}