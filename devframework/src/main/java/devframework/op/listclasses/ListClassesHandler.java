package devframework.op.listclasses;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import devframework.domain.ClassDescriptor;
import devframework.services.PersistenceService;
import devframework.servlet.IJsonRequestHandler;
import devframework.utils.ClassLoaderUtils;
import devframework.utils.Utils;

/**
 * Trata as requisicoes de listar as classes salvas.
 */
public class ListClassesHandler implements IJsonRequestHandler {
	public JsonObject handleAsync(HttpServletRequest request) throws FileNotFoundException {
		//carrega todas as classes do diretorio
		
		try {
			ClassLoaderUtils.getInstance().loadAll();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// obtem a lista de classes validas
		List<ClassDescriptor> classesList = PersistenceService.getInstance().list();

		Gson gson = new GsonBuilder().create();
		JsonObject jsonObject = new JsonObject();
		
		try
		{
			JsonArray jarray = gson.toJsonTree(classesList).getAsJsonArray();
			jsonObject.add("clazzes", jarray);
			jsonObject.addProperty("message", "");
			jsonObject.addProperty("success", true);
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
