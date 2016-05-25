package com.mycompany.advertisementproject.control;

import static com.mycompany.advertisementproject.enumz.SessionAttributes.ADVERTTOMODIFY;
import static com.mycompany.advertisementproject.enumz.SessionAttributes.CURRENTUSER;
import static com.mycompany.advertisementproject.enumz.SessionAttributes.LETTERTOSHOW;
import static com.mycompany.advertisementproject.enumz.Views.ADVERTMOD;
import static com.mycompany.advertisementproject.enumz.Views.ADVERTREG;
import static com.mycompany.advertisementproject.enumz.Views.LETTER;
import com.mycompany.advertisementproject.view.vaadinviews.AccountView;
import com.mycompany.advertisementproject.model.entities.Advertisement;
import com.mycompany.advertisementproject.model.entities.Advertiser;
import com.mycompany.advertisementproject.model.entities.Letter;
import com.mycompany.advertisementproject.model.entities.Picture;
import com.mycompany.advertisementproject.model.facades.AdvertisementFacade;
import com.mycompany.advertisementproject.model.facades.LetterFacade;
import com.mycompany.advertisementproject.model.facades.PictureFacade;
import com.mycompany.advertisementproject.toolz.Global;
import com.mycompany.advertisementproject.view.vaadinviews.AdvertRegView;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Notification;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

public class AccountController implements Serializable {

    @Inject
    private AdvertisementFacade advertisementFacade;
    @Inject
    private PictureFacade pictureFacade;
    @Inject
    private LetterFacade letterFacade;

    private AccountView view;

    private List<Advertisement> adverts = new ArrayList<>();
    private List<Letter> letters = new ArrayList<>();
    private Advertiser current_advertiser;

    private int k;
    private int j;

    /**
     *
     * @throws Exception
     */
    public void fillAdverts() throws Exception {
        current_advertiser = (Advertiser) VaadinSession.getCurrent().getAttribute(CURRENTUSER.toString());
        adverts = advertisementFacade.getMyAdvertisements(current_advertiser);
    }

    /**
     *
     */
    public void populateAdverts() {
        int i = 1;
        for (final Advertisement a : adverts) {
            String date = Global.DATEFORMAT.format(a.getRegistrationDate());
            view.createAdvertButtons();
            view.addListenerToBtnDelete(a);
            view.addListenerToBtnModify(a);
            view.getTblAdverts().addItem(new Object[]{a.getTitle(),
                date,
                getPrice(a),
                view.getBtnDeleteAdvert(),
                view.getBtnModifyAdvert()
            }, i);
            i++;
        }
    }

    private String getPrice(Advertisement a) {
        if (a.getPrice() != null) {
            return Global.CURRENCY.format(a.getPrice());
        }
        return null;
    }

    /**
     *
     * @param a
     */
    public void deletePicture(Advertisement a) {
        try {
            for (Picture p : a.getPictureCollection()) {
                pictureFacade.remove(p);
            }
            advertisementFacade.remove(a);
        } catch (Exception ex) {
            Notification.show(ex.getMessage());
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param a
     */
    public void modifyAdvert(Advertisement a) {
        try {
            VaadinSession.getCurrent().getLockInstance().lock();
            VaadinSession.getCurrent().setAttribute(ADVERTTOMODIFY.toString(), a);
        } finally {
            VaadinSession.getCurrent().getLockInstance().unlock();
        }
        view.getUI().getNavigator().navigateTo(ADVERTMOD.toString());
    }

    /**
     *
     * @throws Exception
     */
    public void fillLetters() throws Exception {
        current_advertiser = (Advertiser) VaadinSession.getCurrent().getAttribute(CURRENTUSER.toString());
        letters = letterFacade.getMyLetters(current_advertiser);
    }

    /**
     *
     */
    public void popluateLetters() {
        k = 1;
        j = 1;
        String date;

        for (final Letter letter : letters) {
            if (letter.getSendDate() != null) {
                date = Global.DATEFORMAT.format(letter.getSendDate());
            } else {
                date = null;
            }
            Object object[] = new Object[]{
                letter,
                letter.getMailtitle(),
                letter.getMailtext(),
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

//    private String formLetterText(String text) {
//        if (text.length() > Integer.valueOf(view.getLetterTextBoundary())) {
//            text = text.substring(0, Integer.valueOf(view.getLetterTextBoundary()));
//        }
//        return text;
//    }

    /**
     *
     * @param event
     */
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

    /**
     *
     * @param event
     */
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

    public void setView(AccountView view) {
        this.view = view;
    }
}
