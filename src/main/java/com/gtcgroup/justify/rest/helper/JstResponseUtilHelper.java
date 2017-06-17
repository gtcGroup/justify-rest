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

import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gtcgroup.justify.core.exception.internal.JustifyRuntimeException;

/**
 * This Util Helper class provides convenience methods for building response
 * instances.
 *
 * <p style="font-family:Verdana; font-size:10px; font-style:italic">
 * Copyright (c) 2006 - 2016 by Global Technology Consulting Group, Inc. at
 * <a href="http://gtcGroup.com">gtcGroup.com </a>.
 * </p>
 *
 * @author Marvin Toll
 * @since v3.0
 */
public enum JstResponseUtilHelper {

	@SuppressWarnings("javadoc")
	INSTANCE;

	/**
	 * @param statusAsInt
	 * @return {@link Response}
	 */
	public static Response buildResponseInstance(final int statusAsInt) {

		final ResponseBuilder responseBuilder = Response.status(statusAsInt);
		return responseBuilder.build();
	}

	/**
	 * @param entity
	 * @param statusAsInt
	 * @return {@link Response}
	 */
	public static Response buildResponseInstance(final Object entity, final int statusAsInt) {

		final ResponseBuilder responseBuilder = Response.status(statusAsInt);
		return responseBuilder.entity(entity).build();
	}

	/**
	 * @param responseContext
	 * @return {@link String}
	 */
	public static String formatResponseEntity(final ContainerResponseContext responseContext) {

		String message = null;

		final ObjectMapper mapper = new ObjectMapper();

		try {
			message = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseContext.getEntity());

		} catch (final Exception e) {

            throw new JustifyRuntimeException(e);
		}
		return message;
	}
}