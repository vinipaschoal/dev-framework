package org.esfinge.virtuallab.descriptors;

import org.esfinge.virtuallab.metadata.ClassMetadata;
import org.esfinge.virtuallab.utils.Utils;

/**
 * Descritor de classes.
 */
public class ClassDescriptor
{
	// nome da classe
	private String name;
	
	// label da classe
	private String label;

	// nome qualificado da classe (pacote + nomeclasse)
	private String qualifiedName;
	
	// texto informativo sobre a classe
	private String description;

	
	/**
	 * Construtor padrao.
	 */
	public ClassDescriptor()
	{
		
	}
	
	/**
	 * Construtor a partir dos metadados de uma classe.
	 */
	public ClassDescriptor(ClassMetadata classMetadata)
	{
		this.name = classMetadata.getClazz().getSimpleName();
		this.qualifiedName = classMetadata.getClazz().getCanonicalName();
		this.description = classMetadata.getDescription();
		
		// verifica se foi informado um label para a classe
		this.label = Utils.isNullOrEmpty(classMetadata.getLabel()) ?
				this.name : classMetadata.getLabel();
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getLabel()
	{
		return label;
	}
	
	public void setLabel(String label)
	{
		this.label = label;
	}

	public String getQualifiedName()
	{
		return qualifiedName;
	}

	public void setQualifiedName(String qualifiedName)
	{
		this.qualifiedName = qualifiedName;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
}
