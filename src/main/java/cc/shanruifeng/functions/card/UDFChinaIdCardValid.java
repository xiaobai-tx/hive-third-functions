package cc.shanruifeng.functions.card;

import cc.shanruifeng.functions.utils.CardUtils;
import org.apache.hadoop.io.BooleanWritable;
import org.apache.hadoop.io.Text;

/**
 * @author ruifeng.shan
 * @date 2016-07-25
 * @time 20:15
 */
public class UDFChinaIdCardValid {
    private BooleanWritable result = new BooleanWritable();

    public UDFChinaIdCardValid() {
    }

    public BooleanWritable evaluate(Text idCard) {
        result.set(CardUtils.isValidIdCard(idCard.toString()));
        return result;
    }
}
