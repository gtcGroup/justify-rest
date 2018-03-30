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
package com.gtcgroup.justify.rest.test.assertion;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import com.gtcgroup.justify.core.base.JstBasePO;
import com.gtcgroup.justify.rest.rule.JstRuleRestPO;

/**
 * This Parameter Object class supports JAX-RS testing assertions.
 *
 * <p style="font-family:Verdana; font-size:10px; font-style:italic">
 * Copyright (c) 2006 - 2016 by Global Technology Consulting Group, Inc. at
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

	private String requestPath;

	private String baseURI = null;

	private String[] acceptedResponseMediaTypes;

	private final Map<String, Object[]> queryParamMap = new HashMap<>();

	/**
	 * Constructor
	 */
	protected JstAssertRestPO(final String[] acceptedResponseMediaTypes) {

		super();
		this.acceptedResponseMediaTypes = acceptedResponseMediaTypes;
		return;
	}

	/**
	 * @param queryName
	 * @param queryParams
	 * @return {@link JstAssertRestPO}
	 */
	public JstAssertRestPO addQueryParam(final String queryName, final String[] queryParams) {
		this.queryParamMap.put(queryName, queryParams);
		return this;
	}

	/**
	 * @return {@link Map}
	 */
	public Map<String, Object[]> getQueryParamMap() {

		return this.queryParamMap;
	}

	/**
	 * @return {@link JstRuleRestPO}
	 */
	public JstAssertRestPO withBaseURI(final String baseURI) {

		this.baseURI = baseURI;
		return this;
	}

	/**
	 * @return {@link JstAssertRestPO}
	 */
	public JstAssertRestPO withRequestPath(final String requestPath) {

		this.requestPath = requestPath;
		return this;
	}

	protected boolean containsBaseURI() {
		return null != this.baseURI;
	}

	protected boolean containsRequestPath() {
		return null != this.requestPath;
	}

	protected String[] getAcceptedResponseMediaTypes() {
		return this.acceptedResponseMediaTypes;
	}

	/**
	 * @return {@link String}
	 */
	protected String getBaseURI() {

		return this.baseURI;
	}

	/**
	 * @return {@link String}
	 */
	protected String getRequestPath() {

		return this.requestPath;
	}
}
