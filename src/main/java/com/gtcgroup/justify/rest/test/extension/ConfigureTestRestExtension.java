package com.gtcgroup.justify.rest.test.extension;

import org.glassfish.jersey.test.spi.TestContainer;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import com.gtcgroup.justify.core.test.extension.JstBaseExtension;
import com.gtcgroup.justify.rest.test.helper.JstRestCacheHelper;

public class ConfigureTestRestExtension extends JstBaseExtension implements BeforeAllCallback, AfterAllCallback {

	private TestContainer testContainer;

	@Override
	public void afterAll(final ExtensionContext context) throws Exception {

		this.testContainer.stop();
	}

	@Override
	public void beforeAll(final ExtensionContext extensionContext) throws Exception {

		try {
			final Class<? extends JstConfigureTestRestPO> configureTestRestClassPO = initializePropertiesFromAnnotation(
					extensionContext);

			final JstConfigureTestRestPO configureTestRestInstancePO = configureTestRestClassPO.newInstance();

			this.testContainer = JstRestCacheHelper.initializeTestContainer(configureTestRestInstancePO);

		} catch (final RuntimeException runtimeException) {
			handleBeforeAllException(extensionContext, runtimeException); // Tested
		}
	}

	@Override
	protected Class<? extends JstConfigureTestRestPO> initializePropertiesFromAnnotation(
			final ExtensionContext extensionContext) {

		final JstConfigureTestREST configureTestREST = (JstConfigureTestREST) retrieveAnnotation(
				extensionContext.getRequiredTestClass(), JstConfigureTestREST.class);

		// Retrieve values from annotation.
		return configureTestREST.configureTestRestPO();
	}

}
