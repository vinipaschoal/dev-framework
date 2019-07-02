package devframework.servlet;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

public class FileUploadHandler implements IRequestHandler
{

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			response.setContentType("application/json;charset=UTF-8");

	        ServletOutputStream out = response.getOutputStream();
	        
	        JsonObject jsonObject = new JsonObject();
	        jsonObject.addProperty("classe", "NOSSA CLASSE");
	        jsonObject.addProperty("success", true);
	        out.print(jsonObject.toString());
	        
	        //
			return null;
		}
		catch (Exception e)
		{
			// armazena os objetos que serao utilizados na pagina de resposta
			request.setAttribute("erro", e.toString());
			
			// retorna o nome da pagina de resposta
			return ( "paginaErro.jsp" );

		}
	}
}
