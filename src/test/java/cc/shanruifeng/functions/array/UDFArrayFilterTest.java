package cc.shanruifeng.functions.array;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import static java.util.Arrays.asList;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UDFArrayFilterTest {
    @Test
    public void test() throws HiveException {
        UDFArrayFilter udf = new UDFArrayFilter();

        ObjectInspector arrayOI = ObjectInspectorFactory.getStandardListObjectInspector(ObjectInspectorFactory.getStandardListObjectInspector(PrimitiveObjectInspectorFactory.javaStringObjectInspector));
        ObjectInspector valueOI = PrimitiveObjectInspectorFactory.javaStringObjectInspector;
        ObjectInspector[] arguments = {arrayOI, valueOI};

        udf.initialize(arguments);
        List<List<String>> array = ImmutableList.of(asList("abc", null, "123"), ImmutableList.of("a", "b"));
        GenericUDF.DeferredObject arrayObj = new GenericUDF.DeferredJavaObject(array);
        GenericUDF.DeferredObject valueObj = new GenericUDF.DeferredJavaObject("s -> s.get(1) == null");
        GenericUDF.DeferredObject[] args = {arrayObj, valueObj};
        ArrayList<Object> output = (ArrayList<Object>)  udf.evaluate(args);

        assertTrue(Iterables.elementsEqual(ImmutableList.of(asList("abc", null, "123")), output));
    }

    @Test
    public void test1() throws HiveException {
        UDFArrayFilter udf = new UDFArrayFilter();

        ObjectInspector arrayOI = ObjectInspectorFactory.getStandardListObjectInspector(PrimitiveObjectInspectorFactory.javaBooleanObjectInspector);
        ObjectInspector valueOI = PrimitiveObjectInspectorFactory.javaStringObjectInspector;
        ObjectInspector[] arguments = {arrayOI, valueOI};

        udf.initialize(arguments);
        List<Boolean> array = asList(null);
        GenericUDF.DeferredObject arrayObj = new GenericUDF.DeferredJavaObject(array);
        GenericUDF.DeferredObject valueObj = new GenericUDF.DeferredJavaObject("x -> x != null && x");
        GenericUDF.DeferredObject[] args = {arrayObj, valueObj};
        ArrayList<Object> output = (ArrayList<Object>)  udf.evaluate(args);

        assertTrue(Iterables.elementsEqual(asList(true), output));
    }
}