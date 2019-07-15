package devframework.op.listmethods;

import java.io.FileNotFoundException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import devframework.domain.ClassDescriptor;
import devframework.services.PersistenceService;
import devframework.servlet.IJsonRequestHandler;

/**
 * Trata as requisicoes de listar os metodos das classes salvas.
 */
public class ListMethodsHandler implements IJsonRequestHandler {
	public JsonObject handleAsync(HttpServletRequest request) throws FileNotFoundException {
		
		String rClass = request.getParameter("class");

		Gson gson = new GsonBuilder().create();
		JsonObject jsonObject = new JsonObject();
		
		try
		{
			// obtem a lista de classes validas
			ClassDescriptor classDescriptor = PersistenceService.getInstance().getClass(rClass);
			
			//JsonArray jarray = gson.toJsonTree(classesList).getAsJsonArray();
			//jsonObject.add("classes", jarray);
			jsonObject.addProperty("message", classDescriptor != null ? "" : "Classe pesquisada n�o existe.");
			jsonObject.addProperty("success", classDescriptor != null ? true : false);
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			jsonObject.addProperty("message", "Erro: " + e.toString());
			jsonObject.addProperty("success", false);
		}

		return jsonObject;
	}
}