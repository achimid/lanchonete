package br.com.ecommerce.bff.adm.ecommerceadm.base;

import org.springframework.beans.factory.annotation.Autowired;

public class BaseController{

    @Autowired
    Messages messages;

    protected String getMessage(String code) {
        return messages.get(code);
    }
}
