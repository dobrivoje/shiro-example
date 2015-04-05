package org.vaadin.example.shiro;

import org.vaadin.example.shiro.pages.LogoutView;
import org.vaadin.example.shiro.pages.NoRightsView;
import org.vaadin.example.shiro.pages.LoginView;
import org.vaadin.example.shiro.pages.ErrorView;
import org.vaadin.example.shiro.pages.SecureView;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.shiro.SecurityUtils;
import org.vaadin.example.shiro.functionalities.SecurityDefs_New;
import org.vaadin.example.shiro.pages.InfoView;

@Theme("shiroexample")
public class MainUI extends UI {

    @WebServlet(value = "/*")
    @VaadinServletConfiguration(productionMode = false, ui = MainUI.class)
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        Navigator navigator = new Navigator(this, this);

        navigator.addView(LoginView.class.getSimpleName(), new LoginView());
        navigator.addView(LogoutView.class.getSimpleName(), LogoutView.class);
        navigator.addView(SecureView.class.getSimpleName(), SecureView.class);
        navigator.addView(NoRightsView.class.getSimpleName(), NoRightsView.class);
        navigator.addView(InfoView.class.getSimpleName(), InfoView.class);
        navigator.setErrorView(ErrorView.class);

        navigator.addViewChangeListener(new ViewChangeListener() {
            @Override
            public boolean beforeViewChange(ViewChangeListener.ViewChangeEvent event) {
                if (event.getViewName().equals(LoginView.class.getSimpleName())
                        && SecurityUtils.getSubject().hasRole(SecurityDefs_New.ROLE1.getRole())) {
                    
                    event.getNavigator().navigateTo(InfoView.class.getSimpleName());
                    return false;
                }

                if (!event.getViewName().equals(LoginView.class.getSimpleName())
                        && !SecurityUtils.getSubject().hasRole(SecurityDefs_New.ROLE1.getRole())) {
                    
                    event.getNavigator().navigateTo(LoginView.class.getSimpleName());
                    return false;
                }

                return true;
            }

            @Override
            public void afterViewChange(ViewChangeListener.ViewChangeEvent event) {
                Logger.getLogger(event.getClass().getSimpleName())
                        .log(Level.INFO, ", pozivanje: ".concat(event.getViewName()));
            }
        });
    }
}
