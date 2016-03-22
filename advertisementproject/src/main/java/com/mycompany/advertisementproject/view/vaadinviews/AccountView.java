package com.mycompany.advertisementproject.view.vaadinviews;

import static com.mycompany.advertisementproject.enumz.StyleNames.NAVBUTTON;
import com.mycompany.advertisementproject.control.AccountController;
import com.mycompany.advertisementproject.toolz.XmlFileReader;
import com.mycompany.advertisementproject.model.entities.Advertisement;
import com.mycompany.advertisementproject.model.entities.Letter;
import com.mycompany.advertisementproject.model.facades.AdvertisementFacade;
import com.mycompany.advertisementproject.model.facades.AdvertstateFacade;
import com.mycompany.advertisementproject.model.facades.AdverttypeFacade;
import com.mycompany.advertisementproject.model.facades.LetterFacade;
import com.mycompany.advertisementproject.model.facades.MaincategoryFacade;
import com.mycompany.advertisementproject.model.facades.PictureFacade;
import com.mycompany.advertisementproject.model.facades.SubcategoryFacade;
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

@CDIView("USERPAGE")
public class AccountView extends VerticalLayout implements View {

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
    private String letterTextBoundary;

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

    private XmlFileReader xmlReader;

    private AccountController controller;

    @Inject
    MaincategoryFacade maincategoryFacade;
    @Inject
    SubcategoryFacade subcategoryFacade;
    @Inject
    PictureFacade pictureFacade;
    @Inject
    AdverttypeFacade adverttypeFacade;
    @Inject
    AdvertisementFacade advertisementFacade;
    @Inject
    AdvertstateFacade advertstateFacade;
    @Inject
    LetterFacade letterFacade;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        getUI().focus();
    }

    @PostConstruct
    public void initComponent() {
        if (availability) {
            try {
                controller = new AccountController(this);
                readLabelText();
                buildTabs();
                buildView();
            } catch (Exception ex) {
                Logger.getLogger(AccountView.class.getName()).log(Level.SEVERE, null, ex);
            }
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
        tblAdverts.addContainerProperty(advertTblTitle, String.class, null);
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
                try {
                    controller.deletePicture(a);
                    refreshAdvertContent();
                } catch (Exception ex) {
                    Logger.getLogger(AccountView.class.getName()).log(Level.SEVERE, null, ex);
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

    private void refreshAdvertContent() throws Exception {
        innerAdvert.removeAllComponents();
        addAdvertContent();
    }

    private void addPostBoxContent() throws Exception {
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

    private void readLabelText() {
        try {
            xmlReader = new XmlFileReader();
            xmlReader.setAccView(this);
            xmlReader.setTagName(this.getClass().getSimpleName());
            xmlReader.readXml();
        } catch (Exception ex) {
            Logger.getLogger(AccountView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public AdvertisementFacade getAdvertisementFacade() {
        return advertisementFacade;
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

    public MaincategoryFacade getMaincategoryFacade() {
        return maincategoryFacade;
    }

    public SubcategoryFacade getSubcategoryFacade() {
        return subcategoryFacade;
    }

    public PictureFacade getPictureFacade() {
        return pictureFacade;
    }

    public AdverttypeFacade getAdverttypeFacade() {
        return adverttypeFacade;
    }

    public AdvertstateFacade getAdvertstateFacade() {
        return advertstateFacade;
    }

    public LetterFacade getLetterFacade() {
        return letterFacade;
    }

    public Panel getAdvertPanel() {
        return advertPanel;
    }

    public Panel getPostBoxPanel() {
        return postBoxPanel;
    }

    public TabSheet getLetterTabSheet() {
        return letterTabSheet;
    }

    public void setAdvertTabText(String advertTabText) {
        this.advertTabText = advertTabText;
    }

    public void setAdvertTblTitle(String advertTblTitle) {
        this.advertTblTitle = advertTblTitle;
    }

    public void setAdvertTblDate(String advertTblDate) {
        this.advertTblDate = advertTblDate;
    }

    public void setAdvertTblPrice(String advertTblPrice) {
        this.advertTblPrice = advertTblPrice;
    }

    public void setAdvertTblDelete(String advertTblDelete) {
        this.advertTblDelete = advertTblDelete;
    }

    public void setAdvertTblModify(String advertTblModify) {
        this.advertTblModify = advertTblModify;
    }

    public void setLetterTblSubject(String letterTblSubject) {
        this.letterTblSubject = letterTblSubject;
    }

    public void setLetterTblMessage(String letterTblMessage) {
        this.letterTblMessage = letterTblMessage;
    }

    public void setLetterTblSender(String letterTblSender) {
        this.letterTblSender = letterTblSender;
    }

    public void setLetterTblDate(String letterTblDate) {
        this.letterTblDate = letterTblDate;
    }

    public void setIncoming(String incoming) {
        this.incoming = incoming;
    }

    public void setOutgoing(String outgoing) {
        this.outgoing = outgoing;
    }

    public void setAdvertPanelWidth(String advertPanelWidth) {
        this.advertPanelWidth = advertPanelWidth;
    }

    public void setPostboxPanelWidth(String letterPanelWidth) {
        this.postboxPanelWidth = letterPanelWidth;
    }

    public void setLetterTabSheetWidth(String letterTabSheetWidth) {
        this.letterTabSheetWidth = letterTabSheetWidth;
    }

    public void setTableLetterWidth(String tableLetterWidth) {
        this.tableLetterWidth = tableLetterWidth;
    }

    public void setTableAdvertWidth(String tableAdvertWidth) {
        this.tableAdvertWidth = tableAdvertWidth;
    }

    public void setPostboxTabText(String postboxTabText) {
        this.postboxTabText = postboxTabText;
    }

    public void setDelButtonText(String delButtonText) {
        this.delButtonText = delButtonText;
    }

    public void setModButtonText(String modButtonText) {
        this.modButtonText = modButtonText;
    }

    public String getLetterTextBoundary() {
        return letterTextBoundary;
    }

    public void setLetterTextBoundary(String letterTextBoundary) {
        this.letterTextBoundary = letterTextBoundary;
    }

    public static void setAvailability(boolean availability) {
        AccountView.availability = availability;
    }
}
