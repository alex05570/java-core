package org.example;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DataAggregator {

    private final ExecutorService executor = Executors.newFixedThreadPool(3);
    private final Random random = new Random();


    private double fetchPrice(String productName) {
        sleepAndMaybeFail();
        return 899.99;
    }

    private String fetchDescription(String productName) {
        sleepAndMaybeFail();
        return "Игровой ноутбук";
    }

    private double fetchRating(String productName) {
        sleepAndMaybeFail();
        return 4.7;
    }

    private void sleepAndMaybeFail() {
        try {
            Thread.sleep(1000 + random.nextInt(2000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
        if (random.nextInt(100) < 20) {
            throw new RuntimeException("Сервис недоступен");
        }
    }


    ProductInfo aggregateProductInfo(String productName) {

        CompletableFuture<Double> priceFuture = CompletableFuture
                .supplyAsync(() -> fetchPrice(productName), executor)
                .exceptionally(ex -> {
                    System.out.println("Ошибка получения цены: " + ex.getMessage());
                    return 0.0;
                });

        CompletableFuture<String> descriptionFuture = CompletableFuture
                .supplyAsync(() -> fetchDescription(productName), executor)
                .exceptionally(ex -> {
                    System.out.println("Ошибка получения описания: " + ex.getMessage());
                    return "Нет данных";
                });

        CompletableFuture<Double> ratingFuture = CompletableFuture
                .supplyAsync(() -> fetchRating(productName), executor)
                .exceptionally(ex -> {
                    System.out.println("Ошибка получения рейтинга: " + ex.getMessage());
                    return 0.0;
                });

        CompletableFuture<ProductInfo> resultFuture = CompletableFuture.allOf(priceFuture, descriptionFuture, ratingFuture)
                .thenApply(v -> new ProductInfo(
                        productName,
                        priceFuture.join(), descriptionFuture.join(), ratingFuture.join()));

        return resultFuture.join();
    }

    public void shutdown() throws InterruptedException {
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
    }


}
