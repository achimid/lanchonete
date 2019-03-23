package br.com.ecommerce.bff.adm.ecommerceadm.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/** Classe de controlle, utilizada para acesso da camada View. Anotações especificas do Spring MVC.
 * Gerenciamento do redirecionamento de possiveis erros.
 * 
 * Na classe de configuração da aplicação, foi configurado que
 * ao ocorrer um erro, seriam redirecionados para as url /404, /403, /500
 * nesta classe é mapeado a pagina que será exibido quando ocorrer o erro.
 */

@Controller
public class ErrorPages {

	
	@RequestMapping("/404")
	public String notFound() {
		return "/pages/error/404";
	}

	@RequestMapping("/500")
	public String internalServerError() {
		return "/pages/error/500";
	}
	
}
