package cc.shanruifeng.functions.card;

import cc.shanruifeng.functions.utils.CardUtils;
import org.apache.hadoop.io.Text;

/**
 * @author ruifeng.shan
 * @date 2016-07-25
 * @time 20:11
 */
public class UDFChinaIdCardCity {
    private Text result = new Text();

    public UDFChinaIdCardCity() {
    }

    public Text evaluate(Text idCard) {
        result.set(CardUtils.getIdCardCity(idCard.toString()));
        return result;
    }
}
