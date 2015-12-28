package com.mycompany.advertisementproject.Enums.control;

import com.mycompany.advertisementproject.Tools.MyMultiFileUpload;
import com.mycompany.advertisementproject.UIs.Views.AdvertRegView;
import com.mycompany.advertisementproject.entities.Advertisement;
import com.mycompany.advertisementproject.entities.Advertiser;
import com.mycompany.advertisementproject.entities.Advertstate;
import com.mycompany.advertisementproject.entities.Adverttype;
import com.mycompany.advertisementproject.entities.City;
import com.mycompany.advertisementproject.entities.Country;
import com.mycompany.advertisementproject.entities.Maincategory;
import com.mycompany.advertisementproject.entities.Picture;
import com.mycompany.advertisementproject.entities.Subcategory;
import com.vaadin.server.VaadinSession;
import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.vaadin.easyuploads.FileBuffer;

public class AdvertRegController {

    private static final String TEMP_FILE_DIR = new File(System.getProperty("java.io.tmpdir")).getPath();

    private final AdvertRegView view;

    private List<File> files = new ArrayList<>();
    private List<Picture> pictureCollection = new ArrayList<>();

    private MyMultiFileUpload mfu;
    private Picture picture;
    private boolean filled = false;

    private final Advertiser current_advertiser;
    private final Advertisement advert_to_mod;

    public AdvertRegController(AdvertRegView view) {
        this.view = view;
        current_advertiser = (Advertiser) VaadinSession.getCurrent().getAttribute("current_user");
        advert_to_mod = (Advertisement) VaadinSession.getCurrent().getAttribute("AdvertToModify");
    }

    public void checkSessionAttribute() {
        try {
            if (advert_to_mod != null) {
                view.prepareForModification(advert_to_mod);
            } else {
                view.prepareForRegistration();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void registerAdvert() {
        Advertisement a = handleAdvert();
        view.getAdvertisementFacade().create(a);
    }

    public void modifyAdvert() {
        Advertisement a = handleAdvert();
        view.getAdvertisementFacade().edit(a);
        view.getAdvertisementFacade().remove(advert_to_mod);
    }

    private Advertisement handleAdvert() {
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
        advertisement.setRegistrationDate(currentDate());
        advertisement.setTitle(view.getTxtFieldTitle().getValue());

        for (File f : files) {
            picture = new Picture();
            picture.setAccessPath(f.getAbsolutePath());
            picture.setAdvertisementId(advertisement);
            pictureCollection.add(picture);
        }

        advertisement.setPictureCollection(pictureCollection);
        return advertisement;
    }

    public void linkDataToFields(Advertisement ad) {
        view.getTxtFieldTitle().setValue(ad.getTitle());
        view.getTxtAreaDescription().setValue(ad.getDescription());
        view.getTxtFldPrice().setValue(String.valueOf(ad.getPrice()));
        view.getCmbbxCategory().select(ad.getMainCategoryId().getName());

        fillCmbBxSubCategory(ad.getMainCategoryId().getName());
        for (Subcategory s : ad.getMainCategoryId().getSubcategoryCollection()) {
            if (s.getId().equals(ad.getSubCategoryId())) {
                view.getCmbbxSubCategory().select(s.getName());
            }
        }
        view.getCmbbxSubCategory().select(ad.getSubCategoryId());
        view.getCmbbxAdvertType().select(ad.getAdvertTypeId().getName());
        view.getCmbbxAdvertState().select(ad.getAdvertStateId().getName());
        view.getCmbbxCountry().select(ad.getCountryId().getCountryName());

        view.getCmbbxCity().select(ad.getCityId().getCityName());
        fillCmbBxCity(ad.getCountryId().getCountryName());
        for (City c : ad.getCountryId().getCityCollection()) {
            if (c.getId().equals(ad.getCityId())) {
                view.getCmbbxSubCategory().select(c.getCityName());
            }
        }
        for (Picture p : ad.getPictureCollection()) {
            File file = new File(p.getAccessPath());
            files.add(file);
            view.showImage(file);
        }
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

    public void fillComboBoxes() {
        if (!filled) {
            for (Maincategory m : view.getMaincategoryFacade().findAll()) {
                view.getCmbbxCategory().addItem(m.getName());
            }
            for (Advertstate a : view.getAdvertstateFacade().findAll()) {
                view.getCmbbxAdvertState().addItem(a.getName());
            }
            for (Adverttype a : view.getAdverttypeFacade().findAll()) {
                view.getCmbbxAdvertType().addItem(a.getName());
            }
            for (Country c : view.getCountryFacade().findAll()) {
                view.getCmbbxCountry().addItem(c.getCountryName());
            }
            filled = true;
        }
    }

    public void fillCmbBxSubCategory(Object value) {
        view.getCmbbxSubCategory().removeAllItems();
        view.getCmbbxSubCategory().setEnabled(true);
        for (Maincategory mcat : view.getMaincategoryFacade().findAll()) {
            if (mcat.getName().equals(value)) {
                for (Subcategory s : mcat.getSubcategoryCollection()) {
                    view.getCmbbxSubCategory().addItem(s.getName());
                }
            }
        }
    }

    public void fillCmbBxCity(Object value) {
        view.getCmbbxCity().removeAllItems();
        view.getCmbbxCity().setEnabled(true);
        for (City c : view.getCityFacade().findAll()) {
            if (c.getCountryId().equals(value)) {
                view.getCmbbxCity().addItem(c.getCityName());
            }
        }
    }

    private Advertstate selectedState() {
        List<Advertstate> states = view.getAdvertstateFacade().findAll();
        for (Advertstate state : states) {
            if (state.getName().equals(view.getCmbbxAdvertState().getValue())) {
                return state;
            }
        }
        return null;
    }

    private Adverttype selectedType() {
        List<Adverttype> types = view.getAdverttypeFacade().findAll();
        for (Adverttype type : types) {
            if (type.getName().equals(view.getCmbbxAdvertType().getValue())) {
                return type;
            }
        }
        return null;
    }

    private Country selectedCountry() {
        List<Country> countries = view.getCountryFacade().findAll();
        for (Country country : countries) {
            if (country.getCountryName().equals(view.getCmbbxCountry().getValue())) {
                return country;
            }
        }
        return null;
    }

    private City selectedCity() {
        List<City> cities = view.getCityFacade().findAll();
        for (City city : cities) {
            if (city.getCityName().equals(view.getCmbbxCity().getValue())) {
                return city;
            }
        }
        return null;
    }

    private Maincategory selectedMainCategory() {
        List<Maincategory> categoires = view.getMaincategoryFacade().findAll();
        for (Maincategory category : categoires) {
            if (category.getName().equals(view.getCmbbxCategory().getValue())) {
                return category;
            }
        }
        return null;
    }

    private Subcategory selectedSubCategory() {
        List<Subcategory> categoires = view.getSubcategoryFacade().findAll();
        for (Subcategory category : categoires) {
            if (category.getName().equals(view.getCmbbxSubCategory().getValue())) {
                return category;
            }
        }
        return null;
    }

    private Date currentDate() {
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new Date(utilDate.getTime());
        return sqlDate;
    }

    public void removeFile(File file) {
        file.delete();
        files.remove(file);
    }

    public MyMultiFileUpload getMfu() {
        return mfu;
    }
}
