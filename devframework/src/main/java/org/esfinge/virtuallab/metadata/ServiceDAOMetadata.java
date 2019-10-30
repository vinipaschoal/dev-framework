package org.esfinge.virtuallab.metadata;

import java.util.ArrayList;
import java.util.List;

import org.esfinge.virtuallab.api.annotations.ServiceDAO;

import net.sf.esfinge.metadata.annotation.container.AnnotationProperty;
import net.sf.esfinge.metadata.annotation.container.ContainerFor;
import net.sf.esfinge.metadata.annotation.container.ContainsAnnotation;
import net.sf.esfinge.metadata.annotation.container.ElementName;
import net.sf.esfinge.metadata.annotation.container.ProcessMethods;
import net.sf.esfinge.metadata.annotation.container.ReflectionReference;
import net.sf.esfinge.metadata.container.ContainerTarget;

/**
 * Extrai informacoes de metadados de DataSource das classes de acesso a Banco de Dados.
 */
@ContainerFor(ContainerTarget.TYPE)
public class ServiceDAOMetadata implements ClassMetadata
{
	// indica se a classe contem a anotacao @ServiceDAO
	@ContainsAnnotation(ServiceDAO.class)
	private boolean serviceDAO;
	
	// metodos da classe
	@ProcessMethods
	private List<MethodMetadata> methods;

	// URL de conexao com o BD
	@AnnotationProperty(annotation = ServiceDAO.class, property = "url")
	private String url;

	// dialeto Hibernate do BD
	@AnnotationProperty(annotation = ServiceDAO.class, property = "dialect")
	private String dialect;

	// usuario para conexao com o BD 
	@AnnotationProperty(annotation = ServiceDAO.class, property = "user")
	private String user;

	// senha para conexao com o BD
	@AnnotationProperty(annotation = ServiceDAO.class, property = "password")
	private String password;

	// rotulo para a classe
	@AnnotationProperty(annotation = ServiceDAO.class, property = "label")
	private String label;

	// texto informativo sobre a classe
	@AnnotationProperty(annotation=ServiceDAO.class, property = "description")
	private String description;
	
	// classe da classe
	@ReflectionReference
	private Class<?> clazz;

	// nome da classe
	@ElementName
	private String className;
	

	/**
	 * Construtor padrao.
	 */
	public ServiceDAOMetadata()
	{
		this.methods = new ArrayList<>();
	}
	
	public boolean isServiceDAO()
	{
		return serviceDAO;
	}

	public void setServiceDAO(boolean serviceDAO)
	{
		this.serviceDAO = serviceDAO;
	}

	public List<MethodMetadata> getMethods()
	{
		return methods;
	}

	public void setMethods(List<MethodMetadata> methods)
	{
		this.methods = methods;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getDialect()
	{
		return dialect;
	}

	public void setDialect(String dialect)
	{
		this.dialect = dialect;
	}

	public String getUser()
	{
		return user;
	}

	public void setUser(String user)
	{
		this.user = user;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getLabel()
	{
		return label;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Class<?> getClazz()
	{
		return clazz;
	}

	public void setClazz(Class<?> clazz)
	{
		this.clazz = clazz;
	}

	public String getClassName()
	{
		return className;
	}

	public void setClassName(String className)
	{
		this.className = className;
	}
}
