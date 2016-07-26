package cc.shanruifeng.functions.card;

import cc.shanruifeng.functions.utils.CardUtils;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

/**
 * @author ruifeng.shan
 * @date 2016-07-25
 * @time 20:14
 */
public class UDFChinaIdCardBirthday extends UDF{
    private Text result = new Text();

    public UDFChinaIdCardBirthday() {
    }

    public Text evaluate(Text idCard) {
        result.set(CardUtils.getIdCardBirthday(idCard.toString()));
        return result;
    }
}
