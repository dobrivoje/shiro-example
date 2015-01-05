package org.vaadin.example.shiro.pages;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.vaadin.server.VaadinService;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.Reindeer;
import java.io.File;
import java.net.URL;
import org.apache.shiro.SecurityUtils;
import org.vaadin.example.shiro.functionalities.SecureAccessView;
import org.vaadin.example.shiro.functionalities.SecurityDefs;

public class SecureView extends SecureAccessView {

    private Button logoutButton;

    @Override
    protected void createView() {
        setSizeUndefined();
        
        Logger.getLogger(getClass().getCanonicalName()).log(Level.INFO, "Initializing secure view");
        Label label = new Label("Super secret documentation of your project");
        label.setStyleName(Reindeer.BUTTON_DEFAULT);
        addComponent(label);
        
        BrowserFrame embedded1 = new BrowserFrame();
        BrowserFrame embedded2 = new BrowserFrame();

        String pdf1 = "docs/pdfs/secret.pdf";
        String image1 = "docs/imgs/Slika042.jpg";

        URL url1 = VaadinService.getCurrent().getClassLoader().getResource(pdf1);
        embedded1.setSource(new FileResource(new File(url1.getFile())));

        URL url2 = VaadinService.getCurrent().getClassLoader().getResource(image1);
        embedded2.setSource(new FileResource(new File(url2.getFile())));

        embedded1.setWidth(100, Unit.PERCENTAGE);
        embedded1.setHeight(500, Unit.PIXELS);
        addComponent(embedded1);

        embedded2.setWidth(100, Unit.PERCENTAGE);
        embedded2.setHeight(100, Unit.PERCENTAGE);
        addComponent(embedded2);

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
