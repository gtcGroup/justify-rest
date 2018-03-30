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

import java.util.function.Function;
import java.util.function.Supplier;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.grizzly.GrizzlyTestContainerFactory;
import org.junit.jupiter.api.extension.ExtensionContext;

import com.gtcgroup.justify.core.test.extension.JstBaseExtension;

/**
 * This Parameter Object class supports configuring an
 * {@link GrizzlyTestContainerFactory}.
 *
 * <p style="font-family:Verdana; font-size:10px; font-style:italic">
 * Copyright (c) 2006 - 2018 by Global Technology Consulting Group, Inc. at
 * <a href="http://gtcGroup.com">gtcGroup.com </a>.
 * </p>
 *
 * @author Marvin Toll
 * @since v.8.5
 */
public abstract class JstConfigureTestRestPO {

	private Function<ExtensionContext, Application> applicationProvider;

	private Supplier<Application> applicationSupplier;

	private ResourceConfig resourceConfig;

	private final boolean isConnectionString = false;

	private boolean isFirstInvocation = true;

	public Function<ExtensionContext, Application> getApplicationProvider() {
		this.applicationProvider = populateApplicationProviderTM();
		return this.applicationProvider;
	}

	public Supplier<Application> getApplicationSuplier() {
		this.applicationSupplier = populateApplicationSupplierTM();
		return this.applicationSupplier;
	}

	public ResourceConfig getResourceConfig() {
		this.resourceConfig = instantiateResourceConfigTM();
		return this.resourceConfig;
	}

	boolean isConnectionString() {
		return this.isConnectionString;
	}

	/**
	 * This method changes the value upon the first invocation. It is intended for
	 * use by a subclass of {@link JstBaseExtension}.
	 *
	 * @return
	 */
	boolean isFirstInvocation() {

		boolean returnValue = false;

		if (this.isFirstInvocation) {

			returnValue = true;
			this.isFirstInvocation = false;
		}
		return returnValue;
	}

	protected abstract ResourceConfig instantiateResourceConfigTM();

	protected abstract Function<ExtensionContext, Application> populateApplicationProviderTM();

	protected abstract Supplier<Application> populateApplicationSupplierTM();
}
