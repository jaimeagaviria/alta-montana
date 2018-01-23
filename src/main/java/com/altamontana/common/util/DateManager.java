package com.altamontana.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Component
public class DateManager extends AbstractClass {

    @Value("${date.format.db}")
    protected String formatoFechaDB;

    @Value("${datetime.format.db}")
    protected String formatoFechaHoraDB;

    @Value("${date.format.web}")
    protected String formatoFechaWeb;

    @Value("${datetime.format.web}")
    protected String formatoFechaHoraWeb;

    public DateManager() {
    }

    public Date stringToDateDB(String texto) {
        SimpleDateFormat formatter = new SimpleDateFormat(this.formatoFechaDB);
        return this.getDate(texto, formatter);
    }

    public Date stringToDate(String texto, String format) {
        if (this.debug) {
            this.logger.debug("stringToDate texto: {} format: {}", texto, format);
        }

        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return this.getDate(texto, formatter);
    }

    public Date stringToDateTimeDB(String texto) {
        SimpleDateFormat formatter = new SimpleDateFormat(this.formatoFechaHoraDB);
        return this.getDate(texto, formatter);
    }

    public Date stringToDateWeb(String texto) {
        SimpleDateFormat formatter = new SimpleDateFormat(this.formatoFechaWeb);
        return this.getDate(texto, formatter);
    }

    public Date stringToDateTimeWeb(String texto) {
        SimpleDateFormat formatter = new SimpleDateFormat(this.formatoFechaHoraWeb);
        return this.getDate(texto, formatter);
    }

    private Date getDate(String texto, SimpleDateFormat formatter) {
        try {
            if (this.debug) {
                this.logger.debug("getDate texto: {}", texto);
            }

            return formatter.parse(texto);
        } catch (ParseException var4) {
            this.logger.error(var4.getMessage(), var4);
            return null;
        }
    }

    public String dateToString(Date fecha, String formato) {
        if (fecha != null) {
            SimpleDateFormat formatoDelTexto = new SimpleDateFormat(formato);
            return formatoDelTexto.format(fecha);
        } else {
            return "";
        }
    }

    public String dateToStringDB(Date fecha) {
        if (fecha != null) {
            SimpleDateFormat formatoDelTexto = new SimpleDateFormat(this.formatoFechaDB);
            return formatoDelTexto.format(fecha);
        } else {
            return "";
        }
    }

    public String dateToStringDB(Timestamp fecha) {
        if (fecha != null) {
            SimpleDateFormat formatoDelTexto = new SimpleDateFormat(this.formatoFechaDB);
            return formatoDelTexto.format(fecha);
        } else {
            return "";
        }
    }

    public String dateToStringWeb(Date fecha) {
        if (fecha != null) {
            SimpleDateFormat formatoDelTexto = new SimpleDateFormat(this.formatoFechaWeb);
            return formatoDelTexto.format(fecha);
        } else {
            return "";
        }
    }

    public String dateToStringWeb(Timestamp fecha) {
        if (fecha != null) {
            SimpleDateFormat formatoDelTexto = new SimpleDateFormat(this.formatoFechaHoraWeb);
            return formatoDelTexto.format(fecha);
        } else {
            return "";
        }
    }

    public Long getDateUTC() {
        Calendar myGregCal = new GregorianCalendar();
        return myGregCal.getTimeInMillis();
    }

    public Date sumarMinutos(Date fecha, int minutos) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(12, minutos);
        return calendar.getTime();
    }

    public Date restarMinutos(Date fecha, int minutos) {
        return this.sumarMinutos(fecha, minutos * -1);
    }

    public Date sumarDias(Date fecha, int dias) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(6, dias);
        return calendar.getTime();
    }

    public Date restarDias(Date fecha, int dias) {
        return this.sumarDias(fecha, dias * -1);
    }

    public String getMonthOfDate(Date date) {
        String formato = "MM";
        SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
        return dateFormat.format(date);
    }

    public String getDayOfDate(Date date) {
        String formato = "dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
        return dateFormat.format(date);
    }

    public String getDayOfHour(Date date) {
        String formato = "HHmmss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
        return dateFormat.format(date);
    }
}
