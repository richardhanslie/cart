package id.co.miniproject.cart.controller;

import id.co.miniproject.cart.model.addItemRequest;
import id.co.miniproject.cart.model.addItemResponse;
import id.co.miniproject.cart.service.CartService;
import id.co.miniproject.cart.util.ErrorCode;
import id.co.miniproject.cart.util.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private CartService cartService;
    private ResponseUtils responseUtils;

    @PostMapping()
    private ResponseEntity<Object> addItem(@RequestBody addItemRequest input){
        addItemResponse response = cartService.addItem(input);

        if(ObjectUtils.isEmpty(response)){
            return responseUtils.generate(ErrorCode.General_Error, HttpStatus.BAD_REQUEST, null);
        }
        return responseUtils.generate(ErrorCode.Success, HttpStatus.OK, response);
    }
}
