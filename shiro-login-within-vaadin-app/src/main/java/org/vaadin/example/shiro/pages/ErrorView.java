package org.vaadin.example.shiro.pages;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.RequestHandler;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinResponse;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinServletService;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.BorderStyle;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import java.io.IOException;
import java.util.Date;

public class ErrorView extends VerticalLayout implements View {

    private static final String MY_SERVLET_CONTEXT = "/ex";

    public ErrorView() {
        setMargin(true);
        setSpacing(true);

        addComponent(new Label("Oops. The view you tried to navigate to doesn't exist."));
        addComponents(new Label(""), new Button("Main page", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                UI.getCurrent().getNavigator().navigateTo(SecureView.class.getSimpleName());
            }
        }));
    }

    @Override
    public void enter(ViewChangeEvent event) {
        VaadinSession.getCurrent().addRequestHandler(new RequestHandler() {

            @Override
            public boolean handleRequest(VaadinSession session, VaadinRequest request, VaadinResponse response)
                    throws IOException {
                if (MY_SERVLET_CONTEXT.equals(request.getPathInfo())) {
                    response.setContentType("text/plain");
                    response.getWriter().println("dinamički generisan izveštaj u : " + (new Date()).toString());
                    response.getWriter().println();
                    response.getWriter().println();
                    response.getWriter().println("getRequestURI: " + VaadinServletService.getCurrentServletRequest().getRequestURI());
                    response.getWriter().println("getServletPath: " + VaadinServletService.getCurrentServletRequest().getServletPath());
                    response.getWriter().println("getContentType: " + VaadinServletService.getCurrentServletRequest().getContentType());
                    response.getWriter().println("getPathInfo: " + request.getPathInfo());
                    response.getWriter().println("p1 : " + request.getParameter("p1"));
                    response.getWriter().println("p2 : " + request.getParameter("p2"));
                    response.getWriter().println("post : " + request.getParameter("post"));
                    response.getWriter().println("session : "
                            + VaadinSession.getCurrent().getSession().getId());
                    return true;
                } else {
                    return false;
                }
            }
        });

        Link link = new Link("Klik za ispis stranice!",
                new ExternalResource(VaadinServlet.getCurrent().getServletContext().getContextPath()
                        + VaadinServletService.getCurrentServletRequest().getServletPath() + MY_SERVLET_CONTEXT
                        + "?p1=1&p2=\"dobri\""),
                "_blank", 500, 350, BorderStyle.DEFAULT);

        addComponent(link);
    }
}
