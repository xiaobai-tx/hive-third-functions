package cc.shanruifeng.functions.array;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.BooleanWritable;
import org.apache.hadoop.io.Text;

import java.util.ArrayList;

/**
 * @author ruifeng.shan
 * @date 2015-3-23
 */
public class UDFArrayContains extends UDF {

    private BooleanWritable result = new BooleanWritable();

    public UDFArrayContains() {

    }

    /**
     * Returns <tt>true</tt> if this array contains the specified element.
     *
     * @param arrayList
     * @param dateString
     * @return
     */
    public BooleanWritable evaluate(ArrayList arrayList, Text dateString) {
        if (arrayList == null || arrayList.size() < 1) {
            return result;
        }

        String string = dateString.toString();
        for (Object str : arrayList) {
            if (str.equals(string)) {
                result = new BooleanWritable(true);
                break;
            }
        }

        return result;
    }

}
