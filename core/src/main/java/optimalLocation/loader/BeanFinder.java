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

import optimalLocation.configuration.FolderSettings;
import optimalLocation.query.LocationQuery;
import optimalLocation.query.providers.CandidateProvider;
import optimalLocation.query.providers.ClientProvider;
import optimalLocation.query.providers.FacilityProvider;

public class BeanFinder {

	public static LocationQuery findLocationQuery(String jarName) throws FindLocationQueryException {
		Path jarPath = Paths.get(System.getProperty("user.dir"), FolderSettings.LOCATION_QUERY, String.format("%s.jar", jarName));
		return findClass(LocationQuery.class, jarPath);
	}
	
	public static FacilityProvider findFacilityProvider(String jarName) throws FindLocationQueryException {
		Path jarPath = Paths.get(System.getProperty("user.dir"), FolderSettings.FACILITY_PROVIDERS, String.format("%s.jar", jarName));
		return findClass(FacilityProvider.class, jarPath);
	}
	
	public static CandidateProvider findCandidateProvider(String jarName) throws FindLocationQueryException {
		Path jarPath = Paths.get(System.getProperty("user.dir"), FolderSettings.CANDIDATE_PROVIDERS, String.format("%s.jar", jarName));
		return findClass(CandidateProvider.class, jarPath);
	}
	
	public static ClientProvider findClientProvider(String jarName) throws FindLocationQueryException {
		Path jarPath = Paths.get(System.getProperty("user.dir"), FolderSettings.CLIENT_PROVIDERS, String.format("%s.jar", jarName));
		return findClass(ClientProvider.class, jarPath);
	}

	private static <T> T findClass(Class<T> classToFind, Path jarPath) throws FindLocationQueryException {
		URLClassLoader classLoader = createUrlClassLoader(jarPath);
		Optional<BeanDefinition> optionalBean = getBeanDefinition(classToFind, classLoader);
		
		String errorMessage = "Was not found a class that implements the %s interface.";
		String className = classToFind.getSimpleName();
		if (!optionalBean.isPresent()) throw new FindLocationQueryException(String.format(errorMessage, className));
		
		return instantiateBean(classLoader, optionalBean);
	}

	private static URLClassLoader createUrlClassLoader(Path jarPath) throws FindLocationQueryException {
		URL[] urls = null;
		try {
			urls= new URL[]{jarPath.toUri().toURL()};
		} catch (MalformedURLException e) {
			throw new FindLocationQueryException("Error when trying to generate the url to read the jar.", e);
		}
		
		URLClassLoader classLoader = new URLClassLoader(urls, BeanFinder.class.getClassLoader());
		return classLoader;
	}

	@SuppressWarnings("unchecked")
	private static <T> T instantiateBean(URLClassLoader classLoader, Optional<BeanDefinition> optionalBean) throws FindLocationQueryException {
		String className = optionalBean.get().getBeanClassName();
		try {
			return (T) Class.forName(className, true, classLoader).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			String message = "Error when trying to instantiate the class: " + className;
			throw new FindLocationQueryException(message, e);
		}
	}

	private static <T> Optional<BeanDefinition> getBeanDefinition(Class<T> classToFind, URLClassLoader classLoader) {
		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
		provider.addIncludeFilter(new AssignableTypeFilter(classToFind));
		provider.setResourceLoader(new PathMatchingResourcePatternResolver(classLoader));
		return provider.findCandidateComponents("").stream().findFirst();
	}
}
