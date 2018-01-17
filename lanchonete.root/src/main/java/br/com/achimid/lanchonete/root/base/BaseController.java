package br.com.achimid.lanchonete.root.base;

import com.google.gson.Gson;
import org.codehaus.groovy.runtime.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class BaseController<T extends BaseDTO>{

    @Value("${api.base-url}")
    protected String URL_API;

    @Autowired
    Messages messages;

    protected String getMessage(String code) {
        return messages.get(code);
    }

    public List<T> findAll(String urlPath){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List> response = restTemplate.getForEntity(URL_API.concat(urlPath), List.class);
        return response.getBody();
    }


    public T findOne(String urlPath, Class clazz, Long id){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<T> response = restTemplate.getForEntity(URL_API.concat(urlPath).concat("/").concat(id.toString()), clazz);
        return response.getBody();
    }

    public boolean delete(String urlPath, Long id){
        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.delete(URL_API.concat(urlPath).concat("/").concat(id.toString()), Map.class);
        }catch(Exception e){
            return false;
        }
        return true;
    }

    public boolean save(String urlPath, T obj){
        RestTemplate restTemplate = new RestTemplate();
        try{
            System.out.println(new Gson().toJson(obj));
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    URL_API.concat(urlPath), obj, Map.class);
        }catch(HttpClientErrorException e){
            this.preparaErro(e, obj);
            return false;
        }
        return true;
    }

    private void preparaErro(Exception e, T obj){
        Gson gson = new Gson();
        Map map = gson.fromJson(e.getMessage(), Map.class);
        List<Map> errors = (List<Map>) map.get("errors");
            obj.setErrors(errors.stream()
                    .map(i -> (String) i.get("defaultMessage"))
                    .collect(Collectors.toList()));
    }

    private void preparaErro(HttpClientErrorException e, T obj){
        Gson gson = new Gson();
        Map map = gson.fromJson(e.getResponseBodyAsString(), Map.class);
        List<Map> errors = (List<Map>) map.get("errors");
        obj.setErrors(errors.stream()
                .map(i -> (String) i.get("defaultMessage"))
                .collect(Collectors.toList()));
    }
}
