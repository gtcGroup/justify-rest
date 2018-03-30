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
package com.gtcgroup.justify.rest.test;

import java.lang.reflect.Field;
import java.util.Map;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.test.JerseyTest;

/**
 * This concrete {@link JerseyTest} class supports JUnit 5.
 *
 * <p style="font-family:Verdana; font-size:10px; font-style:italic">
 * Copyright (c) 2006 - 2018 by Global Technology Consulting Group, Inc. at
 * <a href="http://gtcGroup.com">gtcGroup.com </a>.
 * </p>
 *
 * @author Marvin Toll
 * @since v.8.5
 */
public class JstJerseyTest extends JerseyTest {

	public JstJerseyTest(final Application jaxrsApplication) {
		super(jaxrsApplication);
	}

	// protected JstJerseyTest() {
	// super();
	// // TODO Auto-generated constructor stub
	// }
	//
	// protected JstJerseyTest(final TestContainerFactory testContainerFactory) {
	// super(testContainerFactory);
	// // TODO Auto-generated constructor stub
	// }

	public void enable(final String... featureNames) {

		try {
			final Field field = JerseyTest.class.getDeclaredField("propertyMap");
			field.setAccessible(true);

			@SuppressWarnings("unchecked")
			final Map<String, String> propertyMap = (Map<String, String>) field.get(this);
			field.setAccessible(true);

			for (final String featureName : featureNames) {

				propertyMap.put(featureName, Boolean.TRUE.toString());
			}

		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
