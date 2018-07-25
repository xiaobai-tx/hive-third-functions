package cc.shanruifeng.functions.utils;

import org.apache.hadoop.hive.common.type.HiveDecimal;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import pl.joegreen.lambdaFromString.LambdaCreationException;
import pl.joegreen.lambdaFromString.LambdaFactory;
import pl.joegreen.lambdaFromString.TypeReference;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorUtils.compareTypes;

/**
 * @author aaron02
 * @date 2018-07-25 下午4:29
 */
public class LambdaUtils {
    public static Function getFilterFunction(LambdaFactory lambdaFactory, String lambdaString, ObjectInspector oi) throws LambdaCreationException {
        if (compareTypes(oi, PrimitiveObjectInspectorFactory.javaBooleanObjectInspector)) {
            return lambdaFactory.createLambda(lambdaString, new TypeReference<Function<Boolean, Boolean>>(){});
        } else if (compareTypes(oi, PrimitiveObjectInspectorFactory.javaByteObjectInspector)) {
            return lambdaFactory.createLambda(lambdaString, new TypeReference<Function<Byte, Boolean>>(){});
        } else if (compareTypes(oi, PrimitiveObjectInspectorFactory.javaShortObjectInspector)) {
            return lambdaFactory.createLambda(lambdaString, new TypeReference<Function<Short, Boolean>>(){});
        } else if (compareTypes(oi, PrimitiveObjectInspectorFactory.javaIntObjectInspector)) {
            return lambdaFactory.createLambda(lambdaString, new TypeReference<Function<Integer, Boolean>>(){});
        } else if (compareTypes(oi, PrimitiveObjectInspectorFactory.javaLongObjectInspector)) {
            return lambdaFactory.createLambda(lambdaString, new TypeReference<Function<Long, Boolean>>(){});
        } else if (compareTypes(oi, PrimitiveObjectInspectorFactory.javaFloatObjectInspector)) {
            return lambdaFactory.createLambda(lambdaString, new TypeReference<Function<Float, Boolean>>(){});
        } else if (compareTypes(oi, PrimitiveObjectInspectorFactory.javaDoubleObjectInspector)) {
            return lambdaFactory.createLambda(lambdaString, new TypeReference<Function<Double, Boolean>>(){});
        } else if (compareTypes(oi, PrimitiveObjectInspectorFactory.javaStringObjectInspector)) {
            return lambdaFactory.createLambda(lambdaString, new TypeReference<Function<String, Boolean>>(){});
        } else if (compareTypes(oi, PrimitiveObjectInspectorFactory.javaVoidObjectInspector)) {
            return lambdaFactory.createLambda(lambdaString, new TypeReference<Function<Object, Boolean>>(){});
        } else if (compareTypes(oi, PrimitiveObjectInspectorFactory.javaTimestampObjectInspector)) {
            return lambdaFactory.createLambda(lambdaString, new TypeReference<Function<Timestamp, Boolean>>(){});
        } else if (compareTypes(oi, PrimitiveObjectInspectorFactory.javaByteArrayObjectInspector)) {
            return lambdaFactory.createLambda(lambdaString, new TypeReference<Function<byte[], Boolean>>() {});
        }else if (compareTypes(oi, PrimitiveObjectInspectorFactory.javaHiveDecimalObjectInspector)) {
            return lambdaFactory.createLambda(lambdaString, new TypeReference<Function<HiveDecimal, Boolean>>(){});
        } else if (oi.getCategory().equals(ObjectInspector.Category.LIST)) {
            return lambdaFactory.createLambda(lambdaString, new TypeReference<Function<List<Object>, Boolean>>(){});
        } else if (oi.getCategory().equals(ObjectInspector.Category.MAP)) {
            return lambdaFactory.createLambda(lambdaString, new TypeReference<Function<Map<Object, Object>, Boolean>>(){});
        } else {
            return lambdaFactory.createLambda(lambdaString, new TypeReference<Function<Object, Boolean>>(){});
        }
    }
}
