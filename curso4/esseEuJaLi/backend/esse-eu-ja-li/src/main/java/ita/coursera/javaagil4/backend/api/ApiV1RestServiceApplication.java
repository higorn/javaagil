package ita.coursera.javaagil4.backend.api;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/v1")
public class ApiV1RestServiceApplication  extends Application {

	@Inject
	private AccountRestService accountRestService;
	private Set<Object> singletons = new HashSet<Object>();
	 
	public ApiV1RestServiceApplication() {
	}

	@PostConstruct
	public void init() {
		singletons.add(accountRestService);
	}
 
	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}

	@Override
	public Set<Class<?>> getClasses() {
		return null;
	}
}
