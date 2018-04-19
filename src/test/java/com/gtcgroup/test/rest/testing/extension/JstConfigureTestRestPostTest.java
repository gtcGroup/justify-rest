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

package com.gtcgroup.test.rest.testing.extension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.Test;

import com.gtcgroup.justify.core.testing.extension.JstConfigureTestLogToConsole;
import com.gtcgroup.justify.rest.testing.assertion.AssertionsREST;
import com.gtcgroup.justify.rest.testing.assertion.JstAssertRestPO;
import com.gtcgroup.justify.rest.testing.extension.JstConfigureTestingREST;
import com.gtcgroup.test.rest.testing.extension.dependency.ConfigurePostTestRestPO;
import com.gtcgroup.test.rest.to.dependency.HelloTO;
import com.sun.research.ws.wadl.HTTPMethods;

@SuppressWarnings("static-method")
@JstConfigureTestLogToConsole()
@JstConfigureTestingREST(configureTestRestPO = ConfigurePostTestRestPO.class)
public class JstConfigureTestRestPostTest {

	@Test
	public void testPOST_happyPath() {

		AssertionsREST.assertSingle(String.class,
				JstAssertRestPO.withHttpMethod(HTTPMethods.POST.toString()).withPath("body")
						.withResponseEntity(Entity.entity("Hello", MediaType.TEXT_PLAIN))
						.withSpecifiedResponseMediaTypes(MediaType.TEXT_PLAIN));
	}

	@Test
	public void testPOST_mediaTypes() {

		assertAll(() -> {

			assertTrue(AssertionsREST.assertSingle(String.class,
					JstAssertRestPO.withHttpMethod(HTTPMethods.POST.toString())
							.withSpecifiedResponseMediaTypes(MediaType.TEXT_HTML).withPath("hello")
							.withResponseLogging())
					.contains("<h1>Hello Jersey</h1>"));

			assertTrue(AssertionsREST.assertSingle(String.class,
					JstAssertRestPO.withHttpMethod(HTTPMethods.POST.toString())
							.withSpecifiedResponseMediaTypes(MediaType.TEXT_PLAIN).withPath("hello")
							.withRequestLogging())
					.contains("Hello Jersey"));

			assertTrue(AssertionsREST
					.assertSingle(String.class,
							JstAssertRestPO.withHttpMethod(HTTPMethods.POST.toString())
									.withSpecifiedResponseMediaTypes(MediaType.TEXT_XML).withPath("hello")
									.withRequestLogging().withResponseLogging())
					.contains("<hello>Hello Jersey</hello>"));

			assertNull(AssertionsREST.assertList(JstAssertRestPO.withHttpMethod(HTTPMethods.POST.toString())
					.withPath("hello").withResponseLogging()));
		});
	}

	@Test
	public void testPOST_pathAndQueryParam() {

		AssertionsREST.assertSingle(String.class,
				JstAssertRestPO.withHttpMethod(HTTPMethods.POST.toString()).withPath("path/param/1/2")
						.withResponseEntity(Entity.entity(new HelloTO(), MediaType.APPLICATION_JSON))
						.withRequestLogging().withResponseLogging()
						.withQueryParam("detailList", "List Entry One, List Entry Two")
						.withTargetURI("http://localhost:9998/"));
		// .contains("detailList"));
	}

	@Test
	public void testPOST_queryParamAndEntity() {

		assertTrue(AssertionsREST.assertSingle(String.class,
				JstAssertRestPO.withHttpMethod(HTTPMethods.POST.toString()).withPath("query/param")
						.withResponseEntity(Entity.entity(new HelloTO(), MediaType.APPLICATION_JSON))
						.withRequestLogging().withResponseLogging().withQueryParam("from", "1")
						.withQueryParam("to", "2").withQueryParam("detailList", "List Entry One, List Entry Two")
						.withMaxEntityResponseLoggingSize(15).withTargetURI("http://localhost:9998/"))
				.contains("detailList"));
	}

	@Test
	public void testPOST_returnTypeException() {

		AssertionsREST.assertSingle(Long.class, JstAssertRestPO.withHttpMethod(HTTPMethods.POST.toString())
				.withPath("fake").withValidHttpStatusCodes(HttpServletResponse.SC_NOT_FOUND));
	}
}