package id.co.miniproject.cart.model;

import lombok.Data;

@Data
public class ItemResponse {
    private int id;
    private String nama;
    private int stok;
    private int harga;
}
