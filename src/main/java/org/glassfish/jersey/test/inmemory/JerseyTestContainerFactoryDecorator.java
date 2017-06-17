/*
 * [Licensed per the Open Source "MIT License".]
 *
 * Copyright (c) 2006 - 2016 by
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
package org.glassfish.jersey.test.inmemory;

import java.net.URI;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.server.ApplicationHandler;
import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.spi.TestContainer;
import org.glassfish.jersey.test.spi.TestContainerFactory;

/**
 * This class decorates {@link TestContainerFactory}.
 *
 * <p style="font-family:Verdana; font-size:10px; font-style:italic">
 * Copyright (c) 2006 - 2016 by Global Technology Consulting Group, Inc. at
 * <a href="http://gtcGroup.com">gtcGroup.com </a>.
 * </p>
 *
 * @author
 * @since v.6.0
 */
public class JerseyTestContainerFactoryDecorator implements TestContainerFactory {

	private static class InMemoryTestContainer implements TestContainer {

		private static boolean firstTime = true;

		private final URI baseUri;
		private final ApplicationHandler appHandler;
		private final AtomicBoolean started = new AtomicBoolean(false);

		InMemoryTestContainer(final URI baseUri, final DeploymentContext context) {
			this.baseUri = UriBuilder.fromUri(baseUri).path(context.getContextPath()).build();

			if (InMemoryTestContainer.firstTime) {
				System.err.println("JST: RESTful container configured [" + this.baseUri + "] for endpoint testing.");
				InMemoryTestContainer.firstTime = false;
			}

			this.appHandler = new ApplicationHandler(context.getResourceConfig());
		}

		@Override
		public URI getBaseUri() {
			return this.baseUri;
		}

		@Override
		public ClientConfig getClientConfig() {
			return new ClientConfig().connectorProvider(new InMemoryConnector.Provider(this.baseUri, this.appHandler));
		}

		@Override
		public void start() {
			this.started.compareAndSet(false, true);
		}

		@Override
		public void stop() {
			this.started.compareAndSet(true, false);

		}
	}

	/**
	 * @see TestContainerFactory#create(java.net.URI,
	 *      org.glassfish.jersey.test.DeploymentContext)
	 */
	@Override
	public TestContainer create(final URI baseUri, final DeploymentContext context) throws IllegalArgumentException {
		return new InMemoryTestContainer(baseUri, context);
	}
}
