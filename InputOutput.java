import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

class InputOutput extends SharedFunctions
{   
    private JFrame frame1, frame2;
    private static String changeFieldText = "";
    
    boolean isSat()
    {
        int D = (year - 2017) * 365 + (year - 2017) / 4
            - (year-2001)/100 + (year-2001) / 400;
        D += (int) d_after_jan1(month,day,0,0);
        return D % 7 == 6;
    }
    
    void getInput()
    {
        frame1 = new JFrame("Sun Position");
        frame1.setBounds(0,0,680,350);
        frame1.setLayout(null);
        frame1.getContentPane().setBackground(Color.WHITE);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JLabel latLabel = new JLabel("Latitude: ");
        latLabel.setBounds(20,20,100,10);
        JLabel invLatLab = new JLabel("Invalid latitude");
        invLatLab.setBounds(440,20,200,10);
        invLatLab.setForeground(Color.RED);
        invLatLab.setVisible(false);
        JLabel longLabel = new JLabel("Longitude: ");
        longLabel.setBounds(20,50,100,15);
        JLabel invLongLab = new JLabel("Invalid longitude");
        invLongLab.setBounds(440,50,200,15);
        invLongLab.setForeground(Color.RED);
        invLongLab.setVisible(false);
        JLabel longExtra = new JLabel("(Leave blank if latitude is 90 or -90)");
        longExtra.setBounds(180,50,250,15);
        JLabel changeLabel = new JLabel("Time offset: ");
        changeLabel.setBounds(20,80,90,15);
        JLabel invChangeLab = new JLabel("Invalid time offset");
        invChangeLab.setBounds(440,80,200,15);
        invChangeLab.setForeground(Color.RED);
        invChangeLab.setVisible(false);
        JLabel invSolarLab = new JLabel("Solar time not allowed for poles");
        invSolarLab.setBounds(440,80,250,15);
        invSolarLab.setForeground(Color.RED);
        invSolarLab.setVisible(false);
        JLabel invMSolarLab = new JLabel("Mean solar time not allowed for poles");
        invMSolarLab.setBounds(440,80,300,15);
        invMSolarLab.setForeground(Color.RED);
        invMSolarLab.setVisible(false);
        JLabel dateLabel = new JLabel("Date: ");
        dateLabel.setBounds(20,110,100,10);
        JLabel invDateLab = new JLabel("Invalid date");
        invDateLab.setBounds(440,110,150,10);
        invDateLab.setForeground(Color.RED);
        invDateLab.setVisible(false);
        JLabel slash1 = new JLabel("/");
        slash1.setBounds(132,105,20,20);
        slash1.setFont(new Font(slash1.getFont().getName(),Font.PLAIN,20));
        JLabel slash2 = new JLabel("/");
        slash2.setBounds(164,105,20,20);
        slash2.setFont(new Font(slash2.getFont().getName(),Font.PLAIN,20));
        JLabel timeLabel = new JLabel("Time: ");
        timeLabel.setBounds(20,140,100,10);
        JLabel invTimeLab = new JLabel("Invalid time");
        invTimeLab.setBounds(440,140,150,10);
        invTimeLab.setForeground(Color.RED);
        invTimeLab.setVisible(false);
        JLabel auto1 = new JLabel("time blank for auto retrieval)");
        auto1.setBounds(230,130,250,15);
        JLabel auto2 = new JLabel("(Leave time offset, date or ");
        auto2.setBounds(230,110,200,15);
        JLabel colon = new JLabel(":");
        colon.setBounds(132,133,20,20);
        colon.setFont(new Font(colon.getFont().getName(),Font.PLAIN,18));
        
        
        JTextField latField = new JTextField();
        latField.setBounds(110,13,66,25);
        JTextField longField = new JTextField();
        longField.setBounds(110,45,66,25);
        JTextField changeField = new JTextField();
        changeField.setBounds(110,75,66,25);
        JTextField monthField = new JTextField();
        monthField.setBounds(110,106,24,20);
        JTextField dayField = new JTextField();
        dayField.setBounds(141,106,24,20);
        JTextField yearField = new JTextField();
        yearField.setBounds(173,106,42,20);
        JTextField hourField = new JTextField();
        hourField.setBounds(110,135,24,20);
        JTextField minuteField = new JTextField();
        minuteField.setBounds(137,135,24,20);
        
        JCheckBox isSolar = new JCheckBox("Solar time");
        isSolar.setBounds(175,78,110,20);
        isSolar.setOpaque(false);
        JCheckBox isMeanSolar = new JCheckBox("Mean solar time");
        isMeanSolar.setBounds(270,78,150,20);
        isMeanSolar.setOpaque(false);
        
        JButton calc = new JButton("Calculate");
        calc.setBounds(20,170,100,30);
        
        frame1.add(latLabel);
        frame1.add(invLatLab);
        frame1.add(longLabel);
        frame1.add(invLongLab);
        frame1.add(longExtra);
        frame1.add(changeLabel);
        frame1.add(invChangeLab);
        frame1.add(invSolarLab);
        frame1.add(invMSolarLab);
        frame1.add(latField);
        frame1.add(longField);
        frame1.add(changeField);
        frame1.add(isSolar);
        frame1.add(isMeanSolar);
        frame1.add(dateLabel);
        frame1.add(invDateLab);
        frame1.add(monthField);
        frame1.add(slash1);
        frame1.add(dayField);
        frame1.add(slash2);
        frame1.add(yearField);
        frame1.add(timeLabel);
        frame1.add(hourField);
        frame1.add(colon);
        frame1.add(minuteField);
        frame1.add(invTimeLab);
        frame1.add(calc);
        frame1.add(auto1);
        frame1.add(auto2);
        frame1.setVisible(true);
        
        isSolar.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (isSolar.isSelected())
                {
                    if (!isMeanSolar.isSelected())
                        changeFieldText = changeField.getText();
                    changeField.setText("");
                    changeField.setEditable(false);
                    isMeanSolar.setSelected(false);
                }
                else
                {
                    changeField.setEditable(true);
                    changeField.setText(changeFieldText);
                }
            }
        });
        
        isMeanSolar.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (isMeanSolar.isSelected())
                {
                    if (!isSolar.isSelected())
                        changeFieldText = changeField.getText();
                    changeField.setText("");
                    changeField.setEditable(false);
                    isSolar.setSelected(false);
                }
                else
                {
                    changeField.setEditable(true);
                    changeField.setText(changeFieldText);
                }
            }
        });
        
        calc.addActionListener(new ActionListener()
        {
            boolean invLat, invLong, invChange,invDate, invTime, invSolar, 
                    invMeanSolar;
            boolean invalid()
            {
                return invLat || invLong || invChange || invDate || invTime
                              || invSolar || invMeanSolar;
            }
            
            public void actionPerformed(ActionEvent e)
            {
                double tempNum;
                String latText = latField.getText();
                int latLen = latText.length();
                invLat = false;
                invLong = false;
                invChange = false;
                invDate = false;
                invTime = false;
                invSolar = false;
                invMeanSolar = false;
                if (latLen == 0)
                    invLat = true;
                else
                {
                    char last = Character.toLowerCase(latText.charAt(latLen-1));
                    if (!(Character.isDigit(last) || last == '.' || 
                        last == 'n' || last == 's'))
                        invLat = true;
                    else if (last == 'n' || last == 's')
                    {
                        try
                        {
                            tempNum = Double.parseDouble(latText.substring(0,latLen-1));
                            if (tempNum < 0 || tempNum > 90)
                                invLat = true;
                            else if (last == 'n')
                                latitude = tempNum;
                            else
                                latitude = -tempNum;
                        }
                        catch (NumberFormatException ex)
                        {
                            invLat = true;
                        }
                    }
                    else
                    {
                        try
                        {
                            tempNum = Double.parseDouble(latText);
                            if (tempNum < -90 || tempNum > 90)
                                invLat = true;
                            else
                                latitude = tempNum;
                        }
                        catch (NumberFormatException ex)
                        {
                            invLat = true;
                        }
                    }
                }
                 
                String longText = longField.getText();
                int longLen = longText.length();
                if (longLen == 0)
                {
                    if (!invLat && (latitude == 90 || latitude == -90))
                        longitude = 0;
                    else
                        invLong = true;
                }
                else
                {
                    char last = Character.toLowerCase(longText.charAt(longLen-1));
                    if (!(Character.isDigit(last) || last == '.' || 
                        last == 'e' || last == 'w'))
                        invLong = true;
                    else if (last == 'e' || last == 'w')
                    {
                        try
                        {
                            tempNum = Double.parseDouble(longText.substring(0,longLen-1));
                            if (tempNum < 0 || tempNum > 180)
                                invLong = true;
                            else if (last == 'e')
                                longitude = tempNum;
                            else
                                longitude = -tempNum;
                        }
                        catch (NumberFormatException ex)
                        {
                            invLong = true;
                        }
                    }
                    else
                    {
                        try
                        {
                            tempNum = Double.parseDouble(longText);
                            if (tempNum < -180 || tempNum > 180)
                                invLong = true;
                            else
                                longitude = tempNum;
                        }
                        catch (NumberFormatException ex)
                        {
                            invLong = true;
                        }
                    }
                }
                
                TimeZone tz = TimeZone.getDefault();
                double system_offset = tz.getOffset(new Date().getTime()) / (1000.0*3600);
                
                boolean solarSel = isSolar.isSelected();
                boolean meanSel = isMeanSolar.isSelected();
                if (solarSel || meanSel)
                {
                    if (solarSel)
                    {
                        if (Math.abs(latitude) == 90)
                        {
                            isSolar.setSelected(false);
                            changeField.setEditable(true);
                            changeField.setText(changeFieldText);
                            invSolar = true;
                        }
                        else
                            solar = true;
                    }
                    else
                    {
                        if (Math.abs(latitude) == 90)
                        {
                            isMeanSolar.setSelected(false);
                            changeField.setEditable(true);
                            changeField.setText(changeFieldText);
                            invMeanSolar = true;
                        }
                        mean_solar = true;
                    }
                    if (!invLong)
                    {
                        time_change = longitude / 15.0;
                    }
                }
                else
                {
                    String changeText = changeField.getText();
                    if (changeText.length() == 0)
                    {
                        time_change = system_offset;
                    }
                    else
                    {
                        try
                        {
                            time_change = Double.parseDouble(changeText);
                        }
                        catch (NumberFormatException ex)
                        {
                            invChange = true;
                        }
                    }
                }
                
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
                String datetime = formatter.format(date);
                
                String monthText = monthField.getText();
                String dayText = dayField.getText();
                String yearText = yearField.getText();
                String hourText = hourField.getText();
                String minuteText = minuteField.getText();
                
                if (monthText.length() == 0 && dayText.length() == 0
                    && yearText.length() == 0)
                {
                    month = Integer.parseInt(datetime.substring(0,2));
                    day = Integer.parseInt(datetime.substring(3,5));
                    year = Integer.parseInt(datetime.substring(6,10));
                }
                else
                {
                    try
                    {
                        month = Integer.parseInt(monthText);
                        day = Integer.parseInt(dayText);
                        year = Integer.parseInt(yearText);
                        if (!(1 <= month && month <= 12 && 1 <= day && day <= 
                            monthdays(month)))
                            invDate = true;
                    }
                    catch (NumberFormatException ex)
                    {
                        invDate = true;
                    }
                }
                if (hourText.length() == 0 && minuteText.length() == 0)
                {
                    hour = Integer.parseInt(datetime.substring(11,13));
                    minute = Double.parseDouble(datetime.substring(14,16));                  
                    second = Integer.parseInt(datetime.substring(17));
                    minute += second/60.0;
                    double offset = time_change - system_offset;
                    double time = hour + minute / 60.0;
                    double added = time + offset;
                    hour = (int) Math.floor(added);
                    check_hour();
                    minute = (((added * 60) % 60)+ 60) % 60;
                    second = (int) ((((added * 3600) % 60) + 60) % 60);
                    if (solar)
                        change_to_solar = true;
                }
                else
                {
                    try
                    {
                        secondStr = "00";
                        hour = Integer.parseInt(hourText);
                        minute = Double.parseDouble(minuteText);
                        minuteStr = double_digit_format((int) minute);
                        if (hour == 24)
                            hour = 0;
                        if (!(0 <= hour && hour <= 24 && 0 <= minute &&
                            minute < 60))
                            invTime = true;
                    }
                    catch (NumberFormatException ex)
                    {
                        invTime = true;
                    }
                }
                
                if (!invalid())
                {
                    showOutput();
                    return;
                }
                
                if (invLat)
                    invLatLab.setVisible(true);
                else
                    invLatLab.setVisible(false);
                    
                if (invLong)
                    invLongLab.setVisible(true);
                else
                    invLongLab.setVisible(false);
                
                if (invChange)
                    invChangeLab.setVisible(true);
                else
                    invChangeLab.setVisible(false);
                    
                if (invSolar)
                    invSolarLab.setVisible(true);
                else
                    invSolarLab.setVisible(false);
                    
                if (invMeanSolar)
                    invMSolarLab.setVisible(true);
                else
                    invMSolarLab.setVisible(false);
                    
                if (invDate)
                    invDateLab.setVisible(true);
                else
                    invDateLab.setVisible(false);
                    
                if (invTime)
                    invTimeLab.setVisible(true);
                else
                    invTimeLab.setVisible(false);
            }
        });
    }
    
    private void showOutput()
    {
        frame2 = new JFrame("Sun Position");
        frame2.setBounds(0,0,680,350);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setLayout(null);
        frame2.getContentPane().setBackground(Color.WHITE);
        
        SunCalculations sun = new SunCalculations();
        String twi_beg = sun.sunup(-6);
        String sunrise = sun.sunup(-5./6);
        String sunset = sun.sundown(-5./6);
        String twi_end = sun.sundown(-6);
        double rts = sun.raw_time_shift();
        String change_str;
        if (solar)
            change_str = "solar";
        else if (time_change % 1 == 0)
            change_str = ((int) time_change) + " UTC";
        else
            change_str = round(time_change,2) + " UTC";
        if (time_change > 0)
            change_str = "+" + change_str;
        
        JLabel dateTime = new JLabel(month + "/" + day + "/" + year
                                    + " at " + hour + ":" + minuteStr
                                    + ":" + secondStr + " (" + change_str 
                                    + ")");
            dateTime.setBounds(20,20,350,15);
            dateTime.setFont(new Font(dateTime.getFont().getName(),Font.BOLD,14));
        JLabel sunLabel = new JLabel("Sun Info");
            sunLabel.setBounds(20,45,100,15);
            sunLabel.setFont(new Font(sunLabel.getFont().getName(),Font.BOLD,14));
        JLabel altLabel1 = new JLabel("Altitude:");
            altLabel1.setBounds(20,65,70,15);
            JLabel altLabel2 = new JLabel(sun.altitude() + deg);
        altLabel2.setBounds(280,65,71,15);
            frame2.add(dateTime);
            frame2.add(sunLabel);
            frame2.add(altLabel1);
            frame2.add(altLabel2);
        
        JLabel eqLabel1 = new JLabel("Equation of time (mean to solar): ");
        JLabel eqLabel2 = new JLabel(rts + " minutes");
        JLabel shiftLab1 = new JLabel("Clock to solar time shift:");
        JLabel shiftLab2 = new JLabel(sun.time_shift() + " minutes");
        JLabel twi_beg_lab1 = new JLabel("Start of 1st period of civil twilight:");
        JLabel twi_beg_lab2 = new JLabel(twi_beg + "");
        JLabel sunrise_lab1 = new JLabel("Sunrise:");
        JLabel sunrise_lab2 = new JLabel(sunrise+ "");
        JLabel sunset_lab1 = new JLabel("Sunset:");
        JLabel sunset_lab2 = new JLabel(sunset+"");
        JLabel twi_end_lab1 = new JLabel("End of 2nd period of civil twilight:");
        JLabel twi_end_lab2 = new JLabel(twi_end + "");
        JLabel theta_lab1 = new JLabel("Angle traversed in orbit:");
        JLabel theta_lab2 = new JLabel(sun.euler_rounded() + deg);
        JLabel direct_lab1 = new JLabel("Latitude receiving direct rays:");
        JLabel direct_lab2 = new JLabel(sun.direct_rays() + deg);
        
        if (sun.getL() != 90)
        {    
            JLabel dirLabel1 = new JLabel("Direction: ");
                dirLabel1.setBounds(20,82,100,15);
                frame2.add(dirLabel1);
            JLabel dirLabel2 = new JLabel(sun.raw_dir() + deg + " (" + sun.dirLetters() + ")");
                dirLabel2.setBounds(280,82,150,15);
                frame2.add(dirLabel2);
            theta_lab1.setBounds(20,99,250,15);
                frame2.add(theta_lab1);
            theta_lab2.setBounds(280,99,250,15);
                frame2.add(theta_lab2);
            direct_lab1.setBounds(20,116,250,15);
                frame2.add(direct_lab1);
            direct_lab2.setBounds(280,116,250,15);
                frame2.add(direct_lab2);
            JLabel day_end_lab1;
            if (isSat())
                day_end_lab1 = new JLabel("End of Shabbat:");
            else
                day_end_lab1 = new JLabel("End of Jewish day:");
            JLabel day_end_lab2 = new JLabel(sun.sundown(-8.5) + "");
            JLabel noon_alt_lab1 = new JLabel("Noontime altitude:");
            JLabel noon_alt_lab2 = new JLabel(sun.noon_alt() + deg);
            JLabel noon_dir_lab1 = new JLabel("Noontime direction:");
            JLabel noon_dir_lab2 = new JLabel(sun.noon_dir());
            
            if (!sun.solar && !sun.mean_solar)
            {
                eqLabel1.setBounds(20,133,300,15);
                    frame2.add(eqLabel1);
                eqLabel2.setBounds(280,133,150,15);
                    frame2.add(eqLabel2);
                shiftLab1.setBounds(20,150,300,15);
                    frame2.add(shiftLab1);
                shiftLab2.setBounds(280,150,200,15);
                    frame2.add(shiftLab2);
                twi_beg_lab1.setBounds(19,167,300,15);
                    frame2.add(twi_beg_lab1);
                twi_beg_lab2.setBounds(280,167,200,15);
                    frame2.add(twi_beg_lab2);
                sunrise_lab1.setBounds(20,184,200,15);
                    frame2.add(sunrise_lab1);
                sunrise_lab2.setBounds(280,184,200,15);
                    frame2.add(sunrise_lab2);
                sunset_lab1.setBounds(20,201,200,15);
                    frame2.add(sunset_lab1);
                sunset_lab2.setBounds(280,201,200,15);
                    frame2.add(sunset_lab2);
                twi_end_lab1.setBounds(20,218,300,15);
                    frame2.add(twi_end_lab1);
                twi_end_lab2.setBounds(280,218,200,15);
                    frame2.add(twi_end_lab2);
                day_end_lab1.setBounds(20,235,200,15);
                    frame2.add(day_end_lab1);
                day_end_lab2.setBounds(280,235,200,15);
                    frame2.add(day_end_lab2);
                JLabel noon_time_lab1 = new JLabel("Time of noon today:");
                    noon_time_lab1.setBounds(20,252,200,15);
                    frame2.add(noon_time_lab1);
                JLabel noon_time_lab2 = new JLabel(sun.str_noon_time());
                    noon_time_lab2.setBounds(280,252,200,15);
                    frame2.add(noon_time_lab2);
                noon_alt_lab1.setBounds(20,269,200,15);
                    frame2.add(noon_alt_lab1);
                noon_alt_lab2.setBounds(280,269,200,15);
                    frame2.add(noon_alt_lab2);
                if (-sun.tilt() <= latitude && latitude <= sun.tilt())
                {
                    noon_dir_lab1.setBounds(20,286,200,15);
                        frame2.add(noon_dir_lab1);
                    noon_dir_lab2.setBounds(280,286,200,15);
                        frame2.add(noon_dir_lab2);
                }
            }
            else
            {
                if (sun.mean_solar)
                {
                    eqLabel1.setBounds(20,133,300,15);
                        frame2.add(eqLabel1);
                    eqLabel2.setBounds(280,133,200,15);
                        frame2.add(eqLabel2);
                }
                else
                {
                    JLabel eqLabel3 = new JLabel("Equation of time (solar to mean):");
                        eqLabel3.setBounds(20,133,300,15);
                        frame2.add(eqLabel3);
                    JLabel eqLabel4 = new JLabel((-rts) + " minutes");
                        eqLabel4.setBounds(280,133,200,15);
                        frame2.add(eqLabel4);
                }
                twi_beg_lab1.setBounds(19,150,300,15);
                    frame2.add(twi_beg_lab1);
                twi_beg_lab2.setBounds(280,150,200,15);
                    frame2.add(twi_beg_lab2);
                sunrise_lab1.setBounds(20,167,200,15);
                    frame2.add(sunrise_lab1);
                sunrise_lab2.setBounds(280,167,200,15);
                    frame2.add(sunrise_lab2);
                sunset_lab1.setBounds(20,184,200,15);
                    frame2.add(sunset_lab1);
                sunset_lab2.setBounds(280,184,200,15);
                    frame2.add(sunset_lab2);
                twi_end_lab1.setBounds(20,201,300,15);
                    frame2.add(twi_end_lab1);
                twi_end_lab2.setBounds(280,201,200,15);
                    frame2.add(twi_end_lab2);
                day_end_lab1.setBounds(20,218,200,15);
                    frame2.add(day_end_lab1);
                day_end_lab2.setBounds(280,218,200,15);
                    frame2.add(day_end_lab2);
                noon_alt_lab1.setBounds(20,235,200,15);
                    frame2.add(noon_alt_lab1);
                noon_alt_lab2.setBounds(280,235,200,15);
                    frame2.add(noon_alt_lab2);
                if (-sun.tilt() <= latitude && latitude <= sun.tilt())
                {
                    noon_dir_lab1.setBounds(20,252,200,15);
                        frame2.add(noon_dir_lab1);
                    noon_dir_lab2.setBounds(280,252,200,15);
                        frame2.add(noon_dir_lab2);
                }
            }
        }
        else
        {
            theta_lab1.setBounds(20,82,250,15);
                frame2.add(theta_lab1);
            theta_lab2.setBounds(280,82,250,15);
                frame2.add(theta_lab2);
            direct_lab1.setBounds(20,99,200,15);
                frame2.add(direct_lab1);
            direct_lab2.setBounds(280,99,200,15);
                frame2.add(direct_lab2);
            eqLabel1.setBounds(20,116,300,15);
                frame2.add(eqLabel1);
            eqLabel2.setBounds(280,116,200,15);
                frame2.add(eqLabel2);
            if (twi_beg != "None")
            {
                twi_beg_lab1.setBounds(20,133,300,15);
                    frame2.add(twi_beg_lab1);
                twi_beg_lab2.setBounds(280,133,300,15);
                    frame2.add(twi_beg_lab2);
            }
            else if (sunrise != "None")
            {
                sunrise_lab1.setBounds(20,133,200,15);
                    frame2.add(sunrise_lab1);
                sunrise_lab2.setBounds(280,133,200,15);
                    frame2.add(sunrise_lab2);
            }
            else if (sunset != "None")
            {
                sunset_lab1.setBounds(20,133,200,15);
                    frame2.add(sunrise_lab1);
                sunset_lab2.setBounds(280,133,200,15);
                    frame2.add(sunrise_lab2);
            }
            else if (twi_end != "None")
            {
                twi_end_lab1.setBounds(20,133,200,15);
                    frame2.add(sunrise_lab1);
                twi_end_lab2.setBounds(280,133,200,15);
                    frame2.add(sunrise_lab2);
            }
        }
        
        JButton rerun = new JButton("Rerun");
        rerun.setBounds(400,20,100,30);
        frame2.add(rerun);
        
        frame2.setVisible(true);
        frame1.dispose();
        
        rerun.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                mean_solar = false;
                solar = false;
                second = 0;
                getInput();
                frame2.dispose();
            }
        });
    }
}
