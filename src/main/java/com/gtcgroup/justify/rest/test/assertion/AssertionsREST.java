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

import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

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

	private static final String HTTP_LOCALHOST_9998 = "http://localhost:9998/";

	public static List<?> assertList(final JstAssertRestPO assertRestPO) {

		try {

			return buildListResponse(assertRestPO);

		} catch (final Exception e) {
			throwAssertFailedWithMessage(HTTPMethods.GET.toString(), assertRestPO, e.getMessage());
		}
		return null;

	}

	public static <OBJECT> OBJECT assertSingle(final Class<OBJECT> responseClass, final JstAssertRestPO assertRestPO) {

		OBJECT responseInstance = null;
		try {

			responseInstance = buildSingleResponse(responseClass, assertRestPO);

		} catch (final Exception e) {
			throwAssertFailedWithMessage(HTTPMethods.GET.toString(), assertRestPO, e.getMessage());
		}

		if (null == responseInstance) {
			throwAssertFailedWithMessage(HTTPMethods.GET.toString(), assertRestPO, "The response is null.");
		}
		return responseInstance;
	}

	private static List<?> buildListResponse(final JstAssertRestPO assertRestPO) {

		List<?> responseList = null;

		final Invocation.Builder invocationBuilder = instantiateInvocationBuilder(assertRestPO);

		// TODO: Consider for future implementation.
		// invocationBuilder.header(name, value);

		if (assertRestPO.containsEnity()) {
			responseList = (List<?>) invocationBuilder.method(assertRestPO.getHttpMethod(), assertRestPO.getEntity(),
					GenericType.class);
		} else {
			responseList = (List<?>) invocationBuilder.method(assertRestPO.getHttpMethod(), Object.class);
		}

		if (null == responseList) {
			throwAssertFailedWithMessage(HTTPMethods.GET.toString(), assertRestPO, "The response is null.");
		}

		return responseList;
	}

	private static <OBJECT> OBJECT buildSingleResponse(final Class<OBJECT> responseClass,
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

		WebTarget webTarget = client.target(HTTP_LOCALHOST_9998);

		if (assertRestPO.containsWebTargetPath()) {
			webTarget = webTarget.path(assertRestPO.getWebTargetPath());
		}

		if (assertRestPO.containsQueryParam()) {

			for (final Map.Entry<String, Object[]> entry : assertRestPO.getQueryParamMap().entrySet()) {

				webTarget = webTarget.queryParam(entry.getKey(), entry.getValue());
			}
		}

		return webTarget.request(assertRestPO.getAcceptedResponseMediaTypes());
	}

	// public static <OBJECT> OBJECT assertPOST(final Class<OBJECT> returnType,
	// final JstAssertRestPO assertRestPO) {
	//
	// OBJECT values = null;
	// try {
	// values =
	// JstRestCacheHelper.retrieveJerseyTest().target().path(assertRestPO.getRequestPath()).request()
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

		if (assertRestPO.containsWebTargetPath()) {
			assertionFailedMessage.append("target path [");
			assertionFailedMessage.append(HTTP_LOCALHOST_9998 + assertRestPO.getWebTargetPath());
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
