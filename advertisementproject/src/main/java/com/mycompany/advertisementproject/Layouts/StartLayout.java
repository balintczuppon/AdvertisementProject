
package com.mycompany.advertisementproject.Layouts;

import com.vaadin.ui.*;

/**
 *
 * @author Czuppon Bálint 
 */
public class StartLayout extends HorizontalLayout {

    private Button btnSearch;
    private TextField txtFldSearch;
    
    public StartLayout(){
        buildView();
    }

    private void buildView() {
        setSpacing(true);
        setMargin(true);
        txtFldSearch = new TextField();
        btnSearch = new Button("Keresés");
        addComponent(txtFldSearch);
        addComponent(btnSearch);
    }  
}
