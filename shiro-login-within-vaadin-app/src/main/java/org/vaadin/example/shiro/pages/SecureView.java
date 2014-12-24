package org.vaadin.example.shiro.pages;

import com.vaadin.navigator.ViewChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.Reindeer;
import org.apache.shiro.SecurityUtils;
import org.vaadin.example.shiro.functionalities.SecureAccessView;
import org.vaadin.example.shiro.functionalities.SecurityDefs;

public class SecureView extends SecureAccessView {

    private Button logoutButton;

    @Override
    protected void createView() {
        Logger.getLogger(getClass().getCanonicalName()).log(Level.INFO, "Initializing secure view");
        Label label = new Label("Super secret documentation of your project");
        label.setStyleName(Reindeer.BUTTON_DEFAULT);
        addComponent(label);
        BrowserFrame embedded = new BrowserFrame();
        embedded.setSource(new ThemeResource("documents/secret.pdf"));
        embedded.setWidth(66, Unit.PERCENTAGE);
        embedded.setHeight(600, Unit.PIXELS);
        addComponent(embedded);

        logoutButton = new Button("Logout", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                logout(LogoutView.class.getSimpleName());
            }
        });

        addComponent(logoutButton);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if (isPermitted(SecurityDefs.PERMISSION1)) {
            if (!initialized) {
                createView();
                initialized = true;
            }
        } else {
            noRights(NoRightsView.class.getSimpleName());
        }
    }

    @Override
    protected boolean isPermitted(String permission) {
        return SecurityUtils.getSubject().isPermitted(permission);
    }
}
