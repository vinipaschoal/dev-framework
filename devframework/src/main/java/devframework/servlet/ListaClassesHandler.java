package devframework.servlet;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Retorna a lista de classes carregadas. 
 */
public class ListaClassesHandler implements IRequestHandler
{
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	{
		// executa a regra de negocio
		List<String> classes = Arrays.asList("ClasseA.class","ClasseB.class", "ClasseC.class");
		
		// armazena os objetos que serao utilizados na pagina de resposta
		request.setAttribute("classes", classes);
		
		// retorna o nome da pagina de resposta
		return ( "listaClasses.jsp" );
	}
}
