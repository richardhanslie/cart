package id.co.miniproject.cart.model;

import lombok.Data;

@Data
public class BankResponse {
    private String nama_customer;
    private String nomor_rekening;
    private int saldo;
}
