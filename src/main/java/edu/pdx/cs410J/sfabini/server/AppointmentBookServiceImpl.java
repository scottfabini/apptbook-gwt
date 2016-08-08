package edu.pdx.cs410J.sfabini.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.sfabini.client.Appointment;
import edu.pdx.cs410J.sfabini.client.AppointmentBook;
import edu.pdx.cs410J.sfabini.client.AppointmentBookService;

/**
 * The server-side implementation of the division service
 */
public class AppointmentBookServiceImpl extends RemoteServiceServlet implements AppointmentBookService
{
  AppointmentBook book;


  @Override
  public AppointmentBook createAppointmentBook(int numberOfAppointments) {
    if (book == null) {
      book = new AppointmentBook();
        book.setOwnerName("My Owner");
    }
    for (int i = 0; i < numberOfAppointments; i++) {
      book.addAppointment(new Appointment());
    }
    return book;
  }



    public AppointmentBook createAppointmentBook(String owner, Appointment appointment) {
        if (book == null) {
            book = new AppointmentBook();
            book.setOwnerName(owner);
        }
        book.addAppointment(appointment);
        return book;
    }

  @Override
  protected void doUnexpectedFailure(Throwable unhandled) {
    unhandled.printStackTrace(System.err);
    super.doUnexpectedFailure(unhandled);
  }

}
