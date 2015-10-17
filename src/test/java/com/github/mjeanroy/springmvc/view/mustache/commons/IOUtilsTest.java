/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 <mickael.jeanroy@gmail.com>
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

package com.github.mjeanroy.springmvc.view.mustache.commons;

import org.junit.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import static org.assertj.core.api.Assertions.assertThat;

public class IOUtilsTest {

	private static final String LINE_SEPARATOR = System.getProperty("line.separator");

	@Test
	public void it_should_read_input() {
		InputStream stream = getClass().getResourceAsStream("/templates/foo.template.html");
		Reader reader = new InputStreamReader(stream);

		String content = IOUtils.read(reader);

		assertThat(content)
				.isNotNull()
				.isNotEmpty()
				.isEqualTo("<div>Hello {{name}}</div>");
	}

	@Test
	public void it_should_read_multiline_input() {
		InputStream stream = getClass().getResourceAsStream("/templates/composite.template.html");
		Reader reader = new InputStreamReader(stream);

		String content = IOUtils.read(reader);

		assertThat(content)
				.isNotNull()
				.isNotEmpty()
				.isEqualTo("" +
						"<div>" + LINE_SEPARATOR +
						"	{{> /templates/foo.template.html}}" + LINE_SEPARATOR +
						"</div>"
				);
	}
}