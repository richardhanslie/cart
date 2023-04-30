package id.co.miniproject.cart.util;

import id.co.miniproject.cart.model.ErrorSchema;
import id.co.miniproject.cart.model.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResponseUtils {

    private final ErrorDictionary errorDictionary;

    public <T>ResponseEntity<Object> generate(String errorcode, HttpStatus status, T objectSchema){
        ErrorSchema errorSchema = new ErrorSchema();
        errorSchema.setError_code(errorcode);
        errorSchema.setError_Message(errorDictionary.getError_dictionary().get(errorcode));

        Response response = new Response();
        response.setErrorSchema(errorSchema);
        response.setObjectSchema(objectSchema);

        return ResponseEntity
                .status(status)
                .body(objectSchema);
    }
}
