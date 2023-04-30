package id.co.miniproject.cart.service;

import id.co.miniproject.cart.model.CustomerResponse;
import id.co.miniproject.cart.model.ItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class RestTemplateService {
    private final RestTemplate restTemplate;

    public CustomerResponse getCustInfo(String username){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity request = new HttpEntity(headers);
        String urlCustomer = "http://localhost:8080/cust/info/{username}";
        ResponseEntity<CustomerResponse> custRes = restTemplate.exchange(
                urlCustomer,
                HttpMethod.GET,
                request,
                CustomerResponse.class,
                username
        );

        //check response
        if (custRes.getStatusCode() == HttpStatus.OK) {
            System.out.println("Request Successful.");
            System.out.println(custRes.getBody());
        } else {
            System.out.println("Request Failed");
            System.out.println(custRes.getStatusCode());
        }
        return custRes.getBody();
    }

    public ItemResponse getItemInfo(int id){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity request = new HttpEntity(headers);
        String urlItem = "http://localhost:8080/cust/item/{id}";
        ResponseEntity<ItemResponse> item = restTemplate.exchange(
                urlItem,
                HttpMethod.GET,
                request,
                ItemResponse.class,
                id
        );

        //check response
        if (item.getStatusCode() == HttpStatus.OK) {
            System.out.println("Request Successful.");
            System.out.println(item.getBody());
        } else {
            System.out.println("Request Failed");
            System.out.println(item.getStatusCode());
        }
        return item.getBody();
    }
}
