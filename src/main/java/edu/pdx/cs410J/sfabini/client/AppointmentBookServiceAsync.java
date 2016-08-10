package edu.pdx.cs410J.sfabini.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The client-side interface to the ping service
 */
public interface  AppointmentBookServiceAsync {


  /**
   * Return the current date/time on the server.  Used for early testing.
   * @param numberOfAppointments The number of appointments to create.
   * @param async The callback to execute when the appointment book is returned to the client.
     */
  void createAppointmentBook(int numberOfAppointments, AsyncCallback<AppointmentBook> async);




  /**
   * Return the current date/time on the server
   * @param owner The name of the owner of the appointment book
   * @param appointment The appointment to add to the appointment book.  Created by the client.
   * @param async The callback to execute when the appointment book is returned to the client.
     */
  void createAppointmentBook(String owner, Appointment appointment,
                             AsyncCallback<AppointmentBook> async);

  /**
   * Return the current date/time on the server
   * @param owner The name of the owner of the appointment book
   * @param async The callback to execute when the appointment book is returned to the client.
     */
  void getAppointmentBook(String owner, AsyncCallback<AppointmentBook> async);
}
