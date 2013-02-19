package pl.styall.library.core.ext;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.LocaleResolver;

public class LocaleConfigurerFilter extends OncePerRequestFilter {

	private LocaleResolver localeResolver;
	
	protected void initFilterBean() throws ServletException {
		WebApplicationContext wac = WebApplicationContextUtils
				.getRequiredWebApplicationContext(getServletContext());
		Map<String, LocaleResolver> resolvers = wac.getBeansOfType(LocaleResolver.class);
		if (resolvers.size() == 1) {
			localeResolver = (LocaleResolver) resolvers.values().iterator().next();
		}
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		if (localeResolver != null) {
			Locale locale = localeResolver.resolveLocale(request);
			LocaleContextHolder.setLocale(locale);
		}

		chain.doFilter(request, response);

		if (localeResolver != null) {
			LocaleContextHolder.resetLocaleContext();
		}
	}

}
