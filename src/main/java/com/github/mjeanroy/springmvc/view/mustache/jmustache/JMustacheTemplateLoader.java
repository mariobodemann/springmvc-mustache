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

package com.github.mjeanroy.springmvc.view.mustache.jmustache;

import com.github.mjeanroy.springmvc.view.mustache.MustacheTemplateLoader;

import java.io.Reader;

import static com.github.mjeanroy.springmvc.view.mustache.commons.PreConditions.notNull;
import static com.samskivert.mustache.Mustache.TemplateLoader;

/**
 * Implementation of jmustache template loader.
 * Template resolution is delegated to {@link com.github.mjeanroy.springmvc.view.mustache.MustacheTemplateLoader}
 * implementation.
 */
public class JMustacheTemplateLoader implements TemplateLoader {

	/**
	 * Template loader implementation.
	 * Template resolution will be delegated to this implementation.
	 */
	private final MustacheTemplateLoader loader;

	/**
	 * Build new template loader.
	 *
	 * @param loader Loader.
	 */
	public JMustacheTemplateLoader(MustacheTemplateLoader loader) {
		this.loader = notNull(loader, "Template loader must not be null");
	}

	@Override
	public Reader getTemplate(String name) throws Exception {
		return loader.getTemplate(name);
	}
}
