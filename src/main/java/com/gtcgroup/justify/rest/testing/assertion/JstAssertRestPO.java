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
package com.gtcgroup.justify.rest.testing.assertion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.ClientConfig;

import com.gtcgroup.justify.core.base.JstBasePO;
import com.gtcgroup.justify.rest.filter.JstLogRequestDefaultFilter;
import com.gtcgroup.justify.rest.testing.helper.JstRestUtilHelper;

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

	private static final int MAX_ENTITY_LOGGING_SIZE = 8 * 1024;

	private static int maxResponseEntityLoggingSize;

	public static int getMaxEntityLoggingSize() {
		return maxResponseEntityLoggingSize;
	}

	/**
	 * @return {@link JstAssertRestPO}
	 */
	public static JstAssertRestPO withHttpMethod(final String httpMethod) {

		return new JstAssertRestPO(httpMethod);
	}

	private final Map<String, Object[]> queryParamMap = new ConcurrentHashMap<>();

	private final List<Class<?>> providerForRegistrationList = new ArrayList<>();

	private ClientConfig clientConfig = new ClientConfig();

	private String targetURI = JstRestUtilHelper.DEFAULT_TARGET_URI;

	private String httpMethod;

	private String path;

	private String[] specifiedResponseMediaTypes;

	private Entity<?> responseEntity;

	private boolean requestLogging = false;
	private boolean responseLogging = false;

	private int[] validHttpStatusCodes = new int[] { 200, 204 };

	/**
	 * Constructor
	 */
	protected JstAssertRestPO(final String httpMethod) {

		super();
		JstAssertRestPO.maxResponseEntityLoggingSize = JstAssertRestPO.MAX_ENTITY_LOGGING_SIZE;
		this.httpMethod = httpMethod;
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
	public JstAssertRestPO withMaxEntityResponseLoggingSize(final int maxEntityResponseLoggingSize) {
		JstAssertRestPO.maxResponseEntityLoggingSize = maxEntityResponseLoggingSize;
		return this;
	}

	/**
	 * @return {@link JstAssertRestPO}
	 */
	public JstAssertRestPO withPath(final String requestTargetPath) {

		this.path = requestTargetPath;
		return this;
	}

	/**
	 * @return {@link JstAssertRestPO}
	 */
	public JstAssertRestPO withProvidersForRegistration(final Class<?>... providersForRegistration) {

		for (final Class<?> providerForRegistration : providersForRegistration) {
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
	public JstAssertRestPO withRequestLogging() {

		withProvidersForRegistration(JstLogRequestDefaultFilter.class);
		this.requestLogging = true;
		return this;
	}

	/**
	 * @return {@link JstAssertRestPO}
	 */
	public JstAssertRestPO withResponseEntity(final Entity<?> responseEntity) {
		this.responseEntity = responseEntity;
		return this;
	}

	/**
	 * @return {@link JstAssertRestPO}
	 */
	public JstAssertRestPO withResponseLogging() {
		this.responseLogging = true;
		return this;
	}

	/**
	 * This corresponding get method provides a default of
	 * {@link MediaType}APPLICATION_JSON_TYPE.
	 *
	 * @return {@link JstAssertRestPO}
	 */
	public JstAssertRestPO withSpecifiedResponseMediaTypes(final String... specifiedResponseMediaTypes) {

		this.specifiedResponseMediaTypes = specifiedResponseMediaTypes;
		return this;
	}

	/**
	 * @return {@link JstAssertRestPO}
	 */
	public JstAssertRestPO withTargetURI(final String targetURI) {

		this.targetURI = targetURI;
		return this;
	}

	/**
	 * @return {@link JstAssertRestPO}
	 */
	public JstAssertRestPO withValidHttpStatusCodes(final int... validHttpStatusCodes) {
		this.validHttpStatusCodes = validHttpStatusCodes;
		return this;
	}

	protected boolean containsPath() {
		return null != this.path;
	}

	protected boolean containsQueryParam() {

		if (this.queryParamMap.isEmpty()) {
			return false;
		}
		return true;
	}

	protected boolean containsResponseEnity() {

		if (null == this.responseEntity) {
			return false;
		}
		return true;
	}

	protected ClientConfig getClientConfig() {
		return this.clientConfig;
	}

	protected String getHttpMethod() {
		return this.httpMethod;
	}

	/**
	 * @return {@link String}
	 */
	protected String getPath() {

		return this.path;
	}

	protected List<Class<?>> getProviderForRegistrationList() {
		return this.providerForRegistrationList;
	}

	protected Map<String, Object[]> getQueryParamMap() {
		return this.queryParamMap;
	}

	protected Entity<?> getResponseEntity() {
		return this.responseEntity;
	}

	protected String[] getSpecifiedResponseMediaTypes() {

		if (null == this.specifiedResponseMediaTypes) {
			this.specifiedResponseMediaTypes = new String[] { MediaType.APPLICATION_JSON_TYPE.toString() };
		}

		return this.specifiedResponseMediaTypes;
	}

	protected String getTargetURI() {
		return this.targetURI;
	}

	protected int[] getValidHttpStatusCodes() {
		return this.validHttpStatusCodes;
	}

	protected boolean isRequestLogging() {
		return this.requestLogging;
	}

	protected boolean isResponseLogging() {
		return this.responseLogging;
	}
}
