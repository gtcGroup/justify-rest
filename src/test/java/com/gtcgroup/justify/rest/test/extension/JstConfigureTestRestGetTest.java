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

package com.gtcgroup.justify.rest.test.extension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.ClientConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import com.gtcgroup.justify.core.test.extension.JstConfigureTestLogToConsole;
import com.gtcgroup.justify.rest.filter.JstLogRequestDefaultFilter;
import com.gtcgroup.justify.rest.filter.JstLogResponseDefaultFilter;
import com.gtcgroup.justify.rest.test.assertion.AssertionsREST;
import com.gtcgroup.justify.rest.test.assertion.JstAssertRestPO;
import com.gtcgroup.justify.rest.test.extension.dependency.ConcreteConfigureTestRestPO;
import com.gtcgroup.justify.rest.test.extension.dependency.ValuesIC;
import com.sun.research.ws.wadl.HTTPMethods;

@SuppressWarnings("static-method")
@JstConfigureTestLogToConsole()
@JstConfigureTestREST(configureTestRestPO = ConcreteConfigureTestRestPO.class)
public class JstConfigureTestRestGetTest {

	@Test
	public void testGET_enityException() {

		Assertions.assertThrows(AssertionFailedError.class, () -> {
			AssertionsREST.assertSingle(String.class,
					JstAssertRestPO.withHttpMethod(HTTPMethods.GET.toString()).withPath("body")
							.withEntity(Entity.entity("Hello", MediaType.TEXT_PLAIN))
							.withAcceptedResponseMediaTypes(MediaType.TEXT_PLAIN));
		});

	}

	@Test
	public void testGET_requestBaseUriException() {

		Assertions.assertThrows(AssertionFailedError.class, () -> {
			AssertionsREST.assertSingle(String.class,
					JstAssertRestPO.withHttpMethod(HTTPMethods.GET.toString()).withPath("fake"));
		});
	}

	@Test
	public void testGET_returnTypeException() {

		Assertions.assertThrows(AssertionFailedError.class, () -> {
			AssertionsREST.assertSingle(Long.class,
					JstAssertRestPO.withHttpMethod(HTTPMethods.GET.toString()).withPath("values"));
		});
	}

	@Test
	public void testHelloIC_mediaTypes() {

		assertAll(() -> {

			assertTrue(AssertionsREST.assertSingle(String.class,
					JstAssertRestPO.withHttpMethod(HTTPMethods.GET.toString())
							.withAcceptedResponseMediaTypes(MediaType.TEXT_HTML).withPath("hello")
							.withLogResponseFilter())
					.contains("<h1>Hello Jersey</h1>"));

			assertTrue(AssertionsREST.assertSingle(String.class,
					JstAssertRestPO.withHttpMethod(HTTPMethods.GET.toString())
							.withAcceptedResponseMediaTypes(MediaType.TEXT_PLAIN).withPath("hello")
							.withLogRequestFilter())
					.contains("Hello Jersey"));

			assertTrue(AssertionsREST
					.assertSingle(String.class,
							JstAssertRestPO.withHttpMethod(HTTPMethods.GET.toString())
									.withAcceptedResponseMediaTypes(MediaType.TEXT_XML).withPath("hello")
									.withLogRequestFilter().withLogResponseFilter())
					.contains("<hello>Hello Jersey</hello>"));

			assertFalse(AssertionsREST.assertList(JstAssertRestPO.withHttpMethod(HTTPMethods.GET.toString())
					.withPath("hello").withLogResponseFilter()).isEmpty());
		});
	}

	@SuppressWarnings("boxing")
	@Test
	public void testQueryParamsIC() {

		final List<String> detailList = new ArrayList<>();
		detailList.add("List Entry One");
		detailList.add("List Entry Two");

		final Map<String, Object> queryParamMap = new HashMap<>();
		queryParamMap.put("from", 1);
		queryParamMap.put("to", new Integer(2));
		queryParamMap.put("detailList", detailList);

		assertAll(() -> {
			assertTrue(AssertionsREST
					.assertSingle(String.class,
							JstAssertRestPO.withHttpMethod(HTTPMethods.GET.toString()).withPath("query/param1")
									.withLogRequestFilter().withLogResponseFilter().withQueryParamMap(queryParamMap))
					.contains("detailList"));

			assertTrue(AssertionsREST.assertSingle(String.class,
					JstAssertRestPO.withHttpMethod(HTTPMethods.GET.toString()).withPath("query/param1")
							.withLogRequestFilter().withLogResponseFilter().withQueryParam("from", "1")
							.withQueryParam("to", "2").withQueryParam("detailList", "List Entry One, List Entry Two")
							.withMaxEntityLoggingSize(15).withTargetURI("http://localhost:9998/"))
					.contains("detailList"));

			AssertionsREST.assertSingle(String.class,
					JstAssertRestPO.withHttpMethod(HTTPMethods.GET.toString()).withPath("query/param2")
							.withLogRequestFilter(JstLogRequestDefaultFilter.class)
							.withLogResponseFilter(JstLogResponseDefaultFilter.class));
		});
	}

	@Test
	public void testValuesIC() {

		assertTrue(
				AssertionsREST
						.assertSingle(String.class, JstAssertRestPO.withHttpMethod(HTTPMethods.GET.toString())
								.withPath("values").withClientConfig(new ClientConfig()))
						.contains(ValuesIC.DEFAULT_VALUES));

	}
}