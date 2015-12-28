package com.mycompany.advertisementproject.Tools;

import com.mycompany.advertisementproject.UIs.Views.AccountView;
import com.mycompany.advertisementproject.UIs.Views.AdvertListView;
import com.mycompany.advertisementproject.UIs.Views.AdvertRegView;
import com.mycompany.advertisementproject.UIs.Views.LetterView;
import com.mycompany.advertisementproject.UIs.Views.LogInView;
import com.mycompany.advertisementproject.UIs.Views.RegistrationView;
import com.mycompany.advertisementproject.UIs.Views.SelectedAdvertView;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

public class XmlFileReader {

    public static final String fileSource = "/Users/balin/data.xml";

    private LogInView loginView;
    private RegistrationView regView;
    private AdvertListView listView;
    private AdvertRegView advertRegView;
    private LetterView letterView;
    private SelectedAdvertView selectedView;
    private AccountView accView;

    private String tagName;

    public void readXml() throws Exception {
        try {
            File fXmlFile = new File(fileSource);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName(tagName);

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    selectTagName(eElement);
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException | DOMException ex) {
            Logger.getLogger(XmlFileReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void selectTagName(Element eElement) throws Exception {
        switch (tagName) {
            case "RegistrationView": {
                readRegViewData(eElement);
                break;
            }
            case "LogInView": {
                readLoginViewData(eElement);
                break;
            }
            case "AdvertListView": {
                readAdvertListViewData(eElement);
                break;
            }
            case "AdvertRegView": {
                readAdvertRegViewData(eElement);
                break;
            }
            case "SelectedAdvertView": {
                readSelectedAdvertData(eElement);
                break;
            }
            case "AccountView": {
                readAccountViewData(eElement);
                break;
            }
            case "LetterView": {
                readLetterViewData(eElement);
                break;
            }
            case "StartView": {
                break;
            }
        }
    }

    private void readRegViewData(Element eElement) {
        regView.getLblTitle().setValue(eElement.getElementsByTagName("title").item(0).getTextContent());
        regView.getTfEmail().setCaption(eElement.getElementsByTagName("email").item(0).getTextContent());
        regView.getPfPassWord1().setCaption(eElement.getElementsByTagName("password").item(0).getTextContent());
        regView.getPfPassWord2().setCaption(eElement.getElementsByTagName("passwordre").item(0).getTextContent());
        regView.getChkBxTerms().setCaption(eElement.getElementsByTagName("terms").item(0).getTextContent());
        regView.getTfPhoneNumber().setCaption(eElement.getElementsByTagName("phone").item(0).getTextContent());
        regView.getChkBxNewsLetter().setCaption(eElement.getElementsByTagName("newsletter").item(0).getTextContent());
        regView.getTfName().setCaption(eElement.getElementsByTagName("name").item(0).getTextContent());
        regView.getBtnRegistration().setCaption(eElement.getElementsByTagName("regbutton").item(0).getTextContent());
        regView.setTxtSuccess(eElement.getElementsByTagName("succestext").item(0).getTextContent());
//        regView.setEmailUsedError(eElement.getElementsByTagName("emailusederror").item(0).getTextContent());
//        regView.setEmailFormatError(eElement.getElementsByTagName("emailformaterror").item(0).getTextContent());
//        regView.setEmptyFieldError(eElement.getElementsByTagName("emptyfielderror").item(0).getTextContent());
//        regView.setPasswordError(eElement.getElementsByTagName("passworderror").item(0).getTextContent());
//        regView.setConditionError(eElement.getElementsByTagName("conditionerror").item(0).getTextContent());
    }

    private void readLoginViewData(Element eElement) {
        loginView.getLblTitle().setValue(eElement.getElementsByTagName("title").item(0).getTextContent());
        loginView.getTfEmail().setCaption(eElement.getElementsByTagName("email").item(0).getTextContent());
        loginView.getPfPassWord().setCaption(eElement.getElementsByTagName("password").item(0).getTextContent());
        loginView.getBtnLogin().setCaption(eElement.getElementsByTagName("loginbutton").item(0).getTextContent());
        loginView.setErrorText(eElement.getElementsByTagName("errortext").item(0).getTextContent());
    }

    private void readAdvertListViewData(Element eElement) {
        listView.getTxtFldSearch().setWidth(eElement.getElementsByTagName("searchfieldwidth").item(0).getTextContent());
        listView.getBtnSearch().setCaption(eElement.getElementsByTagName("searchbutton").item(0).getTextContent());
        listView.getSearchBarPanel().setWidth(eElement.getElementsByTagName("searchpanelwidth").item(0).getTextContent());
        listView.getLblTitle().setWidth(eElement.getElementsByTagName("adverttitlewidth").item(0).getTextContent());
        listView.getLblFilter().setValue(eElement.getElementsByTagName("filterlabel").item(0).getTextContent());
        listView.getFilterPanel().setWidth(eElement.getElementsByTagName("filterpanelwidth").item(0).getTextContent());
        listView.getAdvertPanel().setWidth(eElement.getElementsByTagName("advertpanelwidth").item(0).getTextContent());
        listView.getCmbBxCategory().setInputPrompt(eElement.getElementsByTagName("category").item(0).getTextContent());
        listView.getCmbBxSubCategory().setInputPrompt(eElement.getElementsByTagName("subcategory").item(0).getTextContent());
        listView.getCmbBxType().setInputPrompt(eElement.getElementsByTagName("type").item(0).getTextContent());
        listView.getCmbBxState().setInputPrompt(eElement.getElementsByTagName("state").item(0).getTextContent());
        listView.getCmbBxCity().setInputPrompt(eElement.getElementsByTagName("city").item(0).getTextContent());
        listView.getCmbBxCountry().setInputPrompt(eElement.getElementsByTagName("county").item(0).getTextContent());
        listView.getTxtFldMinPrice().setInputPrompt(eElement.getElementsByTagName("minprice").item(0).getTextContent());
        listView.getTxtFldMaxPrice().setInputPrompt(eElement.getElementsByTagName("maxprice").item(0).getTextContent());
        listView.getBtnFilter().setCaption(eElement.getElementsByTagName("filterbutton").item(0).getTextContent());
        listView.setImageWidth(eElement.getElementsByTagName("imagewidth").item(0).getTextContent());
        listView.setImageHeight(eElement.getElementsByTagName("imageheight").item(0).getTextContent());
        listView.setNoResult(eElement.getElementsByTagName("noresult").item(0).getTextContent());
    }

    private void readAdvertRegViewData(Element eElement) {
        advertRegView.getTxtFieldTitle().setInputPrompt(eElement.getElementsByTagName("title").item(0).getTextContent());
        advertRegView.getTxtFieldTitle().setWidth(eElement.getElementsByTagName("titlewidth").item(0).getTextContent());
        advertRegView.getTxtAreaDescription().setInputPrompt(eElement.getElementsByTagName("description").item(0).getTextContent());
        advertRegView.getTxtAreaDescription().setWidth(eElement.getElementsByTagName("descriptionwidth").item(0).getTextContent());
        advertRegView.getTxtAreaDescription().setHeight(eElement.getElementsByTagName("descriptionheight").item(0).getTextContent());
        advertRegView.getCmbbxCategory().setInputPrompt(eElement.getElementsByTagName("category").item(0).getTextContent());
        advertRegView.getCmbbxSubCategory().setInputPrompt(eElement.getElementsByTagName("subcategory").item(0).getTextContent());
        advertRegView.getCmbbxAdvertType().setInputPrompt(eElement.getElementsByTagName("type").item(0).getTextContent());
        advertRegView.getCmbbxAdvertState().setInputPrompt(eElement.getElementsByTagName("state").item(0).getTextContent());
        advertRegView.getCmbbxCity().setInputPrompt(eElement.getElementsByTagName("city").item(0).getTextContent());
        advertRegView.getCmbbxCountry().setInputPrompt(eElement.getElementsByTagName("county").item(0).getTextContent());
        advertRegView.getTxtFldPrice().setInputPrompt(eElement.getElementsByTagName("price").item(0).getTextContent());
        advertRegView.getAdverRegPanel().setWidth(eElement.getElementsByTagName("regpanelwidth").item(0).getTextContent());
        advertRegView.getLblAdvertDetails().setValue(eElement.getElementsByTagName("advertdetails").item(0).getTextContent());
        advertRegView.setImageWidth(eElement.getElementsByTagName("imagewidth").item(0).getTextContent());
        advertRegView.setImageHeight(eElement.getElementsByTagName("imageheight").item(0).getTextContent());
        advertRegView.setRemoveButtonText(eElement.getElementsByTagName("removebutton").item(0).getTextContent());
        advertRegView.getLblPictureUpload().setValue(eElement.getElementsByTagName("pictureupload").item(0).getTextContent());
        advertRegView.getPicturePanel().setWidth(eElement.getElementsByTagName("picturepanelwidth").item(0).getTextContent());
        advertRegView.setDropHere(eElement.getElementsByTagName("drophere").item(0).getTextContent());
        advertRegView.setFailedUpload(eElement.getElementsByTagName("uploadfail").item(0).getTextContent());
        advertRegView.setModify(eElement.getElementsByTagName("modify").item(0).getTextContent());
        advertRegView.setRegister(eElement.getElementsByTagName("register").item(0).getTextContent());
        advertRegView.setSuccessUpload(eElement.getElementsByTagName("uploadsuccess").item(0).getTextContent());
    }

    private void readSelectedAdvertData(Element eElement) {

    }

    private void readAccountViewData(Element eElement) {
        accView.setAdvertTabText(eElement.getElementsByTagName("adverttab").item(0).getTextContent());
        accView.setPostboxTabText(eElement.getElementsByTagName("postboxtab").item(0).getTextContent());
        accView.setAdvertPanelWidth(eElement.getElementsByTagName("advertpanelweight").item(0).getTextContent());
        accView.setPostboxPanelWidth(eElement.getElementsByTagName("postboxpanelweight").item(0).getTextContent());
        accView.setTableAdvertWidth(eElement.getElementsByTagName("adverttableweight").item(0).getTextContent());
        accView.setAdvertTblTitle(eElement.getElementsByTagName("adverttabletitle").item(0).getTextContent());
        accView.setAdvertTblPrice(eElement.getElementsByTagName("adverttableprice").item(0).getTextContent());
        accView.setAdvertTblDate(eElement.getElementsByTagName("adverttabledate").item(0).getTextContent());
        accView.setAdvertTblDelete(eElement.getElementsByTagName("adverttabledelete").item(0).getTextContent());
        accView.setAdvertTblModify(eElement.getElementsByTagName("adverttablemodify").item(0).getTextContent());
        accView.setDelButtonText(eElement.getElementsByTagName("deleteadvertbutton").item(0).getTextContent());
        accView.setModButtonText(eElement.getElementsByTagName("modifyadvertbutton").item(0).getTextContent());
        accView.setLetterTabSheetWidth(eElement.getElementsByTagName("lettertabweight").item(0).getTextContent());
        accView.setIncoming(eElement.getElementsByTagName("incomingletters").item(0).getTextContent());
        accView.setOutgoing(eElement.getElementsByTagName("outgoingletters").item(0).getTextContent());
        accView.setTableLetterWidth(eElement.getElementsByTagName("lettertableweight").item(0).getTextContent());
        accView.setTableLetterWidth(eElement.getElementsByTagName("lettertableweight").item(0).getTextContent());
        accView.setLetterTblDate(eElement.getElementsByTagName("lettertabledate").item(0).getTextContent());
        accView.setLetterTblMessage(eElement.getElementsByTagName("lettertablemessage").item(0).getTextContent());
        accView.setLetterTblSender(eElement.getElementsByTagName("lettertablesender").item(0).getTextContent());
        accView.setLetterTblSubject(eElement.getElementsByTagName("lettertablesubject").item(0).getTextContent());
    }

    private void readLetterViewData(Element eElement) {

    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public void setLoginView(LogInView loginView) {
        this.loginView = loginView;
    }

    public void setRegView(RegistrationView regView) {
        this.regView = regView;
    }

    public void setListView(AdvertListView listView) {
        this.listView = listView;
    }

    public void setAdvertRegView(AdvertRegView advertRegView) {
        this.advertRegView = advertRegView;
    }

    public void setLetterView(LetterView letterView) {
        this.letterView = letterView;
    }

    public void setSelectedView(SelectedAdvertView selectedView) {
        this.selectedView = selectedView;
    }

    public void setAccView(AccountView accView) {
        this.accView = accView;
    }
}
