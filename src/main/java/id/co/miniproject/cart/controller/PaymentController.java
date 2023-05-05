package id.co.miniproject.cart.controller;

import id.co.miniproject.cart.model.PaymentResponse;
import id.co.miniproject.cart.model.addItemRequest;
import id.co.miniproject.cart.repository.PaymentRepository;
import id.co.miniproject.cart.service.PaymentService;
import id.co.miniproject.cart.util.ErrorCode;
import id.co.miniproject.cart.util.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentController {
    private ResponseUtils responseUtils;
    private PaymentService paymentService;

    @PostMapping("/payment")
    private ResponseEntity<Object> paymentExecute(@RequestBody addItemRequest input){
        PaymentResponse response = paymentService.payForOneItem(input);
        if(ObjectUtils.isEmpty(response)){
            return responseUtils.generate(ErrorCode.General_Error, HttpStatus.BAD_REQUEST, null);
        }
        return responseUtils.generate(ErrorCode.Success, HttpStatus.OK, response);
    }
}
