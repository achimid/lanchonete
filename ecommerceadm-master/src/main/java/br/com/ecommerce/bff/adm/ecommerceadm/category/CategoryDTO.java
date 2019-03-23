package br.com.ecommerce.bff.adm.ecommerceadm.category;

import br.com.ecommerce.bff.adm.ecommerceadm.base.BaseDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * Created by Lourran on 22/07/2017.
 */
@Data
public class CategoryDTO extends BaseDTO{

    private Long id;
    private String name;
    private String description;

}
