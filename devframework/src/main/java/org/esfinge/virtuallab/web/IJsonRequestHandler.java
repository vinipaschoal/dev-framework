package org.esfinge.virtuallab.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;

/**
 * Interface para tratar requisicoes assincronas.
 */
public interface IJsonRequestHandler extends IRequestHandler {
	/**
	 * Executa a requisicao encaminhada pelo FrontControllerServlet.
	 */
	public default void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// executa a logica da funcionalidade requisitada e obtem o objeto JSON de
		// resposta
		JsonObject jsonObj = this.handleAsync(request);

		// seta o retorno como sendo do tipo JSON
		response.setContentType("application/json;charset=UTF-8");

		// retorna o objeto JSON para a pagina processa-lo de forma assincrona
		response.getOutputStream().print(jsonObj.toString());
	}

	/**
	 * Operacao nao suportada por handlers assincronos! Lanca uma excecao
	 * UnsupportedOperationException caso este metodo seja invocado.
	 */
	public default void callPage(String page, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Page redirect is not allowed to asynchronous handlers!");
	}
	
	/**
	 * Retorna o objeto JSON enviado como parametro na requisicao.
	 */
	public default JsonNode getJsonParameter(HttpServletRequest request) throws Exception
	{
		// obtem o objeto JSON do request
		BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));			
		String jsonString = reader.readLine();

		ObjectMapper mapper = new ObjectMapper();
		
		return mapper.readTree(jsonString);
	}
	
	/**
	 * Retorna o objeto JSON enviado como parametro na requisicao no formato da classe informada.
	 */
	public default <T> T getJsonParameter(HttpServletRequest request, Class<T> typeClass) throws Exception
	{
		// obtem o objeto JSON do request
		BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));			
		String jsonString = reader.readLine();

		ObjectMapper mapper = new ObjectMapper();
		
		return mapper.readValue(jsonString, typeClass);
	}

	/**
	 * Executa a logica da funcionalidade requisitada. Deve retornar um objeto JSON
	 * para ser enviado para a pagina solicitante processa-lo assincronamente.
	 */
	public JsonObject handleAsync(HttpServletRequest request) throws ServletException, IOException;
}
