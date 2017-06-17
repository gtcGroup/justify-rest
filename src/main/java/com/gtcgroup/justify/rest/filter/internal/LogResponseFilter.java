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
package com.gtcgroup.justify.rest.filter.internal;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import com.gtcgroup.justify.core.rule.JstConfigureUserIdRule;
import com.gtcgroup.justify.rest.helper.JstResponseUtilHelper;

/**
 * This {@link ContainerResponseContext} filter provides both request and
 * response logging.
 *
 * <p style="font-family:Verdana; font-size:10px; font-style:italic">
 * Copyright (c) 2006 - 2016 by Global Technology Consulting Group, Inc. at
 * <a href="http://gtcGroup.com">gtcGroup.com </a>.
 * </p>
 *
 * @author
 * @since v.3.0
 */
@Provider
public class LogResponseFilter implements ContainerResponseFilter {

	/**
	 * @param containerRequestContext
	 * @return {@link String}
	 */
	private static String formatRequestEntityMessage(final ContainerRequestContext containerRequestContext) {

		final StringBuilder message = new StringBuilder();

		message.append("\nHTTP REQUEST");
		message.append("\n\tMethod: ").append(containerRequestContext.getMethod());
		message.append("\n\tHeader: ").append(containerRequestContext.getHeaders());
		message.append("\n\tURI:    ").append(containerRequestContext.getUriInfo().getRequestUri());
		message.append("\n");

		return message.toString();
	}

	/**
	 * @param responseContext
	 * @return {@link String}
	 */
	private static String formatResponseEntityMessage(final ContainerResponseContext responseContext) {

		final StringBuilder message = new StringBuilder();

		message.append("HTTP RESPONSE");
		message.append("\n\tHeader: ").append(responseContext.getHeaders());
		message.append("\n\tMedia:  ").append(responseContext.getMediaType());
		message.append("\n\tUser:   ").append(JstConfigureUserIdRule.userID);
		message.append("\n\tStatus: ").append(responseContext.getStatus());

		final String temp = JstResponseUtilHelper.formatResponseEntity(responseContext);
		message.append("\n\tEntity:\n");
		message.append(temp);
		message.append("\n");

		return message.toString();

	}

	/**
	 * @see ContainerResponseFilter#filter(ContainerRequestContext,
	 *      ContainerResponseContext)
	 */
	@Override
	public void filter(final ContainerRequestContext requestContext, final ContainerResponseContext responseContext)
			throws IOException {

		System.out.println(formatRequestEntityMessage(requestContext));
		System.out.println(formatResponseEntityMessage(responseContext));
	}
}
