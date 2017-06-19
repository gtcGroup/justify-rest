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
package com.gtcgroup.justify.rest.helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Entity;

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
     * This method initializes the class.
     *
     * @param requestPath
     * @return {@link JstRuleRestPO}
     */
    public static JstAssertRestPO requestPath(final String requestPath) {
        return new JstAssertRestPO(requestPath);
    }

    private final String requestPath;

    private int expectedResponseStatusCode;

    private String expectedString;

    private List<?> expectedResponseEntityList;

    private String[] fieldNamesForListValueComparison;

    private String[] fieldNamesForEntityValueComparison;

    private Entity<?> requestEntity;

    private Object expectedResponseEntity;

    private final Map<String, Object[]> queryParamMap = new HashMap<>();

    /**
     * Constructor - private
     *
     * @param requestPath
     */
    private JstAssertRestPO(final String requestPath) {
        super();
        this.requestPath = requestPath;
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
     * @return {@link Object}
     */
    public Object getExpectedResponseEntity() {
        return this.expectedResponseEntity;
    }

    /**
     * @return {@link List}<?>
     */
    public List<?> getExpectedResponseEntityList() {

        return this.expectedResponseEntityList;
    }

    /**
     * @return int
     */
    public int getExpectedResponseStatusCode() {

        return this.expectedResponseStatusCode;
    }

    /**
     * @return {@link String}
     */
    public String getExpectedString() {

        return this.expectedString;
    }

    /**
     * @return String []
     */
    public String[] getFieldNamesForEntityValueComparison() {

        return this.fieldNamesForEntityValueComparison;
    }

    /**
     * @return String[]
     */
    public String[] getFieldNamesForListValueComparison() {

        return this.fieldNamesForListValueComparison;
    }

    /**
     * @return {@link Map}
     */
    public Map<String, Object[]> getQueryParamMap() {

        return this.queryParamMap;
    }

    /**
     * @return {@link Entity}<?>
     */
    public Entity<?> getRequestEntity() {

        return this.requestEntity;
    }

    /**
     * @return {@link String}
     */
    public String getRequestPath() {

        return this.requestPath;
    }

    /**
     * @return {@link Object}
     */
    public Object getResponseExpectedEntity() {

        return getExpectedResponseEntity();
    }

    /**
     * @return {@link List}<?>
     */
    public List<?> getResponseExpectedList() {

        return getExpectedResponseEntityList();
    }

    /**
     * @return int
     */
    public int getStatus() {

        return getExpectedResponseStatusCode();
    }

    /**
     * @param <CONCRETE>
     * @param expectedResponseEntity
     * @param fieldNamesForEntityValueComparison
     * @return {@link JstAssertRestPO}
     */
    @SuppressWarnings("unchecked")
    public <CONCRETE extends JstAssertRestPO> CONCRETE setExpectedResponseEntity(final Object expectedResponseEntity,
        final String... fieldNamesForEntityValueComparison) {

        this.expectedResponseEntity = expectedResponseEntity;
        this.fieldNamesForEntityValueComparison = fieldNamesForEntityValueComparison;
        return (CONCRETE)this;
    }

    /**
     * @param <CONCRETE>
     * @param expectedResponseStatusCode
     * @return {@link JstAssertRestPO}
     */
    @SuppressWarnings("unchecked")
    public <CONCRETE extends JstAssertRestPO> CONCRETE setExpectedResponseStatusCode(
        final int expectedResponseStatusCode) {

        this.expectedResponseStatusCode = expectedResponseStatusCode;
        return (CONCRETE)this;
    }

    /**
     * @param <CONCRETE>
     * @param expectedString
     * @return {@link JstAssertRestPO}
     */
    @SuppressWarnings("unchecked")
    public <CONCRETE extends JstAssertRestPO> CONCRETE setExpectedString(final String expectedString) {

        this.expectedString = expectedString;
        return (CONCRETE)this;
    }

    /**
     * @param <CONCRETE>
     * @param requestEntity
     * @return {@link JstAssertRestPO}
     */
    @SuppressWarnings("unchecked")
    public <CONCRETE extends JstAssertRestPO> CONCRETE setRequestEntity(final Entity<?> requestEntity) {

        this.requestEntity = requestEntity;
        return (CONCRETE)this;
    }

    /**
     * @param <CONCRETE>
     * @param expectedResponseEntityList
     * @param fieldNamesForListValueComparison
     * @return {@link JstAssertRestPO}
     */
    @SuppressWarnings("unchecked")
    public <CONCRETE> CONCRETE setResponseExpectedList(final List<?> expectedResponseEntityList,
        final String... fieldNamesForListValueComparison) {

        this.expectedResponseEntity = expectedResponseEntityList;
        this.fieldNamesForListValueComparison = fieldNamesForListValueComparison;
        return (CONCRETE)this;
    }

}
