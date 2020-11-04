package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {
    DataGenerator dataGenerator = new DataGenerator();

    String name = dataGenerator.getName();
    String phone = dataGenerator.getPhone();
    String city = dataGenerator.getCity();

    @Test
    void shouldSubmitRequest() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue(city);
        form.$("[data-test-id=date] input").doubleClick().sendKeys(dataGenerator.createDate(3));
        form.$("[data-test-id=name] input").setValue(name);
        form.$("[data-test-id=phone] input").setValue(phone);
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        $(".notification_status_ok").shouldBe(exist);
        $(".notification__content").shouldHave(exactText("Встреча успешно запланирована на "+ dataGenerator.createDate(3)));
        form.$("[data-test-id=date] input").doubleClick().sendKeys(dataGenerator.createDate(7));
        form.$(".button").click();
        $("[data-test-id=replan-notification]").waitUntil(exist, 15000);
        $(withText("Перепланировать")).click();
        $(".notification_status_ok").shouldBe(exist);
        $(".notification__content").shouldHave(exactText("Встреча успешно запланирована на "+ dataGenerator.createDate(7)));
    }
}
