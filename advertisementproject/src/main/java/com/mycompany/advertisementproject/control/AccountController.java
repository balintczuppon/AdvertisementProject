package com.mycompany.advertisementproject.control;

import static com.mycompany.advertisementproject.Enums.SessionAttributes.ADVERTTOMODIFY;
import static com.mycompany.advertisementproject.Enums.SessionAttributes.CURRENTUSER;
import static com.mycompany.advertisementproject.Enums.SessionAttributes.LETTERTOSHOW;
import static com.mycompany.advertisementproject.Enums.Views.ADVERTREG;
import static com.mycompany.advertisementproject.Enums.Views.LETTER;
import com.mycompany.advertisementproject.Layouts.AppLayout;
import com.mycompany.advertisementproject.vaadinviews.AccountView;
import com.mycompany.advertisementproject.entities.Advertisement;
import com.mycompany.advertisementproject.entities.Advertiser;
import com.mycompany.advertisementproject.entities.Letter;
import com.mycompany.advertisementproject.entities.Picture;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Notification;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountController {

    private AccountView view;

    private List<Advertisement> adverts = new ArrayList<>();
    private List<Letter> letters = new ArrayList<>();
    private Advertiser current_advertiser;

    private int k;
    private int j;

    public AccountController(AccountView view) {
        this.view = view;
    }

    public void fillAdverts() throws Exception {
        current_advertiser = (Advertiser) VaadinSession.getCurrent().getAttribute(CURRENTUSER.toString());
        adverts = view.getAdvertisementFacade().getMyAdvertisements(current_advertiser);
    }

    public void populateAdverts() {
        int i = 1;
        for (final Advertisement a : adverts) {
            view.createAdvertButtons();
            view.addListenerToBtnDelete(a);
            view.addListenerToBtnModify(a);

            String date = AppLayout.formattedDate.format(a.getRegistrationDate());
            view.getTblAdverts().addItem(new Object[]{a.getTitle(),
                date,
                a.getPrice() + AppLayout.currency,
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
            VaadinSession.getCurrent().setAttribute(ADVERTTOMODIFY.toString(), a);
        } finally {
            VaadinSession.getCurrent().getLockInstance().unlock();
        }
        view.getUI().getNavigator().navigateTo(ADVERTREG.toString());
    }

    public void fillLetters() throws Exception {
        current_advertiser = (Advertiser) VaadinSession.getCurrent().getAttribute(CURRENTUSER.toString());
        letters = view.getLetterFacade().getMyLetters(current_advertiser);
    }

    public void popluateLetters() {
        k = 1;
        j = 1;

        String date;

        for (final Letter letter : letters) {

            if (letter.getSendDate() != null) {
                date = AppLayout.formattedDate.format(letter.getSendDate());
            } else {
                date = null;
            }

            Object object[] = new Object[]{
                letter,
                letter.getMailtitle(),
                formLetterText(letter.getMailtext()),
                letter.getSendername(),
                date

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
            VaadinSession.getCurrent().setAttribute(LETTERTOSHOW.toString(), (Letter) object);
        } finally {
            VaadinSession.getCurrent().getLockInstance().unlock();
        }
        view.getUI().getNavigator().navigateTo(LETTER.toString());
    }

    public void selectOutGoingLetter(ItemClickEvent event) {
        Object object = event.getItem().getItemProperty("").getValue();
        try {
            VaadinSession.getCurrent().getLockInstance().lock();
            VaadinSession.getCurrent().setAttribute(LETTERTOSHOW.toString(), (Letter) object);
        } finally {
            VaadinSession.getCurrent().getLockInstance().unlock();
        }
        view.getUI().getNavigator().navigateTo(LETTER.toString());
    }
}
