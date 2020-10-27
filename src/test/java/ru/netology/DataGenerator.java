package ru.netology;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGenerator{
    private Faker faker;
    private LocalDate today = LocalDate.now();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public String createDate(int plusDays) {
        LocalDate newDate = today.plusDays(plusDays);
        return formatter.format(newDate);
    }

    public String getCity() {
        faker = new Faker(new Locale("ru"));
        return faker.address().cityName();
    }

    public String getName() {
        faker = new Faker(new Locale("ru"));
        return (faker.name().lastName()+(" ")+faker.name().firstName());
    }

    public String getPhone() {
        faker = new Faker(new Locale("ru"));
        return faker.phoneNumber().phoneNumber();
    }
}