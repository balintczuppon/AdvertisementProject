package com.mycompany.advertisementproject.view.vaadinviews;

import com.mycompany.advertisementproject.control.LetterController;
import com.mycompany.advertisementproject.model.entities.Letter;
import com.mycompany.advertisementproject.model.facades.LetterFacade;
import com.mycompany.advertisementproject.toolz.AppBundle;
import com.mycompany.advertisementproject.toolz.I18Helper;
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
import org.apache.commons.lang3.StringUtils;

@CDIView("LETTER")
public class LetterView extends VerticalLayout implements View {

    @Inject
    private LetterController controller;

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

    private HorizontalLayout hl;

    private VerticalLayout vlLetter;

    private Panel panel;

    private I18Helper i18Helper;

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
            build();
        }
    }

    public void defaultSettings() {
        controller.setView(this);
        vlLetter = new VerticalLayout();
        vlLetter.setMargin(true);
        vlLetter.setSpacing(true);
        setMargin(true);
    }

    public void build() {
        i18Helper = new I18Helper(AppBundle.currentBundle());
        defaultSettings();
        updateStrings();
        initPanel();
        createFields();
        addButtonLayout();
        addLetterLayout();
    }

    private void initPanel() {
        if (panel == null) {
            panel = new Panel();
            panel.setWidth(panelWidth);
            panel.setHeightUndefined();
        }
        panel.setContent(vlLetter);
    }

    public void showLetter(final Letter letter) {
        initFields(letter);
        addClickListeners(letter);
    }

    private void createFields() {
        lblEnquirer = new Label(lblEnquirerCaption);
        lblEnquirerName = new Label();
        lblTitle = new Label(lblTitleCaption);
        lblMailTitle = new Label();
        lblMailTitle.setWidthUndefined();
        lblText = new Label(lblTextCaption);

        taLetterToShow = new TextArea();
        taLetterToShow.setWidth(taLetterToShowWidth);
        taLetterToShow.setHeight(taLetterToWriteHeight);
        taLetterToShow.setWordwrap(true);
        taLetterToShow.setEnabled(false);

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

    private void initFields(Letter letter) {
        lblEnquirerName.setValue(StringUtils.defaultString(letter.getSendername()));
        lblMailTitle.setValue(StringUtils.defaultString(letter.getMailtitle()));
        taLetterToShow.setValue(StringUtils.defaultString(letter.getMailtext()));
    }

    private void addButtonLayout() {
        hl = new HorizontalLayout();
        hl.setWidthUndefined();
        hl.setSpacing(true);

        hl.addComponent(btnAnswerMail);
        hl.addComponent(btnDeleteMail);
        hl.addComponent(btnBack);
    }

    private void addLetterLayout() {
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
        panelWidth = i18Helper.getMessage("Letter.PanelWidth");
        lblEnquirerCaption = i18Helper.getMessage("TfSender");
        lblTitleCaption = i18Helper.getMessage("TfSubject");
        lblTextCaption = i18Helper.getMessage("TfText");
        taLetterToShowWidth = i18Helper.getMessage("Letter.TaLetterToShowWidth");
        btnAnswerMailCaption = i18Helper.getMessage("Answer");
        btnDeleteMailCaption = i18Helper.getMessage("Delete");
        btnBackCaption = i18Helper.getMessage("Back");
        taLetterToWritePrompt = i18Helper.getMessage("TaMessage");
        taLetterToWriteWidth = i18Helper.getMessage("Letter.TaLetterToWriteWidth");
        taLetterToWriteHeight = i18Helper.getMessage("Letter.TaLetterToWriteHeight");
        responsePrefix = i18Helper.getMessage("responsePrefix");
        pageLink = i18Helper.getMessage("Pagelink");
        linkText = i18Helper.getMessage("Letter.LinkText");
        greetingText = i18Helper.getMessage("Letter.GreetingText");
        messageText1 = i18Helper.getMessage("Letter.MessageText1");
        messageText2 = i18Helper.getMessage("Letter.MessageText2");
        goodbyeText = i18Helper.getMessage("Letter.GoodbyeText");
        senderName = i18Helper.getMessage("Letter.Sendername");
        viewName = i18Helper.getMessage("Letter.ViewName");
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
