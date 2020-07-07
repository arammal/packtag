package net.sf.packtag;

public abstract class ApplicationConfiguration {

	public boolean isEmbeddedResourcesEnabled() {
		return false;
	}

	public String getEmbeddedResourcesContainer() {
		return "META-INF/resources";
	}
}
