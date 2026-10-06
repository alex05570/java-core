package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentBank {

    ConcurrentHashMap<Long, BankAccount> accounts = new ConcurrentHashMap<>();

    public BankAccount createAccount(long initialBalance) {
        BankAccount account = new BankAccount(initialBalance);
        accounts.put(account.getId(), account);
        return account;
    }

    public boolean transfer(BankAccount from, BankAccount to, long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма перевода должна быть положительной");
        }
        if (from == null || to == null) {
            throw new IllegalArgumentException("Счета не могут быть null");
        }
        if (from == to) {
            return true;
        }
        if (!accounts.containsKey(from.getId()) || !accounts.containsKey(to.getId())) {
            throw new IllegalArgumentException("Один из счетов не принадлежит банку");
        }

        BankAccount first;
        BankAccount second;

        if (from.getId() < to.getId()) {
            first = from;
            second = to;
        } else {
            first = to;
            second = from;
        }

        synchronized (first) {
            synchronized (second) {
                if (!from.withdraw(amount)) {
                    return false;
                }

                to.deposit(amount);

                return true;
            }
        }
    }

    public long getTotalBalance() {
        List<BankAccount> sorted = new ArrayList<>(accounts.values());
        sorted.sort(Comparator.comparingLong(BankAccount::getId));

        long total = 0;
        for (BankAccount account : sorted) {
            synchronized (account) {
                total += account.getBalance();
            }
        }
        return total;
    }
}
