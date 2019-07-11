package devframework.op.uploadfile;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.google.gson.JsonObject;

import devframework.services.PersistenceService;
import devframework.servlet.IJsonRequestHandler;
import devframework.utils.Utils;

/**
 * Trata as requisicoes de upload de novas classes/jar.
 */
public class UploadFileHandler implements IJsonRequestHandler
{
	public JsonObject handleAsync(HttpServletRequest request)
	{
		JsonObject jsonObject = new JsonObject();

		try
		{
			String fileName = this.saveFile(request);
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
	
	/**
	 * Processa a requisicao e salva o arquivo de upload se o mesmo for valido.
	 */
    private String saveFile(HttpServletRequest request) throws Exception
    {
    	Utils utils = Utils.getInstance();
    	
    	// Create a factory for disk-based file items
    	DiskFileItemFactory diskFactory = new DiskFileItemFactory();
        
        // Configure a repository (to ensure a secure temp location is used)
        diskFactory.setSizeThreshold(1024 * 1024 * utils.getPropertyAsInt("upload.memory_threshold", 3));
        diskFactory.setRepository(FileUtils.getTempDirectory());
 
        // Create a new file upload handler
        ServletFileUpload fileUpload = new ServletFileUpload(diskFactory);
        fileUpload.setFileSizeMax(1024 * 1024 * utils.getPropertyAsInt("upload.max_file_size", 40));
        fileUpload.setSizeMax(1024 * 1024 * utils.getPropertyAsInt("upload.max_request_size", 50));

        // Parse the request
        List<FileItem> files = fileUpload.parseRequest(request);
        if ( files.size() > 0 )
        {
    		// Process a file upload
        	FileItem item = files.get(0);
    		
    		if (!item.isFormField())
    		{
		    	// obtem o nome do arquivo
    			String fileName = FilenameUtils.getName(item.getName());
    			
    			// tenta salvar o arquivo
    			boolean saved = PersistenceService.getInstance().save(item.getInputStream(), fileName);
    			
    			if (saved)
    				return fileName;
    		}
    		
    		throw new Exception("Arquivo '" + item.getName() + "' não é uma classe/jar válido!" );
        }
        
        throw new Exception("Nenhum arquivo compatível encontrado!" );
    }
}
