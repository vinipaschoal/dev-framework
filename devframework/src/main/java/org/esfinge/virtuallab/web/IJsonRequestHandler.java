package org.esfinge.virtuallab.web;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.esfinge.virtuallab.utils.JsonUtils;

/**
 * Interface para tratar requisicoes assincronas.
 */
public interface IJsonRequestHandler extends IRequestHandler {
	/**
	 * Executa a requisicao encaminhada pelo FrontControllerServlet.
	 */
	public default void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		// executa a logica da funcionalidade requisitada 
		// e obtem o objeto JSON de resposta
		JsonReturn jsonReturn = this.handleAsync(request);

		// seta o retorno como sendo do tipo JSON
		response.setContentType("application/json;charset=UTF-8");

		// retorna o objeto JSON para a pagina processa-lo de forma assincrona
		response.getOutputStream().print(JsonUtils.stringify(jsonReturn));
	}

	/**
	 * Operacao nao suportada por handlers assincronos! Lanca uma excecao
	 * UnsupportedOperationException caso este metodo seja invocado.
	 */
	public default void callPage(String page, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedOperationException 
	{
		throw new UnsupportedOperationException("Page redirect is not allowed to asynchronous handlers!");
	}
	
	/**
	 * Retorna a string representando o objeto JSON enviado como parametro na requisicao.
	 */
	@SuppressWarnings("deprecation")
	public default String getJsonParameter(HttpServletRequest request) throws Exception
	{
		return IOUtils.readLines(request.getInputStream()).stream().collect(Collectors.joining(" "));
	}

	/**
	 * Executa a logica da funcionalidade requisitada. Deve retornar um objeto JSON
	 * para ser enviado para a pagina solicitante processa-lo assincronamente.
	 */
	public JsonReturn handleAsync(HttpServletRequest request) throws Exception;
}
