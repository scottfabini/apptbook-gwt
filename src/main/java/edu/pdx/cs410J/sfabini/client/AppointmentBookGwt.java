package edu.pdx.cs410J.sfabini.client;

import com.google.common.annotations.VisibleForTesting;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;

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
  TextArea textArea;

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
    this.beginDateTextBox = new TextBox();
    this.endDateTextBox = new TextBox();
    this.textArea = new TextArea();
    this.textArea.setVisibleLines(8);
    this.textArea.setCharacterWidth(50);
    this.textArea.getElement().setPropertyString("placeholder", "Leave description blank to display a range of appointments.\n"
            + "Leave all fields blank to display all appointments.");

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
            "* To create an appointment, enter a description, begin Date, and end Date and click submit\n" +
            "* To list all appointments, leave all fields blank and click submit\n" +
            "* To search appointments in a date range, enter a begin date and end date and click submit\n" +
            "* Date Format Example: 01/01/1970 12:00 AM";
    alerter.alert(s);
  }

    /**
     * TODO: pull getTexts out into their own variables for readability.
     */
  private void submit() {


    if (!this.descriptionTextBox.getText().equals("") &&
            !this.beginDateTextBox.getText().equals("") && !this.endDateTextBox.getText().equals("")) {
      if (isProperDateFormat(this.beginDateTextBox.getText(), this.endDateTextBox.getText())){
          createAppointments();
      }

    } else if (this.descriptionTextBox.getText().equals("")
            && !this.beginDateTextBox.getText().equals("") && !this.endDateTextBox.getText().equals("")) {
      prettyPrintRange();
    } else if (this.descriptionTextBox.getText().equals("")
            && this.beginDateTextBox.getText().equals("") && this.endDateTextBox.getText().equals("")) {
        prettyPrintAppointments();
    }
    else {
      alerter.alert("Unexpected input.  Please see Help for hints on usage.");
    }
  }

    private void prettyPrintRange() {
        AppointmentBookServiceAsync async = GWT.create(AppointmentBookService.class);

        async.getAppointmentBook("My Owner", new AsyncCallback<AppointmentBook>() {
            @Override
            public void onSuccess(AppointmentBook appointmentBook) {
                displayRangeInTextBox(appointmentBook);
            }

            @Override
            public void onFailure(Throwable ex) {
                alert(ex);
            }
        });


    }

    private void displayRangeInTextBox(AppointmentBook appointmentBook) {
        StringBuilder sb = new StringBuilder("My Owner ");

        sb.append("Appointments in range:\n");

        List<Appointment> appointments = appointmentBook.getAppointmentsInRange(this.beginDateTextBox.getText(), this.endDateTextBox.getText());
        for (Appointment appointment : appointments) {
            sb.append(appointment);
            sb.append("\n");
        }
        this.textArea.setText(sb.toString());
    }

    private void prettyPrintAppointments() {
    AppointmentBookServiceAsync async = GWT.create(AppointmentBookService.class);

    async.getAppointmentBook("My Owner", new AsyncCallback<AppointmentBook>() {
      @Override
      public void onSuccess(AppointmentBook appointmentBook) {
        displayInTextBox(appointmentBook);
      }

      @Override
      public void onFailure(Throwable ex) {
        alert(ex);
      }
    });

  }

    private void displayInTextBox(AppointmentBook appointmentBook) {
        StringBuilder sb = new StringBuilder("My Owner");

        sb.append(appointmentBook.toString());
        sb.append("\n");

        Collection<Appointment> appointments = appointmentBook.getAppointments();
        for (Appointment appointment : appointments) {
            sb.append(appointment);
            sb.append("\n");
        }
        this.textArea.setText(sb.toString());
    }

    private void createAppointments() {
    AppointmentBookServiceAsync async = GWT.create(AppointmentBookService.class);
    //int numberOfAppointments = getNumberOfAppointments();
    //async.createAppointmentBook(numberOfAppointments, new AsyncCallback<AppointmentBook>() {

    String owner = this.textBox.getText();
    if (owner == null) { owner = "My Owner"; }
    Appointment appointment = new Appointment(this.descriptionTextBox.getText(), this.beginDateTextBox.getText()
            , this.endDateTextBox.getText());

    async.createAppointmentBook(owner, appointment, new AsyncCallback<AppointmentBook>() {
      @Override
      public void onSuccess(AppointmentBook appointmentBook) {
        displayInTextBox(appointmentBook);
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

  private void displayInAlertDialog(AppointmentBook appointmentBook) {
    /*
    TODO: Fix AppointmentBook generated by the server to initialize the owner string correctly.
    May need to move the createAppointmentBook out of the server, but this doesn't make sense
    to have the client manage the book.
     */
    StringBuilder sb = new StringBuilder("My Owner");

    sb.append(appointmentBook.toString());
    sb.append("\n");

    Collection<Appointment> flights = appointmentBook.getAppointments();
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

    DockPanel textAreaPanel = new DockPanel();
    textAreaPanel.setSpacing(4);
    textAreaPanel.add(textArea, DockPanel.WEST);
    textAreaPanel.add(textArea, DockPanel.WEST);

    DockPanel helpPanel = new DockPanel();
    helpPanel.setSpacing(4);
    helpPanel.add(helpButton, DockPanel.EAST);


    appPanel.add(panel, DockPanel.NORTH);
    appPanel.add(descriptionPanel, DockPanel.WEST);
    appPanel.add(beginDatePanel, DockPanel.CENTER);
    appPanel.add(endDatePanel, DockPanel.EAST);

    rootPanel.add(appPanel);
    rootPanel.add(textAreaPanel);
    rootPanel.add(helpPanel);

  }


  private boolean isProperDateFormat(String beginDate, String endDate) {
      boolean isValid;
      DateTimeFormat df = DateTimeFormat.getFormat("MM/dd/yyyy hh:mm a");
      //DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
      try {
          Date parsedDate = df.parse(beginDate.toUpperCase());
          parsedDate = df.parse(endDate.toUpperCase());
      } catch (IllegalArgumentException e) {
          alerter.alert("Illegal date format: " + beginDate + ", " + endDate
          + "\n* Date Format Example: 01/01/1970 12:00 AM");
      }
      return true;
  }

  @VisibleForTesting
  interface Alerter {
    void alert(String message);
  }

}
