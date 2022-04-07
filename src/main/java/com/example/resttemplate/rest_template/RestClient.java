package com.example.resttemplate.rest_template;


import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class RestClient {
    private static final String URL = "http://94.198.50.185:7081/api/users";
    private static final String URL_DELETE = "http://94.198.50.185:7081/api/users/3";

    private static RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    String result = "";
    public static void main(String[] args) {
        RestClient restClient = new RestClient();
        restClient.getUsers();
        restClient.createUser();
        restClient.updateUser();
        restClient.deleteUser();
    }

        private void getUsers(){
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity <String> entity = new HttpEntity<String>("parameters", headers);
            ResponseEntity<String> result = restTemplate.exchange(URL, HttpMethod.GET, entity,
                    String.class);
            System.out.println(result);

            HttpHeaders header = result.getHeaders();
            String cookieArray = header.getFirst("Set-Cookie");
            String cookie = Arrays.stream(cookieArray.split(";"))
                    .findFirst()
                    .map(Object::toString)
                    .orElse("");

            System.out.println("cookie: " + cookie);
            headers.set("Content-Type", "application/json");
            headers.set("Cookie", cookie);
        }
        private void createUser(){
            User newUser = new User(3L, "James", "Brown", (byte) 18);
            HttpEntity<User> requestBody = new HttpEntity<>(newUser,headers);
            String result1 = restTemplate.postForObject(URL, requestBody, String.class);
            System.out.println(result1);
            result = result1;
        }
        private void updateUser(){
            User updateUser = new User(3L, "Thomas", "Shelby", (byte) 18);
            HttpEntity<User> requestBody = new HttpEntity<>(updateUser,headers);
            ResponseEntity<String> userResponseEntity = restTemplate.exchange(URL, HttpMethod.PUT, requestBody, String.class);
            System.out.println("result 2: " + userResponseEntity.getBody());
            result += userResponseEntity.getBody();

        }
        private void deleteUser(){
            HttpEntity<Long> requestBody = new HttpEntity<>(headers);
            ResponseEntity<String> userResponseEntity = restTemplate.exchange(URL_DELETE, HttpMethod.DELETE, requestBody, String.class);
            System.out.println("result 3: " + userResponseEntity.getBody());
            result += userResponseEntity.getBody();
            System.out.println("result: " + result);
        }

}
