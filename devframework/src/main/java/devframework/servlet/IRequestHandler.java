package devframework.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interface padrao para tratar requisicoes da pagina. 
 */
public interface IRequestHandler
{
	/**
	 * Executa a requisicao solicitada ao FrontControllerServlet. 
	 *  
	 * @return a pagina de resposta à requisicao solicitada
	 */
	public String handleRequest(HttpServletRequest request, HttpServletResponse response);
}
