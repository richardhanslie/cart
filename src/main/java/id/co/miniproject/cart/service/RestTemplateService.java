package id.co.miniproject.cart.service;

import id.co.miniproject.cart.model.BankData;
import id.co.miniproject.cart.model.BankResponse;
import id.co.miniproject.cart.model.CustomerResponse;
import id.co.miniproject.cart.model.ItemResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RestTemplateService {
    private final RestTemplate restTemplate;

    public CustomerResponse getCustInfo(String username) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity request = new HttpEntity(headers);
        System.out.println(username);
        String urlCustomer = "http://localhost:8777/cust/info/{username}";

        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("username", username);

        URI uri = UriComponentsBuilder.fromUriString(urlCustomer).buildAndExpand(urlParams).toUri();
        ResponseEntity<CustomerResponse> custRes = restTemplate.exchange(
                uri, HttpMethod.GET, request, CustomerResponse.class
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

    public ItemResponse getItemInfo(int id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("id", id);
        String urlItem = "http://localhost:8777/item/{id}";
        ItemResponse item = restTemplate.getForObject(
                urlItem,
                ItemResponse.class,
                params
        );

        return item;
    }

    public BankResponse getInfoSaldo(String nomorKtp) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        Map<String, String> params = new HashMap<String, String>();
        params.put("nomorKtp", nomorKtp);
        String urlBank = "http://localhost:8999/bank/info/{nomorKtp}";
        BankResponse bankInfo = restTemplate.getForObject(
                urlBank,
                BankResponse.class,
                params
        );

        System.out.println("SALDO " + bankInfo.getSaldo());
        return bankInfo;
    }

    @Transactional
    public Boolean transfer(String nomorRekeningPengirim, String nomorRekeningPenerima, int harga) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        BankData bankData = new BankData();
        bankData.setNomorRekeningPengirim(nomorRekeningPengirim);
        bankData.setNomorRekeningPenerima(nomorRekeningPenerima);
        bankData.setHarga(harga);
        String urlBank = "http://localhost:8999/bank/transfer";
        Boolean bankInfo = restTemplate.postForObject(
                urlBank,
                bankData,
                Boolean.class
        );

        return bankInfo;
    }
}
