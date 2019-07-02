package devframework.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet principal da aplicacao.
 */
public class FrontControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Map<String, IRequestHandler> handlerMap;
	
	
	@Override
	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
		
		// inicialia o mapa de tratamento de requisicao
		this.handlerMap = new HashMap<String, IRequestHandler>();
		this.handlerMap.put("opListaClasses", new ListaClassesHandler());
		this.handlerMap.put("opUploadFile", new FileUploadHandler());
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// obtem a classe que trata a requisicao
		IRequestHandler handler = this.getHandler(request);
		
		// executa a requisicao
		String paginaRetorno = handler.handleRequest(request, response);
		
		// redireciona para a pagina de retorno
		if ( paginaRetorno != null )
			request.getRequestDispatcher("/" + paginaRetorno).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
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
		
		// redireciona para a pagina de erro caso nao exista classe que trate a requisicao
		return handler != null ? handler : new OpcaoInvalidaHandler(requisicao);
	}

	
	/**
	 * Redireciona para a pagina de erro em caso de requisicoes invalidas.
	 */
	private class OpcaoInvalidaHandler implements IRequestHandler
	{
		private String requisicao;
		
		public OpcaoInvalidaHandler(String requisicao)
		{
			this.requisicao = requisicao;
		}
		
		public String handleRequest(HttpServletRequest request, HttpServletResponse response)
		{
			// armazena os objetos que serao utilizados na pagina de resposta
			request.setAttribute("erro", "Opção inválida: " + requisicao);
			
			// retorna o nome da pagina de resposta
			return ( "paginaErro.jsp" );
		}
	}
}


