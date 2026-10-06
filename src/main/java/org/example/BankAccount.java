package org.example;

import java.util.concurrent.atomic.AtomicLong;

public class BankAccount {

    public static AtomicLong ID_GENERATOR = new AtomicLong(1);

    private final long id;
    private long balance;

    public BankAccount(long initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Баланс не может быть меньше 0");
        }

        this.id = ID_GENERATOR.getAndIncrement();
        this.balance = initialBalance;
    }

    public synchronized void deposit(long amount) {
        checkAmount(amount);

        balance += amount;
    }


    public synchronized boolean withdraw(long amount) {
        checkAmount(amount);

        if (balance < amount) {
            return false;
        }

        balance -= amount;
        return true;

    }

    public synchronized long getBalance() {
        return balance;
    }

    public long getId() {
        return id;
    }

    private void checkAmount(long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException( "Сумма должна быть положительной" );
        }
    }
}
