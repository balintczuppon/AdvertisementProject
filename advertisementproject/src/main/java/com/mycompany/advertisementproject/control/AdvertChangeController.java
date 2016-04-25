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
import org.vaadin.easyuploads.FileBuffer;

public class AdvertChangeController {

    private MaincategoryFacade maincategoryFacade;
    private SubcategoryFacade subcategoryFacade;
    private AdverttypeFacade adverttypeFacade;
    protected AdvertisementFacade advertisementFacade;
    private AdvertstateFacade advertstateFacade;
    private CountryFacade countryFacade;
    private CityFacade cityFacade;

    private final AdvertRegView view;

    private final List<File> files = new ArrayList<>();
    private final List<File> tempPictures = new ArrayList<>();
    private final List<Picture> pictureCollection = new ArrayList<>();

    private MyMultiFileUpload mfu;
    private Picture picture;
    private boolean filled = false;

    private Advertiser current_advertiser;
    private Advertisement advert_to_mod;

    public AdvertChangeController(AdvertRegView view) {
        this.view = view;
    }

    public void registerAdvert() throws Exception{
        current_advertiser = (Advertiser) VaadinSession.getCurrent().getAttribute(CURRENTUSER.toString());
        Advertisement advertisement = new Advertisement();
        advertisement.setAdvertiserId(current_advertiser);
        advertisement.setAdvertStateId(selectedState());
        advertisement.setAdvertTypeId(selectedType());
        advertisement.setDescription(view.getTxtAreaDescription().getValue());
        advertisement.setCityId(selectedCity());
        advertisement.setCountryId(selectedCountry());
        advertisement.setMainCategoryId(selectedMainCategory());
        advertisement.setSubCategoryId(selectedSubCategory());
        if (!view.getTxtFldPrice().isEmpty()) {
            advertisement.setPrice(Integer.valueOf(view.getTxtFldPrice().getValue()));
        }
        advertisement.setRegistrationDate(Global.currentDate());
        advertisement.setTitle(view.getTxtFieldTitle().getValue());

        addPictureToAdvert(advertisement);

        advertisement.setPictureCollection(pictureCollection);
        advertisementFacade.create(advertisement);
    }

    public void modifyAdvert() throws Exception{
        advert_to_mod = (Advertisement) VaadinSession.getCurrent().getAttribute(ADVERTTOMODIFY.toString());
        advert_to_mod.setAdvertStateId(selectedState());
        advert_to_mod.setAdvertTypeId(selectedType());
        advert_to_mod.setDescription(view.getTxtAreaDescription().getValue());
        advert_to_mod.setCityId(selectedCity());
        advert_to_mod.setCountryId(selectedCountry());
        advert_to_mod.setMainCategoryId(selectedMainCategory());
        advert_to_mod.setSubCategoryId(selectedSubCategory());
        if (!view.getTxtFldPrice().isEmpty()) {
            advert_to_mod.setPrice(Integer.valueOf(view.getTxtFldPrice().getValue()));
        }
        advert_to_mod.setRegistrationDate(Global.currentDate());
        advert_to_mod.setTitle(view.getTxtFieldTitle().getValue());

        addPictureToAdvert(advert_to_mod);
        deleteUnUsedPictures();

        advert_to_mod.setPictureCollection(pictureCollection);
        advertisementFacade.edit(advert_to_mod);
    }

    public void linkDataToFields() throws Exception{
        view.getTxtFieldTitle().setValue(advert_to_mod.getTitle());
        view.getTxtAreaDescription().setValue(advert_to_mod.getDescription());
        view.getCmbbxCategory().select(advert_to_mod.getMainCategoryId());
        fillCmbBxSubCategory(advert_to_mod.getMainCategoryId());
        view.getCmbbxSubCategory().select(advert_to_mod.getSubCategoryId());
        view.getCmbbxAdvertType().select(advert_to_mod.getAdvertTypeId());
        view.getCmbbxAdvertState().select(advert_to_mod.getAdvertStateId());
        view.getCmbbxCountry().select(advert_to_mod.getCountryId());
        fillCmbBxCity(advert_to_mod.getCountryId());
        view.getCmbbxCity().select(advert_to_mod.getCityId());
        if (advert_to_mod.getPrice() != null) {
            view.getTxtFldPrice().setValue(String.valueOf(advert_to_mod.getPrice()));
        }
        for (Picture p : advert_to_mod.getPictureCollection()) {
            File file = new File(p.getAccessPath());
            files.add(file);
            view.showImage(file);
        }
    }

    public void fillComboBoxes() throws Exception {
        if (!filled) {
            view.getCmbbxCategory().addItems(maincategoryFacade.findAll());
            view.getCmbbxAdvertState().addItems(advertstateFacade.findAll());
            view.getCmbbxAdvertType().addItems(adverttypeFacade.findAll());
            view.getCmbbxCountry().addItems(countryFacade.findAll());
            filled = true;
        }
    }

    public void fillCmbBxSubCategory(Object value)  throws Exception {
        view.getCmbbxSubCategory().removeAllItems();
        view.getCmbbxSubCategory().setEnabled(true);
        if (value != null && !((Maincategory) (value)).getSubcategoryCollection().isEmpty()) {
            view.getCmbbxSubCategory().addItems(((Maincategory) (value)).getSubcategoryCollection());
        }
    }

    public void fillCmbBxCity(Object value) throws Exception  {
        view.getCmbbxCity().removeAllItems();
        view.getCmbbxCity().setEnabled(true);
        if (value != null && !((Country) value).getCityCollection().isEmpty()) {
            view.getCmbbxCity().addItems(((Country) value).getCityCollection());
        }
    }

    private Advertstate selectedState()  throws Exception {
        if (!view.getCmbbxAdvertState().isEmpty()) {
            return advertstateFacade.getStateByName(view.getCmbbxAdvertState().getValue().toString());
        } else {
            return null;
        }
    }

    private Adverttype selectedType()  throws Exception {
        if (!view.getCmbbxAdvertType().isEmpty()) {
            return adverttypeFacade.getTypeByName(view.getCmbbxAdvertType().getValue().toString());
        } else {
            return null;
        }
    }

    private Country selectedCountry()  throws Exception {
        if (!view.getCmbbxCountry().isEmpty()) {
            return countryFacade.getCountryByName(view.getCmbbxCountry().getValue().toString());
        } else {
            return null;
        }
    }

    private City selectedCity()  throws Exception {
        if (!view.getCmbbxCity().isEmpty()) {
            return cityFacade.getCityByName(view.getCmbbxCity().getValue().toString());
        } else {
            return null;
        }
    }

    private Maincategory selectedMainCategory() throws Exception  {
        if (!view.getCmbbxCategory().isEmpty()) {
            return maincategoryFacade.getCategoryByName(view.getCmbbxCategory().getValue().toString());
        } else {
            return null;
        }
    }

    private Subcategory selectedSubCategory() throws Exception  {
        if (!view.getCmbbxSubCategory().isEmpty()) {
            return subcategoryFacade.getSubCateogryByName(view.getCmbbxSubCategory().getValue().toString());
        } else {
            return null;
        }
    }

    public void removeFile(File file) throws Exception  {
        files.remove(file);
        file.delete();
    }

    public void setUpLoadField()  throws Exception {
        mfu = new MyMultiFileUpload() {

            @Override
            protected void handleFile(File file, String fileName, String mimeType, long length) {
                File properFile = new File(Global.TEMP_SERVER + "/" + Global.generatedId() + file.getName());
                file.renameTo(properFile);
                files.add(properFile);
                tempPictures.add(properFile);
                view.showImage(properFile);
            }

            @Override
            protected FileBuffer createReceiver() {
                FileBuffer receiver = super.createReceiver();
                receiver.setDeleteFiles(false);
                return receiver;
            }
        };
        mfu.setRootDirectory(Global.TEMP_SERVER);
        mfu.setCaption(view.getDropHere());
    }

    public void deleteUnUsedPictures()  throws Exception {
        for (File f : tempPictures) {
            if (!files.contains(f)) {
                f.delete();
            }
        }
    }

    public void addPictureToAdvert(Advertisement advertisement) throws Exception  {
        for (File f : files) {
            picture = new Picture();
            picture.setAccessPath(f.getAbsolutePath());
            picture.setAdvertisementId(advertisement);
            pictureCollection.add(picture);
        }
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
