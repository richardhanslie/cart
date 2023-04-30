package id.co.miniproject.cart.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentResponse {
    private int idPembayaran;
    private LocalDateTime waktuPembayaran;
    private String nama;
    private String username;
    private String namaItem;
    private String statusPembayaran;
}
