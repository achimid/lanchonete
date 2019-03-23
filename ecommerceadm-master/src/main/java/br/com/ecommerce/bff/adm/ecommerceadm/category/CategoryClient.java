package br.com.ecommerce.bff.adm.ecommerceadm.category;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CategoryClient {

    @Value("${ecommerce-api.base-url}")
    private String ecommerceApiUrl;

    public List<CategoryDTO> findAll(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List> response = restTemplate.getForEntity(ecommerceApiUrl.concat("/categories"), List.class);
        return response.getBody();
    }

    public CategoryDTO findOne(Long id){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CategoryDTO> response = restTemplate.getForEntity(
                ecommerceApiUrl.concat("/categories/").concat(id.toString()), CategoryDTO.class);
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

    public boolean save(CategoryDTO categorie){
        RestTemplate restTemplate = new RestTemplate();
        try{
            System.out.println(new Gson().toJson(categorie));
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    ecommerceApiUrl.concat("/categories"), categorie, Map.class);
        }catch(HttpClientErrorException e){
            Gson gson = new Gson();
            Map map = gson.fromJson(e.getResponseBodyAsString(), Map.class);
            List<Map> errors = (List<Map>) map.get("errors");
            categorie.setErrors(errors.stream()
                .map(i -> (String) i.get("defaultMessage"))
                .collect(Collectors.toList()));
            return false;
        }
        return true;
    }

}
