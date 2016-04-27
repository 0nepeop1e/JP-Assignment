package com.shopmart.pops.components.abstracts;

import com.shopmart.pops.manager.data.abstracts.AbstractEntry;
import com.shopmart.pops.manager.data.abstracts.AbstractManager;

import java.util.Collection;

/**
 * Created by 0nepeop1e on 4/28/16.
 */
public interface SearchFunction<T extends AbstractEntry, M extends AbstractManager<T>> {
    Collection<T> call(M manager, String keyword);
}
