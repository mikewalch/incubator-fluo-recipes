/*
 * Copyright 2014 Fluo authors (see AUTHORS)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package io.fluo.recipes.map;

import io.fluo.api.client.TransactionBase;
import io.fluo.api.data.Bytes;
import io.fluo.api.data.Column;
import io.fluo.api.observer.AbstractObserver;

public class CollisionFreeMapObserver extends AbstractObserver {

  @SuppressWarnings("rawtypes")
  private CollisionFreeMap cfm;
  private String mapId;

  public CollisionFreeMapObserver() {}

  @Override
  public void init(Context context) throws Exception {
    this.mapId = context.getParameters().get("mapId");
    cfm = CollisionFreeMap.getInstance(mapId, context.getAppConfiguration());
    cfm.updateObserver.init(mapId, context);
  }

  @Override
  public void process(TransactionBase tx, Bytes row, Column col) throws Exception {
    cfm.process(tx, row, col);
  }

  @Override
  public ObservedColumn getObservedColumn() {
    // TODO constants
    return new ObservedColumn(new Column("fluoRecipes", "cfm:" + mapId), NotificationType.WEAK);
  }
}
