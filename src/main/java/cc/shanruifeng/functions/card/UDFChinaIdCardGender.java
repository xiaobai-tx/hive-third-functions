package cc.shanruifeng.functions.card;

import cc.shanruifeng.functions.utils.CardUtils;
import org.apache.hadoop.io.Text;

/**
 * @author ruifeng.shan
 * @date 2016-07-25
 * @time 20:14
 */
public class UDFChinaIdCardGender {
    private Text result = new Text();

    public UDFChinaIdCardGender() {
    }

    public Text evaluate(Text idCard) {
        result.set(CardUtils.getIdCardGender(idCard.toString()));
        return result;
    }
}
