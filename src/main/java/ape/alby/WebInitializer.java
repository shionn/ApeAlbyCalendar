package ape.alby;

import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	public void onStartup(jakarta.servlet.ServletContext servletContext) throws jakarta.servlet.ServletException {
		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Paris"));
		super.onStartup(servletContext);
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { WebMvcConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected void customizeRegistration(jakarta.servlet.ServletRegistration.Dynamic registration) {
		registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");
		super.customizeRegistration(registration);
	}

	@EnableWebMvc
	@Configuration
	@ComponentScan({ "ape.alby" })
	@RequiredArgsConstructor
	public static class WebMvcConfig implements WebMvcConfigurer {

		@Bean
		public InternalResourceViewResolver viewResolver() {
			InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
			viewResolver.setViewClass(JstlView.class);
			viewResolver.setPrefix("/WEB-INF/jsp/");
			viewResolver.setSuffix(".jsp");
			return viewResolver;
		}

		@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			registry.addResourceHandler("/css/**").addResourceLocations("/css/");
			registry.addResourceHandler("/js/**").addResourceLocations("/js/");
			registry.addResourceHandler("/fonts/**").addResourceLocations("/fonts/");
			registry.addResourceHandler("/img/**").addResourceLocations("/img/");
		}

		@Override
		public void addInterceptors(InterceptorRegistry registry) {
			registry.addInterceptor(new HandlerInterceptor() {
				@Override
				public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
						ModelAndView modelAndView) {
					if (modelAndView != null) {
						// TODO ajouter les objet fixe à la page
					}
				}
			});
		}

		@ControllerAdvice
		public static class ExceptionHandler extends ResponseEntityExceptionHandler {

			@Autowired
			private Logger logger;

			@org.springframework.web.bind.annotation.ExceptionHandler(RuntimeException.class)
			public ResponseEntity<String> excpetion(RuntimeException source, WebRequest request) {
				logger.error("on " + request.getContextPath(), source);
				return ResponseEntity.internalServerError().body(source.toString());
			}

		}


	}

	@Bean
	@Scope(value = "prototype")
	public Logger logger(InjectionPoint point) {
		return LoggerFactory.getLogger(point.getClass());
	}

}
