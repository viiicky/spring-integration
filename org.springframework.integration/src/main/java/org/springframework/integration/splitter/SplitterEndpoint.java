/*
 * Copyright 2002-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.integration.splitter;

import java.util.List;

import org.springframework.integration.endpoint.AbstractMessageHandlingEndpoint;
import org.springframework.integration.message.CompositeMessage;
import org.springframework.integration.message.Message;
import org.springframework.util.Assert;

/**
 * @author Mark Fisher
 */
public class SplitterEndpoint extends AbstractMessageHandlingEndpoint {

	private final Splitter splitter;


	public SplitterEndpoint() {
		this(new DefaultSplitter());
	}

	public SplitterEndpoint(Splitter splitter) {
		Assert.notNull(splitter, "splitter must not be null");
		this.splitter = splitter;
	}


	@Override
	protected boolean shouldSplitComposite() {
		return true;
	}

	@Override
	protected Message<?> handle(Message<?> message) {
		List<Message<?>> results = this.splitter.split(message);
		if (results == null || results.isEmpty()) {
			return null;
		}
		return new CompositeMessage(results);
	}

}
