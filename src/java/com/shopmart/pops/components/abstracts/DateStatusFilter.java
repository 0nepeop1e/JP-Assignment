package com.shopmart.pops.components.abstracts;

import com.shopmart.pops.manager.data.abstracts.AbstractEntry;
import com.shopmart.pops.manager.data.abstracts.TimeStatusManager;

/**
 * Created by 0nepeop1e on 4/28/16.
 */
public abstract class DateStatusFilter<T extends AbstractEntry, S extends Enum,
        M extends TimeStatusManager<T, S>> {



    private enum FilterBy{
        DateAfter, DateBefore, DateBetween, Status;
        @Override
        public String toString(){
            switch (this){
                case DateAfter:return "Date After";
                case DateBefore:return "Date Before";
                case DateBetween:return "Date Between";
            }
            return super.toString();
        }
    }
}
