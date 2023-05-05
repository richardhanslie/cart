package id.co.miniproject.cart.repository;

import id.co.miniproject.cart.entitiy.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    @Query(value = "SELECT id FROM cart WHERE id_customer = :custId and id_item = :itemId", nativeQuery = true)
    Integer getCartId(@Param("custId") int custId, @Param("itemId") int itemId);

    @Query(value = "SELECT * FROM cart WHERE id_customer = :custId and status = :status", nativeQuery = true)
    List<Cart> findCartById_customerAndStatus( int custId, String status);

    @Modifying
    @Query(value = "UPDATE cart SET status = :status,  id_payment = :idPay WHERE id IN (:cart)", nativeQuery = true)
    void updateAllCart(@Param("status") String status,@Param("idPay") int idPayment, @Param("cart") List<Integer> cart);
}
