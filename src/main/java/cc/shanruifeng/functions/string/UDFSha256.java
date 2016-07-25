package cc.shanruifeng.functions.string;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.hadoop.io.Text;

/**
 * @author ruifeng.shan
 * @date 2016-07-25
 * @time 14:29
 */
public class UDFSha256 {
    private Text result = new Text();

    public UDFSha256() {
    }

    /**
     * md5 hash.
     *
     * @param text
     * @return
     */
    public Text evaluate(Text text) {
        if (text == null) {
            return null;
        }

        result.set(DigestUtils.sha256Hex((text.toString())));
        return result;
    }
}
