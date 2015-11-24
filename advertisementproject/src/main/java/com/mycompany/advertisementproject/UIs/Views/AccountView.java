package com.mycompany.advertisementproject.UIs.Views;

import static com.mycompany.advertisementproject.Enums.StyleNames.NAVBUTTON;
import static com.mycompany.advertisementproject.Enums.Views.ADVERTREG;
import com.mycompany.advertisementproject.entities.Advertisement;
import com.mycompany.advertisementproject.entities.Advertiser;
import com.mycompany.advertisementproject.entities.Letter;
import com.mycompany.advertisementproject.entities.Picture;
import com.mycompany.advertisementproject.facades.AdvertisementFacade;
import com.mycompany.advertisementproject.facades.AdvertstateFacade;
import com.mycompany.advertisementproject.facades.AdverttypeFacade;
import com.mycompany.advertisementproject.facades.LetterFacade;
import com.mycompany.advertisementproject.facades.MaincategoryFacade;
import com.mycompany.advertisementproject.facades.PictureFacade;
import com.mycompany.advertisementproject.facades.SubcategoryFacade;
import com.vaadin.cdi.CDIView;
import com.vaadin.event.LayoutEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

@CDIView("USERPAGE")
public class AccountView extends VerticalLayout implements View {

    private List<Advertisement> myadverts = new ArrayList<>();
    private List<Letter> myletters = new ArrayList<>();

    private TabSheet tabsheet;

    private VerticalLayout advertLayout;
    private VerticalLayout postBoxLayout;

    private Label lblTitleHeader;
    private Label lblTextHeader;
    private Label lblCandidateHeader;
    private Label lblDateHeader;

    private Button btnDeleteAdvert;
    private Button btnModifyAdvert;
    private Label lblAdvertTitle;
    private Label lblAdvertRegDate;
    private Label lblPrice;

//    private Label lblMailTitle;
//    private Label lblMailText;
//    private Label lblCandidate;
//    private Label lblDate;
    private Panel advertPanel;
    private VerticalLayout innerAdvert;
    private Panel postBoxPanel;
    private VerticalLayout innerPostBox;

    private Advertiser current_advertiser;

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
        buildTabs();
        buildView();
    }

    private void buildView() {
        this.setSizeFull();
        this.addComponent(tabsheet);
        this.setComponentAlignment(tabsheet, Alignment.TOP_CENTER);
    }

    private void buildTabs() {
        tabsheet = new TabSheet();

        tabsheet.setWidthUndefined();
        advertLayout = new VerticalLayout();
        tabsheet.addTab(advertLayout).setCaption("Hirdetéseim");
        buildadvertTab();

        postBoxLayout = new VerticalLayout();
        tabsheet.addTab(postBoxLayout).setCaption("Postaláda");
        buildpostBoxTab();

        tabsheet.setWidthUndefined();
    }

    public void buildadvertTab() {
        advertPanel = new Panel();
        advertPanel.setWidth("1000");
        advertPanel.setHeightUndefined();
        innerAdvert = new VerticalLayout();

        addAdvertContent();

        advertPanel.setContent(innerAdvert);
        advertLayout.addComponent(advertPanel);
    }

    private void addAdvertContent() {
        current_advertiser = (Advertiser) VaadinSession.getCurrent().getAttribute("current_user");
        myadverts = advertisementFacade.getMyAdvertisements(current_advertiser);

        for (final Advertisement a : myadverts) {
            btnDeleteAdvert = new Button("Törlés");
            btnDeleteAdvert.setStyleName(NAVBUTTON.toString());
            btnDeleteAdvert.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    try {
                        for (Picture p : a.getPictureCollection()) {
                            pictureFacade.remove(p);
                        }
                        advertisementFacade.remove(a);
                        refreshAdvertContent();
                    } catch (Exception ex) {
                        Notification.show(ex.getMessage());
                    }
                }
            });
            btnModifyAdvert = new Button("Módosítás");
            btnModifyAdvert.addClickListener(new Button.ClickListener() {

                @Override
                public void buttonClick(Button.ClickEvent event) {
                    try {
                        VaadinSession.getCurrent().getLockInstance().lock();
                        VaadinSession.getCurrent().setAttribute("AdvertToModify", a);
                    } finally {
                        VaadinSession.getCurrent().getLockInstance().unlock();
                    }
                    getUI().getNavigator().navigateTo(ADVERTREG.toString());
                }
            });
            btnModifyAdvert.setStyleName(NAVBUTTON.toString());

            lblAdvertTitle = new Label(a.getTitle());
            lblAdvertTitle.setWidth("200");

            String formattedDate = new SimpleDateFormat("yyyy MMMM dd").format(a.getRegistrationDate());
            lblAdvertRegDate = new Label(formattedDate);
            lblAdvertRegDate.setWidth("200");

            lblPrice = new Label(a.getPrice() + " Ft");
            lblPrice.setWidth("200");

            HorizontalLayout myadvertsLayout = new HorizontalLayout();

            myadvertsLayout.addComponent(lblAdvertTitle);
            myadvertsLayout.addComponent(lblAdvertRegDate);
            myadvertsLayout.addComponent(lblPrice);
            myadvertsLayout.addComponent(btnDeleteAdvert);
            myadvertsLayout.addComponent(new Label("/"));
            myadvertsLayout.addComponent(btnModifyAdvert);

            myadvertsLayout.setWidthUndefined();
            myadvertsLayout.setSpacing(true);
            innerAdvert.setMargin(true);
            innerAdvert.setDefaultComponentAlignment(Alignment.TOP_CENTER);
            innerAdvert.addComponent(myadvertsLayout);
        }
    }

    private void refreshAdvertContent() {
        innerAdvert.removeAllComponents();
        addAdvertContent();
    }

    private void buildpostBoxTab() {
        postBoxPanel = new Panel();
        postBoxPanel.setWidth("1000");
        postBoxPanel.setHeightUndefined();
        innerPostBox = new VerticalLayout();

        addPostBoxContent();

        postBoxPanel.setContent(innerPostBox);
        postBoxLayout.addComponent(postBoxPanel);
    }

    private void addPostBoxContent() {
        current_advertiser = (Advertiser) VaadinSession.getCurrent().getAttribute("current_user");
        myletters = letterFacade.getMyLetters(current_advertiser);

        VerticalLayout hlIncomingLetters = new VerticalLayout();
        hlIncomingLetters.setDefaultComponentAlignment(Alignment.TOP_LEFT);
        hlIncomingLetters.setMargin(true);
        VerticalLayout hlOutconingLetters = new VerticalLayout();
        hlOutconingLetters.setDefaultComponentAlignment(Alignment.TOP_LEFT);
        hlOutconingLetters.setMargin(true);

        TabSheet letterTabSheet = new TabSheet();
        letterTabSheet.setWidth("800");
        letterTabSheet.addTab(hlIncomingLetters).setCaption("Bejövő");
        letterTabSheet.addTab(hlOutconingLetters).setCaption("Kimenő");

        lblTitleHeader = new Label("Tárgy");
        lblTitleHeader.setWidth("200");
        lblTextHeader = new Label("Üzenet");
        lblTextHeader.setWidth("200");
        lblCandidateHeader = new Label("Feladó");
        lblCandidateHeader.setWidth("200");
        lblDateHeader = new Label("Dátum");
        lblDateHeader.setWidth("200");

        HorizontalLayout hlHeader = new HorizontalLayout();

        hlHeader.addComponent(lblTitleHeader);
        hlHeader.addComponent(lblTextHeader);
        hlHeader.addComponent(lblCandidateHeader);
        hlHeader.addComponent(lblDateHeader);

        hlOutconingLetters.addComponent(hlHeader);
        hlOutconingLetters.addComponent(new Label("<hr />", ContentMode.HTML));
        hlIncomingLetters.addComponent(hlHeader);
        hlIncomingLetters.addComponent(new Label("<hr />", ContentMode.HTML));

        for (final Letter letter : myletters) {
            HorizontalLayout hlLetters = new HorizontalLayout();
            hlLetters.setSpacing(true);

            Label lblMailTitle = new Label(letter.getMailtitle());
            lblMailTitle.setWidth("200");

            String text = letter.getMailtext();
            if(text.length() > 20){
                text = text.substring(0, 20);
            }
            Label lblMailText = new Label(text);
            lblMailText.setWidth("200");

            Label lblCandidate = new Label(letter.getSendername());
            lblCandidate.setWidth("200");

            Label lblDate = new Label("2015-01-01");
            lblDate.setWidth("200");

            hlLetters.addComponent(lblMailTitle);
            hlLetters.addComponent(lblMailText);
            hlLetters.addComponent(lblCandidate);
            hlLetters.addComponent(lblDate);
            hlLetters.addLayoutClickListener(new LayoutEvents.LayoutClickListener() {

                @Override
                public void layoutClick(LayoutEvents.LayoutClickEvent event) {
                    try {
                        VaadinSession.getCurrent().getLockInstance().lock();
                        VaadinSession.getCurrent().setAttribute("letterToShow", letter);
                    } finally {
                        VaadinSession.getCurrent().getLockInstance().unlock();
                    }
                    getUI().getNavigator().navigateTo("LETTERVIEW");
                }
            });

            if (letter.getSender() == false) {
                hlIncomingLetters.addComponent(hlLetters);
            } else {
                hlOutconingLetters.addComponent(hlLetters);
            }
        }

        innerPostBox.setMargin(true);
        innerPostBox.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        innerPostBox.addComponent(letterTabSheet);
    }

}
