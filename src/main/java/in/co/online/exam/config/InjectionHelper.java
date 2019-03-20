package in.co.online.exam.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public final class InjectionHelper implements ApplicationContextAware {

	private static final InjectionHelper INSTANCE = new InjectionHelper();
	public static ApplicationContext applicationContext;

	public InjectionHelper() {
	}

	public static void autowire(Object classToAutowire, Object... beansToAutowireInClass) {
		for (Object bean : beansToAutowireInClass) {
			if (bean == null) {
				applicationContext.getAutowireCapableBeanFactory().autowireBean(classToAutowire);
			}
		}
	}

	@Override
	public void setApplicationContext(
			final ApplicationContext applicationContext) {
		InjectionHelper.applicationContext = applicationContext;
	}


	public static InjectionHelper getInstance() {
		return INSTANCE;
	}

	public static <T extends Object> T getBean(Class<T> classToGet) {
		return classToGet.cast(applicationContext.getBean(classToGet));
	}

	public static Object getBean(String classToGet) {
		return applicationContext.getBean(classToGet);
	}

}
