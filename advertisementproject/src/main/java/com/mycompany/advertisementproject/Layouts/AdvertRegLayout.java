
package com.mycompany.advertisementproject.Layouts;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class AdvertRegLayout {

    private TextField txtFieldTitle;
    private TextArea txtAreaDescription;
    private ComboBox cmbbxCategory;
    private ComboBox cmbbxAdvertType;
    private ComboBox cmbbxAdvertState;
    private TextField txtFldPrice;
    private TextField txtFldPostalCode;
    private Label lblPicture;
    private Button btnAddPicture;
    private Button btnRegister;

    public VerticalLayout buildView() {
        VerticalLayout verticalLayout = new VerticalLayout();
        FormLayout formLayout = new FormLayout();

        txtFieldTitle = new TextField("Hirdetés címe");
        txtAreaDescription = new TextArea("Hirdetés leírása");
        cmbbxCategory = new ComboBox("Kategória");
        cmbbxAdvertType = new ComboBox("Hirdetés típusa");
        cmbbxAdvertState = new ComboBox("Állapot");
        txtFldPrice = new TextField("Ár");
        txtFldPostalCode = new TextField("Irányítószám");
        lblPicture = new Label("Kép feltöltése:");
        btnAddPicture = new Button("+");
        btnRegister = new Button("Feladás");

        formLayout.addComponent(txtFieldTitle);
        formLayout.addComponent(txtAreaDescription);
        formLayout.addComponent(cmbbxCategory);
        formLayout.addComponent(cmbbxAdvertType);
        formLayout.addComponent(cmbbxAdvertState);
        formLayout.addComponent(txtFldPrice);
        formLayout.addComponent(txtFldPostalCode);
        formLayout.addComponent(btnAddPicture);

        verticalLayout.addComponent(formLayout);
        verticalLayout.setComponentAlignment(formLayout, Alignment.TOP_CENTER);

        return verticalLayout;
    }

}
