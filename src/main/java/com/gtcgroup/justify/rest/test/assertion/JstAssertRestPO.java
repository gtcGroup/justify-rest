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
package com.gtcgroup.justify.rest.test.assertion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.ClientConfig;

import com.gtcgroup.justify.core.base.JstBasePO;
import com.gtcgroup.justify.rest.filter.JstLogRequestDefaultFilter;
import com.gtcgroup.justify.rest.filter.JstLogResponseDefaultFilter;

/**
 * This Parameter Object class supports JAX-RS testing assertions.
 *
 * <p style="font-family:Verdana; font-size:10px; font-style:italic">
 * Copyright (c) 2006 - 2018 by Global Technology Consulting Group, Inc. at
 * <a href="http://gtcGroup.com">gtcGroup.com </a>.
 * </p>
 *
 * @author Marvin Toll
 * @since v8.3
 */
public class JstAssertRestPO extends JstBasePO {

	private static int maxEntityLoggingSize = 8 * 1024;

	public static int getMaxEntityLoggingSize() {
		return maxEntityLoggingSize;
	}

	/**
	 * This method provides a default of {@link MediaType}APPLICATION_JSON_TYPE.
	 *
	 * @return {@link JstAssertRestPO}
	 */
	public static JstAssertRestPO withAcceptedResponseMediaTypes() {

		return new JstAssertRestPO(new String[] { MediaType.APPLICATION_JSON_TYPE.toString() });
	}

	/**
	 * @return {@link JstAssertRestPO}
	 */
	public static JstAssertRestPO withAcceptedResponseMediaTypes(final String... acceptedResponseMediaTypes) {

		return new JstAssertRestPO(acceptedResponseMediaTypes);
	}

	private ClientConfig clientConfig = new ClientConfig();

	private final List<Class<?>> providerForRegistrationList = new ArrayList<>();

	private WebTarget webTarget;

	private String webTargetPath;

	private final Map<String, Object[]> queryParamMap = new ConcurrentHashMap<>();

	private String[] acceptedResponseMediaTypes;

	/**
	 * Constructor
	 */
	protected JstAssertRestPO(final String[] acceptedResponseMediaTypes) {

		super();
		this.acceptedResponseMediaTypes = acceptedResponseMediaTypes;
		return;
	}

	/**
	 * @return {@link JstAssertRestPO}
	 */
	public JstAssertRestPO withClientConfig(final ClientConfig clientConfig) {
		this.clientConfig = clientConfig;
		return this;
	}

	/**
	 * @return {@link JstAssertRestPO}
	 */
	@SuppressWarnings("unchecked")
	public JstAssertRestPO withLogRequestDefaultFilter() {
		withProvidersForRegistration(JstLogRequestDefaultFilter.class);
		return this;
	}

	/**
	 * @return {@link JstAssertRestPO}
	 */
	@SuppressWarnings("unchecked")
	public JstAssertRestPO withLogResponseDefaultFilter() {
		withProvidersForRegistration(JstLogResponseDefaultFilter.class);
		return this;
	}

	/**
	 * @return {@link JstAssertRestPO}
	 */
	public JstAssertRestPO withMaxEntityLoggingSize(final int maxEntityLoggingSize) {
		JstAssertRestPO.maxEntityLoggingSize = maxEntityLoggingSize;
		return this;
	}

	/**
	 * @return {@link JstAssertRestPO}
	 */
	@SuppressWarnings("unchecked")
	public <PROVIDER> JstAssertRestPO withProvidersForRegistration(final Class<PROVIDER>... providersForRegistration) {

		for (final Class<PROVIDER> providerForRegistration : providersForRegistration) {
			this.providerForRegistrationList.add(providerForRegistration);
		}
		return this;
	}

	/**
	 * @return {@link JstAssertRestPO}
	 */
	public JstAssertRestPO withQueryParam(final String name, final String... values) {
		this.queryParamMap.put(name, values);
		return this;

	}

	/**
	 * @return {@link JstAssertRestPO}
	 */
	public JstAssertRestPO withQueryParamMap(final Map<String, Object> queryParamMap) {

		for (final Map.Entry<String, Object> entry : queryParamMap.entrySet()) {

			this.queryParamMap.put(entry.getKey(), new Object[] { entry.getValue() });
		}
		return this;

	}

	/**
	 * @return {@link JstAssertRestPO}
	 */
	public JstAssertRestPO withRequestPath(final String requestPath) {

		this.webTargetPath = requestPath;
		return this;
	}

	/**
	 * @return {@link JstAssertRestPO}
	 */
	public JstAssertRestPO withWebTarget(final WebTarget webTarget) {
		this.webTarget = webTarget;
		return this;
	}

	protected boolean containsQueryParam() {

		if (this.queryParamMap.isEmpty()) {
			return false;
		}
		return true;
	}

	protected boolean containsWebTargetPath() {
		return null != this.webTargetPath;
	}

	protected String[] getAcceptedResponseMediaTypes() {
		return this.acceptedResponseMediaTypes;
	}

	protected ClientConfig getClientConfig() {
		return this.clientConfig;
	}

	protected List<Class<?>> getProviderForRegistrationList() {
		return this.providerForRegistrationList;
	}

	protected Map<String, Object[]> getQueryParamMap() {
		return this.queryParamMap;
	}

	protected WebTarget getWebTarget() {
		return this.webTarget;
	}

	/**
	 * @return {@link String}
	 */
	protected String getWebTargetPath() {

		return this.webTargetPath;
	}

	protected boolean isWebTarget() {
		return null == this.webTarget;
	}
}
