package com.shopmart.pops.components.abstracts;

import com.shopmart.pops.manager.data.abstracts.AbstractEntry;
import com.shopmart.pops.manager.data.abstracts.AbstractManager;

/**
 * Created by 0nepeop1e on 4/28/16.
 */
public interface SearchFactory<T extends AbstractEntry, M extends AbstractManager<T>> {
    SearchFunction<T, M> getFunction();
}
