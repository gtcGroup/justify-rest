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

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import com.gtcgroup.justify.core.test.extension.JstConfigureTestLogToConsole;
import com.gtcgroup.justify.rest.test.assertion.AssertionsREST;
import com.gtcgroup.justify.rest.test.assertion.JstAssertRestPO;
import com.gtcgroup.justify.rest.test.extension.dependency.ConcreteConfigureTestRestPO;

@SuppressWarnings("static-method")
@JstConfigureTestLogToConsole()
@JstConfigureTestREST(configureTestRestPO = ConcreteConfigureTestRestPO.class)
public class JstConfigureTestRestGetTest {

	@Test
	public void testGET_requestBaseUriException() {

		Assertions.assertThrows(AssertionFailedError.class, () -> {
			AssertionsREST.assertSingleGET(String.class,
					JstAssertRestPO.withAcceptedResponseMediaTypes().withRequestPath("fake"));
		});
	}

	@Test
	public void testGET_returnTypeException() {

		Assertions.assertThrows(AssertionFailedError.class, () -> {
			AssertionsREST.assertSingleGET(Long.class,
					JstAssertRestPO.withAcceptedResponseMediaTypes().withRequestPath("values"));
		});
	}

	@Test
	public void testHelloIC_mediaTypes() {

		assertAll(() -> {

			AssertionsREST.assertSingleGET(String.class,
					JstAssertRestPO.withAcceptedResponseMediaTypes(MediaType.TEXT_HTML).withRequestPath("hello")
							.withLogRequestDefaultFilter());

			AssertionsREST.assertSingleGET(String.class,
					JstAssertRestPO.withAcceptedResponseMediaTypes(MediaType.TEXT_PLAIN).withRequestPath("hello")
							.withLogResponseDefaultFilter());

			AssertionsREST.assertSingleGET(String.class,
					JstAssertRestPO.withAcceptedResponseMediaTypes(MediaType.TEXT_XML).withRequestPath("hello")
							.withLogRequestDefaultFilter().withLogResponseDefaultFilter());

			// AssertionsREST.assertListGET(JstAssertRestPO.withAcceptedResponseMediaTypes().withRequestPath("hello")
			// .withLogResponseDefaultFilter().withExpectedResponseList(HelloIC.helloList));
		});
	}

	@Test
	public void testQueryParamsIC() {

		assertAll(() -> {
			AssertionsREST.assertSingleGET(String.class,
					JstAssertRestPO.withAcceptedResponseMediaTypes().withRequestPath("values"));

			AssertionsREST.assertSingleGET(String.class,
					JstAssertRestPO.withAcceptedResponseMediaTypes().withRequestPath("values"));

			AssertionsREST.assertSingleGET(String.class,
					JstAssertRestPO.withAcceptedResponseMediaTypes().withRequestPath("values"));
		});
	}

	@Test
	public void testValuesIC() {

		assertAll(() -> {
			AssertionsREST.assertSingleGET(String.class,
					JstAssertRestPO.withAcceptedResponseMediaTypes().withRequestPath("values"));

			AssertionsREST.assertSingleGET(String.class,
					JstAssertRestPO.withAcceptedResponseMediaTypes().withRequestPath("values"));

			AssertionsREST.assertSingleGET(String.class,
					JstAssertRestPO.withAcceptedResponseMediaTypes().withRequestPath("values"));
		});
	}
}