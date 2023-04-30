package id.co.miniproject.cart.model;

import lombok.Data;

@Data
public class addItemRequest {
    private String CustomerUsername;
    private String itemId;
}
