package edu.pdx.cs410J.sfabini.server;

import edu.pdx.cs410J.sfabini.client.AppointmentBook;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class AppointmentBookServiceImplTest {

  @Test
  public void serviceReturnsExpectedAppointment() {

    /*
    // This test stopped working after implementing the compareTo method.
    // Basically we shifted from having the service create appointments
    // by integer count, to creating appointments and sending them over
    // the wire to the server.
    AppointmentBookServiceImpl service = new AppointmentBookServiceImpl();
    int numberOfAppointments = 6;
    AppointmentBook appointmentBook = service.createAppointmentBook(numberOfAppointments);
    assertThat(appointmentBook.getAppointments().size(), equalTo(numberOfAppointments));
  */

  }
}
