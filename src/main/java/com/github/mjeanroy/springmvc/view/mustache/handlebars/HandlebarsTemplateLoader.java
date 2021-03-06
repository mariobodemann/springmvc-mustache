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

package com.github.mjeanroy.springmvc.view.mustache.handlebars;

import com.github.jknack.handlebars.io.StringTemplateSource;
import com.github.jknack.handlebars.io.TemplateLoader;
import com.github.jknack.handlebars.io.TemplateSource;
import com.github.mjeanroy.springmvc.view.mustache.MustacheTemplateLoader;

import java.io.IOException;
import java.io.Reader;

import static com.github.mjeanroy.springmvc.view.mustache.commons.IOUtils.read;
import static com.github.mjeanroy.springmvc.view.mustache.commons.PreConditions.notNull;

/**
 * Implement template loader for use with java handlebar
 * implementation.
 */
public class HandlebarsTemplateLoader implements TemplateLoader {

	/**
	 * Template loader implementation.
	 * Template resolution will be delegated to this implementation.
	 */
	private final MustacheTemplateLoader loader;

	/**
	 * Build new handlebar template loader.
	 * This template loader will delegate implementation to an
	 * instance of {@link MustacheTemplateLoader} class.
	 *
	 * @param loader Instance of mustache template loader.
	 */
	public HandlebarsTemplateLoader(MustacheTemplateLoader loader) {
		this.loader = notNull(loader, "Loader must not be null");
	}

	@Override
	public TemplateSource sourceAt(String location) throws IOException {
		notNull(location, "location must not be null");
		Reader reader = loader.getTemplate(location);
		String content = read(reader);
		return new StringTemplateSource(location, content);
	}

	@Override
	public String resolve(String name) {
		notNull(name, "name must not be null");
		return loader.resolve(name);
	}

	@Override
	public String getPrefix() {
		return loader.getPrefix();
	}

	@Override
	public String getSuffix() {
		return loader.getSuffix();
	}

	@Override
	public void setPrefix(String prefix) {
		notNull(prefix, "prefix must not be null");
		loader.setPrefix(prefix);
	}

	@Override
	public void setSuffix(String suffix) {
		notNull(suffix, "suffix must not be null");
		loader.setSuffix(suffix);
	}
}
