package br.com.achimid.lanchonete.root.home;

import br.com.achimid.lanchonete.root.base.Util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.NestedServletException;

import javax.servlet.http.HttpServletRequest;

/** Classe de controlle, utilizada para acesso da camada View. Anotações especificas do Spring MVC.
 * Gerenciamento do redirecionamento de possiveis erros.
 * 
 * Na classe de configuração da aplicação, foi configurado que
 * ao ocorrer um erro, seriam redirecionados para as url /404, /403, /500
 * nesta classe é mapeado a pagina que será exibido quando ocorrer o erro.
 */

@Controller
public class ErrorPages {

	private String ATTR_EXCEPTION_MSG = "javax.servlet.error.message";
	private String ATTR_EXCEPTION_STACK = "javax.servlet.error.exception";
	
	@RequestMapping("/404")
	public String notFound() {
		return "/pages/error/404";
	}

	@RequestMapping("/500")
	public ModelAndView internalServerError(HttpServletRequest httpRequest) {
		ModelAndView errorPage = new ModelAndView("/pages/error/500");

		try {
			errorPage.addObject("erroStack", getStackException(httpRequest));
			errorPage.addObject("erroMsg", getMessageException(httpRequest));
		}catch (Exception e){
			e.printStackTrace();
		}
		return errorPage;
	}

	private String getMessageException(HttpServletRequest httpRequest) {
		return (String)httpRequest.getAttribute(ATTR_EXCEPTION_MSG);
	}

	private String getStackException(HttpServletRequest httpRequest) {
		NestedServletException obj = (NestedServletException) httpRequest.getAttribute(ATTR_EXCEPTION_STACK);
		return (String) Util.getInstance().exceptionToString(obj);
	}
	
}
