package edu.pdx.cs410J.sfabini.client;

import com.google.gwt.i18n.client.DateTimeFormat;
import edu.pdx.cs410J.AbstractAppointment;

import java.util.Date;

/**
 * This class represents an <code>Appointment</code>
 */
public class Appointment extends AbstractAppointment implements Comparable {

    private String description;
    private Date beginTime;
    private Date endTime;

    /**
     * Creates a new <code>Appointment</code>
     */
    public Appointment() {
        this.description = "Default Description";

        this.beginTime = null;
        this.endTime = null;
        //DateTimeFormat df = DateTimeFormat.getFormat("MM/dd/yyyy hh:mm a");
        //beginTime = df.parse("01/01/1970 12:00 AM");
        //endTime = df.parse("01/01/2000 12:00 AM");
    }


    /**
     * Creates a new <code>Appointment</code>
     *
     * @param description The appointment description
     * @param beginTimeString The time the appointment begins
     * @param endTimeString   The time the appointment ends
     */
    public Appointment(String description, String beginTimeString, String endTimeString) {
        this.description = description;
        DateTimeFormat df = DateTimeFormat.getFormat("MM/dd/yyyy hh:mm a");
        //DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        beginTime = df.parse(beginTimeString.toUpperCase());
        endTime = df.parse(endTimeString.toUpperCase());
    }

    /**
     * Override the compareTo of Comparable interface.  Makes a series of comparisons: earliest beginTime,
     * earliestEndTime, and earliest lexigraphical description.
     * @param o The object to compare to.  This class only performs comparisons to objects of the same class.
     * @return 0 if equal. negative number if less than, per the assignment criteria (beginTime first...).
     * positive number if greater than.
     */
    @Override
    public int compareTo(Object o) {
        int comparisonResult = 0;
        if (this.getClass().equals(o.getClass())) {
            Appointment appointment = (Appointment) o;
            comparisonResult = this.beginTime.compareTo(appointment.beginTime);
            if (comparisonResult == 0) {
                comparisonResult = this.endTime.compareTo(appointment.endTime);
                if (comparisonResult == 0) {
                    comparisonResult = this.description.compareTo(appointment.description);
                }
            }
        }
        return comparisonResult;
    }

    /**
     * Several getters/setters
     */
    @Override
    public Date getBeginTime() { return beginTime; }

    @Override
    public Date getEndTime() {
        return endTime;
    }

    @Override
    public String getBeginTimeString() {
        return DateTimeFormat.getFormat("MM/dd/yyyy hh:mm a").format(beginTime);
    }

    @Override
    public String getEndTimeString() {
        return DateTimeFormat.getFormat("MM/dd/yyyy hh:mm a").format(endTime);
    }


    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return this.getDescription() + " from " + this.getBeginTimeString() + " until " + this.getEndTimeString();
    }

    private String formatDate(Date date) {
        String pattern = "MM/dd/yyyy hh:mm a";
        return DateTimeFormat.getFormat(pattern).format(date);
    }
}







