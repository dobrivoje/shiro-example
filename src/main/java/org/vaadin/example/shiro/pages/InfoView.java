package org.vaadin.example.shiro.pages;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class InfoView extends VerticalLayout implements View {

    public InfoView() {
        setMargin(true);
        setSpacing(true);

        addComponent(new Label("No need for Login Page, as you're authenticated already."));
        addComponents(new Label(""), new Button("Back to Secure page", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                UI.getCurrent().getNavigator().navigateTo(SecureView.class.getSimpleName());
            }
        }));
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }
}
