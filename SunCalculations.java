class SunCalculations extends SharedFunctions
{
    private double mEq, rawEq, tilt, init_eq, L,noonH,noonM, N1, N2,raw_dir;
    private String[] dirList = {"N","NNE","NE","ENE","E","ESE","SE","SSE","S",
                                   "SSW","SW","WSW","W","WNW","NW","NNW"};
    
    private void setConstants()
    {
        mEq = mar_equinox();
        rawEq = raw_equinox(hour,minute);
        tilt = 23.437 - 0.4684/3600*(year-2019);
        init_eq = init_eq();
        L = Math.abs(latitude);
        double[] noonHM = noon_time();
        noonH = noonHM[0];
        noonM = noonHM[1];
        N1 = max_alt(0,0);
        N2 = max_alt(24,0);
        raw_dir = direction();
        isLeap = isLeap();
        if (change_to_solar)
        {
            double shift = raw_time_shift();
            double time = hour + minute / 60.0;
            double added = time + shift / 60.0;
            hour = (int) Math.floor(added);
            check_hour();
            minute = (((added * 60) % 60)+60)%60;
            second = (int) ((((added * 3600) % 60)+60)%60);
            //minute = (added * 60) % 60;
            //second = (int) ((added * 3600) % 60);
            change_to_solar = false;
            setConstants();
        }
        minuteStr = double_digit_format((int)Math.floor(minute));
        secondStr = double_digit_format(second);
    }
                                   
    SunCalculations()
    {
        setConstants();
    }
    
    SunCalculations(double latitude, double longitude, double time_change,
                    int year, int month, int day, int hour, int minute,
                    boolean solar, boolean mean_solar)
    {
        super(latitude, longitude, time_change,
                    year, month, day, hour, minute,
                    solar, mean_solar);
        setConstants();
    }
    
    double getL() {return L;}
    
    double raw_dir(){return raw_dir;}
    
    double tilt() {return tilt;}
    
    private double sin(double theta)
    {
        return Math.sin(Math.toRadians(theta));
    }
    
    private double cos(double theta)
    {
        return Math.cos(Math.toRadians(theta));
    }
    
    private double tan(double theta)
    {
        return Math.tan(Math.toRadians(theta));
    }
    
    private double asin(double x)
    {
        return Math.toDegrees(Math.asin(x));
    }
    
    private double acos(double x)
    {
        return Math.toDegrees(Math.acos(x));
    }
    
    private double atan(double x)
    {
        return Math.toDegrees(Math.atan(x));
    }
    
    private double mar_equinox()
    {
        switch (year)
        {
            case 2016: return d_after_jan1(3,20,16+time_change,30);
            case 2017: return d_after_jan1(3,20,10+time_change,28);
            case 2018: return d_after_jan1(3,20,16+time_change,15);
            case 2019: return d_after_jan1(3,20,21+time_change,58);
            case 2020: return d_after_jan1(3,20,3+time_change,50);
            case 2021: return d_after_jan1(3,20,9+time_change,37);
            case 2022: return d_after_jan1(3,20,15+time_change,32);
        }
        double x = (year-2020) * 0.2422 - (year-2020)/4 + (year-2000)/100 - (year-2000)/400;
        return d_after_jan1(3,20+x,3+time_change,50);
    }
    
    private double raw_equinox(double h, double m)
    {
        return d_after_jan1(month,day,h,m)-mEq;
    }
    
    private double d_after_mar_eq(double h, double m)
    {
        double raw = raw_equinox(h,m);
        if (raw < 0)
        {
            raw += 365;
            if (isLeap)
                raw += 1;
        }
        return raw;
    }
    
    private double euler(double x, boolean bool)
    {
        int num_subint = 5000; 
        double y_1 = 0;
        double len_subint = x/num_subint;
        int years = year - 2019;
        if (rawEq < 0)
            years -= 1;
        double prec = Math.toRadians(13.21 + years * 360.0/25772);
        for (int i = 0; i < num_subint; i++)
        {
            double m = Math.pow((1-0.0167*Math.sin(y_1-prec)),2)/58.1080384769;
            if (bool)
            {
                prec += 2*Math.PI*len_subint/(25772*365.24219);
                m += 2*Math.PI/(365.24219*25772);
            }
            y_1 += m*len_subint;
        }
        return Math.toDegrees(y_1);
    }
    
    double euler()
    {
        double H = hour;
        if (solar)
            H -= raw_time_shift(hour,minute);
        return euler(d_after_mar_eq(H,minute),true);
    }
    
    double euler_rounded()
    {
        return round(euler(),1);
    }
    
    private double theta(double h, double m)
    {
        if (solar)
            m -= raw_time_shift(h,m);
        double D = euler(d_after_mar_eq(h,m),true);
        if (latitude < 0)
            D = (D + 180) % 360;
        return D;
    }
    
    private double f(double theta)
    {
        double f = atan(cos(tilt)*tan(theta));
        if (0 <= theta && theta <= 90)
            return f;
        else if (90 < theta && theta <= 270)
            return f+180;
        else
            return f+360;
    }
    
    private double init_eq()
    {
        int dyear = year - 2019;
        if (0 <= dyear && dyear < 500)
            return -7.445 + 0.28/500*dyear;
        else if (500 <= dyear && dyear < 1000)
            return -7.165 + 0.38/500 * (dyear-500);
        else if (-500 <= dyear && dyear < 0)
            return -7.615+0.17/500 * (dyear + 500);
        else if (-1000 <= dyear && dyear < -500)
            return -7.670+0.055/500* (dyear + 1000);
        else if (1000 <= dyear)
            return -6.785+1.055/1000 * (dyear - 1000);
        else
            return -7.445 - 0.335 / 1000 * (dyear + 2000);
    }
    
    private double raw_time_shift(double h, double m)
    {
        double k = 0.99726957175;
        double d = d_after_mar_eq(h,m);
        double p = f(euler(k*d,false));
        return 1440*(d - (k*d + k/360*p+k/360*(f(euler(k*d+k/360*p,false))-p)))
                +init_eq;
    }
    
    private double time_shift(double h, double m)
    {
        return raw_time_shift(h,m) + 4*(longitude-15*time_change);
    }
    
    double raw_time_shift()
    {
        return round(raw_time_shift(hour,minute),1);
    }
    
    double time_shift()
    {
        return round(time_shift(hour,minute),1);
    }
    
    private double getT(double h, double m)
    {
        double T = (h-6)*60+m;
        if (!solar) T += time_shift(h,m);
        if (T < 0)
            T += 1440;
        else if (T >= 1440)
            T -= 1440;
        return T;
    }
    
    private double trig(double theta)
    {
        return asin(sin(tilt)*sin(theta));
    }
    
    double direct_rays()
    {
        return round(trig(euler()),1);
    }
    
    private double arc1(double h, double m)
    {
        return trig(theta(h,m));
    }
    
    private double arc2(double h, double m)
    {
        double a1 = arc1(h,m);
        double T = getT(h,m);
        return acos(cos(a1)*cos(T/4));
    }
    
    private double angle3(double h, double m)
    {
        double a1 = arc1(h,m);
        double T = getT(h,m);
        double x = sin(T/4);
        double y = tan(a1);
        if (x != 0)
            return atan(y/x);
        int sign = (int) (y/Math.abs(y));
        return sign*90;
    }
    
    double true_alt(double h, double m)
    {
        double a2 = arc2(h,m);
        double a3 = angle3(h,m);
        double T = getT(h,m);
        double A = asin(sin(a3+90-L)*sin(a2));
        if (720 < T && T < 1440)
            A *= -1;
        return A;
    }
    
    private double refraction(double alt)
    {
        if (-5.232 <= alt && alt <= -5.002)
            return alt;
        else
            return alt + 1.02/(60*tan(alt+10.3/(alt+5.11)));
    }
    
    double altitude()
    {
        return round(refraction(true_alt(hour,minute)),1);
    }
    
    private double direction(double h, double m)
    {
        double a2 = arc2(h,m);
        double a3 = angle3(h,m);
        double T = getT(h,m);
        double rawDir = atan(cos(a3+90-L)*tan(a2));
        if (360 < T && T <= 720)
            rawDir += 180;
        else if (720 < T && T <= 1080)
            rawDir = -rawDir + 180;
        else if (1080 < T && T < 1440)
            rawDir = -rawDir + 360;
        if (latitude < 0)
            rawDir *= -1;
        return (((rawDir + 90) % 360)+360)%360;
        //return (rawDir + 90) % 360;
    }
    
    double direction()
    {
        return round(direction(hour,minute),1);
    }
    
    private int roundDir(double dir)
    {
        int rounded = 0;
        while (dir - rounded * 22.5 >= 0)
            rounded ++;
        if (dir - (rounded - 1) * 22.5 < rounded * 22.5 - dir)
            rounded--;
        if (rounded == 16)
            rounded = 0;
        return rounded;
    }
    
    private String dirLetters(double dir)
    {
        int index = roundDir(dir);
        return dirList[index]; 
    }
    
    String dirLetters()
    {
        return dirLetters(raw_dir);
    }
    
    private double noon_shift()
    {
        if (solar)
            return 0;
        else
            return -time_shift(12,4*(15*time_change-longitude));
    }
    
    private double[] noon_time()
    {
        double shift = noon_shift();
        double[] noonHM = new double[2];
        noonHM[0] = 12 + Math.floor(shift/60);
        double temp = ((shift % 60)+60)%60;
        //double temp = (shift % 60);
        noonHM[1] = temp;
        return noonHM;
    }
    
    private String print_time(int H, int M)
    {
        H += Math.floor(M/60.0);
        M = ((M % 60)+60)%60;
        //M = (M%60);
        return H + ":" + double_digit_format(M);
    }
    
    private String print_time_dir(int H, int M)
    {
        String str = print_time(H,M);
        double dir = direction(H,M);
        if (!solar)
            str += " at " + round(dir,1) + deg + " (" + 
                    dirLetters(dir) + ")";
        return str;
    }
    
    String str_noon_time()
    {
        int H = (int) noonH;
        int M = (int) Math.round(noonM);
        return print_time(H,M);
    }
    
    private double raw_max_alt(double h, double m)
    {
        return 90-L + arc1(h,m);
    }
    
    private double max_alt(double h, double m)
    {
        double A = raw_max_alt(h,m);
        if (A <= 90)
            return A;
        else
            return 180-A;
    }
    
    private double raw_noon_alt()
    {
        return raw_max_alt(noonH,noonM);
    }
    
    double noon_alt()
    {
        return round(max_alt(noonH,noonM),1);
    }
    
    String noon_dir()
    {
        double A = raw_noon_alt();
        if ((latitude >= 0 && A < 90)||(latitude < 0 && A > 90))
            return "South";
        else
            return "North";
    }
    
    private double error(double h, double m, double alt)
    {
        return Math.abs(true_alt(h,m)-alt);
    }
    
    private boolean no_rise(double alt)
    {
        return (N1 < alt && N2 < alt) || 
                (2*(L-90)+N1 > alt && 2*(L-90) + N2 > alt);
    }
    
    String sunup(double alt)
    {
        if (no_rise(alt) || (true_alt(0,0) > alt && true_alt(24,0) < alt))
            return "None";
        int h = 6;
        int m = 0;
        int incr = 5;
        while (incr >= 1)
        {
            while (true_alt(h,m) > alt)
            {
                m -= Math.pow(2,incr);
                if (-384 <= m && m < -360)
                    m += 1440;
            }
            while (true_alt(h,m) < alt)
            {
                m += Math.pow(2,incr-1);
                if (1080 <= m && m <= 1088)
                    m -= 1440;
            }
            incr -= 2;
        }
        if (error(h,m-1,alt) < error(h,m,alt))
            m --;
        return print_time_dir(h,m);
    }
    String sundown(double alt)
    {
        if (no_rise(alt) || (true_alt(0,0) < alt && true_alt(24,0) > alt))
            return "None";
        int h = 18;
        int m = 0;
        int incr = 5;
        while (incr >= 1)
        {
            while (true_alt(h,m) > alt)
            {
                m += Math.pow(2,incr);
                if (360 <= m && m <= 384)
                    m -= 1440;
            }
            while (true_alt(h,m) < alt)
            {
                m -= Math.pow(2,incr-1);
                if (-1088 <= m && m < -1080)
                    m += 1440;
            }
            incr -= 2;
        }
        if (error(h,m+1,alt) < error(h,m,alt))
            m += 1;
        return print_time_dir(h,m);
    }
}
