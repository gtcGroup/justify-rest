package com.gtcgroup.justify.rest.rule;

import java.util.Map;

import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseFilter;

import org.glassfish.jersey.server.ResourceConfig;

import com.gtcgroup.justify.rest.test.assertion.JstAssertRestPO;

/**
 * This Decorator class extends the {@link ResourceConfig} class.
 *
 * <p style="font-family:Verdana; font-size:10px; font-style:italic">
 * Copyright (c) 2006 - 2016 by Global Technology Consulting Group, Inc. at
 * <a href="http://gtcGroup.com">gtcGroup.com </a>.
 * </p>
 *
 * @author
 * @since v.8.3
 */
public class JstRuleRestPO extends ResourceConfig {

	/**
	 * This method initializes the class.
	 *
	 * @param classes
	 * @return {@link JstRuleRestPO}
	 */
	public static JstRuleRestPO endPointClasses(final Class<?>... classes) {
		return new JstRuleRestPO(classes);
	}

	private boolean suppressJsonLogging = false;

	/**
	 * Constructor - private
	 *
	 * @param classes
	 */
	private JstRuleRestPO(final Class<?>... classes) {

		super(classes);
		return;
	}

	/**
	 * Constructor - Package Protected
	 *
	 * @param originalResourceConfig
	 */
	JstRuleRestPO(final ResourceConfig originalResourceConfig) {

		super(originalResourceConfig);

		return;
	}

	/**
	 * @param componentClass
	 * @param <CONCRETE>
	 * @return {@link JstRuleRestPO}
	 * @see ResourceConfig#register(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public <CONCRETE extends JstRuleRestPO> CONCRETE addComponent(final Class<?> componentClass) {

		super.register(componentClass);

		return (CONCRETE) this;
	}

	/**
	 * @param componentClass
	 * @param contracts
	 * @param <CONCRETE>
	 * @return {@link JstRuleRestPO}
	 * @see ResourceConfig#register(java.lang.Class, java.lang.Class[])
	 */
	@SuppressWarnings("unchecked")
	public <CONCRETE extends JstRuleRestPO> CONCRETE addComponent(final Class<?> componentClass,
			final Class<?>... contracts) {

		super.register(componentClass, contracts);

		return (CONCRETE) this;
	}

	/**
	 * @param componentClass
	 * @param bindingPriority
	 * @param <CONCRETE>
	 * @return {@link JstRuleRestPO}
	 * @see ResourceConfig#register(java.lang.Class, int)
	 */
	@SuppressWarnings("unchecked")
	public <CONCRETE extends JstRuleRestPO> CONCRETE addComponent(final Class<?> componentClass,
			final int bindingPriority) {

		super.register(componentClass, bindingPriority);

		return (CONCRETE) this;
	}

	/**
	 * @param componentClass
	 * @param contracts
	 * @param <CONCRETE>
	 * @return {@link JstRuleRestPO}
	 * @see ResourceConfig#register(java.lang.Class, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public <CONCRETE extends JstRuleRestPO> CONCRETE addComponent(final Class<?> componentClass,
			final Map<Class<?>, Integer> contracts) {

		super.register(componentClass, contracts);

		return (CONCRETE) this;
	}

	/**
	 * @param component
	 * @param <CONCRETE>
	 * @return {@link JstRuleRestPO}
	 * @see ResourceConfig#register(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public <CONCRETE extends JstRuleRestPO> CONCRETE addComponent(final Object component) {

		super.register(component);

		return (CONCRETE) this;
	}

	/**
	 * @param component
	 * @param contracts
	 * @param <CONCRETE>
	 * @return {@link JstRuleRestPO}
	 * @see ResourceConfig#register(java.lang.Object, java.lang.Class[])
	 */
	@SuppressWarnings("unchecked")
	public <CONCRETE extends JstRuleRestPO> CONCRETE addComponent(final Object component, final Class<?>... contracts) {

		super.register(component, contracts);

		return (CONCRETE) this;
	}

	/**
	 * @param component
	 * @param bindingPriority
	 * @param <CONCRETE>
	 * @return {@link JstRuleRestPO}
	 * @see ResourceConfig#register(java.lang.Object, int)
	 */
	@SuppressWarnings("unchecked")
	public <CONCRETE extends JstRuleRestPO> CONCRETE addComponent(final Object component, final int bindingPriority) {

		super.register(component, bindingPriority);

		return (CONCRETE) this;
	}

	/**
	 * @param component
	 * @param contracts
	 * @param <CONCRETE>
	 * @return {@link JstRuleRestPO}
	 * @see ResourceConfig#register(java.lang.Object, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public <CONCRETE extends JstRuleRestPO> CONCRETE addComponent(final Object component,
			final Map<Class<?>, Integer> contracts) {

		super.register(component, contracts);

		return (CONCRETE) this;
	}

	/**
	 * @param <CONCRETE>
	 * @param recursive
	 * @param files
	 * @return {@link JstRuleRestPO}
	 * @see ResourceConfig#files(boolean, String...)
	 */
	@SuppressWarnings("unchecked")
	public final <CONCRETE extends JstRuleRestPO> CONCRETE addFiles(final boolean recursive, final String... files) {

		super.files(recursive, files);

		return (CONCRETE) this;
	}

	/**
	 * @param <CONCRETE>
	 * @param files
	 * @return {@link JstRuleRestPO}
	 * @see ResourceConfig#files(java.lang.String[])
	 */
	@SuppressWarnings("unchecked")
	public final <CONCRETE extends JstRuleRestPO> CONCRETE addFiles(final String... files) {

		super.files(files);

		return (CONCRETE) this;
	}

	/**
	 * @param properties
	 * @param <CONCRETE>
	 * @return {@link JstAssertRestPO}
	 * @see ResourceConfig#setProperties(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public <CONCRETE extends JstRuleRestPO> CONCRETE addPropertiez(final Map<String, Object> properties) {

		super.addProperties(properties);

		return (CONCRETE) this;
	}

	/**
	 * @param <CONCRETE>
	 * @param name
	 * @param value
	 * @return {@link JstRuleRestPO}
	 * @see ResourceConfig#property(String, Object)
	 */
	@SuppressWarnings("unchecked")
	public <CONCRETE extends JstRuleRestPO> CONCRETE addProperty(final String name, final Object value) {

		super.property(name, value);

		return (CONCRETE) this;
	}

	/**
	 * @param <CONCRETE>
	 * @param requestFilters
	 * @return {@link JstAssertRestPO}
	 */
	@SuppressWarnings("unchecked")
	public <CONCRETE extends JstRuleRestPO> CONCRETE addRequestFilters(final ContainerRequestFilter... requestFilters) {

		for (final ContainerRequestFilter containerRequestFilter : requestFilters) {

			addComponent(containerRequestFilter);
		}
		return (CONCRETE) this;
	}

	/**
	 * @param <CONCRETE>
	 * @param responseFilters
	 * @return {@link JstAssertRestPO}
	 */
	@SuppressWarnings("unchecked")
	public <CONCRETE extends JstRuleRestPO> CONCRETE addResponseFilters(
			final ContainerResponseFilter... responseFilters) {

		for (final ContainerResponseFilter containerResponseFilter : responseFilters) {

			addComponent(containerResponseFilter);
		}
		return (CONCRETE) this;
	}

	/**
	 * @return boolean
	 */
	public boolean isSuppressJsonLogging() {
		return this.suppressJsonLogging;
	}

	/**
	 * @param <CONCRETE>
	 * @param suppressJsonLogging
	 * @return JstRuleRestPO.java
	 */
	@SuppressWarnings("unchecked")
	public <CONCRETE extends JstRuleRestPO> CONCRETE setSuppressJsonLogging(final boolean suppressJsonLogging) {

		this.suppressJsonLogging = suppressJsonLogging;
		return (CONCRETE) this;
	}
}
