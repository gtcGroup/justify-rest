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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

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

	private static final String HTTP_LOCALHOST_9998 = "http://localhost:9998/";

	@SuppressWarnings({ "unchecked" })
	public static <TO> TO assertListGET(final JstAssertRestPO assertRestPO) {

		try {
			final List<?> actualResponseList = buildListResponse(assertRestPO);

			if (assertRestPO.containsExpectedResponseList()) {
				assertEquals(assertRestPO.getExpectedResponseList().toArray(), actualResponseList.toArray());
			}

			return (TO) actualResponseList;

		} catch (final Exception e) {
			throwAssertFailedWithMessage(HTTPMethods.GET.toString(), assertRestPO, e.getMessage());
		}
		return null;

	}

	@SuppressWarnings("unchecked")
	public static <TO> TO assertSingleGET(final Class<TO> transferObjectClass, final JstAssertRestPO assertRestPO) {

		TO transferObject = null;
		try {

			transferObject = buildSingleResponse(transferObjectClass, assertRestPO);

		} catch (final Exception e) {
			throwAssertFailedWithMessage(HTTPMethods.GET.toString(), assertRestPO, e.getMessage());
		}

		if (null == transferObject) {
			throwAssertFailedWithMessage(HTTPMethods.GET.toString(), assertRestPO, "The response is null.");
		}
		return transferObject;
	}

	@SuppressWarnings("resource")
	private static List<?> buildListResponse(final JstAssertRestPO assertRestPO) {

		final ClientConfig clientConfig = assertRestPO.getClientConfig();

		for (final Class<Object> filter : assertRestPO.getClientFilterList()) {

			clientConfig.register(filter);
		}

		final Client client = ClientBuilder.newClient(clientConfig);

		// TODO: Consider for future implementation.
		// client.register(componentClass);

		WebTarget webTarget = client.target(HTTP_LOCALHOST_9998);

		if (assertRestPO.containsWebTargetPath()) {
			webTarget = webTarget.path(assertRestPO.getWebTargetPath());
		}

		if (assertRestPO.containsQueryParam()) {
			webTarget = webTarget.queryParam(assertRestPO.getQueryParamName(), assertRestPO.getQueryParamValues());
		}

		final Invocation.Builder invocationBuilder = webTarget.request(assertRestPO.getAcceptedResponseMediaTypes());

		// TODO: Consider for future implementation.
		// invocationBuilder.header(name, value);

		final Response response = invocationBuilder.get();
		List<?> responseList = null;

		if (null == response) {
			throwAssertFailedWithMessage(HTTPMethods.GET.toString(), assertRestPO, "The response is null.");
		} else {

			responseList = response.readEntity(new GenericType<List<?>>() {
				// Empty Block
			});
		}

		return responseList;
	}

	private static <TO> TO buildSingleResponse(final Class<TO> transferObjectClass,
			final JstAssertRestPO assertRestPO) {

		final ClientConfig clientConfig = assertRestPO.getClientConfig();

		for (final Class<Object> filter : assertRestPO.getClientFilterList()) {

			clientConfig.register(filter);
		}

		final Client client = ClientBuilder.newClient(clientConfig);

		WebTarget webTarget = client.target(HTTP_LOCALHOST_9998);

		if (assertRestPO.containsWebTargetPath()) {
			webTarget = webTarget.path(assertRestPO.getWebTargetPath());
		}

		if (assertRestPO.containsQueryParam()) {
			webTarget = webTarget.queryParam(assertRestPO.getQueryParamName(), assertRestPO.getQueryParamValues());
		}

		final Invocation.Builder invocationBuilder = webTarget.request(assertRestPO.getAcceptedResponseMediaTypes());

		return invocationBuilder.get(transferObjectClass);
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

		assertionFailedMessage.append("return type [");

		for (final String mediaTypes : assertRestPO.getAcceptedResponseMediaTypes()) {
			assertionFailedMessage.append(mediaTypes);
		}

		assertionFailedMessage.append("] resulted in an exception [");
		assertionFailedMessage.append(message);
		assertionFailedMessage.append("].");

		throw new AssertionFailedError(assertionFailedMessage.toString());
	}
}
