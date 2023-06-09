package id.co.miniproject.cart.service;

import id.co.miniproject.cart.entitiy.Cart;
import id.co.miniproject.cart.model.CustomerResponse;
import id.co.miniproject.cart.model.ItemResponse;
import id.co.miniproject.cart.model.addItemRequest;
import id.co.miniproject.cart.model.addItemResponse;
import id.co.miniproject.cart.repository.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final RestTemplateService restTemplateService;

    public addItemResponse addItem(addItemRequest input){
        addItemResponse result = new addItemResponse();

        CustomerResponse customerResponse = restTemplateService.getCustInfo(input.getCustomerUsername());
        ItemResponse itemResponse = restTemplateService.getItemInfo(Integer.parseInt(input.getItemId()));

        if(itemResponse.getStok()>0){
            result.setNamaItem(itemResponse.getNama());
            result.setNamaCustomer(customerResponse.getNama());

            Cart newcart = new Cart();
            newcart.setStatus("On-Cart");
            newcart.setId_item(itemResponse.getId());
            newcart.setId_customer(customerResponse.getId());
            cartRepository.save(newcart);
        }else{
            return null;
        }

        return result;
    }

    public Cart updateCartOnSuccessPay(int payment, int itemId, int custId){
        int cartId = cartRepository.getCartId(custId, itemId);
        Cart newCart = new Cart();
        newCart.setId(cartId);
        newCart.setStatus("Payment");
        newCart.setId_payment(payment);
        return cartRepository.save(newCart);
    }

    @Transactional
    public void updateAllCart(int payment,List<Integer> cartId){
        cartRepository.updateAllCart("PAYMENT",payment, cartId);
    }

    public List<Cart> getCartByCust(int cust){
        return cartRepository.findCartById_customerAndStatus(cust, "On-Cart");
    }
}
