package id.co.miniproject.cart.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {
    private ErrorSchema errorSchema;
    @JsonIgnore
    private T objectSchema;
}
