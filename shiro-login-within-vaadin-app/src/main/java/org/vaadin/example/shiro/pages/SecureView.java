package org.vaadin.example.shiro.pages;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.Reindeer;
import org.vaadin.example.shiro.functionalities.SecureAccessView;

public class SecureView extends SecureAccessView {

    private Button logOutButton;

    @Override
    protected void login() {
        Logger.getLogger(getClass().getCanonicalName()).log(Level.INFO, "Initializing secure view");
        Label label = new Label("Super secret documentation of your project");
        label.setStyleName(Reindeer.BUTTON_DEFAULT);
        addComponent(label);
        BrowserFrame embedded = new BrowserFrame();
        embedded.setSource(new ThemeResource("documents/secret.pdf"));
        embedded.setWidth(66, Unit.PERCENTAGE);
        embedded.setHeight(600, Unit.PIXELS);
        addComponent(embedded);

        logOutButton = new Button("Logout", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                logout(LogoutView.class.getSimpleName());
            }
        });

        addComponent(logOutButton);
    }
}
