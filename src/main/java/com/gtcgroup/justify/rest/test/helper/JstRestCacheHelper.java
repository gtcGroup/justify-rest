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

package com.gtcgroup.justify.rest.test.helper;

import java.util.Optional;

import org.glassfish.jersey.test.JerseyTest;

import com.gtcgroup.justify.rest.test.JstJerseyTest;
import com.gtcgroup.justify.rest.test.extension.JstConfigureTestRestPO;

/**
 * This Helper class caches {@link EntityManagerFactory}(s).
 *
 * <p style="font-family:Verdana; font-size:10px; font-style:italic">
 * Copyright (c) 2006 - 2018 by Global Technology Consulting Group, Inc. at
 * <a href="http://gtcGroup.com">gtcGroup.com </a>.
 * </p>
 *
 * @author Marvin Toll
 * @since v3.0
 */
public enum JstRestCacheHelper {

	/** Instance */
	INSTANCE;

	private static JerseyTest jerseyTestCached;
	// private static Map<String, JerseyTest> jerseyTestMap = new
	// ConcurrentHashMap<>();

	/**
	 * This method returns a cached {@link JerseyTest} instance.
	 *
	 * @return {@link Optional}
	 */
	public static JerseyTest initializeJerseyTest(final JstConfigureTestRestPO configureTestRestPO,
			final String... featureNames) {

		final JstJerseyTest jerseyTest = new JstJerseyTest(configureTestRestPO.getResourceConfig());

		try {
			jerseyTest.setUp();
		} catch (final Exception e) {

			try {
				jerseyTest.tearDown();
			} catch (final Exception e1) {

				e1.printStackTrace();
			}

			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		jerseyTest.enable(featureNames);

		jerseyTestCached = jerseyTest;

		// if (isFirstUseOfFactory) {
		// return Optional.of(configureTestInstancePO);
		// }
		return jerseyTest;
	}

	public static JerseyTest retrieveJerseyTest() {
		return jerseyTestCached;
	}
	//
	// public static String retrievePersistenceKey(final String persistenceUnitName,
	// final Class<? extends JstConfigureTestJpaPO>
	// entityManagerFactoryPropertyClassPO) {
	//
	// return persistenceUnitName + "/" +
	// entityManagerFactoryPropertyClassPO.getName();
	// }
	//
	// /**
	// * @return boolean
	// */
	// private static boolean createEntityManagerFactory(final JstConfigureTestJpaPO
	// entityManagerFactoryPropertyPO) {
	//
	// final String key =
	// retrievePersistenceKey(entityManagerFactoryPropertyPO.getPersistenceUnitName(),
	// entityManagerFactoryPropertyPO.getClass());
	//
	// if (entityManagerFactoryMap.containsKey(key)) {
	// currentEntityManagerFactory.replace(entityManagerFactoryPropertyPO.getPersistenceUnitName(),
	// entityManagerFactoryMap.get(key));
	//
	// // isFirstUseOfFactory
	// return false;
	// }
	//
	// final Map<String, Object> entityManagerFactoryPropertyMap =
	// entityManagerFactoryPropertyPO
	// .getEntityManagerFactoryPropertyMap();
	//
	// final EntityManagerFactory entityManagerFactory =
	// Persistence.createEntityManagerFactory(
	// entityManagerFactoryPropertyPO.getPersistenceUnitName(),
	// entityManagerFactoryPropertyMap);
	//
	// final EntityManager entityManager =
	// entityManagerFactory.createEntityManager();
	//
	// JstRestCacheHelper.closeEntityManager(entityManager);
	//
	// entityManagerFactoryMap.put(key, entityManagerFactory);
	// currentEntityManagerFactory.put(entityManagerFactoryPropertyPO.getPersistenceUnitName(),
	// entityManagerFactory);
	//
	// // isFirstUseOfFactory
	// return true;
	// }
}