
package com.mycompany.advertisementproject.toolz;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;
import java.util.Collection;
import java.util.List;
import org.vaadin.pagingcomponent.ComponentsManager;
import org.vaadin.pagingcomponent.PagingComponent;
import org.vaadin.pagingcomponent.builder.ElementsBuilder;
import org.vaadin.pagingcomponent.button.ButtonPageNavigator;
import org.vaadin.pagingcomponent.customizer.adaptator.GlobalCustomizer;
import org.vaadin.pagingcomponent.listener.impl.LazyPagingComponentListener;

public class MyAdvertPager {

    public void pageAdverts(VerticalLayout advertList, VerticalLayout itemsArea,final List<HorizontalLayout> advertlayouts) {
        GlobalCustomizer adaptator = new GlobalCustomizer() {

            @Override
            public Button createButtonFirst() {
                Button button = new Button("<<");
                button.setStyleName(BaseTheme.BUTTON_LINK);
                return button;
            }

            @Override
            public Button createButtonLast() {
                Button button = new Button(">>");
                button.setStyleName(BaseTheme.BUTTON_LINK);
                return button;
            }

            @Override
            public Button createButtonNext() {
                Button button = new Button(">");
                button.setStyleName(BaseTheme.BUTTON_LINK);
                return button;
            }

            @Override
            public Button createButtonPrevious() {
                Button button = new Button("<");
                button.setStyleName(BaseTheme.BUTTON_LINK);
                return button;
            }

            @Override
            public Component createFirstSeparator() {
                return null;
            }

            @Override
            public Component createLastSeparator() {
                return null;
            }

            @Override
            public ButtonPageNavigator createButtonPage() {
                ButtonPageNavigator button = new ButtonPageNavigator();
                button.setStyleName(BaseTheme.BUTTON_LINK);
                return button;
            }

            @Override
            public void styleButtonPageCurrentPage(ButtonPageNavigator button, int pageNumber) {
                button.setPage(pageNumber, "[" + pageNumber + "]");
                button.addStyleName("styleRed");
                button.focus();
            }

            @Override
            public void styleButtonPageNormal(ButtonPageNavigator button, int pageNumber) {
                button.setPage(pageNumber);
                button.removeStyleName("styleRed");
            }

            @Override
            public void styleTheOthersElements(ComponentsManager manager, ElementsBuilder builder) {
                // Do nothing
            }
        };

        final PagingComponent<HorizontalLayout> pagingComponent = PagingComponent.paginate(advertlayouts)
                .numberOfItemsPerPage(5)
                .numberOfButtonsPage(5)
                .globalCustomizer(adaptator).addListener(new LazyPagingComponentListener<HorizontalLayout>(itemsArea) {

                    Panel panel;

                    @Override
                    protected Collection<HorizontalLayout> getItemsList(int startIndex, int endIndex) {
                        return advertlayouts.subList(startIndex, endIndex);
                    }

                    @Override
                    protected Component displayItem(int index, HorizontalLayout item) {
                        panel = new Panel();
                        panel.setContent(item);
                        return panel;
                    }
                }).build();
        itemsArea.setSpacing(true);
        advertList.addComponent(itemsArea);
        advertList.setComponentAlignment(itemsArea, Alignment.TOP_CENTER);
        advertList.addComponent(pagingComponent);
        advertList.setComponentAlignment(pagingComponent, Alignment.TOP_CENTER);
    }
}
