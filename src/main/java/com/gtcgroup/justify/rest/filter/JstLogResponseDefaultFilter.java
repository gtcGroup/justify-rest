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
package com.gtcgroup.justify.rest.filter;

import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.ext.Provider;

import com.gtcgroup.justify.core.test.extension.JstBaseExtension;
import com.gtcgroup.justify.core.test.helper.internal.LogTestConsoleUtilHelper;
import com.gtcgroup.justify.rest.filter.internal.LogEntityUtilHelper;
import com.gtcgroup.justify.rest.test.assertion.JstAssertRestPO;

/**
 * This {@link ClientResponseFilter} filter provides logging.
 *
 * <p style="font-family:Verdana; font-size:10px; font-style:italic">
 * Copyright (c) 2006 - 2018 by Global Technology Consulting Group, Inc. at
 * <a href="http://gtcGroup.com">gtcGroup.com </a>.
 * </p>
 *
 * @author
 * @since v8.5.0
 */
@Provider
public class JstLogResponseDefaultFilter implements ClientResponseFilter {

	/**
	 * @return {@link String}
	 */
	private static String formatResponseEntityMessage(final ClientResponseContext responseContext) throws IOException {

		final StringBuilder message = new StringBuilder();

		message.append("HTTP RESPONSE");
		message.append("\n\tHeader: ").append(responseContext.getHeaders());
		message.append("\n\tUser:   ").append(JstBaseExtension.getUserId());
		message.append("\n\tStatus: ").append(responseContext.getStatus());

		if (responseContext.hasEntity()) {
			message.append("\n\tEntity:\n");
			LogEntityUtilHelper.retrieveEntity(message, JstAssertRestPO.getMaxEntityLoggingSize(), responseContext);
		}

		message.append("\n");

		return message.toString();
	}

	@Override
	public void filter(final ClientRequestContext requestContext, final ClientResponseContext responseContext)
			throws IOException {

		LogTestConsoleUtilHelper.logToConsole(formatResponseEntityMessage(responseContext));

	}
}
