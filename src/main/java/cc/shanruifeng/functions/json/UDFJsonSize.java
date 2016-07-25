package cc.shanruifeng.functions.json;

import cc.shanruifeng.functions.utils.json.JsonExtract;
import cc.shanruifeng.functions.utils.json.JsonPath;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

/**
 * @author ruifeng.shan
 * @date 2016-07-25
 * @time 16:33
 */
public class UDFJsonSize extends UDF{
    private LongWritable result = new LongWritable();

    public UDFJsonSize() {
    }

    public LongWritable evaluate(Text json, Text path) {
        try {
            JsonPath jsonPath = new JsonPath(path.toString());
            Long size = JsonExtract.extract(json.toString(), jsonPath.getSizeExtractor());
            result.set(size);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new LongWritable(-1234L);
        }
    }
}
