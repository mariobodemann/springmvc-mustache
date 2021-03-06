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

package com.github.mjeanroy.springmvc.view.mustache.mustachejava;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.Reader;

import org.junit.Before;
import org.junit.Test;

import com.github.mjeanroy.springmvc.view.mustache.MustacheTemplateLoader;

public class SpringMustacheFactoryTest {

	private MustacheTemplateLoader templateLoader;

	private SpringMustacheFactory springMustacheFactory;

	@Before
	public void setUp() {
		templateLoader = mock(MustacheTemplateLoader.class);
		springMustacheFactory = new SpringMustacheFactory(templateLoader);
	}

	@Test
	public void it_should_resolve_template_name_with_template_loader() throws Exception {
		String name = "foo";
		String location = "/templates/foo.template.html";
		when(templateLoader.resolve(name)).thenReturn(location);

		String result = springMustacheFactory.resolvePartialPath("dir", name, "extension");

		assertThat(result).isNotNull().isNotEmpty().isEqualTo(location);
		verify(templateLoader).resolve(name);
	}

	@Test
	public void it_should_resolve_template() throws Exception {
		Reader reader = mock(Reader.class);
		String name = "foo";
		when(templateLoader.getTemplate(name)).thenReturn(reader);

		Reader result = springMustacheFactory.getReader(name);

		assertThat(result).isNotNull().isEqualTo(reader);
		verify(templateLoader).getTemplate(name);
	}
}
