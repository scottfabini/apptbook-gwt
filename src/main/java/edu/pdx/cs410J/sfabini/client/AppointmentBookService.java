package edu.pdx.cs410J.sfabini.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * A GWT remote service that returns a dummy appointment book
 */
@RemoteServiceRelativePath("appointments")
public interface AppointmentBookService extends RemoteService {


  public AppointmentBook createAppointmentBook(int numberOfAppointments);

  /**
   * Create a new appointmentBook, or append to add of current appointmentBook if one already exists
   * @param owner AppointmentBook owner
   * @param appointment The appointment
   * @return the appointmentBook
     */
  public AppointmentBook createAppointmentBook(String owner, Appointment appointment);

  public AppointmentBook getAppointmentBook(String owner);
}
