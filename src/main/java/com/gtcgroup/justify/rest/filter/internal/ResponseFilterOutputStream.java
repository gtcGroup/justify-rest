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

package com.gtcgroup.justify.rest.filter.internal;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * This {@link FileOutputStream} class provides convenience methods for building
 * response instances.
 *
 * <p style="font-family:Verdana; font-size:10px; font-style:italic">
 * Copyright (c) 2006 - 2018 by Global Technology Consulting Group, Inc. at
 * <a href="http://gtcGroup.com">gtcGroup.com </a>.
 * </p>
 *
 * @author Marvin Toll
 * @since v8.3
 */
public class ResponseFilterOutputStream extends FilterOutputStream {

	private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

	private final ByteArrayOutputStream baos = new ByteArrayOutputStream();

	public ResponseFilterOutputStream(final OutputStream outputStream) {
		super(outputStream);
	}

	public void retrieveEntityMessage(final StringBuilder message, final int maxEntitySize) {

		final byte[] entity = this.baos.toByteArray();

		message.append(new String(entity, 0, entity.length, DEFAULT_CHARSET));
		if (entity.length > maxEntitySize) {
			message.append("...more...");
		}
	}

	@Override
	public void write(final int maxEntitySize) throws IOException {
		if (this.baos.size() <= maxEntitySize) {
			this.baos.write(maxEntitySize);
		}
		this.out.write(maxEntitySize);
	}
}