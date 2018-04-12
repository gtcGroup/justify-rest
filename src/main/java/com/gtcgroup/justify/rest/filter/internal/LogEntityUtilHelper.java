package com.gtcgroup.justify.rest.filter.internal;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.client.ClientResponseContext;

public enum LogEntityUtilHelper {

	INSTANCE;

	public static void retrieveEntity(final StringBuilder message, final int maxEntityLoggingSize,
			final ClientResponseContext responseContext) throws IOException {

		responseContext
				.setEntityStream(logInboundEntity(message, maxEntityLoggingSize, responseContext.getEntityStream()));
	}

	private static InputStream logInboundEntity(final StringBuilder message, final int maxEntityLoggingSize,
			final InputStream inputStream) throws IOException {

		final InputStream stream = new BufferedInputStream(inputStream);
		stream.mark(maxEntityLoggingSize + 1);
		final byte[] entity = new byte[maxEntityLoggingSize + 1];
		final int entitySize = stream.read(entity);
		message.append(new String(entity, 0, Math.min(entitySize, maxEntityLoggingSize)));
		if (entitySize > maxEntityLoggingSize) {
			message.append("... [");
			message.append(maxEntityLoggingSize);
			message.append(" maximum logging characters]");
		}
		stream.reset();
		return stream;
	}
}