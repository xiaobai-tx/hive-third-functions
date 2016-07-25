package cc.shanruifeng.functions.json;

import cc.shanruifeng.functions.utils.json.JsonUtils;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

/**
 * @author ruifeng.shan
 * @date 2016-07-25
 * @time 14:57
 */
public class UDFJsonArrayLength extends UDF {
    private LongWritable result = new LongWritable();

    public UDFJsonArrayLength() {
    }

    public LongWritable evaluate(Text text) {
        try {
            result.set(JsonUtils.jsonArrayLength(text.toString()));
            return result;
        } catch (Exception e) {
            return null;
        }
    }
}
