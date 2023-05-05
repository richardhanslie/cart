package id.co.miniproject.cart.service;

import id.co.miniproject.cart.entitiy.Cart;
import id.co.miniproject.cart.entitiy.Payment;
import id.co.miniproject.cart.model.*;
import id.co.miniproject.cart.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final RestTemplateService restTemplateService;
    private final CartService cartService;

    private static final String nomorRekeningPemilik = "1234567890";

    public PaymentResponse payForOneItem(addItemRequest input){
        PaymentResponse response = new PaymentResponse();
        //get customer info -> butuh nomor_ktp, nama, username
        CustomerResponse custInfo = restTemplateService.getCustInfo(input.getCustomerUsername());
        if(ObjectUtils.isEmpty(custInfo)){
            return response;
        }
        //get item info -> butuh nama barang, stok, harga
        ItemResponse itemInfo = restTemplateService.getItemInfo(Integer.parseInt(input.getItemId()));
        if(ObjectUtils.isEmpty(itemInfo)){
            return response;
        }
        //get bank info by nomor ktp dari custInfo-> butuh cek saldo
        BankResponse bankInfo = restTemplateService.getInfoSaldo(custInfo.getNomor_ktp());
        if(ObjectUtils.isEmpty(bankInfo)){
            return response;
        }
        // cek saldo > harga dan stok barang
        if(itemInfo.getStok()>1 && itemInfo.getHarga()<bankInfo.getSaldo()){
            System.out.println(bankInfo.getNomor_rekening());
            //update saldo bank
            Boolean isTransfeSuccess = restTemplateService.transfer(bankInfo.getNomor_rekening(), nomorRekeningPemilik, itemInfo.getHarga());
            if(isTransfeSuccess){
                System.out.println("Transfer Berhasil");
            }
            else{
                System.out.println("Transfer Gagal");
                return response;
            }
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

        //cartService.updateCartOnSuccessPay(newPayment.getId(), Integer.parseInt(input.getItemId()), custInfo.getId());

        response.setNamaItem(itemInfo.getNama());
        response.setUsername(input.getCustomerUsername());
        response.setNama(custInfo.getNama());
        response.setWaktuPembayaran(newPayment.getDatetime());
        response.setNamaItem(itemInfo.getNama());
        response.setStatusPembayaran("Pembayaran Berhasil");

        return response;
    }

    public PaymentResponse payForManyItem(addItemRequest input){
        PaymentResponse response = new PaymentResponse();
        //get customer info -> butuh nomor_ktp, nama, username
        CustomerResponse custInfo = restTemplateService.getCustInfo(input.getCustomerUsername());
        if(ObjectUtils.isEmpty(custInfo)){
            return response;
        }
        List<Integer> cartId = new ArrayList<>();
        List<Cart> getCart = cartService.getCartByCust(custInfo.getId());

        if(!ObjectUtils.isEmpty(getCart)){
            int harga = 0;
            for (Cart i : getCart) {
                cartId.add(i.getId());
                //get item info -> butuh nama barang, stok, harga
                ItemResponse itemInfo = restTemplateService.getItemInfo(i.getId_item());
                if(ObjectUtils.isEmpty(itemInfo)){
                    return response;
                }
                harga+= itemInfo.getHarga();
            }

            BankResponse bankInfo = restTemplateService.getInfoSaldo(custInfo.getNomor_ktp());
            if(ObjectUtils.isEmpty(bankInfo)){
                return response;
            }
            // cek saldo > harga dan stok barang
            if(bankInfo.getSaldo()>harga){
                //update saldo bank
                Boolean isTransfeSuccess = restTemplateService.transfer(bankInfo.getNomor_rekening(), nomorRekeningPemilik, harga);
                if(isTransfeSuccess){
                    System.out.println("Transfer Berhasil");
                }
                else{
                    System.out.println("Transfer Gagal");
                    return response;
                }
            }else{
                //payment gagal saldo tidak mencukup
                return response;
            }
        }

        //save payment ke db
        Payment payment = new Payment();
        payment.setDatetime(LocalDateTime.now());
        payment.setId_customer(custInfo.getId());

        Payment newPayment = paymentRepository.save(payment);

        //panggil update status cart
        cartService.updateAllCart(newPayment.getId(),cartId );

        response.setNamaItem("CART");
        response.setUsername(input.getCustomerUsername());
        response.setNama(custInfo.getNama());
        response.setWaktuPembayaran(newPayment.getDatetime());
        response.setStatusPembayaran("Pembayaran Berhasil");

        return response;
    }
}
