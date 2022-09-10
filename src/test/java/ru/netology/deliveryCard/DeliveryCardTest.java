package ru.netology.deliveryCard;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.conditions.Visible;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeliveryCardTest {

    @BeforeEach
    void openWeb() {
        Configuration.holdBrowserOpen=true;
        open("http://localhost:9999/");
    }

    @Test
    void shouldValidApplicationDataXPath() {
        $x("//input[@placeholder=\"Город\"]").setValue("Петропавловск-Камчатский");
        $x("//input[@placeholder=\"Дата встречи\"]").setValue("14.09.2022");
        $x("//input[@name=\"name\"]").setValue("Андрей Бородин-Петров");
        $x("//input[@name=\"phone\"]").setValue("+79875675656");
        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        $x("//*[contains(text(),'Успешно!')]").should(visible, Duration.ofSeconds(15));
        $x("//*[contains(text(),'Встреча успешно забронирована на')]").should(visible, Duration.ofSeconds(15));
        $x("//*[@class=\"notification__content\"]").should(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldValidApplicationDataCss() {
        $("[data-test-id='city'] input").setValue("Петропавловск-Камчатский");
        $("[placeholder=\"Дата встречи\"]").setValue("14.09.2022");
        $("[data-test-id='name'] input").setValue("Андрей Бородин-Петров");
        $("[data-test-id='phone'] input").setValue("+79875675656");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $x("//*[contains(text(),'Успешно!')]").should(visible, Duration.ofSeconds(15));
        $x("//*[contains(text(),'Встреча успешно забронирована на')]").should(visible, Duration.ofSeconds(15));
        $(".notification__content").should(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldNotValidCity() {
        $x("//input[@placeholder=\"Город\"]").setValue("Kazan");
        $x("//input[@placeholder=\"Дата встречи\"]").setValue("14.09.2022");
        $x("//input[@name=\"name\"]").setValue("Андрей Бородин-Петров");
        $x("//input[@name=\"phone\"]").setValue("+79875675656");
        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        String text = $x("//*[@data-test-id=\"city\"]").getText();
        assertEquals("Доставка в выбранный город недоступна", text);
    }


    @Test
    void shouldZeroCity() {
        $x("//input[@placeholder=\"Город\"]").setValue("");
        $x("//input[@placeholder=\"Дата встречи\"]").setValue("14.09.2022");
        $x("//input[@name=\"name\"]").setValue("Андрей Бородин-Петров");
        $x("//input[@name=\"phone\"]").setValue("+79875675656");
        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        String text = $x("//*[@data-test-id=\"city\"]").getText();
        assertEquals("Поле обязательно для заполнения", text);
    }

//    @Test
//    void shouldNotValidDataMeet() {
//        $x("//input[@placeholder=\"Город\"]").setValue("Петропавловск-Камчатский");
//        $x("//input[@placeholder=\"Дата встречи\"]").setValue("12.09.2022");
//        $x("//input[@name=\"name\"]").setValue("Андрей Бородин-Петров");
//        $x("//input[@name=\"phone\"]").setValue("+79875675656");
//        $x("//*[@data-test-id=\"agreement\"]").click();
//        $x("//*[@class=\"button__text\"]").click();
//        String text = $x("//*[contains(text(),'Заказ на выбранную дату невозможен')]").getText();
//        assertEquals("Заказ на выбранную дату невозможен", text);
//    }
//
//    @Test
//    void shouldZeroData() {
//        $x("//input[@placeholder=\"Город\"]").setValue("Петропавловск-Камчатский");
//        $x("//input[@placeholder=\"Дата встречи\"]").setValue("");
//        $x("//input[@name=\"name\"]").setValue("Андрей Бородин-Петров");
//        $x("//input[@name=\"phone\"]").setValue("+79875675656");
//        $x("//*[@data-test-id=\"agreement\"]").click();
//        $x("//*[@class=\"button__text\"]").click();
//        String text = $x("//*[contains(text(),'Неверно введена дата')]").getText();
//        assertEquals("Неверно введена дата", text);
//    }

    @Test
    void shouldNotValidNameAndLastName() {
        $x("//input[@placeholder=\"Город\"]").setValue("Петропавловск-Камчатский");
        $x("//input[@placeholder=\"Дата встречи\"]").setValue("14.09.2022");
        $x("//input[@name=\"name\"]").setValue("Andrey");
        $x("//input[@name=\"phone\"]").setValue("+79875675656");
        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        String text = $x("//*[@data-test-id=\"name\"]").getText();
        assertEquals("Фамилия и имя\n" + "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text);
    }

    @Test
    void shouldZeroNameAndLastName() {
        $x("//input[@placeholder=\"Город\"]").setValue("Петропавловск-Камчатский");
        $x("//input[@placeholder=\"Дата встречи\"]").setValue("14.09.2022");
        $x("//input[@name=\"name\"]").setValue("");
        $x("//input[@name=\"phone\"]").setValue("+79875675656");
        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        String text = $x("//*[@data-test-id=\"name\"]").getText();
        assertEquals("Фамилия и имя\n" + "Поле обязательно для заполнения", text);
    }

    @Test
    void shouldNotValidPhone() {
        $x("//input[@placeholder=\"Город\"]").setValue("Петропавловск-Камчатский");
        $x("//input[@placeholder=\"Дата встречи\"]").setValue("14.09.2022");
        $x("//input[@name=\"name\"]").setValue("Андрей Бородин-Петров");
        $x("//input[@name=\"phone\"]").setValue("Валера");
        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        String text = $x("//*[@data-test-id=\"phone\"]").getText();
        assertEquals("Мобильный телефон\n" + "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text);
    }

    @Test
    void shouldZeroPhone() {
        $x("//input[@placeholder=\"Город\"]").setValue("Петропавловск-Камчатский");
        $x("//input[@placeholder=\"Дата встречи\"]").setValue("14.09.2022");
        $x("//input[@name=\"name\"]").setValue("Андрей Бородин-Петров");
        $x("//input[@name=\"phone\"]").setValue("");
        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        String text = $x("//*[@data-test-id=\"phone\"]").getText();
        assertEquals("Мобильный телефон\n" + "Поле обязательно для заполнения", text);
    }

    @Test
    void shouldNotValidOtherFirstNumberPhone() {
        $x("//input[@placeholder=\"Город\"]").setValue("Петропавловск-Камчатский");
        $x("//input[@placeholder=\"Дата встречи\"]").setValue("14.09.2022");
        $x("//input[@name=\"name\"]").setValue("Андрей Бородин-Петров");
        $x("//input[@name=\"phone\"]").setValue("89772345657");
        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        String text = $x("//*[@data-test-id=\"phone\"]").getText();
        assertEquals("Мобильный телефон\n" + "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text);
    }

    @Test
    void shouldNotValidMorePhoneNumbers() {
        $x("//input[@placeholder=\"Город\"]").setValue("Петропавловск-Камчатский");
        $x("//input[@placeholder=\"Дата встречи\"]").setValue("14.09.2022");
        $x("//input[@name=\"name\"]").setValue("Андрей Бородин-Петров");
        $x("//input[@name=\"phone\"]").setValue("+797723456578");
        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        String text = $x("//*[@data-test-id=\"phone\"]").getText();
        assertEquals("Мобильный телефон\n" + "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text);
    }

    @Test
    void shouldNotValidLessPhoneNumbers() {
        $x("//input[@placeholder=\"Город\"]").setValue("Петропавловск-Камчатский");
        $x("//input[@placeholder=\"Дата встречи\"]").setValue("14.09.2022");
        $x("//input[@name=\"name\"]").setValue("Андрей Бородин-Петров");
        $x("//input[@name=\"phone\"]").setValue("+7977234565");
        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        String text = $x("//*[@data-test-id=\"phone\"]").getText();
        assertEquals("Мобильный телефон\n" + "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text);
    }

    @Test
    void shouldNotValidCheckBox() {
        $x("//input[@placeholder=\"Город\"]").setValue("Петропавловск-Камчатский");
        $x("//input[@placeholder=\"Дата встречи\"]").setValue("14.09.2022");
        $x("//input[@name=\"name\"]").setValue("Андрей Бородин-Петров");
        $x("//input[@name=\"phone\"]").setValue("+79875675656");
        $x("//*[@class=\"button__text\"]").click();
        String text = $x("//*[@data-test-id=\"agreement\"]").getText();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных", text);
    }


}

