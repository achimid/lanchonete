package br.com.ecommerce.bff.adm.ecommerceadm.dto;

import br.com.ecommerce.bff.adm.ecommerceadm.product.ProductDTO;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by Lourran on 16/07/2017.
 */
@Data
public class OrderDetailDTO{

    private Long id;
    private OrderDTO order;
    private ProductDTO product;
    private BigDecimal price;
    private BigDecimal quantity;

}
