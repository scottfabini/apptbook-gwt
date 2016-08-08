package edu.pdx.cs410J.sfabini.client;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AppointmentBook extends AbstractAppointmentBook<Appointment>
{
    private String owner = "My Owner";
    private Collection<Appointment> appts = new ArrayList<>();

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
    public Collection<Appointment> getAppointments()
    {
        return this.appts;
    }

    @Override
    public void addAppointment( Appointment appt )
    {
        this.appts.add(appt);
        //Collections.sort(appts);
    }
}
