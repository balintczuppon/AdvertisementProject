package com.mycompany.advertisementproject.Layouts;

import com.vaadin.ui.*;

public class StartLayout {

    private Button btnSearch;
    private TextField txtFldSearch;

    public HorizontalLayout buildView() {
        HorizontalLayout hl = new HorizontalLayout();
        hl.setSpacing(true);
        hl.setMargin(true);
        txtFldSearch = new TextField();
        btnSearch = new Button("Keresés");
        hl.addComponent(txtFldSearch);
        hl.addComponent(btnSearch);
        return hl;
    }
}
