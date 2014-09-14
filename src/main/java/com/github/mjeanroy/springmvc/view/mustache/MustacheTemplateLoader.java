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

package com.github.mjeanroy.springmvc.view.mustache;

import java.io.Reader;
import java.util.Map;

/**
 * Mustache template loader.
 *
 * Resource are retrieved using {@link org.springframework.core.io.DefaultResourceLoader} by default unless a
 * specific resource loader is used during construction.
 *
 * Prefix and Suffix can be set, these will be used to retrieve template by its name (if given
 * does not already starts with prefix and does not already ends with suffix).
 *
 * For example:
 * - If prefix and suffix are null:
 * getTemplate("foo"); // Call internally resourceLoader.getResource("foo");
 *
 * - If prefix or suffix are not null:
 * getTemplate("foo"); // Call internally resourceLoader.getResource({prefix} + "foo" + {suffix});
 */
public interface MustacheTemplateLoader {

	/**
	 * Get template from name.
	 *
	 * @param name Name of template.
	 *
	 * @return Template reader.
	 */
	Reader getTemplate(String name);

	/**
	 * Get template from name using partials mapping.
	 *
	 * @param name           Name of template.
	 * @param partialAliases Partials mapping.
	 *
	 * @return Template reader.
	 */
	Reader getTemplate(String name, Map<String, String> partialAliases);

	/**
	 * Set prefix on template names.
	 *
	 * @param prefix New prefix.
	 */
	void setPrefix(String prefix);

	/**
	 * Set suffix on template names.
	 *
	 * @param suffix New suffix.
	 */
	void setSuffix(String suffix);

	/**
	 * Add partials mapping.
	 *
	 * @param partialAliases Partials aliases.
	 */
	void addPartialAliases(Map<String, String> partialAliases);

	/**
	 * Clone current instance.
	 *
	 * @return Clone instance.
	 */
	MustacheTemplateLoader clone();
}