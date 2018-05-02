package com.gtcgroup.justify.rest.helper.internal;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gtcgroup.justify.core.testing.extension.JstBaseTestingExtension;
import com.gtcgroup.justify.rest.filter.JstLogRequestDefaultFilter;

public enum LogRestUtilHelper {

	INSTANCE;

	/**
	 * @return {@link String}
	 */
	public static String formatRequestMessage() {

		final ClientRequestContext requestContext = JstLogRequestDefaultFilter.retrieveClientRequestContext();

		final StringBuilder message = new StringBuilder();

		message.append("\nHTTP REQUEST");
		message.append("\n\tMethod: ").append(requestContext.getMethod());
		message.append("\n\tHeader: ").append(requestContext.getHeaders());
		message.append("\n\tPath:    ").append(requestContext.getUri().getPath());
		message.append("\n");

		return message.toString();
	}

	/**
	 * @return {@link String}
	 */
	public static String formatResponseMessage(final Response responseSingle, final Object entityReturned,
			final int maxEntityLoggingSize) {

		final StringBuilder message = new StringBuilder();

		message.append("HTTP RESPONSE");
		message.append("\n\tHeader: ").append(responseSingle.getHeaders());
		message.append("\n\tUser:   ").append(JstBaseTestingExtension.getUserId());
		message.append("\n\tStatus: ").append(responseSingle.getStatus());

		message.append("\n\tEntity: ");

		if (null == entityReturned || "".equals(entityReturned)) {
			message.append("[no response entity]\n");
		} else {

			try {

				String entityString = new ObjectMapper().writer().withDefaultPrettyPrinter()
						.writeValueAsString(entityReturned);

				if (maxEntityLoggingSize < entityString.length()) {
					entityString = entityString.substring(0, maxEntityLoggingSize) + "... [" + maxEntityLoggingSize
							+ " maximum logging characters]";
				}

				message.append("\n");
				message.append(entityString);

			} catch (@SuppressWarnings("unused") final Exception e) {

				if (entityReturned instanceof String) {

					message.append(entityReturned);

				} else {

					message.append("[no loggable response entity]");
				}

			} finally {

				message.append("\n");
			}
		}
		return message.toString();

	}
}