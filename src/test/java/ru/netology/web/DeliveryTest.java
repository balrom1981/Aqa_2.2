package ru.netology.web;

import com.codeborne.selenide.Selectors;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class DeliveryTest {
    @Test
    void shouldAcceptInformation() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Сочи");
        $("[data-test-id='date'] input").click();
        $("[placeholder='Дата встречи']").clear();
//        $(Selectors.withText("10.03.2021")).clear();
        $("[data-test-id='date'] input").setValue("20.03.2021");
        $("[data-test-id='name'] input").setValue("Иванов Василий");
        $("[data-test-id='phone'] input").setValue("+792000000000");


    }

}
