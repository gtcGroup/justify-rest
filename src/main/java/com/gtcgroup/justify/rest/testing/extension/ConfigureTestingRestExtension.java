package com.gtcgroup.justify.rest.testing.extension;

import org.glassfish.jersey.test.spi.TestContainer;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import com.gtcgroup.justify.core.testing.extension.JstBaseTestingExtension;
import com.gtcgroup.justify.rest.testing.helper.JstRestUtilHelper;

public class ConfigureTestingRestExtension extends JstBaseTestingExtension
		implements BeforeAllCallback, AfterAllCallback {

	private TestContainer testContainer;

	@Override
	public void afterAll(final ExtensionContext context) throws Exception {

		this.testContainer.stop();
	}

	@Override
	public void beforeAll(final ExtensionContext extensionContext) throws Exception {

		try {
			final Class<? extends JstConfigureTestingRestPO> configureTestRestClassPO = initializePropertiesFromAnnotation(
					extensionContext);

			final JstConfigureTestingRestPO configureTestRestInstancePO = configureTestRestClassPO.newInstance();

			this.testContainer = JstRestUtilHelper.initializeTestContainer(configureTestRestInstancePO);

		} catch (final RuntimeException runtimeException) {
			handleBeforeAllException(extensionContext, runtimeException); // Tested
		}
	}

	@Override
	protected Class<? extends JstConfigureTestingRestPO> initializePropertiesFromAnnotation(
			final ExtensionContext extensionContext) {

		final JstConfigureTestingREST configureTestREST = (JstConfigureTestingREST) retrieveAnnotation(
				extensionContext.getRequiredTestClass(), JstConfigureTestingREST.class);

		// Retrieve values from annotation.
		return configureTestREST.configureTestingRestPO();
	}

}
