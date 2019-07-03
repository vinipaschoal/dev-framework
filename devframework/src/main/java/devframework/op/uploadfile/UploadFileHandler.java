package devframework.op.uploadfile;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.JsonObject;

import devframework.servlet.IJsonRequestHandler;

/**
 * Trata as requisicoes de upload de novas classes.
 */
public class UploadFileHandler implements IJsonRequestHandler
{
	public JsonObject handleAsync(HttpServletRequest request)
	{
		JsonObject jsonObject = new JsonObject();

		try
		{
			String fileName = new PersistFile().save(request);
			jsonObject.addProperty("classe", fileName);
			jsonObject.addProperty("success", true);
		}
		catch ( Exception e )
		{
			e.printStackTrace();			
			jsonObject.addProperty("success", false);
		}
		
		return jsonObject;	
	}
}
