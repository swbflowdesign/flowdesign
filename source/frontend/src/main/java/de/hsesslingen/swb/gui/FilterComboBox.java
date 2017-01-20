package de.hsesslingen.swb.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

/**
 * Created by lheft on 15.12.2016.
 */
public class FilterComboBox extends ComboBox<String> {
    private ObservableList<String> initialList;

    public FilterComboBox(ObservableList<String> items) {
        super(items);
        super.setEditable(true);
        this.initialList = items;

        this.configAutoFilterListener();
    }

    private void configAutoFilterListener() {
        this.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if (newValue.equals("")) {
                    FilterComboBox.super.setItems(initialList);
                } else {
                    FilterComboBox.super.setItems(filter(newValue, initialList));
                }
            }
        });

    }

    private ObservableList<String> filter(String filter, ObservableList<String> originalList) {
        ObservableList<String> filteredList = FXCollections.observableArrayList();
        for (String item : originalList) {
            if (item.toLowerCase().startsWith(filter.toLowerCase())) {
                filteredList.add(item);
            }
        }
        return filteredList;
    }
}
