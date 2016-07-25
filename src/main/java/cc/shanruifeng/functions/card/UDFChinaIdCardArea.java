package cc.shanruifeng.functions.card;

import cc.shanruifeng.functions.utils.CardUtils;
import org.apache.hadoop.io.Text;

/**
 * @author ruifeng.shan
 * @date 2016-07-25
 * @time 20:12
 */
public class UDFChinaIdCardArea {
    private Text result = new Text();

    public UDFChinaIdCardArea() {
    }

    public Text evaluate(Text idCard) {
        result.set(CardUtils.getIdCardArea(idCard.toString()));
        return result;
    }
}
