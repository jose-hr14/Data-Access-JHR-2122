package com.company;

public class Date {
    int day;
    int month;
    int year;

    public Date() {
    }

    public Date(int day, int month, int year) {
        setDay(day);
        setMonth(month);
        setYear(year);
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public void setDay(int day) {
        if(month < 1 || month > 12)
        {
            this.month = Integer.parseInt(null);
            throw new IllegalArgumentException();
        }

        else if(this.month == 02 && this.day == 30)
        {
            this.day = Integer.parseInt(null);
            throw new IllegalArgumentException();
        }

        else
        {
            this.day = day;
        }

    }

    public Boolean setMonth(int month) {
        if(month < 1 || month > 12)
        {
            throw new IllegalArgumentException();
        }

        else if(this.month == 02 && this.day == 30)
        {
            throw new IllegalArgumentException();
        }

        else
        {
            this.month = month;
            throw new IllegalArgumentException();
        }


    }

    public void setYear(int year) {
        this.year = year;
    }

    public Boolean leap()
    {
        if(this.year % 4 == 0 && this.year % 100 == 0 && this.year % 400 == 0)
            return true;
        else
            return false;
    }

}
