package org.vaadin.example.shiro;

import javax.servlet.annotation.WebServlet;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

@Theme("shiroexample")
public class ExampleUI extends UI {

    private static final String PERMISSION1 = "stampac_print_pdf";
    private static final String PERMISSION2 = "stampa:xerox5225a";

    private final Navigator navigator = new Navigator(this, this);

    @WebServlet(value = "/*")
    @VaadinServletConfiguration(productionMode = false, ui = ExampleUI.class)
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        navigator.addView(LoginView.ID, LoginView.class);
        navigator.addView(LogoutView.ID, LogoutView.class);
        
        navigator.setErrorView(ErrorView.class);

        if (SecurityUtils.getSubject().isPermitted(PERMISSION1)) {
            getUI().getNavigator().addView(SecureView.ID, SecureView.class);
        }

        navigator.addViewChangeListener(new ViewChangeListener() {

            @Override
            public boolean beforeViewChange(ViewChangeListener.ViewChangeEvent event) {
                Subject currentUser = SecurityUtils.getSubject();

                if (currentUser.isPermitted(PERMISSION1)
                        && event.getViewName().equals(LoginView.ID)) {
                    event.getNavigator().navigateTo(SecureView.ID);
                    return false;
                }

                if (!currentUser.hasRole(PERMISSION1)
                        && !event.getViewName().equals(LoginView.ID)) {
                    event.getNavigator().navigateTo(LoginView.ID);
                    return false;
                }

                return true;
            }

            @Override
            public void afterViewChange(ViewChangeListener.ViewChangeEvent event) {
            }
        });
    }
}
