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

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.Test;

import com.gtcgroup.justify.core.test.extension.JstConfigureTestLogToConsole;
import com.gtcgroup.justify.rest.test.assertion.AssertionsREST;
import com.gtcgroup.justify.rest.test.assertion.JstAssertRestPO;
import com.gtcgroup.justify.rest.test.extension.dependency.ConfigurePutTestRestPO;
import com.gtcgroup.justify.rest.test.to.dependency.HelloTO;
import com.sun.research.ws.wadl.HTTPMethods;

@SuppressWarnings("static-method")
@JstConfigureTestLogToConsole()
@JstConfigureTestREST(configureTestRestPO = ConfigurePutTestRestPO.class)
public class JstConfigureTestRestPutTest {

	@Test
	public void testPUT_happyPath() {

		assertTrue(AssertionsREST.assertSingle(String.class,
				JstAssertRestPO.withHttpMethod(HTTPMethods.PUT.toString()).withPath("path/query/entity/1")
						.withEntity(Entity.entity(new HelloTO(), MediaType.APPLICATION_JSON)).withLogRequestFilter()
						.withLogResponseFilter().withQueryParam("detailList", "List Entry One, List Entry Two")
						.withTargetURI("http://localhost:9998/"))
				.contains("detailList"));
	}

	@Test
	public void testPUT_noResponseList() {

		assertNull(AssertionsREST
				.assertList(JstAssertRestPO.withHttpMethod(HTTPMethods.PUT.toString()).withPath("no/response/1")
						.withEntity(Entity.entity(new HelloTO(), MediaType.APPLICATION_JSON)).withLogRequestFilter()
						.withLogResponseFilter().withQueryParam("detailList", "List Entry One, List Entry Two")
						.withTargetURI("http://localhost:9998/")));
	}

	@Test
	public void testPUT_noResponseSingle() {

		assertNull(AssertionsREST.assertSingle(String.class,
				JstAssertRestPO.withHttpMethod(HTTPMethods.PUT.toString()).withPath("no/response/1")
						.withEntity(Entity.entity(new HelloTO(), MediaType.APPLICATION_JSON)).withLogRequestFilter()
						.withLogResponseFilter().withQueryParam("detailList", "List Entry One, List Entry Two")
						.withTargetURI("http://localhost:9998/")));
	}
}