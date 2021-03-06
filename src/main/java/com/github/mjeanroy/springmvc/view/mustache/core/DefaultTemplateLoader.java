/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 <mickael.jeanroy@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.github.mjeanroy.springmvc.view.mustache.core;

import com.github.mjeanroy.springmvc.view.mustache.MustacheTemplateLoader;
import com.github.mjeanroy.springmvc.view.mustache.exceptions.MustacheTemplateException;
import com.github.mjeanroy.springmvc.view.mustache.exceptions.MustacheTemplateNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import static com.github.mjeanroy.springmvc.view.mustache.commons.PreConditions.notNull;

/**
 * Default template loader implementation.
 *
 * This class can be considered as thread safe if internal state is not
 * modified (if prefix and suffix are not modified, or if aliases are not added).
 */
public class DefaultTemplateLoader implements MustacheTemplateLoader {

	private static final Logger log = LoggerFactory.getLogger(DefaultTemplateLoader.class);

	/**
	 * Resource loader that will be used to retrieve mustache template
	 * from template name.
	 */
	private final ResourceLoader resourceLoader;

	/**
	 * Prefix to prepend to resource before retrieving template name.
	 */
	// Volatile because it can be accessed by more than one thread
	private volatile String prefix;

	/**
	 * Suffix to append to resource before retrieving template name.
	 */
	// Volatile because it can be accessed by more than one thread
	private volatile String suffix;

	/**
	 * Partial aliases.
	 */
	private final Map<String, String> partialAliases = new HashMap<String, String>();

	/**
	 * Temporary partial aliases: i.e. aliases that can be added
	 * before compilation with {@link #addPartialAliases(java.util.Map)} method and
	 * removed after compilation with {@link #removeTemporaryPartialAliases()} method.
	 * This implementation use a thread local object to be thread safe.
	 */
	private final ThreadLocal<Map<String, String>> temporaryPartialAliases = new ThreadLocal<Map<String, String>>() {
		@Override
		public Map<String, String> initialValue() {
			return new HashMap<String, String>();
		}
	};

	/**
	 * Build new template loader.
	 *
	 * @param resourceLoader Resource loader implementation to use.
	 */
	public DefaultTemplateLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = notNull(resourceLoader, "Resource loader must not be null");
		this.prefix = null;
		this.suffix = null;
	}

	/**
	 * Build new template loader.
	 *
	 * @param resourceLoader Resource loader implementation to use.
	 * @param prefix         Prefix to prepend to template name before loading it using resource loader.
	 * @param suffix         Suffix to append to template before loading it using resource loader.
	 */
	public DefaultTemplateLoader(ResourceLoader resourceLoader, String prefix, String suffix) {
		this(resourceLoader);
		this.prefix = notNull(prefix, "Prefix must not be null");
		this.suffix = notNull(suffix, "Suffix must not be null");
	}

	@Override
	public void setPrefix(String prefix) {
		log.trace("Set template loader prefix: {}", prefix);
		this.prefix = prefix;
	}

	@Override
	public void setSuffix(String suffix) {
		log.trace("Set template loader suffix: {}", suffix);
		this.suffix = suffix;
	}

	@Override
	public String getPrefix() {
		return prefix;
	}

	@Override
	public String getSuffix() {
		return suffix;
	}

	@Override
	public void addPartialAliases(Map<String, String> partialAliases) {
		log.trace("Add new partial aliases: {}", partialAliases);
		notNull(partialAliases, "Partial aliases must not be null");
		this.partialAliases.putAll(partialAliases);
	}

	@Override
	public Reader getTemplate(String name) {
		final String templateName = resolve(name);
		final Resource resource = resourceLoader.getResource(templateName);

		if (!resource.exists()) {
			log.error("Mustache template '{}' does not exist, template is not found", templateName);
			throw new MustacheTemplateNotFoundException(templateName);
		}

		try {
			final InputStream inputStream = resource.getInputStream();
			return new InputStreamReader(inputStream);
		}
		catch (IOException ex) {
			log.error(ex.getMessage(), ex);
			throw new MustacheTemplateException(ex);
		}
	}

	@Override
	public String resolve(String name) {
		name =  resolveTemplateName(name);
		return formatName(name);
	}

	private String resolveTemplateName(String name) {
		final Map<String, String> partialsAliases = getPartialAliases();

		final String realName;
		if (partialsAliases.containsKey(name)) {
			realName = partialsAliases.get(name);
		} else {
			realName = name;
		}

		if (log.isDebugEnabled()) {
			log.debug("Load template: {}", name);

			if (log.isTraceEnabled()) {
				log.trace("  => Real name: {}", realName);
				log.trace("  => Template name: {}", realName);
				log.trace("  => Partials: ");

				for (Map.Entry<String, String> entry : partialsAliases.entrySet()) {
					log.trace("     {} -> {}", entry.getKey(), entry.getValue());
				}
			}
		}

		return realName;
	}

	private String formatName(String name) {
		name = prependPrefix(name);
		name = appendSuffix(name);
		return name;
	}

	private String prependPrefix(String name) {
		if (prefix == null) {
			return name;
		}

		if (!name.startsWith(prefix)) {
			name = prefix + name;
		}

		return name;
	}

	private String appendSuffix(String name) {
		if (suffix == null) {
			return name;
		}

		if (!name.endsWith(suffix)) {
			name = name + suffix;
		}

		return name;
	}

	@Override
	public void addTemporaryPartialAliases(Map<String, String> partialAliases) {
		notNull(partialAliases, "Partial aliases must not be null");
		temporaryPartialAliases.get().putAll(partialAliases);
	}

	@Override
	public void removeTemporaryPartialAliases() {
		temporaryPartialAliases.remove();
	}

	private Map<String, String> getPartialAliases() {
		final Map<String, String> aliases = new HashMap<String, String>();
		aliases.putAll(partialAliases);
		aliases.putAll(temporaryPartialAliases.get());
		return aliases;
	}
}
