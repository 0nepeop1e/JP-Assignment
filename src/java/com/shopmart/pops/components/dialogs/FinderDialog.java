package com.shopmart.pops.components.dialogs;

import com.shopmart.pops.components.abstracts.AbstractFinder;
import com.shopmart.pops.manager.data.abstracts.AbstractEntry;
import javafx.scene.control.Alert;

import java.util.Optional;

/**
 * Created by 0nepeop1e on 4/28/16.
 */
public class FinderDialog<T extends AbstractEntry,F extends AbstractFinder<T, ?>> extends Alert {

    private AbstractFinder finder;

    public FinderDialog(F finder){
        super(AlertType.INFORMATION);
        this.getDialogPane().setContent(finder);
        this.finder = finder;
    }

    public Optional<T> getSelectedItem(){
        return Optional.ofNullable((T) finder.getSelectedItem());
    }
}
