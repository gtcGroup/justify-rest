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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.assertj.core.api.Assertions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.gtcgroup.justify.core.exception.internal.JustifyRuntimeException;
import com.gtcgroup.justify.rest.helper.internal.RestCacheHelper;

/**
 * This Util Helper class provides support for REST assertion processing.
 *
 * <p style="font-family:Verdana; font-size:10px; font-style:italic">
 * Copyright (c) 2006 - 2016 by Global Technology Consulting Group, Inc. at
 * <a href="http://gtcGroup.com">gtcGroup.com </a>.
 * </p>
 *
 * @author Marvin Toll
 * @since v3.0
 */

public class AssertionsRestUtilHelper {

    private static final String CDI_RULE_SEQUENCE = "\n\n\tCDI may not be properly configured; review Rule sequence.\n";

    private static void assertResponse(final JstAssertRestPO jstAssertRestPO, final Response response) {

        Assertions.assertThat(response.getStatus()).isEqualTo(jstAssertRestPO.getExpectedResponseStatusCode());

        equalsExpectedList(response, jstAssertRestPO);

        equalsExpectedEntity(response, jstAssertRestPO);
    }

    /**
     * @param jstAssertRestPO
     * @return {@link Response}
     */
    public static Response assertResponseDELETE(final JstAssertRestPO jstAssertRestPO) {

        final WebTarget webTarget = initializeWebTarget(jstAssertRestPO);

        Response response;
        try {
            response = webTarget.request().delete(Response.class);
        } catch (@SuppressWarnings("unused") final Exception e) {
            throw new JustifyRuntimeException(AssertionsRestUtilHelper.CDI_RULE_SEQUENCE);
        }

        assertResponse(jstAssertRestPO, response);

        return response;
    }

    /**
     * @param jstAssertRestPO
     * @return {@link Response}
     */
    public static Response assertResponseGET(final JstAssertRestPO jstAssertRestPO) {

        final WebTarget webTarget = initializeWebTarget(jstAssertRestPO);

        Response response;
        try {
            response = webTarget.request().get(Response.class);
        } catch (@SuppressWarnings("unused") final Exception e) {
            throw new JustifyRuntimeException(AssertionsRestUtilHelper.CDI_RULE_SEQUENCE);
        }

        assertResponse(jstAssertRestPO, response);

        return response;
    }

    /**
     * @param jstAssertRestPO
     * @return {@link Response}
     */
    public static Response assertResponsePOST(final JstAssertRestPO jstAssertRestPO) {

        final WebTarget webTarget = initializeWebTarget(jstAssertRestPO);

        Response response;
        try {
            response = webTarget.request().post(jstAssertRestPO.getRequestEntity(), Response.class);
        } catch (@SuppressWarnings("unused") final Exception e) {
            throw new JustifyRuntimeException(AssertionsRestUtilHelper.CDI_RULE_SEQUENCE);
        }

        assertResponse(jstAssertRestPO, response);

        return response;
    }

    /**
     * @param jstAssertRestPO
     * @return {@link Response}
     */
    public static Response assertResponsePUT(final JstAssertRestPO jstAssertRestPO) {

        final WebTarget webTarget = initializeWebTarget(jstAssertRestPO);

        Response response;
        try {
            response = webTarget.request().put(jstAssertRestPO.getRequestEntity(), Response.class);
        } catch (@SuppressWarnings("unused") final Exception e) {
            throw new JustifyRuntimeException(AssertionsRestUtilHelper.CDI_RULE_SEQUENCE);
        }

        assertResponse(jstAssertRestPO, response);

        return response;
    }

    /**
     * @param jstAssertRestPO
     * @return {@link String}
     */
    public static String assertStringGET(final JstAssertRestPO jstAssertRestPO) {

        final WebTarget webTarget = initializeWebTarget(jstAssertRestPO);

        String response;
        try {
            response = webTarget.request().get(String.class);
        } catch (@SuppressWarnings("unused") final Exception e) {
            throw new JustifyRuntimeException(AssertionsRestUtilHelper.CDI_RULE_SEQUENCE);
        }

        Assertions.assertThat(response).isEqualTo(jstAssertRestPO.getExpectedString());

        return response;
    }

    /**
     * @param <TYPE>
     * @param response
     * @param expectedList
     * @param fieldNames
     */
    @SuppressWarnings("unchecked")
    private static <TYPE> void equalsExpected(final Response response, final List<?> expectedList,
        final String[] fieldNames) {

        final CollectionType collectionType = new ObjectMapper().getTypeFactory().constructCollectionType(List.class,
            expectedList.get(0).getClass());

        final List<TYPE> actualList;

        try {
            actualList = new ObjectMapper().readValue(response.readEntity(String.class), collectionType);
        } catch (final Exception e) {
            throw new JustifyRuntimeException(e);
        }

        Assertions.assertThat(actualList)
            .hasSameSizeAs(expectedList)
            .usingElementComparatorOnFields(fieldNames)
            .containsAll((Iterable<? extends TYPE>)expectedList);

        return;
    }

    private static void equalsExpectedEntity(final Response response, final JstAssertRestPO jstAssertRestPO) {

        if (null == jstAssertRestPO.getExpectedResponseEntity()) {
            return;
        }

        final List<Object> entityList = new ArrayList<>();
        entityList.add(jstAssertRestPO.getExpectedResponseEntity());

        equalsExpected(response, entityList, jstAssertRestPO.getFieldNamesForEntityValueComparison());
        return;
    }

    private static void equalsExpectedList(final Response response, final JstAssertRestPO jstAssertRestPO) {

        if (null == jstAssertRestPO.getExpectedResponseEntityList()) {
            return;
        }

        equalsExpected(response, jstAssertRestPO.getExpectedResponseEntityList(),
            jstAssertRestPO.getFieldNamesForListValueComparison());
    }

    private static WebTarget initializeWebTarget(final JstAssertRestPO jstAssertRestPO) {

        final WebTarget webTarget = RestCacheHelper.target(jstAssertRestPO.getRequestPath());

        for (final Map.Entry<String, Object[]> entry : jstAssertRestPO.getQueryParamMap().entrySet()) {

            final String paramName = entry.getKey();
            final Object[] paramValues = entry.getValue();

            webTarget.queryParam(paramName, paramValues);

        }
        return webTarget;
    }

    /**
     * This method enables access to the resource presuming the in-memory server
     * was previously initialized.
     *
     * @param requestPath
     * @return {@link WebTarget}
     */
    public static WebTarget retrieveWebTarget(final String requestPath) {

        return RestCacheHelper.target(requestPath);
    }

    /**
     * Constructor
     */
    private AssertionsRestUtilHelper() {
        super();
        return;
    }
}