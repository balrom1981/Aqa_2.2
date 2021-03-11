package ru.netology.web;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;


import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

class DeliveryTest {


    @BeforeEach
    void shouldCleanAndSetData() {
        open("http://localhost:9999");
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        String str = LocalDate.now().plusDays(10).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] input").setValue(str);
    }

    @Test
    void shouldAcceptInformation() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id='name'] input").setValue("Иванов Василий");
        $("[data-test-id='phone'] input").setValue("+79200000000");
        $("[data-test-id=agreement]").click();
        $$("button").get(1).click();
        String data = $("[data-test-id='date'] input").getCssValue("value");
        $("[class='notification__content']").shouldHave(exactText(data), Duration.ofSeconds(14));
    }

    @Test
    void shouldRejectInvalidСity() {
        $("[data-test-id=city] input").setValue("Сочи");
        $("[data-test-id='name'] input").setValue("Иванов Василий");
        $("[data-test-id='phone'] input").setValue("+79200000000");
        $("[data-test-id=agreement]").click();
        $$("button").get(1).click();
        $(withText("Доставка в выбранный город недоступна")).shouldBe(visible);
    }

    @Test
    void shouldRejectInvalidName() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id='name'] input").setValue("Rachel");
        $("[data-test-id='phone'] input").setValue("+79200000000");
        $("[data-test-id=agreement]").click();
        $$("button").get(1).click();
        $(withText("Имя и Фамилия указаные неверно. " +
                "Допустимы только русские буквы, пробелы и дефисы.")).shouldBe(visible);
    }

    @Test
    void shouldRejectInvalidPhone() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id='name'] input").setValue("Иванов Василий");
        $("[data-test-id='phone'] input").setValue("89200000000");
        $("[data-test-id=agreement]").click();
        $$("button").get(1).click();
        $(withText("Телефон указан неверно. Должно быть 11 цифр, " +
                "например, +79012345678.")).shouldBe(visible);
    }

    @Test
    void shouldRejectEmptyCheckBox() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id='name'] input").setValue("Иванов Василий");
        $("[data-test-id='phone'] input").setValue("+79200000000");
        $$("button").get(1).click();
        $(withText("Я соглашаюсь с условиями обработки и использования " +
                "моих персональных данных")).shouldBe(visible);
    }

}
