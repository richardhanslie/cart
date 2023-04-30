package id.co.miniproject.cart.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorSchema {
    private String Error_Message;
    private String Error_code;
}
