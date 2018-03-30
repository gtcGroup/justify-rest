package com.gtcgroup.justify.rest.test.extension;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import com.gtcgroup.justify.core.test.extension.JstBaseExtension;
import com.gtcgroup.justify.rest.test.helper.JstRestCacheHelper;

public class ConfigureTestRestExtension extends JstBaseExtension implements BeforeAllCallback, AfterAllCallback {

	private JerseyTest jerseyTest;

	private Application applicationProvider;

	public ConfigureTestRestExtension() {

	}

	// public ConfigureTestRestExtension(final Application applicationProvider) {
	// this.applicationProvider = applicationProvider;
	// }
	//
	// public ConfigureTestRestExtension(final Supplier<Application>
	// applicationSupplier) {
	// // this.applicationProvider = (unused) -> applicationSupplier.get();
	// }

	@Override
	public void afterAll(final ExtensionContext context) throws Exception {

		this.jerseyTest.tearDown();

	}

	@Override
	public void beforeAll(final ExtensionContext extensionContext) throws Exception {

		try {
			final Class<? extends JstConfigureTestRestPO> configureTestRestClassPO = initializePropertiesFromAnnotation(
					extensionContext);

			final JstConfigureTestRestPO configureTestRestInstancePO = configureTestRestClassPO.newInstance();

			this.jerseyTest = JstRestCacheHelper.initializeJerseyTest(configureTestRestInstancePO,
					TestProperties.LOG_TRAFFIC, TestProperties.DUMP_ENTITY);

		} catch (final RuntimeException runtimeException) {
			handleBeforeAllException(extensionContext, runtimeException);
		}
	}

	@Override
	protected Class<? extends JstConfigureTestRestPO> initializePropertiesFromAnnotation(
			final ExtensionContext extensionContext) {

		final JstConfigureTestREST configureTestREST = (JstConfigureTestREST) retrieveAnnotation(
				extensionContext.getRequiredTestClass(), JstConfigureTestREST.class);

		// Values from Annotation.
		return configureTestREST.configureTestRestPO();
	}

}
