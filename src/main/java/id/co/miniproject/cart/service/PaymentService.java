package id.co.miniproject.cart.service;

import id.co.miniproject.cart.entitiy.Payment;
import id.co.miniproject.cart.model.*;
import id.co.miniproject.cart.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final RestTemplateService restTemplateService;
    private final CartService cartService;

    @Transactional
    public PaymentResponse paymentItem(addItemRequest input){
        PaymentResponse response = new PaymentResponse();
        //get customer info -> butuh nomor_ktp, nama, username
        CustomerResponse custInfo = restTemplateService.getCustInfo(input.getCustomerUsername());

        //get item info -> butuh nama barang, stok, harga
        ItemResponse itemInfo = restTemplateService.getItemInfo(Integer.parseInt(input.getItemId()));

        //get bank info by nomor ktp dari custInfo-> butuh cek saldo
        BankResponse bankInfo = new BankResponse(); //ganti panggil restTemplate

        // cek saldo > harga dan stok barang
        if(itemInfo.getStok()>1 && itemInfo.getHarga()>bankInfo.getSaldo()){
            //update saldo bank -> panggil restTemplate
        }else{
            //payment gagal saldo tidak mencukup
            return response;
        }

        //save payment ke db
            Payment payment = new Payment();
        payment.setDatetime(LocalDateTime.now());
        payment.setId_item(Integer.parseInt(input.getItemId()));
        payment.setId_customer(custInfo.getId());

        Payment newPayment = paymentRepository.save(payment);

        //panggil update status cart
        cartService.updateCartOnSuccessPay(newPayment.getId(), Integer.parseInt(input.getItemId()));

        response.setNamaItem(itemInfo.getNama());
        response.setUsername(input.getCustomerUsername());
        response.setNama(custInfo.getNama());
        response.setWaktuPembayaran(newPayment.getDatetime());
        response.setNamaItem(itemInfo.getNama());
        response.setStatusPembayaran("Pembayaran Berhasil");

        return response;
    }
}
