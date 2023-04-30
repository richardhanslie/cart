package id.co.miniproject.cart.util;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Getter
@Component
public class ErrorDictionary {

    private Map<String, String> error_dictionary = new HashMap<String, String>();

    private ErrorDictionary(){
        error_dictionary.put("2-1-000", "Sukses");
        error_dictionary.put("5-2-001", "Data Tidak Ditemukan");
        error_dictionary.put("5-2-002", "Terjadi Error Mohon Coba Lagi");
    }


}
