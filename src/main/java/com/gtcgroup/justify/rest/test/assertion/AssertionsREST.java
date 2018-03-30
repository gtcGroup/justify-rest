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

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.test.JerseyTest;
import org.opentest4j.AssertionFailedError;

import com.gtcgroup.justify.rest.test.helper.JstRestCacheHelper;
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

	public static <OBJECT> OBJECT assertGET(final Class<OBJECT> returnType, final JstAssertRestPO assertRestPO) {

		OBJECT response = null;
		try {
			if (true) {

				response = buildSingleResponse(returnType, assertRestPO);

			} else {

				final JerseyTest jerseyTest = JstRestCacheHelper.retrieveJerseyTest();

				final List<OBJECT> items = jerseyTest.target().request(MediaType.APPLICATION_JSON)
						.get(new GenericType<List<OBJECT>>() {
						});

				response = (OBJECT) items;
			}

		} catch (final Exception e) {
			throwAssertFailedWithMessage(HTTPMethods.GET.toString(), assertRestPO, e.getMessage());
		}
		return response;
	}

	private static <OBJECT> OBJECT buildSingleResponse(final Class<OBJECT> returnType,
			final JstAssertRestPO assertRestPO) {

		final JerseyTest jerseyTest = JstRestCacheHelper.retrieveJerseyTest();

		WebTarget webTarget;

		if (assertRestPO.containsBaseURI()) {
			webTarget = jerseyTest.target(assertRestPO.getBaseURI());
		} else {
			webTarget = jerseyTest.target();
		}

		if (assertRestPO.containsRequestPath()) {
			webTarget = webTarget.path(assertRestPO.getRequestPath());
		}
		return webTarget.request(assertRestPO.getAcceptedResponseMediaTypes()).get(returnType);
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
		assertionFailedMessage.append("] method response with ");

		if (assertRestPO.containsBaseURI()) {
			assertionFailedMessage.append("base uri [");
			assertionFailedMessage.append(assertRestPO.getBaseURI());
			assertionFailedMessage.append("] and ");
		}

		if (assertRestPO.containsRequestPath()) {
			assertionFailedMessage.append("request path [");
			assertionFailedMessage.append(assertRestPO.getRequestPath());
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
