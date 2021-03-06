/*
 * Copyright 1999-2011 Alibaba Group Holding Ltd.
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
package com.alibaba.druid.benckmark.sql;

import java.util.List;

import junit.framework.TestCase;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlOutputVisitor;

public class MySqlPerfTest extends TestCase {
	private String sql;

	protected void setUp() throws Exception {
		sql = "SELECT * FROM T";
	}

	public void test_pert() throws Exception {
		for (int i = 0; i < 10; ++i) {
			perfMySql(sql);
		}
	}

	long perfMySql(String sql) {
		long startMillis = System.currentTimeMillis();
		for (int i = 0; i < 1000 * 1000; ++i) {
			execMySql(sql);
		}
		long millis = System.currentTimeMillis() - startMillis;
		System.out.println("MySql\t" + millis);
		return millis;
	}

	private String execMySql(String sql) {
		StringBuilder out = new StringBuilder();
		MySqlOutputVisitor visitor = new MySqlOutputVisitor(out);
		MySqlStatementParser parser = new MySqlStatementParser(sql);
		List<SQLStatement> statementList = parser.parseStatementList();
		for (SQLStatement statement : statementList) {
			statement.accept(visitor);
			visitor.println();
		}
		return out.toString();
	}
}
