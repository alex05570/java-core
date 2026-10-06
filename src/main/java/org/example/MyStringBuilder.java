package org.example;

import java.util.ArrayDeque;
import java.util.Deque;


public class MyStringBuilder {

    private char[] value;
    private int count;

    private final Deque<Snapshot> snapshots = new ArrayDeque<>();

    public MyStringBuilder() {
        value = new char[16];
    }

    public MyStringBuilder(String s) {
        this.value = new char[Math.max(s.length(), 16)];
        s.getChars(0, s.length(), value, 0);
        count = s.length();
    }

    public MyStringBuilder append(String str) {
        saveSnapshot();

        ensureCapacity(count + str.length());
        str.getChars(0, str.length(), value, count);
        count += str.length();

        return this;
    }

    public MyStringBuilder delete(int start, int end) {
        checkRange(start, end);

        saveSnapshot();

        int length = end - start;

        System.arraycopy(value, end, value, start, count - end);

        count -= length;

        return this;
    }

    public MyStringBuilder setCharAt(int index, char ch) {
        checkIndex(index); saveSnapshot();

        value[index] = ch;

        return this;
    }

    private void ensureCapacity(int minimumCapacity) {
        if (minimumCapacity <= value.length) {
            return;
        }

        int newCapacity = value.length * 2 + 2;

        if (newCapacity < minimumCapacity) {
            newCapacity = minimumCapacity;
        }

        char[] newValue = new char[newCapacity];
        System.arraycopy(value, 0, newValue, 0, count);
        value = newValue;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= count) {
            throw new IllegalArgumentException();
        }
    }

    private void checkRange(int start, int end) {
        if (start < 0 || start > end || end > count) {
            throw new IllegalArgumentException();
        }
    }

    private static class Snapshot {

        private final char[] value;
        private final int count;

        private Snapshot(char[] value, int count) {
            this.value = value; this.count = count;
        }
    }

    public MyStringBuilder undo() {
        if (!snapshots.isEmpty()) {
            Snapshot snapshot = snapshots.pop();

            value = snapshot.value;
            count = snapshot.count;
        }

        return this;
    }

    private void saveSnapshot() {
        snapshots.push(new Snapshot(value.clone(), count));
    }

    public String toString() {
        return new String(value, 0, count);
    }
}
