package com.mycompany.advertisementproject.view.vaadinviews;

import com.mycompany.advertisementproject.control.LetterController;
import com.mycompany.advertisementproject.model.entities.Letter;
import com.mycompany.advertisementproject.model.facades.LetterFacade;
import com.mycompany.advertisementproject.toolz.AppBundle;
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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

@CDIView("LETTER")
public class LetterView extends VerticalLayout implements View {

    private ResourceBundle bundle;

    private static boolean availability = false;

    private String panelWidth;
    private String lblEnquirerCaption;
    private String lblTitleCaption;
    private String lblTextCaption;
    private String taLetterToShowWidth;
    private String btnAnswerMailCaption;
    private String btnDeleteMailCaption;
    private String btnBackCaption;
    private String taLetterToWritePrompt;
    private String taLetterToWriteWidth;
    private String taLetterToWriteHeight;
    private String responsePrefix;
    private String pageLink;
    private String viewName;
    private String linkText;
    private String greetingText;
    private String messageText1;
    private String messageText2;
    private String goodbyeText;
    private String senderName;

    private Label lblMailTitle;
    private Label lblText;
    private Label lblEnquirer;
    private Label lblEnquirerName;
    private Label lblTitle;

    private Button btnAnswerMail;
    private Button btnDeleteMail;
    private Button btnBack;

    private TextArea taLetterToWrite;
    private TextArea taLetterToShow;

    private LetterController controller;

    private HorizontalLayout hl;

    private VerticalLayout vlLetter;

    private Panel panel;

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
        if (availability) {
            bundle = AppBundle.currentBundle();
            defaultSettings();
            build();
        }
    }

    public void defaultSettings() {
        controller = new LetterController(this);
        controller.setLetterFacade(letterFacade);
        vlLetter = new VerticalLayout();
        vlLetter.setMargin(true);
        vlLetter.setSpacing(true);
        setMargin(true);
    }

    public void build() {
        updateStrings();
        initPanel();
    }

    private void initPanel() {
        panel = new Panel();
        panel.setWidth(panelWidth);
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
        lblEnquirer = new Label(lblEnquirerCaption);
        lblEnquirerName = new Label(letter.getSendername());

        lblTitle = new Label(lblTitleCaption);
        lblMailTitle = new Label(letter.getMailtitle());
        lblMailTitle.setWidthUndefined();

        lblText = new Label(lblTextCaption);
        taLetterToShow = new TextArea();
        taLetterToShow.setWidth(taLetterToShowWidth);
        taLetterToShow.setHeightUndefined();
        taLetterToShow.setValue(letter.getMailtext());
        taLetterToShow.setReadOnly(true);
        taLetterToShow.setWordwrap(true);

        btnAnswerMail = new Button(btnAnswerMailCaption);
        btnDeleteMail = new Button(btnDeleteMailCaption);
        btnBack = new Button(btnBackCaption);
        addTextArea();
    }

    private void addTextArea() {
        taLetterToWrite = new TextArea();
        taLetterToWrite.setInputPrompt(taLetterToWritePrompt);
        taLetterToWrite.setWidth(taLetterToWriteWidth);
        taLetterToWrite.setHeight(taLetterToWriteHeight);
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
        addAnswerButtonListener(letter);
        addDeleteButtonListener(letter);
        addBackButtonListener();
    }

    private void addAnswerButtonListener(final Letter letter) {
        btnAnswerMail.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    controller.response(letter);
                } catch (Exception ex) {
                    Logger.getLogger(LetterView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void addDeleteButtonListener(final Letter letter) {
        btnDeleteMail.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    controller.deleteLetter(letter);
                } catch (Exception ex) {
                    Logger.getLogger(LetterView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void addBackButtonListener() {
        btnBack.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                controller.jumpBack();
            }
        });
    }

    public void updateStrings() {
        panelWidth = bundle.getString("Letter.PanelWidth");
        lblEnquirerCaption = bundle.getString("TfSender");
        lblTitleCaption = bundle.getString("TfSubject");
        lblTextCaption = bundle.getString("TfText");
        taLetterToShowWidth = bundle.getString("Letter.TaLetterToShowWidth");
        btnAnswerMailCaption = bundle.getString("Answer");
        btnDeleteMailCaption = bundle.getString("Delete");
        btnBackCaption = bundle.getString("Back");
        taLetterToWritePrompt = bundle.getString("TaMessage");
        taLetterToWriteWidth = bundle.getString("Letter.TaLetterToWriteWidth");
        taLetterToWriteHeight = bundle.getString("Letter.TaLetterToWriteHeight");
        responsePrefix = bundle.getString("responsePrefix");
        pageLink = bundle.getString("Letter.PageLink");
        linkText = bundle.getString("Letter.LinkText");
        greetingText = bundle.getString("Letter.GreetingText");
        messageText1 = bundle.getString("Letter.MessageText1");
        messageText2 = bundle.getString("Letter.MessageText2");
        goodbyeText = bundle.getString("Letter.GoodbyeText");
        senderName = bundle.getString("Letter.Sendername");
        viewName = bundle.getString("Letter.ViewName");
    }

    public TextArea getTaLetterToWrite() {
        return taLetterToWrite;
    }

    public TextArea getTaLetterToShow() {
        return taLetterToShow;
    }

    public String getResponsePrefix() {
        return responsePrefix;
    }

    public String getPageLink() {
        return pageLink;
    }

    public String getViewName() {
        return viewName;
    }

    public String getLinkText() {
        return linkText;
    }

    public String getGreetingText() {
        return greetingText;
    }

    public String getMessageText1() {
        return messageText1;
    }

    public String getMessageText2() {
        return messageText2;
    }

    public String getGoodbyeText() {
        return goodbyeText;
    }

    public String getSenderName() {
        return senderName;
    }
    
    public static void setAvailability(boolean availability) {
        LetterView.availability = availability;
    }

}
