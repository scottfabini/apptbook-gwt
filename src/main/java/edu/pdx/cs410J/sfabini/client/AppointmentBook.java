package edu.pdx.cs410J.sfabini.client;

import com.google.gwt.i18n.client.DateTimeFormat;
import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;

import java.util.*;

public class AppointmentBook extends AbstractAppointmentBook<Appointment>
{
    private String owner = "My Owner";
    private List<Appointment> appts = new ArrayList<>();

    public AppointmentBook() {
        this.owner = "My Owner";
    }

    public void setOwnerName(String owner)
    {
        this.owner = owner;
    }

    @Override
    public String getOwnerName()
    {
        return this.owner;
    }

    @Override
    public List<Appointment> getAppointments()
    {
        return this.appts;
    }

    @Override
    public void addAppointment( Appointment appt )
    {
        this.appts.add(appt);
        Collections.sort(appts);
    }

    /** Note: this must be called only from the client.  Converts an appointment book returned
     * from the server to only contain dates within the specified range.
     * @return
     */
    public List<Appointment> getAppointmentsInRange(String beginTimeString, String endTimeString) {
        Date beginTime;
        Date endTime;

        DateTimeFormat df = DateTimeFormat.getFormat("MM/dd/yyyy hh:mm a");
        beginTime = df.parse(beginTimeString.toUpperCase());
        endTime = df.parse(endTimeString.toUpperCase());
        List<Appointment> appointmentList = getAppointments();
        List<Appointment> appointmentListWithRange = new ArrayList<>();
        for (Appointment appointment : appointmentList) {
            if ((appointment.getBeginTime().after(beginTime) || appointment.getBeginTime().equals(beginTime))
                    && (appointment.getEndTime().before(endTime) || appointment.getEndTime().equals(endTime))) {
                appointmentListWithRange.add(appointment);
            }
        }
        return appointmentListWithRange;
    }
}
