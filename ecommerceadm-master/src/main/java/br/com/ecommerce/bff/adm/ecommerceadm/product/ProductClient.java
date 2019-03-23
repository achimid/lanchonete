package br.com.ecommerce.bff.adm.ecommerceadm.product;

import br.com.ecommerce.bff.adm.ecommerceadm.category.CategoryClient;
import br.com.ecommerce.bff.adm.ecommerceadm.product.ProductDTO;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ProductClient {

    @Value("${ecommerce-api.base-url}")
    private String ecommerceApiUrl;

    @Autowired
    private CategoryClient categoryClient;

    public List<ProductDTO> findAll(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List> response = restTemplate.getForEntity(ecommerceApiUrl.concat("/products"), List.class);
        return response.getBody();
    }

    public ProductDTO findOne(Long id){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ProductDTO> response = restTemplate.getForEntity(
                ecommerceApiUrl.concat("/products/").concat(id.toString()), ProductDTO.class);
        return response.getBody();
    }

    public boolean delete(Long id){
        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.delete(ecommerceApiUrl.concat("/products/").concat(id.toString()), Map.class);
        }catch(HttpClientErrorException e){
            return false;
        }
        return true;
    }

    public boolean save(ProductDTO product){
        RestTemplate restTemplate = new RestTemplate();
        try{
            if(product.getId() != null){
                product.setCategory(findOne(product.getId()).getCategory());
            }else{
                product.setCategory(categoryClient.findOne(2l));
            }
            System.out.println(new Gson().toJson(product));
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    ecommerceApiUrl.concat("/products"), product, Map.class);
        }catch(HttpClientErrorException e){
            Gson gson = new Gson();
            Map map = gson.fromJson(e.getResponseBodyAsString(), Map.class);
            List<Map> errors = (List<Map>) map.get("errors");
            product.setErrors(errors.stream()
                .map(i -> (String) i.get("defaultMessage"))
                .collect(Collectors.toList()));
            return false;
        }
        return true;
    }

}
