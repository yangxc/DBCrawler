package com.peraglobal.crawler.model;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ConfigNameConstants {
	public static final String NAME = "name";
	public static final String QUERY = "query";
	public static final String DATA_SRC = "dataSource";

	public static final String IMPORTER_NS = "dataimporter";
	public static final String CLASS = "class";
	public static final Set<String> RESERVED_WORDS;
	static {
		Set<String> rw = new HashSet<String>();
		rw.add(IMPORTER_NS);
		rw.add("request");
		rw.add("delta");
		rw.add("functions");
		rw.add("session");
		RESERVED_WORDS = Collections.unmodifiableSet(rw);
	}
}
