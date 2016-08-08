package edu.pdx.cs410J.sfabini.client;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import org.junit.Test;

/**
 * An integration test for the airline GWT UI.  Remember that GWTTestCase is JUnit 3 style.
 * So, test methods names must begin with "test".
 * And since this test code is compiled into JavaScript, you can't use hamcrest matchers.  :(
 */
public class AppointmentBookGwtIT extends GWTTestCase {
  @Override
  public String getModuleName() {
    return "edu.pdx.cs410J.sfabini.AppointmentBookIntegrationTests";
  }

  @Test
  public void testClickingButtonAlertsWithAppointmentInformation() {
    final CapturingAlerter alerter = new CapturingAlerter();

    AppointmentBookGwt ui = new AppointmentBookGwt(alerter);
    ui.textBox.setText("4");
    ui.descriptionTextBox.setText("4");
    ui.beginDateTextBox.setText("01/01/1970 12:00 am");
    ui.endDateTextBox.setText("01/01/2000 12:00 am");
    click(ui.button);

    Timer verify = new Timer() {
      @Override
      public void run() {
        String message = alerter.getMessage();
        assertNotNull(message);
        assertTrue(message, message.contains("My Owner's appointment book with 4 appointments"));
        finishTest();
      }
    };

    // Wait for the RPC call to return
    verify.schedule(500);

    delayTestFinish(1000);
  }

  @Test
  public void testHelpAlertsWithREADME() {
    final CapturingAlerter alerter = new CapturingAlerter();

    AppointmentBookGwt ui = new AppointmentBookGwt(alerter);
    click(ui.helpButton);

    Timer verify = new Timer() {
      @Override
      public void run() {
        String message = alerter.getMessage();
        assertNotNull(message);
        assertTrue(message, message.contains("To create an appointment, enter a description, begin Date, and end Date and click submit"));
        finishTest();
      }
    };

    // Wait for the RPC call to return
    verify.schedule(500);

    delayTestFinish(1000);
  }


  /**
   * Clicks a <code>Button</code>
   *
   * One would think that you could testing clicking a button with Button.click(), but it looks
   * like you need to fire the native event instead.  Lame.
   *
   * @param button
   *        The button to click
   */
  private void click(Button button) {
    NativeEvent event = Document.get().createClickEvent(0, 0, 0, 0, 0, false, false, false, false);
    DomEvent.fireNativeEvent(event, button);
  }

  private class CapturingAlerter implements AppointmentBookGwt.Alerter {
    private String message;

    @Override
    public void alert(String message) {
      this.message = message;
    }

    public String getMessage() {
      return message;
    }
  }
}
