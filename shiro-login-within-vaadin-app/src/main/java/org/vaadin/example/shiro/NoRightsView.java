package org.vaadin.example.shiro;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.apache.shiro.SecurityUtils;

public class NoRightsView extends VerticalLayout implements View {
    public NoRightsView() {
        
        addComponent(new Label("You don't have necessary rights."));
        addComponent(new Label(""));
        //addComponent(new Label("Apps: " + app.getName() + ", desc: " + app.getDescription()));
        addComponent(new Label(""));
        addComponent(new Button("Login Page", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                SecurityUtils.getSubject().logout();
                UI.getCurrent().getNavigator().navigateTo(LoginView.class.getSimpleName());
            }
        }));
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }
}
