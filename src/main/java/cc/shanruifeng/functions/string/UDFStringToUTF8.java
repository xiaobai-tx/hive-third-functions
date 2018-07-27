package cc.shanruifeng.functions.string;

import io.airlift.slice.SliceUtf8;
import io.airlift.slice.Slices;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

/**
 * @author aaron02
 * @date 2018-07-27 下午2:02
 */
@Description(name = "to_utf8"
        , value = "_FUNC_(string) - encodes the string to UTF-8."
        , extended = "Example:\n > select _FUNC_(string) from src;")
public class UDFStringToUTF8 extends UDF {
    private Text result = new Text();

    public UDFStringToUTF8() {
    }

    public Text evaluate(Text text) {
        if (text == null) {
            return null;
        }

        result.set(Slices.utf8Slice(text.toString()).toString());
        return result;
    }
}
