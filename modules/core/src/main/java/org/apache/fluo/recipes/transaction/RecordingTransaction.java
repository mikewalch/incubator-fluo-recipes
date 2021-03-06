/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.apache.fluo.recipes.transaction;

import java.util.function.Predicate;

import org.apache.fluo.api.client.Transaction;
import org.apache.fluo.api.exceptions.CommitException;

/**
 * An implementation of {@link Transaction} that logs all transactions operations (GET, SET, or
 * DELETE) in a {@link TxLog} that can be used for exports
 */
public class RecordingTransaction extends RecordingTransactionBase implements Transaction {

  private final Transaction tx;

  private RecordingTransaction(Transaction tx) {
    super(tx);
    this.tx = tx;
  }

  private RecordingTransaction(Transaction tx, Predicate<LogEntry> filter) {
    super(tx, filter);
    this.tx = tx;
  }

  @Override
  public void commit() throws CommitException {
    tx.commit();
  }

  @Override
  public void close() {
    tx.close();
  }

  /**
   * Creates a RecordingTransaction by wrapping an existing Transaction
   */
  public static RecordingTransaction wrap(Transaction tx) {
    return new RecordingTransaction(tx);
  }

  /**
   * Creates a RecordingTransaction using the provided LogEntry filter and existing Transaction
   */
  public static RecordingTransaction wrap(Transaction tx, Predicate<LogEntry> filter) {
    return new RecordingTransaction(tx, filter);
  }
}
