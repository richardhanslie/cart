package id.co.miniproject.cart.entitiy;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int id_customer;
    private int id_item;
    private String status;
    private int id_payment;
}
