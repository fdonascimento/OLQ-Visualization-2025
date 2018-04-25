package optimalLocation.loader;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.filter.AssignableTypeFilter;

import optimalLocaiton.query.LocationQuery;
import optimalLocation.configuration.FolderSettings;

public class ClassLoader {

	public static LocationQuery findLocationQuery(String jarName) throws FindLocationQueryException {
		Path path = Paths.get(System.getProperty("user.dir"), FolderSettings.LOCATION_QUERY, String.format("%s.jar", jarName));
		URL[] urls = null;
		try {
			urls= new URL[]{path.toUri().toURL()};
		} catch (MalformedURLException e) {
			throw new FindLocationQueryException("Error when trying to generate the url to read the jar.", e);
		}
		URLClassLoader classLoader = new URLClassLoader(urls, ClassLoader.class.getClassLoader());
		
		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
		provider.addIncludeFilter(new AssignableTypeFilter(LocationQuery.class));
		provider.setResourceLoader(new PathMatchingResourcePatternResolver(classLoader));
		Optional<BeanDefinition> optionalBean = provider.findCandidateComponents("").stream().findFirst();
		
		if (!optionalBean.isPresent()) throw new FindLocationQueryException("Was not found a class that implements the LocationQuery interface.");
		
		String className = optionalBean.get().getBeanClassName();
		try {
			return (LocationQuery) Class.forName(className, true, classLoader).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			String message = "Error when trying to instantiate the class: " + className;
			throw new FindLocationQueryException(message, e);
		}
	}
}
