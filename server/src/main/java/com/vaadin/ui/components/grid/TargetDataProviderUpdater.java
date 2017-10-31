/*
 * Copyright 2000-2016 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.ui.components.grid;

import java.io.Serializable;
import java.util.Collection;

import com.vaadin.data.provider.DataProvider;

/**
 * A handler for target grid data provider updater for {@link GridDragger}.
 *
 * Used to handle updates to the target grid's {@link DataProvider} after a
 * drop.
 *
 * @author Vaadin Ltd
 * @since
 *
 * @param <T>
 *            the bean type
 */
@FunctionalInterface
public interface TargetDataProviderUpdater<T> extends Serializable {

    /**
     * Called when items have been dropped on the target Grid.
     *
     * @param dataProvider
     *            the target grid data provider
     * @param index
     *            the target index, {@link Integer#MAX_VALUE} is used for
     *            dropping things always to the end of the grid without having
     *            to fetch the size of the data provider
     * @param items
     *            items to be added.
     */
    public void onDrop(DataProvider<T, ?> dataProvider, int index,
            Collection<T> items);
}
