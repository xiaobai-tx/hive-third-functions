package cc.shanruifeng.functions.string;

import io.airlift.slice.InvalidUtf8Exception;
import io.airlift.slice.SliceUtf8;
import io.airlift.slice.Slices;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

import java.util.OptionalInt;

import static java.lang.Character.MAX_CODE_POINT;
import static java.lang.Character.SURROGATE;

/**
 * @author ruifeng.shan
 * @date 2018-07-27 下午2:02
 */
@Description(name = "from_utf8"
        , value = "_FUNC_(string) - decodes the UTF-8 encoded string\n" +
        "_FUNC_(string, string) - decodes the UTF-8 encoded string\n" +
        "_FUNC_(string, long) - decodes the UTF-8 encoded string."
        , extended = "Example:\n > select _FUNC_(string) from src;")
public class UDFStringFromUTF8 extends UDF {
    private Text result = new Text();

    public UDFStringFromUTF8() {
    }

    public Text evaluate(Text text) {
        if (text == null) {
            return null;
        }

        result.set(SliceUtf8.fixInvalidUtf8(Slices.utf8Slice(text.toString())).toString());
        return result;
    }

    public Text evaluate(Text text, Text replacementCharacter) throws HiveException {
        if (text == null) {
            return null;
        }

        if (replacementCharacter == null) {
            throw new HiveException("Replacement character string must empty or a single character");
        }

        int count = replacementCharacter.getLength();
        if (count > 1) {
            throw new HiveException("Replacement character string must empty or a single character");
        }

        OptionalInt replacementCodePoint;
        if (count == 1) {
            try {
                replacementCodePoint = OptionalInt.of(replacementCharacter.charAt(0));
            }
            catch (InvalidUtf8Exception e) {
                throw new HiveException("Invalid replacement character");
            }
        }
        else {
            replacementCodePoint = OptionalInt.empty();
        }

        result.set(SliceUtf8.fixInvalidUtf8(Slices.utf8Slice(text.toString()), replacementCodePoint).toString());
        return result;
    }

    public Text evaluate(Text text, LongWritable replacementCodePoint) throws HiveException {
        if (text == null) {
            return null;
        }

        if (replacementCodePoint == null) {
            throw new HiveException("replacement long value cannot be null!");
        }

        long codePoint = replacementCodePoint.get();

        if (replacementCodePoint.get() > MAX_CODE_POINT || Character.getType((int) codePoint) == SURROGATE) {
            throw new HiveException("Invalid replacement character");
        }

        result.set(SliceUtf8.fixInvalidUtf8(Slices.utf8Slice(text.toString()), OptionalInt.of((int) codePoint)).toString());
        return result;
    }

}
