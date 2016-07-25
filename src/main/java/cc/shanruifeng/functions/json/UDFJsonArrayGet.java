package cc.shanruifeng.functions.json;

import cc.shanruifeng.functions.utils.json.JsonUtils;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

/**
 * @author ruifeng.shan
 * @date 2016-07-25
 * @time 15:29
 */
public class UDFJsonArrayGet extends UDF {
    private Text result = new Text();

    public UDFJsonArrayGet() {
    }

    public Text evaluate(Text json, long index) {
        try {
            result.set(JsonUtils.jsonArrayGet(json.toString(), index));
            return result;
        } catch (Exception e) {
            return null;
        }
    }
}
