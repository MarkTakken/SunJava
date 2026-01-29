class SharedFunctions
{
    static double latitude,longitude,time_change;
    static int year,month,day,hour,second;
    static double minute;
    static boolean solar,mean_solar,change_to_solar;
    static String secondStr, minuteStr;
    final static int[] monthdays = {31,28,31,30,31,30,31,31,30,31,30,31};
    
    static boolean isLeap;
    private static final int[] cumulative = {0,31,59,90,120,151,181,212,243,273,304,334};
    
    static final String deg = "°";
    
    SharedFunctions() {}
    
    SharedFunctions(double latitude, double longitude, double time_change,
                    int year, int month, int day, int hour, int minute,
                    boolean solar, boolean mean_solar)
    {
        this.latitude = latitude;
        this.longitude = longitude;
        this.time_change = time_change;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.solar = solar;
        this.mean_solar = mean_solar;
    }
    
    int monthdays(int mon)
    {
        if (isLeap && mon == 2)
            return 29;
        else
            return monthdays[mon-1];
    }
    
    boolean isLeap()
    {
        return (year % 4 == 0) && (year % 100 != 0 || year % 400 == 0);
    }
    
    double d_after_jan1(int mon, double d, double h, double m)
    {
        double days = cumulative[mon-1] + d-1 + h/24.0 + m/1440.0;
        if (isLeap && mon >= 3) 
            return days + 1;
        else
            return days;
    }
    
    String double_digit_format(int num)
    {
        String str = num + "";
        if (num < 10)
            str = "0" + str;
        return str;
    }
    
    double round(double num,int place)
    {
        double power = Math.pow(10,place);
        return Math.round(num*power)/power;
    }
    
    void check_hour()
    {
        if (hour >= 24)
        {
            hour -= 24;
            day++;
            if (day > monthdays(month))
            {
                day = 1;
                month++;
                if (month > 12)
                {
                    month = 1;
                    year++;
                }
            }
        }
        else if (hour < 0)
        {
            hour += 24;
            day--;
            if (day < 1)
            {
                month --;
                if (month < 1)
                {
                    month = 12;
                    year --;
                }
                day = monthdays(month-1);
            }
        }
    }
}
