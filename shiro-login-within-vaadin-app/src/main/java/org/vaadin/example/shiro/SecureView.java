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

public class SecureView extends VerticalLayout implements View {

    public static final String ID = "SecureView";
    private boolean initialized;

    private Button dugmence;

    private void build() {
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
                UI.getCurrent().getSession().close();
                SecurityUtils.getSubject().logout();
                
                UI.getCurrent().getNavigator().navigateTo(ErrorView.ID);
            }
        });
        addComponent(dugmence);
    }

    @Override
    public void enter(ViewChangeEvent event) {
        if (!initialized) {
            build();
            initialized = true;
        }
    }
}
