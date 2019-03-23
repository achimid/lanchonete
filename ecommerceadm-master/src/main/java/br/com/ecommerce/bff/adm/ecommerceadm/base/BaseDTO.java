package br.com.ecommerce.bff.adm.ecommerceadm.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Collection;

@Data
public class BaseDTO {

    @JsonIgnore
    private Collection errors;
}
