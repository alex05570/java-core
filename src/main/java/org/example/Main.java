package org.example;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        DataAggregator aggregator = new DataAggregator();

        System.out.println("Старт сбора данных...");
        long start = System.currentTimeMillis();

        ProductInfo info = aggregator.aggregateProductInfo("Ноутбук");

        long elapsed = System.currentTimeMillis() - start;
        System.out.println(info);
        System.out.println("Время: " + elapsed + " мс");

        aggregator.shutdown();
    }
}