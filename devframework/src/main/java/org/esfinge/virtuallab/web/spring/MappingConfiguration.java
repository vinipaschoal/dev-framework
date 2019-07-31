package org.esfinge.virtuallab.web.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.PathResourceResolver;

/**
 * Classe de configuracao do Spring Framework.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "org.esfinge.virtuallab.*")
public class MappingConfiguration extends WebMvcConfigurerAdapter
{
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry)
	{
		// mapeamento para os recursos do Spring WebJars
		if (!registry.hasMappingForPattern("/webjars/**"))
			registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");

		// mapeamento para os recursos da aplicacao (figuras, css, js..)
		if (!registry.hasMappingForPattern("/resources/**"))
			registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/statics/")
					.setCachePeriod(3600).resourceChain(true).addResolver(new PathResourceResolver());

		registry.setOrder(1);
	}
}
