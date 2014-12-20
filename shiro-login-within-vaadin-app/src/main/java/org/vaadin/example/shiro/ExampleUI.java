package org.vaadin.example.shiro;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

@Theme("shiroexample")
public class ExampleUI extends UI {

    private static final String ROLE1 = "stampac_print_pdf";
    private static final String ROLE2 = "stampa:xerox5225a";

    @WebServlet(value = "/*")
    @VaadinServletConfiguration(productionMode = false, ui = ExampleUI.class)
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        Navigator navigator = new Navigator(this, this);
        navigator.addView(LoginView.class.getSimpleName(), LoginView.class);
        navigator.addView(LogoutView.class.getSimpleName(), LogoutView.class);
        navigator.addView(NoRightsView.class.getSimpleName(), NoRightsView.class);
        navigator.setErrorView(ErrorView.class);

        if (SecurityUtils.getSubject().hasRole(ROLE1)) {
            UI.getCurrent().getNavigator().addView(SecureView.class.getSimpleName(), SecureView.class);
        }

        navigator.addViewChangeListener(new ViewChangeListener() {

            @Override
            public boolean beforeViewChange(ViewChangeListener.ViewChangeEvent event) {
                Subject currentUser = SecurityUtils.getSubject();

                if (currentUser.hasRole(ROLE1)
                        && event.getViewName().equals(LoginView.class.getSimpleName())) {
                    event.getNavigator().navigateTo(SecureView.class.getSimpleName());
                    return false;
                }

                if (!currentUser.hasRole(ROLE1)
                        && !event.getViewName().equals(LoginView.class.getSimpleName())) {
                    event.getNavigator().navigateTo(LoginView.class.getSimpleName());
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
