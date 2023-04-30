package id.co.miniproject.cart.repository;

import id.co.miniproject.cart.entitiy.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
}
