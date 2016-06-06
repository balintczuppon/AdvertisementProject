package com.mycompany.advertisementproject.view.vaadinviews;

import static com.mycompany.advertisementproject.enumz.StyleNames.NAVBUTTON;
import com.mycompany.advertisementproject.control.AccountController;
import com.mycompany.advertisementproject.model.entities.Advertisement;
import com.mycompany.advertisementproject.model.entities.Letter;
import com.mycompany.advertisementproject.toolz.AppBundle;
import com.mycompany.advertisementproject.toolz.I18Helper;
import com.vaadin.cdi.CDIView;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.vaadin.dialogs.ConfirmDialog;

/**
 *
 * @author Czuppon Balint Peter
 */
@CDIView("USERPAGE")
public class AccountView extends VerticalLayout implements View {

    @Inject
    private AccountController controller;

    private static boolean availability;

    private String advertTabText;
    private String postboxTabText;
    private String incoming;
    private String outgoing;
    private String advertTblTitle;
    private String advertTblDate;
    private String advertTblPrice;
    private String advertTblDelete;
    private String advertTblModify;
    private String letterTblSubject;
    private String letterTblMessage;
    private String letterTblSender;
    private String letterTblDate;
    private String advertPanelWidth;
    private String postboxPanelWidth;
    private String letterTabSheetWidth;
    private String tableLetterWidth;
    private String tableAdvertWidth;
    private String delButtonText;
    private String modButtonText;
    private String confirmation;
    private String confirmDelete;
    private String confimYes;
    private String confirmNo;

    private VerticalLayout advertLayout;
    private VerticalLayout postBoxLayout;
    private VerticalLayout hlInComingLetters;
    private VerticalLayout hlOutGoingLetters;
    private VerticalLayout innerAdvert;
    private VerticalLayout innerPostBox;

    private Table tblAdverts;
    private Table tbloutgLetters;
    private Table tblIncLetters;

    private TabSheet tabsheet;
    private TabSheet letterTabSheet;

    private Button btnDeleteAdvert;
    private Button btnModifyAdvert;

    private Panel advertPanel;
    private Panel postBoxPanel;

    private I18Helper i18Helper;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if (!availability) {
            getUI().getNavigator().navigateTo("");
        } else {
            getUI().focus();
        }
        try {
            addAdvertContent();
            addPostBoxContent();
        } catch (Exception ex) {
            Logger.getLogger(AccountView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @PostConstruct
    public void initComponent() {
        i18Helper = new I18Helper(AppBundle.currentBundle());
        controller.setView(this);
        build();
    }

    public void build() {
        try {
            updateStrings();
            buildTabs();
            buildView();

        } catch (Exception ex) {
            Logger.getLogger(AccountView.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void buildView() {
        this.setSizeFull();
        this.addComponent(tabsheet);
        this.setComponentAlignment(tabsheet, Alignment.TOP_CENTER);
    }

    private void buildTabs() throws Exception {
        tabsheet = new TabSheet();
        tabsheet.setWidthUndefined();
        addAdvertTab();
        addPostBoxTab();
    }

    private void addAdvertTab() throws Exception {
        advertLayout = new VerticalLayout();
        tabsheet.addTab(advertLayout).setCaption(advertTabText);
        buildadvertTab();
    }

    private void addPostBoxTab() throws Exception {
        postBoxLayout = new VerticalLayout();
        tabsheet.addTab(postBoxLayout).setCaption(postboxTabText);
        buildpostBoxTab();
    }

    public void buildadvertTab() throws Exception {
        advertPanel = new Panel();
        advertPanel.setWidth(advertPanelWidth);
        advertPanel.setHeightUndefined();
        innerAdvert = new VerticalLayout();

        addAdvertContent();

        advertPanel.setContent(innerAdvert);
        advertLayout.addComponent(advertPanel);
    }

    private void buildpostBoxTab() throws Exception {
        postBoxPanel = new Panel();
        postBoxPanel.setWidth(postboxPanelWidth);
        postBoxPanel.setHeightUndefined();
        innerPostBox = new VerticalLayout();

        addPostBoxContent();

        postBoxPanel.setContent(innerPostBox);
        postBoxLayout.addComponent(postBoxPanel);
    }

    private void addAdvertContent() throws Exception {
        innerAdvert.removeAllComponents();
        controller.fillAdverts();
        createAdvertTable();
        createAdvertButtons();
        controller.populateAdverts();
        innerAdvert.setMargin(true);
        innerAdvert.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        innerAdvert.addComponent(tblAdverts);
    }

    private void createAdvertTable() {
        tblAdverts = new Table();
        tblAdverts.setWidth(tableAdvertWidth);
        tblAdverts
                .addContainerProperty(advertTblTitle, String.class, null);
        tblAdverts.addContainerProperty(advertTblDate, String.class, null);
        tblAdverts.addContainerProperty(advertTblPrice, String.class, null);
        tblAdverts.addContainerProperty(advertTblDelete, Button.class, null);
        tblAdverts.addContainerProperty(advertTblModify, Button.class, null);
    }

    public void createAdvertButtons() {
        btnDeleteAdvert = new Button(delButtonText);
        btnDeleteAdvert.setStyleName(NAVBUTTON.toString());
        btnModifyAdvert = new Button(modButtonText);
        btnModifyAdvert.setStyleName(NAVBUTTON.toString());
    }

    public void addListenerToBtnDelete(final Advertisement a) {
        btnDeleteAdvert.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                confirmDelete(a);
            }
        });
    }

    private void confirmDelete(final Advertisement a) {
        ConfirmDialog.show(this.getUI(), confirmation, confirmDelete, confimYes, confirmNo, new ConfirmDialog.Listener() {
            @Override
            public void onClose(ConfirmDialog dialog) {
                if (dialog.isConfirmed()) {
                    try {
                        controller.deletePicture(a);
                        addAdvertContent();
                    } catch (Exception ex) {
                        Logger.getLogger(AccountView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }

    public void addListenerToBtnModify(final Advertisement a) {
        btnModifyAdvert.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                controller.modifyAdvert(a);
            }
        });
    }

    private void addPostBoxContent() throws Exception {
        innerPostBox.removeAllComponents();
        controller.fillLetters();
        createLetterLayouts();
        createLetterTabSheet();
        createTableForInComingLetters();
        createTableForOutGoingLetters();
        controller.popluateLetters();
        hlInComingLetters.addComponent(tblIncLetters);
        hlOutGoingLetters.addComponent(tbloutgLetters);
        innerPostBox.setMargin(true);
        innerPostBox.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        innerPostBox.addComponent(letterTabSheet);
    }

    private void createLetterLayouts() {
        hlInComingLetters = new VerticalLayout();
        hlInComingLetters.setDefaultComponentAlignment(Alignment.TOP_LEFT);
        hlInComingLetters.setMargin(true);
        hlOutGoingLetters = new VerticalLayout();
        hlOutGoingLetters.setDefaultComponentAlignment(Alignment.TOP_LEFT);
        hlOutGoingLetters.setMargin(true);
    }

    private void createLetterTabSheet() {
        letterTabSheet = new TabSheet();
        letterTabSheet.setWidth(letterTabSheetWidth);
        letterTabSheet.addTab(hlInComingLetters).setCaption(incoming);
        letterTabSheet.addTab(hlOutGoingLetters).setCaption(outgoing);
    }

    private void createTableForInComingLetters() {
        tblIncLetters = new Table();
        tblIncLetters.setWidth(tableLetterWidth);
        tblIncLetters.addContainerProperty("", Letter.class, null);
        tblIncLetters.addContainerProperty(letterTblSubject, String.class, null);
        tblIncLetters.addContainerProperty(letterTblMessage, String.class, null);
        tblIncLetters.addContainerProperty(letterTblSender, String.class, null);
        tblIncLetters.addContainerProperty(letterTblDate, String.class, null);
        addListenerToTblIncLetters();
    }

    private void createTableForOutGoingLetters() {
        tbloutgLetters = new Table();
        tbloutgLetters.setWidth(tableLetterWidth);
        tbloutgLetters.addContainerProperty("", Letter.class, null);
        tbloutgLetters.addContainerProperty(letterTblSubject, String.class, null);
        tbloutgLetters.addContainerProperty(letterTblMessage, String.class, null);
        tbloutgLetters.addContainerProperty(letterTblSender, String.class, null);
        tbloutgLetters.addContainerProperty(letterTblDate, String.class, null);
        addListenerToTblOutgLetters();
    }

    public void addListenerToTblIncLetters() {
        tblIncLetters.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent event) {
                controller.selectInComingLetter(event);
            }
        });
    }

    public void addListenerToTblOutgLetters() {
        tbloutgLetters.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent event) {
                controller.selectOutGoingLetter(event);
            }
        });
    }

    public void updateStrings() {
        advertTabText = i18Helper.getMessage("Adverts");
        postboxTabText = i18Helper.getMessage("PostBox");
        advertPanelWidth = i18Helper.getMessage("Account.AdvertPanelWidth");
        postboxPanelWidth = i18Helper.getMessage("Account.PostBoxPanelWidth");
        tableAdvertWidth = i18Helper.getMessage("Account.AdvertTableWidth");
        advertTblTitle = i18Helper.getMessage("Title");
        advertTblPrice = i18Helper.getMessage("Price");
        advertTblDate = i18Helper.getMessage("Date");
        advertTblDelete = i18Helper.getMessage("Delete");
        advertTblModify = i18Helper.getMessage("Modify");
        delButtonText = i18Helper.getMessage("Delete");
        modButtonText = i18Helper.getMessage("Modify");
        letterTabSheetWidth = i18Helper.getMessage("Account.LetterTabSheetWidth");
        incoming = i18Helper.getMessage("Incoming");
        outgoing = i18Helper.getMessage("Outgoing");
        tableLetterWidth = i18Helper.getMessage("Account.IncomingTableWidth");
        tableLetterWidth = i18Helper.getMessage("Account.OutGoingTableWidth");
        letterTblDate = i18Helper.getMessage("Date");
        letterTblMessage = i18Helper.getMessage("Message");
        letterTblSender = i18Helper.getMessage("Sender");
        letterTblSubject = i18Helper.getMessage("Subject");
        confirmation = i18Helper.getMessage("comfirmation");
        confirmDelete = i18Helper.getMessage("comfirmDelete");
        confimYes = i18Helper.getMessage("ComfirmYes");
        confirmNo = i18Helper.getMessage("ComfirmNo");
    }

    public Button getBtnDeleteAdvert() {
        return btnDeleteAdvert;
    }

    public Button getBtnModifyAdvert() {
        return btnModifyAdvert;
    }

    public Table getTblAdverts() {
        return tblAdverts;
    }

    public Table getTblOutgLetters() {
        return tbloutgLetters;
    }

    public Table getTblIncLetters() {
        return tblIncLetters;
    }

    public static void setAvailability(boolean availability) {
        AccountView.availability = availability;
    }
}
