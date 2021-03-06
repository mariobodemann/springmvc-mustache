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

import com.github.mjeanroy.springmvc.view.mustache.MustacheTemplate;
import com.github.mjeanroy.springmvc.view.mustache.core.AbstractMustacheTemplate;
import com.samskivert.mustache.Template;

import java.io.Writer;
import java.util.Map;

import static com.github.mjeanroy.springmvc.view.mustache.commons.PreConditions.notNull;

/**
 * Implementation of {@link MustacheTemplate} using JMustache
 * as real template implementation.
 */
public class JMustacheTemplate extends AbstractMustacheTemplate implements MustacheTemplate {

	/**
	 * JMustache template.
	 * This template will be rendered using jmustache api.
	 */
	private final Template template;

	/**
	 * Build new template.
	 *
	 * @param template JMustache template.
	 */
	public JMustacheTemplate(Template template) {
		this.template = notNull(template, "Template must not be null");
	}

	@Override
	protected void doExecute(Map<String, Object> model, Writer writer) throws Exception {
		template.execute(model, writer);
	}
}
