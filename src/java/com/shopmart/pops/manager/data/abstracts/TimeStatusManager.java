package com.shopmart.pops.manager.data.abstracts;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Created by 0nepeop1e on 4/28/16.
 */
public interface TimeStatusManager<T extends AbstractEntry, E extends Enum> {
    Collection<T> getAllBefore(LocalDateTime time);
    Collection<T> getAllAfter(LocalDateTime time);
    Collection<T> getAllBetween(LocalDateTime time1, LocalDateTime time2);
    Collection<T> getAllByStatus(E status);
}
