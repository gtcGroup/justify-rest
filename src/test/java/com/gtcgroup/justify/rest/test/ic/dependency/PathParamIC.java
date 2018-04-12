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

package com.gtcgroup.justify.rest.test.ic.dependency;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.gtcgroup.justify.rest.test.JstBaseIC;

/**
 * An I/O Controller class used for testing.
 *
 * <p style="font-family:Verdana; font-size:10px; font-style:italic">
 * Copyright (c) 2006 - 2018 by Global Technology Consulting Group, Inc. at
 * <a href="http://gtcGroup.com">gtcGroup.com </a>.
 * </p>
 *
 * @author Marvin Toll
 * @since v.8.5
 */
@Path("/query")
@SuppressWarnings("static-method")
public class PathParamIC extends JstBaseIC {

	@GET
	@Path("/param1")
	public Response getData(@QueryParam("from") final int from, @QueryParam("to") final int to,
			@QueryParam("detailList") final List<String> detailList) {

		return Response.status(200)
				.entity("getUsers is called, from : " + from + ", to : " + to + ", detailList" + detailList.toString())
				.build();

	}

	@GET
	@Path("/param2")
	public Response getData(@Context final UriInfo info) {

		final String from = info.getQueryParameters().getFirst("from");
		final String to = info.getQueryParameters().getFirst("to");
		final List<String> detailList = info.getQueryParameters().get("detailList");

		return Response.status(200)
				.entity("getUsers is called, from : " + from + ", to : " + to + ", detailList" + detailList.toString())
				.build();

	}

}