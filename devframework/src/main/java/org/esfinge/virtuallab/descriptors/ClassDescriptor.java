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

	// nome qualificado da classe (pacote + nomeclasse)
	private String qualifiedName;

	
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
		this();
		
		// se informado, utiliza o label da classe; caso contrario, utiliza o nome da classe
		this.name = Utils.isNullOrEmpty(classMetadata.getLabel()) ?
				classMetadata.getClazz().getSimpleName() : classMetadata.getLabel();
		this.qualifiedName = classMetadata.getClazz().getCanonicalName();
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getQualifiedName()
	{
		return qualifiedName;
	}

	public void setQualifiedName(String qualifiedName)
	{
		this.qualifiedName = qualifiedName;
	}
}
