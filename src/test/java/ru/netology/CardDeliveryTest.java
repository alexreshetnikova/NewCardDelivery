package ru.netology;

import com.codeborne.selenide.SelenideElement;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {
    LocalDate today = LocalDate.now();
    LocalDate newDate = today.plusDays(3);
    LocalDate newestDate = today.plusDays(5);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private Faker faker;

    @BeforeEach
    void setUpAll() {
        faker = new Faker(new Locale("ru"));
    }

    @Test
    void shouldSubmitRequest() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").sendKeys(faker.address().cityName());
        form.$("[data-test-id=date] input").doubleClick().sendKeys(formatter.format(newDate));
        form.$("[data-test-id=name] input").sendKeys(faker.name().lastName());
        form.$("[data-test-id=name] input").setValue(" ");
        form.$("[data-test-id=name] input").sendKeys(faker.name().firstName());
        form.$("[data-test-id=phone] input").sendKeys(faker.phoneNumber().phoneNumber());
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        $(".notification_status_ok").shouldBe(exist);
        form.$("[data-test-id=date] input").doubleClick().sendKeys(formatter.format(newestDate));
        form.$(".button").click();
        $("[data-test-id=replan-notification]").waitUntil(exist, 15000);
        $(withText("Перепланировать")).click();
        $(".notification_status_ok").shouldBe(exist);
    }
}
