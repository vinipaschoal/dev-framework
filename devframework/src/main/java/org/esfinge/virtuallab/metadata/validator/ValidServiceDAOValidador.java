package org.esfinge.virtuallab.metadata.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.List;

import javax.persistence.Entity;

import org.esfinge.virtuallab.utils.ReflectionUtils;
import org.esfinge.virtuallab.utils.Utils;

import net.sf.esfinge.metadata.AnnotationValidationException;
import net.sf.esfinge.metadata.AnnotationValidator;
import net.sf.esfinge.querybuilder.Repository;

/**
 * Valida classes anotadas com @ServiceDAO.
 */
public class ValidServiceDAOValidador implements AnnotationValidator
{

	@Override
	public void initialize(Annotation self)
	{
	}

	@Override
	public void validate(Annotation toValidate, AnnotatedElement annotated) throws AnnotationValidationException
	{
		// cast para a classe
		Class<?> clazz = (Class<?>) annotated;
		
		// verifica se eh uma interface e estende Repository (do QueryBuilder)
		if ( !clazz.isInterface() || !Repository.class.isAssignableFrom(clazz) )
			throw new AnnotationValidationException(String.format(
					"A classe '%s' deve ser uma interface que estende 'net.sf.esfinge.querybuilder.Repository' do Esfinge QueryBuilder!", clazz.getCanonicalName()));
		
		// verifica se a entidade implementa a anotacao JPA @Entity
		List<Class<?>> entityList = ReflectionUtils.getActualTypesFromGenericInterface(Repository.class, clazz);
		if (! entityList.get(0).isAnnotationPresent(Entity.class) )
			throw new AnnotationValidationException(String.format(
					"A classe '%s' deve ser uma entidade JPA anotada com 'javax.persistence.Entity'!", entityList.get(0).getCanonicalName()));
		
		// obtem os metodos declarados (por ser interface, todos sao publicos)
		Method[] methods = clazz.getDeclaredMethods();
		
		// verifica se possui metodos declarados
		if ( Utils.isNullOrEmpty(methods) )
			throw new AnnotationValidationException(String.format(
					"A interface '%s' nao possui nenhum metodo declarado!", clazz.getCanonicalName()));
		
		// verifica se os metodos utilizam tipos validos
		ValidServiceMethodValidator methodValidator = new ValidServiceMethodValidator();
		for ( Method method : methods )
			methodValidator.validate(null, method);
	}
}
