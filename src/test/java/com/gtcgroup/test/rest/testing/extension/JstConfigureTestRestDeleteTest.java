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

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.gtcgroup.justify.core.testing.extension.JstConfigureTestLogToConsole;
import com.gtcgroup.justify.rest.testing.assertion.AssertionsREST;
import com.gtcgroup.justify.rest.testing.assertion.JstAssertRestPO;
import com.gtcgroup.justify.rest.testing.extension.JstConfigureTestingREST;
import com.gtcgroup.test.rest.testing.extension.dependency.ConfigureDeleteTestRestPO;
import com.gtcgroup.test.rest.to.dependency.HelloTO;
import com.sun.research.ws.wadl.HTTPMethods;

@SuppressWarnings("static-method")
@JstConfigureTestLogToConsole()
@JstConfigureTestingREST(configureTestRestPO = ConfigureDeleteTestRestPO.class)
public class JstConfigureTestRestDeleteTest {

	@Test
	public void testDelete() {

		final HelloTO hello = AssertionsREST.assertSingle(HelloTO.class,
				JstAssertRestPO.withHttpMethod(HTTPMethods.DELETE.toString()).withPath("entity/" + "uuid")
						.withRequestLogging().withResponseLogging());

		assertNull(hello.getText());

		// hello.getClass();
		// final HelloTO helloTO = hello.getEntity();
		// assertEquals(NoteDE.class, note.getEntity().getClass());
	}
}