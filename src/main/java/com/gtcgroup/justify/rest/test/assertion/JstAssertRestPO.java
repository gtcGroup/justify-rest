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

import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.ClientConfig;

import com.gtcgroup.justify.core.base.JstBasePO;
import com.gtcgroup.justify.rest.filter.internal.LogRequestDefaultFilter;
import com.gtcgroup.justify.rest.filter.internal.LogResponseDefaultFilter;

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

	/**
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

	private final List<Class<Object>> clientFilterList = new ArrayList<>();

	private WebTarget webTarget;

	private String webTargetPath;

	private String queryParamName;

	private Object[] queryParamValues;

	private String[] acceptedResponseMediaTypes;

	List<?> expectedResponseList;

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
	public <FILTER extends ClientRequestFilter> JstAssertRestPO withClientRequestFilters(
			final Class<FILTER>... clientRequestFilters) {

		for (final Class<FILTER> clientFilter : clientRequestFilters) {
			this.clientFilterList.add((Class<Object>) clientFilter);
		}
		return this;
	}

	/**
	 * @return {@link JstAssertRestPO}
	 */
	@SuppressWarnings("unchecked")
	public <FILTER extends ClientResponseFilter> JstAssertRestPO withClientResponseFilters(
			final Class<FILTER>... clientResponseFilters) {

		for (final Class<FILTER> clientFilter : clientResponseFilters) {
			this.clientFilterList.add((Class<Object>) clientFilter);
		}
		return this;
	}

	/**
	 * @return {@link JstAssertRestPO}
	 */
	public JstAssertRestPO withExpectedResponseList(final List<?> expectedResponseList) {
		this.expectedResponseList = expectedResponseList;
		return this;
	}

	/**
	 * @return {@link JstAssertRestPO}
	 */
	@SuppressWarnings("unchecked")
	public JstAssertRestPO withLogRequestDefaultFilter() {
		withClientRequestFilters(LogRequestDefaultFilter.class);
		return this;
	}

	/**
	 * @return {@link JstAssertRestPO}
	 */
	@SuppressWarnings("unchecked")
	public <FILTER extends ClientRequestFilter> JstAssertRestPO withLogRequestFilter(
			final Class<FILTER> requestFilter) {
		withClientRequestFilters(requestFilter);
		return this;
	}

	/**
	 * @return {@link JstAssertRestPO}
	 */
	@SuppressWarnings("unchecked")
	public JstAssertRestPO withLogResponseDefaultFilter() {
		withClientResponseFilters(LogResponseDefaultFilter.class);
		return this;
	}

	/**
	 * @return {@link JstAssertRestPO}
	 */
	@SuppressWarnings("unchecked")
	public <FILTER extends ClientResponseFilter> JstAssertRestPO withLogResponseFilter(
			final Class<FILTER> responseFilter) {
		withClientResponseFilters(responseFilter);
		return this;
	}

	/**
	 * @return {@link JstAssertRestPO}
	 */
	public JstAssertRestPO withQueryParam(final String name, final String... values) {
		this.queryParamName = name;
		this.queryParamValues = values;
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

	protected boolean containsExpectedResponseList() {
		return null != this.expectedResponseList;
	}

	protected boolean containsQueryParam() {

		if (null == this.queryParamName || null == this.queryParamValues) {
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

	protected List<Class<Object>> getClientFilterList() {
		return this.clientFilterList;
	}

	protected List<?> getExpectedResponseList() {
		return this.expectedResponseList;
	}

	protected String getQueryParamName() {
		return this.queryParamName;
	}

	protected Object[] getQueryParamValues() {
		return this.queryParamValues;
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
