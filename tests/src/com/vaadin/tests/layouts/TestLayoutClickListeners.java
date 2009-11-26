package com.vaadin.tests.layouts;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.tests.components.AbstractTestCase;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class TestLayoutClickListeners extends AbstractTestCase {

    private Log log = new Log(5);

    public static class Log extends VerticalLayout {
        List<Label> eventLabels = new ArrayList<Label>();

        public Log(int nr) {
            for (int i = 0; i < nr; i++) {
                Label l = createEventLabel();
                eventLabels.add(l);
                addComponent(l);
            }

            setCaption("Events:");
        }

        public void clear() {
            for (Label l : eventLabels) {
                l.setValue("");
            }
        }

        public void log(String event) {
            int nr = eventLabels.size();
            for (int i = nr - 1; i > 0; i--) {
                eventLabels.get(i).setValue(eventLabels.get(i - 1).getValue());
            }
            eventLabels.get(0).setValue(event);
        }

        private Label createEventLabel() {
            Label l = new Label("&nbsp;", Label.CONTENT_XHTML);
            l.setWidth(null);
            return l;
        }

    }

    @Override
    public void init() {
        Window w = new Window("main window");
        setMainWindow(w);
        setTheme("tests-tickets");

        HorizontalLayout layoutsLayout = new HorizontalLayout();
        layoutsLayout.setSpacing(true);
        w.setContent(layoutsLayout);

        layoutsLayout.addComponent(createClickableGridLayout());
        layoutsLayout.addComponent(createClickableVerticalLayout());
        layoutsLayout.addComponent(createClickableAbsoluteLayout());

        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);
        w.setContent(mainLayout);
        mainLayout.addComponent(log);
        mainLayout.addComponent(layoutsLayout);
    }

    private Component createClickableAbsoluteLayout() {
        final AbsoluteLayout al = new AbsoluteLayout();
        al.setCaption("AbsoluteLayout");
        al.setStyleName("borders");
        al.setWidth("300px");
        al.setHeight("500px");
        al.addComponent(new TextField("This is its caption",
                "This is a textfield"), "top: 60px; left: 0px; width: 100px;");
        al.addComponent(new TextField("Another textfield caption",
                "This is another textfield"),
                "top: 120px; left: 20px; width: 100px;");

        al.addComponent(new Button("A button with its own click listener",
                new Button.ClickListener() {

                    public void buttonClick(
                            com.vaadin.ui.Button.ClickEvent event) {
                        log.log("Button " + event.getButton().getCaption()
                                + " was clicked");

                    }
                }));
        al.addListener(new LayoutClickListener() {

            public void layoutClick(LayoutClickEvent event) {
                logLayoutClick("AbsoluteLayout", event.getChildComponent());
            }
        });

        return al;

    }

    private Layout createClickableGridLayout() {

        GridLayout gl = new GridLayout(4, 4);
        gl.setHeight("400px");
        gl.setStyleName("borders");
        gl.setSpacing(true);
        addContent(gl, 4);
        TextField largeTextarea = new TextField("Large textarea");
        largeTextarea.setWidth("100%");
        largeTextarea.setHeight("99%");
        gl.addComponent(largeTextarea, 0, 3, 3, 3);

        gl.addListener(new LayoutClickListener() {

            public void layoutClick(LayoutClickEvent event) {
                logLayoutClick("GridLayout", event.getChildComponent());
            }
        });
        gl.setRowExpandRatio(3, 1);
        return wrap(gl, "GridLayout");
    }

    protected void logLayoutClick(String layout, Component comp) {
        String target = "&lt;none>";
        if (comp != null) {
            target = comp.getCaption();
            if (target == null && comp instanceof Label) {
                target = ((Label) comp).getValue().toString();
            }
        }
        log.log(layout + ": Click on " + target);

    }

    private Layout createClickableVerticalLayout() {

        VerticalLayout gl = new VerticalLayout();
        addContent(gl, 5);

        gl.addListener(new LayoutClickListener() {

            public void layoutClick(LayoutClickEvent event) {
                logLayoutClick("VerticalLayout", event.getChildComponent());

            }
        });

        return wrap(gl, "Clickable VerticalLayout");
    }

    private void addContent(Layout gl, int nr) {
        for (int i = 1; i < nr; i++) {
            Label l = new Label("This is label " + i);
            l.setWidth(null);
            gl.addComponent(l);
        }
        for (int i = nr; i < nr * 2; i++) {
            gl.addComponent(new TextField("This is tf" + i, "this is tf " + i));
        }
    }

    private Layout wrap(Component c, String caption) {
        VerticalLayout vl = new VerticalLayout();
        Label l = new Label(caption);
        l.setWidth(null);
        vl.addComponent(l);
        vl.addComponent(c);

        return vl;
    }

    @Override
    protected String getDescription() {
        return "All layouts have click listeners attached and the events are shown in the event log at the top";
    }

    @Override
    protected Integer getTicketNumber() {
        return 3541;
    }

}
