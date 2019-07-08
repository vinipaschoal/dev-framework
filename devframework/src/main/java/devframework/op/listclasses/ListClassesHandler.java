package devframework.op.listclasses;

import java.io.FileNotFoundException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import devframework.op.uploadfile.PersistFile;
import devframework.servlet.IJsonRequestHandler;

/**
 * Trata as requisicoes de listar as classes salvas.
 */
public class ListClassesHandler implements IJsonRequestHandler
{
	public JsonObject handleAsync(HttpServletRequest request) throws FileNotFoundException
	{
		// Fake classes list
		List<String> classesList = new PersistFile().list();

        Gson gson = new GsonBuilder().create();
        
        JsonArray jarray = gson.toJsonTree(classesList).getAsJsonArray();
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("classes", jarray);

		return jsonObject;
	}
}
