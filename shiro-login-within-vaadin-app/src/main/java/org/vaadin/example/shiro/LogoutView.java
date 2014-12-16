package org.vaadin.example.shiro;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class LogoutView extends VerticalLayout implements View {

    public static final String ID = "LogoutView";

    public LogoutView() {
        addComponent(new Label("LogoutView."));
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }
}
