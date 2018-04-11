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

package com.gtcgroup.justify.rest.test.intentional.failure;

import javax.ws.rs.client.Entity;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.gtcgroup.justify.core.test.extension.JstConfigureTestLogToConsole;
import com.gtcgroup.justify.rest.test.assertion.AssertionsREST;
import com.gtcgroup.justify.rest.test.assertion.JstAssertRestPO;
import com.gtcgroup.justify.rest.test.extension.JstConfigureTestREST;
import com.gtcgroup.justify.rest.test.extension.dependency.ConcreteConfigureTestRestPO;
import com.sun.research.ws.wadl.HTTPMethods;

@SuppressWarnings("static-method")
@Tag(value = "intentional")
@JstConfigureTestLogToConsole()
@JstConfigureTestREST(configureTestRestPO = ConcreteConfigureTestRestPO.class)
public class Intentional3AssertionFailedTest {

	@Test
	public void testGET_noBodyAllowed() {

		AssertionsREST.assertSingle(String.class, JstAssertRestPO.withHttpMethod(HTTPMethods.GET.toString())
				.withRequestPath("body").withEntity(Entity.text("Hello")));
	}

	@Test
	public void testGET_parsingWithWrongResponseClass() {

		AssertionsREST.assertSingle(Long.class,
				JstAssertRestPO.withHttpMethod(HTTPMethods.GET.toString()).withRequestPath("values"));
	}

	@Test
	public void testGET_requestPathException() {

		AssertionsREST.assertSingle(String.class,
				JstAssertRestPO.withHttpMethod(HTTPMethods.GET.toString()).withRequestPath("fake"));
	}
}