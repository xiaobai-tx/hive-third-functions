package cc.shanruifeng.functions.card;

import cc.shanruifeng.functions.utils.CardUtils;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

/**
 * Created by ruifengshan on 16/3/22.
 */

//身份证->json
public class UDFChinaIdCardInfo extends UDF {
    private Text result = new Text();

    public UDFChinaIdCardInfo() {
    }

    public Text evaluate(Text idCard) {
        result.set(CardUtils.getJsonOfChinaIdCard(idCard.toString()));
        return result;
    }
}
