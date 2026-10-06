package org.example;

import java.util.LinkedList;
import java.util.Queue;

public class BlockingQueue<T> {

    private final Queue<T> queue = new LinkedList<>();
    private final int capacity;
    private final Object lock = new Object();

    public BlockingQueue(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Вместимость должна быть больше 0");
        }

        this.capacity = capacity;
    }

    public void enqueue(T item) throws InterruptedException {
        synchronized (lock) {
            while (queue.size() == capacity) {
                lock.wait();
            }

            queue.add(item);

            lock.notify();
        }
    }

    public T dequeue() throws InterruptedException {
        synchronized (lock) {
            while (queue.isEmpty()) {
                lock.wait();
            }

            T el = queue.poll();
            lock.notifyAll();

            return el;
        }
    }

    public int size() {
        synchronized (lock) {
            return queue.size();
        }
    }
}
