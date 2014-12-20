package org.vaadin.example.shiro;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public class SecureView extends VerticalLayout implements View {

    private static final String PERMISSION1 = "xerox5225:print:pdf";
    private static final String PERMISSION2 = "xerox5225:print:xml";

    private boolean initialized = false;

    private Button dugmence;

    private void build() {
        Subject subject = SecurityUtils.getSubject();

        System.err.println("sess:" + subject.getSession().toString());
        System.err.println("principal:" + subject.getPrincipal().toString());
        System.err.println("perm 1 :" + subject.isPermitted(PERMISSION1));
        System.err.println("perm 2 :" + subject.isPermitted(PERMISSION2));

        Logger.getLogger(getClass().getCanonicalName()).log(Level.INFO,
                "Initializing secure view");
        Label label = new Label("Super secret documentation of your project");
        label.setStyleName(Reindeer.BUTTON_DEFAULT);
        addComponent(label);
        BrowserFrame embedded = new BrowserFrame();
        embedded.setSource(new ThemeResource("documents/secret.pdf"));
        embedded.setWidth(66, Unit.PERCENTAGE);
        embedded.setHeight(600, Unit.PIXELS);
        addComponent(embedded);

        dugmence = new Button("Logout", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                logout();
            }
        });
        addComponent(dugmence);
    }

    private void logout() {
        Logger.getLogger(getClass().getCanonicalName()).log(Level.INFO, "LOGOUT !");
        UI.getCurrent().getNavigator().navigateTo(LogoutView.class.getSimpleName());

        initialized = false;
    }

    private void noRights() {
        Logger.getLogger(getClass().getCanonicalName()).log(Level.INFO, "NO RIGHTS !");
        UI.getCurrent().getNavigator().navigateTo(NoRightsView.class.getSimpleName());

        initialized = false;
    }

    @Override
    public void enter(ViewChangeEvent event) {
        if (SecurityUtils.getSubject().isPermitted(PERMISSION2)) {
            if (!initialized) {
                build();
                initialized = true;
            }
        } else {
            noRights();
        }
    }
}
