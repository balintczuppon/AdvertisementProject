
package com.mycompany.advertisementproject.UIs.Views;

import com.mycompany.advertisementproject.Enums.control.LetterController;
import com.mycompany.advertisementproject.entities.Letter;
import com.mycompany.advertisementproject.facades.LetterFacade;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

@CDIView("LETTERVIEW")
public class LetterView extends VerticalLayout implements View {

    private LetterController controller;

    private HorizontalLayout hl;
    private VerticalLayout vlLetter;

    private Panel panel;

    private Label lblMailTitle;
    private Label lblText;
    private Label lblEnquirer;
    private Label lblEnquirerName;
    private Label lblTitle;

    private TextArea taLetterToWrite;
    private TextArea taLetterToShow;

    private Button btnAnswerMail;
    private Button btnDeleteMail;
    private Button btnBack;

    @Inject
    private LetterFacade letterFacade;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        controller.getParameter(event);
        getUI().focus();
        try {
            controller.checkSessionAttribute();
        } catch (Exception ex) {
            Logger.getLogger(LetterView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @PostConstruct
    public void initComponents() {
        defaultSettings();
        buildView();
    }

    public void defaultSettings() {
        controller = new LetterController(this);
        vlLetter = new VerticalLayout();
        vlLetter.setMargin(true);
        vlLetter.setSpacing(true);
        setMargin(true);
    }

    private void buildView() {
        initPanel();
    }

    private void initPanel() {
        panel = new Panel();
        panel.setWidth("1000");
        panel.setHeightUndefined();
        panel.setContent(vlLetter);
    }

    public void showLetter(final Letter letter) {
        initFields(letter);
        initButtonLayout();
        initLetterLayout();
        addClickListeners(letter);
    }

    private void initFields(Letter letter) {
        lblEnquirer = new Label("Feladó:");
        lblEnquirerName = new Label(letter.getSendername());

        lblTitle = new Label("Tárgy:");
        lblMailTitle = new Label(letter.getMailtitle());
        lblMailTitle.setWidthUndefined();

        lblText = new Label("Üzenet:");
        taLetterToShow = new TextArea();
        taLetterToShow.setWidth("950");
        taLetterToShow.setHeightUndefined();
        taLetterToShow.setValue(letter.getMailtext());
        taLetterToShow.setReadOnly(true);
        taLetterToShow.setWordwrap(true);

        btnAnswerMail = new Button("Válaszolok");
        btnDeleteMail = new Button("Törlöm");
        btnBack = new Button("Vissza");
        addTextArea();
    }

    private void addTextArea() {
        taLetterToWrite = new TextArea();
        taLetterToWrite.setInputPrompt("Ide írhatja az üzetet..");
        taLetterToWrite.setWidth("800");
        taLetterToWrite.setHeight("200");
    }

    private void initButtonLayout() {
        hl = new HorizontalLayout();
        hl.setWidthUndefined();
        hl.setSpacing(true);

        hl.addComponent(btnAnswerMail);
        hl.addComponent(btnDeleteMail);
        hl.addComponent(btnBack);
    }

    private void initLetterLayout() {
        vlLetter.addComponent(lblEnquirer);
        vlLetter.addComponent(lblEnquirerName);
        vlLetter.addComponent(lblTitle);
        vlLetter.addComponent(lblMailTitle);
        vlLetter.addComponent(lblText);
        vlLetter.addComponent(taLetterToShow);
        vlLetter.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        vlLetter.addComponent(new Label("<hr />", ContentMode.HTML));
        vlLetter.addComponent(taLetterToWrite);
        vlLetter.addComponent(hl);
        addComponent(panel);
        setComponentAlignment(panel, Alignment.TOP_CENTER);
    }

    private void addClickListeners(final Letter letter) {
        btnAnswerMail.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                controller.response(letter);
            }
        });
        btnDeleteMail.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                controller.deleteLetter(letter);
            }
        });
        btnBack.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                controller.jumpBack();
            }
        });
    }

    public TextArea getTaLetterToWrite() {
        return taLetterToWrite;
    }

    public TextArea getTaLetterToShow() {
        return taLetterToShow;
    }

    public LetterFacade getLetterFacade() {
        return letterFacade;
    }
    
    
}
