package com.mycompany.advertisementproject.Enums.control;

import static com.mycompany.advertisementproject.Enums.Views.ADVERTREG;
import com.mycompany.advertisementproject.UIs.Views.AccountView;
import com.mycompany.advertisementproject.entities.Advertisement;
import com.mycompany.advertisementproject.entities.Advertiser;
import com.mycompany.advertisementproject.entities.Letter;
import com.mycompany.advertisementproject.entities.Picture;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Notification;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AccountController {

    private AccountView view;

    private List<Advertisement> adverts = new ArrayList<>();
    private List<Letter> letters = new ArrayList<>();
    private Advertiser current_advertiser;

    private final SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy MMMM dd");

    private int k;
    private int j;

    public AccountController(AccountView view) {
        this.view = view;
    }

    public void fillAdverts() throws Exception {
        current_advertiser = (Advertiser) VaadinSession.getCurrent().getAttribute("current_user");
        adverts = view.getAdvertisementFacade().getMyAdvertisements(current_advertiser);
    }

    public void populateAdverts() {
        int i = 1;
        for (final Advertisement a : adverts) {
            view.createAdvertButtons();
            view.addListenerToBtnDelete(a);
            view.addListenerToBtnModify(a);

            String date = formattedDate.format(a.getRegistrationDate());
            view.getTblAdverts().addItem(new Object[]{a.getTitle(),
                date,
                a.getPrice() + " HUF",
                view.getBtnDeleteAdvert(),
                view.getBtnModifyAdvert()
            }, i);
            i++;
        }
    }

    public void deletePicture(Advertisement a) {
        try {
            for (Picture p : a.getPictureCollection()) {
                view.getPictureFacade().remove(p);
            }
            view.getAdvertisementFacade().remove(a);
        } catch (Exception ex) {
            Notification.show(ex.getMessage());
        }
    }

    public void modifyAdvert(Advertisement a) {
        try {
            VaadinSession.getCurrent().getLockInstance().lock();
            VaadinSession.getCurrent().setAttribute("AdvertToModify", a);
        } finally {
            VaadinSession.getCurrent().getLockInstance().unlock();
        }
        view.getUI().getNavigator().navigateTo(ADVERTREG.toString());
    }

    public void fillLetters() throws Exception {
        current_advertiser = (Advertiser) VaadinSession.getCurrent().getAttribute("current_user");
        letters = view.getLetterFacade().getMyLetters(current_advertiser);
    }

    public void popluateLetters() {
        k = 1;
        j = 1;
        for (final Letter letter : letters) {
            Object object[] = new Object[]{
                letter,
                letter.getMailtitle(),
                formLetterText(letter.getMailtext()),
                letter.getSendername(),
                "2015-01-01"
            };
            splitLetters(letter, object);
        }
    }

    private void splitLetters(Letter letter, Object[] o) {
        if (letter.getSender() == false) {
            view.getTblIncLetters().addItem(o, k);
            k++;
        } else {
            view.getTblOutgLetters().addItem(o, j);
            j++;
        }
    }

    private String formLetterText(String text) {
        if (text.length() > 20) {
            text = text.substring(0, 20);
        }
        return text;
    }

    public void selectInComingLetter(ItemClickEvent event) {
        Object object = event.getItem().getItemProperty("").getValue();
        try {
            VaadinSession.getCurrent().getLockInstance().lock();
            VaadinSession.getCurrent().setAttribute("letterToShow", (Letter) object);
        } finally {
            VaadinSession.getCurrent().getLockInstance().unlock();
        }
        view.getUI().getNavigator().navigateTo("LETTERVIEW");
    }

    public void selectOutGoingLetter(ItemClickEvent event) {
        Object object = event.getItem().getItemProperty("").getValue();
        try {
            VaadinSession.getCurrent().getLockInstance().lock();
            VaadinSession.getCurrent().setAttribute("letterToShow", (Letter) object);
        } finally {
            VaadinSession.getCurrent().getLockInstance().unlock();
        }
        view.getUI().getNavigator().navigateTo("LETTERVIEW");
    }
}
