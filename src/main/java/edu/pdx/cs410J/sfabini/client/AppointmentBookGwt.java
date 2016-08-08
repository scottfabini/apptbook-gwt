package edu.pdx.cs410J.sfabini.client;

import com.google.common.annotations.VisibleForTesting;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

import java.util.Collection;

/**
 * A basic GWT class that makes sure that we can send an appointment book back from the server
 */
public class AppointmentBookGwt implements EntryPoint {
  private final Alerter alerter;

  @VisibleForTesting
  Button button;
  Button helpButton;
  TextBox textBox;
  TextBox descriptionTextBox;
  TextBox beginDateTextBox;
  TextBox endDateTextBox;

  public AppointmentBookGwt() {
    this(new Alerter() {
      @Override
      public void alert(String message) {
        Window.alert(message);
      }
    });
  }

  @VisibleForTesting
  AppointmentBookGwt(Alerter alerter) {
    this.alerter = alerter;

    addWidgets();
  }

  private void addWidgets() {
    // Text Boxes
    this.textBox = new TextBox();
    this.textBox.getElement().setPropertyString("placeholder", "My Owner");
    this.textBox.setReadOnly(true);
    this.descriptionTextBox = new TextBox();
    this.descriptionTextBox.getElement().setPropertyString("placeholder", "4");
    this.beginDateTextBox = new TextBox();
    this.endDateTextBox = new TextBox();

    // Buttons
    button = new Button("Submit");
    button.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        submit();
      }
    });
    helpButton = new Button("Help");
    helpButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        alertWithReadme();
      }
    });
  }

  private void alertWithReadme() {
    String s = "Scott Fabini, Project 5 Usage:\n" +
            "To create an appointment, enter a description, begin Date, and end Date and click submit\n" +
            "To list all appointments, leave all fields blank and click submit\n" +
            "To search appointments in a date range, enter a begin date and end date and click submit";
    alerter.alert(s);
  }

  private void submit() {
    if (this.descriptionTextBox.getText() != null &&
            this.beginDateTextBox.getText() != null && this.endDateTextBox.getText() != null) {
      createAppointments();
    } else {
      alerter.alert("Unexpected input.");
    }
  }

  private void createAppointments() {
    AppointmentBookServiceAsync async = GWT.create(AppointmentBookService.class);
    int numberOfAppointments = getNumberOfAppointments();
    async.createAppointmentBook(numberOfAppointments, new AsyncCallback<AppointmentBook>() {

      @Override
      public void onSuccess(AppointmentBook airline) {
        displayInAlertDialog(airline);
      }

      @Override
      public void onFailure(Throwable ex) {
        alert(ex);
      }
    });
  }

  private int getNumberOfAppointments() {
    String number = this.descriptionTextBox.getText();

    return Integer.parseInt(number);
  }

  private void displayInAlertDialog(AppointmentBook airline) {
    StringBuilder sb = new StringBuilder(airline.toString());
    sb.append("\n");

    Collection<Appointment> flights = airline.getAppointments();
    for (Appointment flight : flights) {
      sb.append(flight);
      sb.append("\n");
    }
    alerter.alert(sb.toString());
  }

  private void alert(Throwable ex) {
    alerter.alert(ex.toString());
  }

  @Override
  public void onModuleLoad() {
    RootPanel rootPanel = RootPanel.get();

    DockPanel appPanel = new DockPanel();
    appPanel.setSpacing(4);
    appPanel.setHorizontalAlignment(DockPanel.ALIGN_LEFT);

    DockPanel panel = new DockPanel();
    panel.setSpacing(4);
    panel.add(new Label("Owner:   "), DockPanel.WEST);
    panel.add(textBox, DockPanel.CENTER);
    panel.add(button, DockPanel.EAST);

    DockPanel descriptionPanel = new DockPanel();
    descriptionPanel.setSpacing(4);
    descriptionPanel.add(new Label("Description:   "), DockPanel.WEST);
    descriptionPanel.add(descriptionTextBox, DockPanel.CENTER);

    DockPanel beginDatePanel = new DockPanel();
    beginDatePanel.setSpacing(4);
    beginDatePanel.add(new Label("Begin Date:   "), DockPanel.WEST);
    beginDatePanel.add(beginDateTextBox, DockPanel.CENTER);

    DockPanel endDatePanel = new DockPanel();
    endDatePanel.setSpacing(4);
    endDatePanel.add(new Label("End Date:   "), DockPanel.WEST);
    endDatePanel.add(endDateTextBox, DockPanel.CENTER);

    DockPanel helpPanel = new DockPanel();
    appPanel.setSpacing(4);
    helpPanel.add(helpButton, DockPanel.WEST);

    appPanel.add(panel, DockPanel.NORTH);
    appPanel.add(descriptionPanel, DockPanel.WEST);
    appPanel.add(beginDatePanel, DockPanel.CENTER);
    appPanel.add(endDatePanel, DockPanel.EAST);
   // appPanel.add(helpPanel, DockPanel.SOUTH);

    rootPanel.add(appPanel);
    rootPanel.add(helpPanel);
  }

  @VisibleForTesting
  interface Alerter {
    void alert(String message);
  }

}
