package br.com.ecommerce.bff.adm.ecommerceadm.product;

import br.com.ecommerce.bff.adm.ecommerceadm.base.BaseDTO;
import br.com.ecommerce.bff.adm.ecommerceadm.category.CategoryDTO;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by Lourran on 22/07/2017.
 */
@Data
public class ProductDTO extends BaseDTO {

    private Long id;
    private String name;
    private BigDecimal priceSale;
    private BigDecimal priceCost;
    private String url;
    private String description;
    private CategoryDTO category;
}
