package com.gtcgroup.justify.rest.filter.internal;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.client.ClientResponseContext;

public enum LogEntityUtilHelper {

	INSTANCE;

	public static void retrieveEntity(final StringBuilder message, final int maxEntitySize,
			final ClientResponseContext responseContext) throws IOException {

		responseContext.setEntityStream(logInboundEntity(message, maxEntitySize, responseContext.getEntityStream()));
	}

	private static InputStream logInboundEntity(final StringBuilder message, final int maxEntitySize,
			final InputStream inputStream) throws IOException {

		InputStream stream;

		if (!inputStream.markSupported()) {
			stream = new BufferedInputStream(inputStream);
		} else {
			stream = inputStream;
		}
		stream.mark(maxEntitySize + 1);
		final byte[] entity = new byte[maxEntitySize + 1];
		final int entitySize = stream.read(entity);
		message.append(new String(entity, 0, Math.min(entitySize, maxEntitySize)));
		if (entitySize > maxEntitySize) {
			message.append("...more...");
		}
		stream.reset();
		return stream;
	}
}