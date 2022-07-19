package com.github.aaronshan.functions.card;

import org.apache.hadoop.io.BooleanWritable;
import org.apache.hadoop.io.Text;
import org.junit.Assert;
import org.junit.Test;

public class UDFChinaIdCardValidTest {

    protected void runTest(Text expect, UDFChinaIdCardValid udf) {
        BooleanWritable res = udf.evaluate(expect);
        Assert.assertNotNull(res);
//        Assert.assertEquals("bd_to_gcj test", expect.toString(), res.toString());
    }

    @Test
    public void testBdToGcj() throws Exception {
        runTest(new Text(""), new UDFChinaIdCardValid());
    }

}
