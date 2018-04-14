/*
 * [Licensed per the Open Source "MIT License".]
 *
 * Copyright (c) 2006 - 2018 by
 * Global Technology Consulting Group, Inc. at
 * http://gtcGroup.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.gtcgroup.justify.rest.test.assertion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.opentest4j.AssertionFailedError;

import com.sun.research.ws.wadl.HTTPMethods;

/**
 * This Assertions class provides convenience methods for assertion processing.
 *
 * <p style="font-family:Verdana; font-size:10px; font-style:italic">
 * Copyright (c) 2006 - 2018 by Global Technology Consulting Group, Inc. at
 * <a href="http://gtcGroup.com">gtcGroup.com </a>.
 * </p>
 *
 * @author Marvin Toll
 * @since v3.0
 */
public enum AssertionsREST {

	INSTANCE;

	public static List<?> assertList(final JstAssertRestPO assertRestPO) {

		try {

			return buildResponseList(assertRestPO);

		} catch (final Exception e) {
			throwAssertFailedWithMessage(HTTPMethods.GET.toString(), assertRestPO, e.getMessage()); // Tested
		}
		return null;

	}

	/**
	 * This method supports status code processing for {@link Exception}s. A
	 * {@link Boolean} value is returned if a {@link WebApplicationException} is
	 * thrown indicating a valid status code as defined in the
	 * {@link JstAssertRestPO}.
	 *
	 * @return {@link Object}
	 */
	@SuppressWarnings("unchecked")
	public static <OBJECT> OBJECT assertSingle(final Class<OBJECT> responseClass, final JstAssertRestPO assertRestPO) {

		OBJECT responseInstance = null;
		try {

			responseInstance = buildResponseSingle(responseClass, assertRestPO);

		} catch (final Exception e) {

			if (isWebApplicationException(assertRestPO, e)) {

				return (OBJECT) Boolean.TRUE;
			}

			throwAssertFailedWithMessage(assertRestPO.getHttpMethod().toString(), assertRestPO, e.getMessage());
		}

		return responseInstance;
	}

	private static List<?> buildResponseList(final JstAssertRestPO assertRestPO) {

		List<?> responseList = new ArrayList<>();

		final Invocation.Builder invocationBuilder = instantiateInvocationBuilder(assertRestPO);

		// TODO: Consider for future implementation.
		// invocationBuilder.header(name, value);

		if (assertRestPO.containsEnity()) {
			responseList = (List<?>) invocationBuilder.method(assertRestPO.getHttpMethod(), assertRestPO.getEntity(),
					GenericType.class);
		} else {
			responseList = (List<?>) invocationBuilder.method(assertRestPO.getHttpMethod(), Object.class);
		}

		return responseList;
	}

	private static <OBJECT> OBJECT buildResponseSingle(final Class<OBJECT> responseClass,
			final JstAssertRestPO assertRestPO) {

		final Invocation.Builder invocationBuilder = instantiateInvocationBuilder(assertRestPO);

		if (assertRestPO.containsEnity()) {
			return invocationBuilder.method(assertRestPO.getHttpMethod(), assertRestPO.getEntity(), responseClass);
		}

		return invocationBuilder.method(assertRestPO.getHttpMethod(), responseClass);
	}

	private static Invocation.Builder instantiateInvocationBuilder(final JstAssertRestPO assertRestPO) {
		final ClientConfig clientConfig = assertRestPO.getClientConfig();

		for (final Class<?> providerClass : assertRestPO.getProviderForRegistrationList()) {

			clientConfig.register(providerClass);
		}

		final Client client = ClientBuilder.newClient(clientConfig);

		// TODO: Consider for future implementation.
		// client.register(componentClass);

		WebTarget webTarget = client.target(assertRestPO.getTargetURI());

		if (assertRestPO.containsPath()) {
			webTarget = webTarget.path(assertRestPO.getPath());
		}

		if (assertRestPO.containsQueryParam()) {

			for (final Map.Entry<String, Object[]> entry : assertRestPO.getQueryParamMap().entrySet()) {

				webTarget = webTarget.queryParam(entry.getKey(), entry.getValue());
			}
		}

		return webTarget.request(assertRestPO.getAcceptedResponseMediaTypes());
	}

	private static boolean isWebApplicationException(final JstAssertRestPO assertRestPO, final Exception e) {

		if (e instanceof WebApplicationException) {

			final WebApplicationException webApplicationException = (WebApplicationException) e;

			// TODO: Take a look at this.
			@SuppressWarnings("resource")
			final Response response = webApplicationException.getResponse();

			if (assertRestPO.containsValidHttpStatusCodes()) {

				for (final int statusCode : assertRestPO.getValidHttpStatusCodes()) {

					if (statusCode == response.getStatus()) {

						response.close();
						return true;
					}
				}
			}
		}
		return false;
	}

	// public static <OBJECT> OBJECT assertPOST(final Class<OBJECT> returnType,
	// final JstAssertRestPO assertRestPO) {
	//
	// OBJECT values = null;
	// try {
	// values =
	// JstRestUtilHelper.retrieveJerseyTest().target().path(assertRestPO.getRequestPath()).request()
	// .post(returnType);
	//
	// target("customers").request().post(Entity.entity(joe,
	// MediaType.APPLICATION_JSON), String.class);
	//
	// assertEquals(ValuesIC.DEFAULT_VALUES, values);
	// } catch (final Exception e) {
	// throwAssertFailedWithMessage(HTTPMethods.GET.toString(), e.getMessage());
	// }
	// return values;
	// }

	private static void throwAssertFailedWithMessage(final String methodType, final JstAssertRestPO assertRestPO,
			final String message) {

		final StringBuilder assertionFailedMessage = new StringBuilder();

		assertionFailedMessage.append("The [");
		assertionFailedMessage.append(methodType);
		assertionFailedMessage.append("] method with ");

		if (assertRestPO.containsPath()) {
			assertionFailedMessage.append("target path [");
			assertionFailedMessage.append(assertRestPO.getTargetURI() + assertRestPO.getPath());
			assertionFailedMessage.append("] and ");
		}

		assertionFailedMessage.append("response type [");

		for (final String mediaTypes : assertRestPO.getAcceptedResponseMediaTypes()) {
			assertionFailedMessage.append(mediaTypes);
		}

		assertionFailedMessage.append("] resulted in an exception [");
		assertionFailedMessage.append(message);
		assertionFailedMessage.append("].");

		throw new AssertionFailedError(assertionFailedMessage.toString());
	}
}
