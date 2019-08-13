package org.esfinge.virtuallab.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.esfinge.virtuallab.web.op.InvokeMethodHandler;
import org.esfinge.virtuallab.web.op.ListServiceClassesHandler;
import org.esfinge.virtuallab.web.op.ListServiceMethodsHandler;
import org.esfinge.virtuallab.web.op.UploadFileHandler;

/**
 * Servlet principal da aplicacao.
 */
public class FrontControllerServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private Map<String, IRequestHandler> handlerMap;

	@Override
	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);

		// inicialia o mapa de tratamento de requisicao
		this.handlerMap = new HashMap<String, IRequestHandler>();
		this.handlerMap.put("uploadFile.op", new UploadFileHandler());
		this.handlerMap.put("listClasses.op", new ListServiceClassesHandler());
		this.handlerMap.put("listMethods.op", new ListServiceMethodsHandler());
		this.handlerMap.put("invokeMethod.op", new InvokeMethodHandler());
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			// obtem a classe que trata a requisicao
			IRequestHandler handler = this.getHandler(request);
			handler.handleRequest(request, response);
		}
		catch (Exception e)
		{
			//TODO: debug..
			e.printStackTrace();
			
			// redireciona para a pagina de erro
			new ErrorRequestHandler(e.getMessage()).handleRequest(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// redireciona para o metodo doPost()
		doGet(request, response);
	}

	/**
	 * Retorna a classe responsavel em tratar a requisicao.
	 */
	private IRequestHandler getHandler(HttpServletRequest request)
	{
		// obtem a requisicao
		String requisicao = request.getRequestURI().substring(this.getServletContext().getContextPath().length() + 1);

		// obtem a classe que trata a requisicao
		IRequestHandler handler = this.handlerMap.get(requisicao);

		// redireciona para a pagina de erro caso nao exista classe que trate a
		// requisicao
		return handler != null ? handler : new ErrorRequestHandler("Opção inválida: " + requisicao);
	}

	/**
	 * Redireciona para a pagina de erro em caso de requisicoes invalidas ou erros
	 * no processamento da requisicao.
	 */
	private class ErrorRequestHandler implements IRequestHandler
	{
		// mensagem de erro
		private String errorMsg;

		public ErrorRequestHandler(String errorMsg)
		{
			this.errorMsg = errorMsg;
		}

		public void handleRequest(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException
		{
			// armazena os objetos que serao utilizados na pagina de resposta
			request.setAttribute("erro", errorMsg);

			// redireciona para a pagina de erro
			this.callPage("paginaErro.jsp", request, response);
		}
	}
}
