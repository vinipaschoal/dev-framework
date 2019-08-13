package org.esfinge.virtuallab.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interface para tratar requisicoes sincronas.
 */
public interface IRequestHandler {
	/**
	 * Executa a requisicao encaminhada pelo FrontControllerServlet.
	 */
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception;

	/**
	 * Redireciona para a pagina informada.
	 * 
	 * @param page nome da pagina a ser redirecionada (incluir a extensao)
	 */
	public default void callPage(String page, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/" + page).forward(request, response);
	}
}
