package cc.shanruifeng.functions.date;

import com.google.common.collect.ImmutableMap;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.serde2.io.TimestampWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.joda.time.LocalDate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @author ruifeng.shan
 * @date 15-9-1
 */
public class UDFTypeOfDay extends UDF {
    public static final Map<String, Boolean> holidayDayMap = ImmutableMap.<String, Boolean>builder().put("20130101", true).put("20130102", true).put("20130103", true)
            .put("20130209", true).put("20130210", true).put("20130211", true).put("20130212", true).put("20130213", true).put("20130214", true).put("20130215", true)
            .put("20130404", true).put("20130405", true).put("20130406", true).put("20130429", true).put("20130430", true).put("20130501", true).put("20130610", true)
            .put("20130611", true).put("20130612", true).put("20130919", true).put("20130920", true).put("20130921", true).put("20131001", true).put("20131002", true)
            .put("20131003", true).put("20131004", true).put("20131005", true).put("20131006", true).put("20131007", true).put("20140101", true).put("20140121", true)
            .put("20140201", true).put("20140202", true).put("20140203", true).put("20140204", true).put("20140205", true).put("20140206", true).put("20140405", true)
            .put("20140406", true).put("20140407", true).put("20140501", true).put("20140502", true).put("20140503", true).put("20140531", true).put("20140601", true)
            .put("20140602", true).put("20140906", true).put("20140907", true).put("20140908", true).put("20141001", true).put("20141002", true).put("20141003", true)
            .put("20141004", true).put("20141005", true).put("20141006", true).put("20141007", true).put("20150101", true).put("20150102", true).put("20150103", true)
            .put("20150218", true).put("20150219", true).put("20150220", true).put("20150221", true).put("20150222", true).put("20150223", true).put("20150224", true)
            .put("20150404", true).put("20150405", true).put("20150406", true).put("20150501", true).put("20150502", true).put("20150503", true).put("20150620", true)
            .put("20150621", true).put("20150622", true).put("20150903", true).put("20150904", true).put("20150905", true).put("20150926", true).put("20150927", true)
            .put("20151001", true).put("20151002", true).put("20151003", true).put("20151004", true).put("20151005", true).put("20151006", true).put("20151007", true)
            .build();
    public static final Map<String, Boolean> workDayMap = ImmutableMap.<String, Boolean>builder().put("20130105", true).put("20130106", true)
            .put("20130216", true).put("20130217", true).put("20130407", true).put("20130427", true).put("20130428", true).put("20130608", true).put("20130609", true)
            .put("20130922", true).put("20130929", true).put("20131012", true).put("20140126", true).put("20140208", true).put("20140504", true).put("20140928", true)
            .put("20141011", true).put("20150104", true).put("20150215", true).put("20150228", true).put("20150906", true).put("20151010", true).build();

    private final SimpleDateFormat formatFrom = new SimpleDateFormat("yyyyMMdd");
    private IntWritable result = new IntWritable();

    public UDFTypeOfDay() {

    }

    /**
     * Get whether is holiday or not.
     *
     * @param dateString the dateString in the format of "yyyyMMdd".
     * @return 1: 法定节假日, 2: 正常周末, 3: 正常工作日 4:攒假的工作日
     */
    public IntWritable evaluate(Text dateString) {
        if (dateString == null) {
            return null;
        }

        try {
            if (holidayDayMap.containsKey(dateString.toString())) {
                result.set(1);
            } else if (workDayMap.containsKey(dateString.toString())) {
                result.set(4);
            } else {
                Date dateFrom = formatFrom.parse(dateString.toString());
                LocalDate date = LocalDate.fromDateFields(dateFrom);
                if (date.getDayOfWeek() < 6) {
                    result.set(3);
                } else {
                    result.set(2);
                }
            }

            return result;
        } catch (Exception e) {
            return null;
        }
    }

    public IntWritable evaluate(TimestampWritable t) {
        if (t == null) {
            return null;
        }

        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(t.getTimestamp());
            String dateString = formatFrom.format(calendar.getTime());

            if (holidayDayMap.containsKey(dateString)) {
                result.set(1);
            } else if (workDayMap.containsKey(dateString)) {
                result.set(4);
            } else {
                LocalDate date = LocalDate.fromCalendarFields(calendar);
                if (date.getDayOfWeek() < 6) {
                    result.set(3);
                } else {
                    result.set(2);
                }
            }
        } catch (Exception e) {
            return null;
        }


        return result;
    }
}
