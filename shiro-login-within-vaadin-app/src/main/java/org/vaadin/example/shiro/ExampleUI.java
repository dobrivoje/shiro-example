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

    @WebServlet(value = "/*")
    @VaadinServletConfiguration(productionMode = false, ui = ExampleUI.class)
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        Navigator navigator = new Navigator(this, this);
        navigator.addViewChangeListener(new ViewChangeListener() {
            
            @Override
            public boolean beforeViewChange(ViewChangeEvent event) {
                Subject currentUser = SecurityUtils.getSubject();

                if (currentUser.isAuthenticated() && event.getViewName().equals(LoginView.ID)) {
                    event.getNavigator().navigateTo(SecureView.ID);
                    return false;
                }

                if (!currentUser.isAuthenticated() && !event.getViewName().equals(LoginView.ID)) {
                    event.getNavigator().navigateTo(LoginView.ID);
                    return false;
                }

                return true;
            }

            @Override
            public void afterViewChange(ViewChangeEvent event) {
            }
        });

        navigator.addView(LoginView.ID, LoginView.class);
        if (SecurityUtils.getSubject().isAuthenticated()) {
            getUI().getNavigator().addView(SecureView.ID, SecureView.class);
        }
        navigator.setErrorView(ErrorView.class);
    }
}
