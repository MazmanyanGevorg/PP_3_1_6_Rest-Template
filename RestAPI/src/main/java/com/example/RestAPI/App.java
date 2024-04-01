package com.example.RestAPI;

import com.example.RestAPI.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class App {
    private static final String URL = "http://94.198.50.185:7081/api/users";
//  - объявляет константу `URL`, содержащую адрес, на который будет отправлен HTTP-запрос.



    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
// - создает новый экземпляр RestTemplate, который будет использоваться для взаимодействия с удаленным сервером.
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.GET,
                null, String.class);
//  - выполняет HTTP-запрос методом GET к указанному URL с помощью метода `exchange`,
//  который возвращает ответ в виде строки. `ResponseEntity` представляет ответ целиком,
//  включая заголовки, тело и статус ответа.

        System.out.println("restTemplate: " + restTemplate);
        System.out.println("responseEntity: " + responseEntity);

        String set_cookie = responseEntity.getHeaders().getFirst("Set-Cookie");
// - извлекает значение заголовка "Set-Cookie" из ответа в виде строки.
// Этот заголовок обычно содержит информацию о сессии,
// которую необходимо сохранить и использовать для последующих запросов.

        System.out.println("set_cookie: " + set_cookie);

        HttpHeaders headers = new HttpHeaders();
// - создает новый объект HttpHeaders, который позволяет управлять заголовками запроса.
        headers.set("Cookie", set_cookie);
//  - устанавливает заголовок "Cookie" в HttpHeaders с значением,
//  полученным из заголовка "Set-Cookie" в предыдущем ответе.
//  Это необходимо для передачи `sessionId` или других данных
//  аутентификации в следующих запросах для поддержания сессии.

        User user = new User();
        user.setId(3L);
        user.setName("James");
        user.setLastName("Brown");

        System.out.println("user: " + user);

        ResponseEntity<String> responseEntity1 = restTemplate.exchange(URL,HttpMethod.POST,
                new HttpEntity<>(user,headers), String.class);

        System.out.println("responseEntity1: " + responseEntity1);
        System.out.println("responseEntity1.getBody(): " + responseEntity1.getBody());
        System.out.println("responseEntity1.getHeaders(): " + responseEntity1.getHeaders());
        System.out.println("responseEntity1.getHeaders().getFirst(HttpHeaders.DATE): " + responseEntity1.getHeaders().getFirst(HttpHeaders.DATE));
        System.out.println("responseEntity1.getStatusCode(): " + responseEntity1.getStatusCode());

        user.setId(3L);
        user.setName("Thomas");
        user.setLastName("Shelby");
        user.setAge((byte) 20);

        ResponseEntity<String> responseEntity2 = restTemplate.exchange(URL,HttpMethod.PUT,
                new HttpEntity<>(user,headers), String.class);

        System.out.println("responseEntity2: " + responseEntity2);
        ResponseEntity<String> responseEntity3 = restTemplate.exchange (URL + "/" + 3 ,HttpMethod.DELETE,
                new HttpEntity<>(headers), String.class);
        System.out.println("responseEntity3: " + responseEntity3);

        System.out.println(responseEntity1.getBody() +
                responseEntity2.getBody() + responseEntity3.getBody());

    }
}

