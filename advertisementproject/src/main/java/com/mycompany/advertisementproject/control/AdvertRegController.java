package com.mycompany.advertisementproject.control;

import static com.mycompany.advertisementproject.enumz.SessionAttributes.ADVERTTOMODIFY;
import static com.mycompany.advertisementproject.enumz.SessionAttributes.CURRENTUSER;
import com.mycompany.advertisementproject.toolz.MyMultiFileUpload;
import com.mycompany.advertisementproject.view.vaadinviews.AdvertRegView;
import com.mycompany.advertisementproject.model.entities.Advertisement;
import com.mycompany.advertisementproject.model.entities.Advertiser;
import com.mycompany.advertisementproject.model.entities.Advertstate;
import com.mycompany.advertisementproject.model.entities.Adverttype;
import com.mycompany.advertisementproject.model.entities.City;
import com.mycompany.advertisementproject.model.entities.Country;
import com.mycompany.advertisementproject.model.entities.Maincategory;
import com.mycompany.advertisementproject.model.entities.Picture;
import com.mycompany.advertisementproject.model.entities.Subcategory;
import com.mycompany.advertisementproject.model.facades.AdvertisementFacade;
import com.mycompany.advertisementproject.model.facades.AdvertstateFacade;
import com.mycompany.advertisementproject.model.facades.AdverttypeFacade;
import com.mycompany.advertisementproject.model.facades.CityFacade;
import com.mycompany.advertisementproject.model.facades.CountryFacade;
import com.mycompany.advertisementproject.model.facades.MaincategoryFacade;
import com.mycompany.advertisementproject.model.facades.SubcategoryFacade;
import com.mycompany.advertisementproject.toolz.Global;
import com.vaadin.server.VaadinSession;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.vaadin.easyuploads.FileBuffer;

public class AdvertRegController {

    private MaincategoryFacade maincategoryFacade;
    private SubcategoryFacade subcategoryFacade;
    private AdverttypeFacade adverttypeFacade;
    private AdvertisementFacade advertisementFacade;
    private AdvertstateFacade advertstateFacade;
    private CountryFacade countryFacade;
    private CityFacade cityFacade;
    
    private static final String TEMP_FILE_DIR = new File(System.getProperty("java.io.tmpdir")).getPath();

    private final AdvertRegView view;

    private final List<File> files = new ArrayList<>();
    private final List<Picture> pictureCollection = new ArrayList<>();

    private MyMultiFileUpload mfu;
    private Picture picture;
    private boolean filled = false;

    private final Advertiser current_advertiser;
    private final Advertisement advert_to_mod;

    public AdvertRegController(AdvertRegView view) {
        this.view = view;
        current_advertiser = (Advertiser) VaadinSession.getCurrent().getAttribute(CURRENTUSER.toString());
        advert_to_mod = (Advertisement) VaadinSession.getCurrent().getAttribute(ADVERTTOMODIFY.toString());
    }

    public void checkSessionAttribute() {
        try {
            if (advert_to_mod != null) {
                view.prepareForModification(advert_to_mod);
            } else {
                view.prepareForRegistration();
            }
        } catch (Exception ex) {
            Logger.getLogger(AdvertRegController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void registerAdvert() {
        Advertisement advertisement = new Advertisement();
        advertisement.setAdvertiserId(current_advertiser);
        advertisement.setAdvertStateId(selectedState());
        advertisement.setAdvertTypeId(selectedType());
        advertisement.setDescription(view.getTxtAreaDescription().getValue());
        advertisement.setCityId(selectedCity());
        advertisement.setCountryId(selectedCountry());
        advertisement.setMainCategoryId(selectedMainCategory());
        advertisement.setSubCategoryId(selectedSubCategory());
        advertisement.setPrice(Integer.valueOf(view.getTxtFldPrice().getValue()));
        advertisement.setRegistrationDate(Global.currentDate());
        advertisement.setTitle(view.getTxtFieldTitle().getValue());

        for (File f : files) {
            picture = new Picture();
            picture.setAccessPath(f.getAbsolutePath());
            picture.setAdvertisementId(advertisement);
            pictureCollection.add(picture);
        }

        advertisement.setPictureCollection(pictureCollection);
        advertisementFacade.create(advertisement);
    }

    public void modifyAdvert() {
        advert_to_mod.setAdvertStateId(selectedState());
        advert_to_mod.setAdvertTypeId(selectedType());
        advert_to_mod.setDescription(view.getTxtAreaDescription().getValue());
        advert_to_mod.setCityId(selectedCity());
        advert_to_mod.setCountryId(selectedCountry());
        advert_to_mod.setMainCategoryId(selectedMainCategory());
        advert_to_mod.setSubCategoryId(selectedSubCategory());
        advert_to_mod.setPrice(Integer.valueOf(view.getTxtFldPrice().getValue()));
        advert_to_mod.setRegistrationDate(Global.currentDate());
        advert_to_mod.setTitle(view.getTxtFieldTitle().getValue());

        for (File f : files) {
            picture = new Picture();
            picture.setAccessPath(f.getAbsolutePath());
            picture.setAdvertisementId(advert_to_mod);
            pictureCollection.add(picture);
        }

        advert_to_mod.setPictureCollection(pictureCollection);
        advertisementFacade.edit(advert_to_mod);
    }

    public void linkDataToFields(Advertisement ad) {
        view.getTxtFieldTitle().setValue(ad.getTitle());
        view.getTxtAreaDescription().setValue(ad.getDescription());
        view.getTxtFldPrice().setValue(String.valueOf(ad.getPrice()));
        view.getCmbbxCategory().select(ad.getMainCategoryId());
        fillCmbBxSubCategory(ad.getMainCategoryId());
        view.getCmbbxSubCategory().select(ad.getSubCategoryId());
        view.getCmbbxAdvertType().select(ad.getAdvertTypeId());
        view.getCmbbxAdvertState().select(ad.getAdvertStateId());
        view.getCmbbxCountry().select(ad.getCountryId());
        fillCmbBxCity(ad.getCountryId());
        view.getCmbbxCity().select(ad.getCityId());
        for (Picture p : ad.getPictureCollection()) {
            File file = new File(p.getAccessPath());
            files.add(file);
            view.showImage(file);
        }
    }

    public void fillComboBoxes() {
        if (!filled) {
            view.getCmbbxCategory().addItems(maincategoryFacade.findAll());
            view.getCmbbxAdvertState().addItems(advertstateFacade.findAll());
            view.getCmbbxAdvertType().addItems(adverttypeFacade.findAll());
            view.getCmbbxCountry().addItems(countryFacade.findAll());
            filled = true;
        }
    }

    public void fillCmbBxSubCategory(Object value) {
        view.getCmbbxSubCategory().removeAllItems();
        view.getCmbbxSubCategory().setEnabled(true);
        if (value != null && !((Maincategory) (value)).getSubcategoryCollection().isEmpty()) {
            view.getCmbbxSubCategory().addItems(((Maincategory) (value)).getSubcategoryCollection());
        }
    }

    public void fillCmbBxCity(Object value) {
        view.getCmbbxCity().removeAllItems();
        view.getCmbbxCity().setEnabled(true);
        if (value != null && !((Country) value).getCityCollection().isEmpty()) {
            view.getCmbbxCity().addItems(((Country) value).getCityCollection());
        }
    }

    private Advertstate selectedState() {
        if (!view.getCmbbxAdvertState().isEmpty()) {
            return advertstateFacade.getStateByName(view.getCmbbxAdvertState().getValue().toString());
        } else {
            return null;
        }
    }

    private Adverttype selectedType() {
        if (!view.getCmbbxAdvertType().isEmpty()) {
            return adverttypeFacade.getTypeByName(view.getCmbbxAdvertType().getValue().toString());
        } else {
            return null;
        }
    }

    private Country selectedCountry() {
        if (!view.getCmbbxCountry().isEmpty()) {
            return countryFacade.getCountryByName(view.getCmbbxCountry().getValue().toString());
        } else {
            return null;
        }
    }

    private City selectedCity() {
        if (!view.getCmbbxCity().isEmpty()) {
            return cityFacade.getCityByName(view.getCmbbxCity().getValue().toString());
        } else {
            return null;
        }
    }

    private Maincategory selectedMainCategory() {
        if (!view.getCmbbxCategory().isEmpty()) {
            return maincategoryFacade.getCategoryByName(view.getCmbbxCategory().getValue().toString());
        } else {
            return null;
        }
    }

    private Subcategory selectedSubCategory() {
        if (!view.getCmbbxSubCategory().isEmpty()) {
            return subcategoryFacade.getSubCateogryByName(view.getCmbbxSubCategory().getValue().toString());
        } else {
            return null;
        }
    }

    public void removeFile(File file) {
        file.delete();
        files.remove(file);
    }

    public void setUpLoadField() {
        mfu = new MyMultiFileUpload() {
            @Override
            protected void handleFile(final File file, String fileName, String mimeType, long length) {
                files.add(file);
                view.showImage(file);
            }

            @Override
            protected FileBuffer createReceiver() {
                FileBuffer receiver = super.createReceiver();
                receiver.setDeleteFiles(false);
                return receiver;
            }
        };
        mfu.setRootDirectory(TEMP_FILE_DIR);
        mfu.setCaption(view.getDropHere());
    }

    public MyMultiFileUpload getMfu() {
        return mfu;
    }

    public void setMaincategoryFacade(MaincategoryFacade maincategoryFacade) {
        this.maincategoryFacade = maincategoryFacade;
    }

    public void setSubcategoryFacade(SubcategoryFacade subcategoryFacade) {
        this.subcategoryFacade = subcategoryFacade;
    }

    public void setAdverttypeFacade(AdverttypeFacade adverttypeFacade) {
        this.adverttypeFacade = adverttypeFacade;
    }

    public void setAdvertisementFacade(AdvertisementFacade advertisementFacade) {
        this.advertisementFacade = advertisementFacade;
    }

    public void setAdvertstateFacade(AdvertstateFacade advertstateFacade) {
        this.advertstateFacade = advertstateFacade;
    }

    public void setCountryFacade(CountryFacade countryFacade) {
        this.countryFacade = countryFacade;
    }

    public void setCityFacade(CityFacade cityFacade) {
        this.cityFacade = cityFacade;
    }
    
}
