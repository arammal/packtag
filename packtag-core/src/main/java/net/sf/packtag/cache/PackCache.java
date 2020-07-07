/* Project pack:tag >> https://github.com/galan/packtag */
package net.sf.packtag.cache;

import net.sf.packtag.ApplicationConfiguration;
import net.sf.packtag.util.ContextConfiguration;

import javax.servlet.ServletContext;


/**
 * Singleton to the Cache
 *
 * @author Daniel Galán y Martins
 */
public class PackCache {

	private static PackCache instance;
	private CacheProvider provider;


	//private final ResourceCache resourceCache = new ResourceCache();

	public static PackCache instantiate(ApplicationConfiguration appConfiguration) {
		if (instance == null) {
			synchronized (PackCache.class) {
				if (instance == null) {
					instance = new PackCache();

					if (appConfiguration != null) {
						ContextConfiguration.setApplicationConfiguration(appConfiguration);
					}
				}
			}
		}
		return instance;
	}

	public static PackCache getInstance() {
		return instantiate(null);
	}

	public Resource getResourceByAbsolutePath(final ServletContext context, final String absolutePath) {
		return getResourceCache(context).getResourceByAbsolutePath(absolutePath);
	}


	public Resource getResourceByMappedPath(final ServletContext context, final String mappedPath) {
		return getResourceCache(context).getResourceByMappedPath(mappedPath);
	}


	public boolean existResource(final ServletContext context, final String absolutePath) {
		return getResourceCache(context).existResource(absolutePath);
	}


	public void store(final ServletContext context, final Resource resource, final boolean clearDependingCombinedResources) {
		getResourceCache(context).store(resource, clearDependingCombinedResources);
	}


	public void clearCache(final ServletContext context) {
		getResourceCache(context).clearCache();
	}


	private CacheProvider getResourceCache(final ServletContext context) {
		if (provider == null) {
			Class providerClass = ContextConfiguration.getCacheProviderClass(context);
			provider = new CacheProviderFactory().build(providerClass);
			provider.init(context);
		}
		return provider;
	}
}
