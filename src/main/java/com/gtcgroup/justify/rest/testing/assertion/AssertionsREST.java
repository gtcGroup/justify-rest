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

package com.gtcgroup.justify.rest.testing.assertion;

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

import com.gtcgroup.justify.rest.helper.internal.LogRestUtilHelper;

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

	/**
	 * This method supports status code processing for {@link Exception}s. A
	 * {@link Boolean} value is returned if a {@link WebApplicationException} is
	 * thrown indicating a valid status code was returned as defined in the
	 * {@link JstAssertRestPO}.
	 *
	 * @return {@link List} or empty list if the I/O Controller returns no response.
	 */
	@SuppressWarnings("unchecked")
	public static List<Object> assertList(final JstAssertRestPO assertRestPO) {

		try {

			return (List<Object>) buildResponseList(assertRestPO);

		} catch (final Exception e) {
			throwAssertFailedWithMessage("GET", assertRestPO, null, e.getMessage()); // Tested
		}
		return new ArrayList<>();

	}

	/**
	 * This method supports status code processing for {@link Exception}s. A
	 * {@link Boolean} value is returned if a {@link WebApplicationException} is
	 * thrown indicating a valid status code was returned as defined in the
	 * {@link JstAssertRestPO}.
	 *
	 * @return {@link Object} or null.
	 */
	@SuppressWarnings("resource")
	public static <OBJECT> OBJECT assertSingle(final Class<OBJECT> entityType, final JstAssertRestPO assertRestPO) {

		final StringBuilder message = new StringBuilder();
		Response responseSingle = null;
		OBJECT entityReturned = null; // for return

		try {

			responseSingle = buildResponseSingle(assertRestPO);

			boolean validStatusCode = false;

			for (final int statusCode : assertRestPO.getValidHttpStatusCodes()) {

				if (statusCode == responseSingle.getStatus()) {
					validStatusCode = true;
					break;
				}
			}

			if (!validStatusCode) {
				throwAssertFailedWithMessage(assertRestPO.getHttpMethod(), assertRestPO, entityType,
						"The response status code [" + responseSingle.getStatus() + "] is not valid.");
			}

			if (null != responseSingle) {

				entityReturned = responseSingle.readEntity(entityType);
			}

		} catch (final Exception e) {

			throwAssertFailedWithMessage(assertRestPO.getHttpMethod(), assertRestPO, entityType, e.getMessage());

		} finally {

			try {
				if (assertRestPO.isRequestLogging()) {
					message.append(LogRestUtilHelper.formatRequestMessage());
				}
				if (assertRestPO.isResponseLogging()) {
					message.append(LogRestUtilHelper.formatResponseMessage(responseSingle, entityReturned,
							JstAssertRestPO.getMaxEntityLoggingSize()));
				}
				System.out.println(message.toString());
			} finally {
				if (null != responseSingle) {
					responseSingle.close();
				}
			}
		}
		if (null == entityReturned || "".equals(entityReturned)) {
			return null;
		}
		return entityReturned;
	}

	private static List<?> buildResponseList(final JstAssertRestPO assertRestPO) {

		final Invocation.Builder invocationBuilder = instantiateInvocationBuilder(assertRestPO);

		List<?> responseList;

		if (assertRestPO.containsResponseEnity()) {
			responseList = (List<?>) invocationBuilder.method(assertRestPO.getHttpMethod(),
					assertRestPO.getResponseEntity(), GenericType.class);
		} else {
			responseList = (List<?>) invocationBuilder.method(assertRestPO.getHttpMethod(), Object.class);
		}

		return responseList;
	}

	@SuppressWarnings("resource")
	private static Response buildResponseSingle(final JstAssertRestPO assertRestPO) {

		final Invocation.Builder invocationBuilder = instantiateInvocationBuilder(assertRestPO);

		Response responseSingle;

		if (assertRestPO.containsResponseEnity()) {
			responseSingle = invocationBuilder.method(assertRestPO.getHttpMethod(), assertRestPO.getResponseEntity());
		} else {

			responseSingle = invocationBuilder.method(assertRestPO.getHttpMethod());
		}
		return responseSingle;
	}

	private static Invocation.Builder instantiateInvocationBuilder(final JstAssertRestPO assertRestPO) {
		final ClientConfig clientConfig = assertRestPO.getClientConfig();

		for (final Class<?> providerClass : assertRestPO.getProviderForRegistrationList()) {

			clientConfig.register(providerClass);
		}

		final Client client = ClientBuilder.newClient(clientConfig);

		WebTarget webTarget = client.target(assertRestPO.getTargetURI());

		if (assertRestPO.containsPath()) {
			webTarget = webTarget.path(assertRestPO.getPath());
		}

		if (assertRestPO.containsQueryParam()) {

			for (final Map.Entry<String, Object[]> entry : assertRestPO.getQueryParamMap().entrySet()) {

				webTarget = webTarget.queryParam(entry.getKey(), entry.getValue());
			}
		}

		return webTarget.request(assertRestPO.getSpecifiedResponseMediaTypes());
	}

	private static void throwAssertFailedWithMessage(final String methodType, final JstAssertRestPO assertRestPO,
			final Class<?> entityType, final String message) {

		final StringBuilder failedMessage = new StringBuilder();

		failedMessage.append("The [");
		failedMessage.append(methodType);
		failedMessage.append("] method with ");

		if (assertRestPO.containsPath()) {
			failedMessage.append("target path [");
			failedMessage.append(assertRestPO.getTargetURI() + assertRestPO.getPath());
			failedMessage.append("] and ");
		}

		failedMessage.append("content type [");

		for (final String mediaTypes : assertRestPO.getSpecifiedResponseMediaTypes()) {
			failedMessage.append(mediaTypes);
		}

		if (null != entityType) {
			failedMessage.append("] and entity type [");
			failedMessage.append(entityType.getSimpleName());
		}

		failedMessage.append("]\n\t\t\tresulted in an exception [");
		failedMessage.append(message);
		failedMessage.append("].");

		throw new AssertionFailedError(failedMessage.toString());
	}
}
