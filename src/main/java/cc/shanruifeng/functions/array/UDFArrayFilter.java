package cc.shanruifeng.functions.array;

import cc.shanruifeng.functions.utils.LambdaUtils;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ListObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import pl.joegreen.lambdaFromString.LambdaCreationException;
import pl.joegreen.lambdaFromString.LambdaFactory;

import java.util.ArrayList;
import java.util.function.Function;

import static org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorUtils.compareTypes;

/**
 * @author ruifeng.shan
 * @date 2018-07-25 下午1:37
 */
@Description(name = "array_filter"
        , value = "_FUNC_(array<E>, function<E, boolean>) - constructs an array from those elements of array for which function returns true."
        , extended = "Example:\n > select _FUNC_(array, x -> true) from src;")
public class UDFArrayFilter extends GenericUDF {

    private static final int ARRAY_IDX = 0;
    private static final int LAMBDA_FUNCTION_IDX = 1;
    private static final int ARG_COUNT = 2; // Number of arguments to this UDF
    private transient ObjectInspector lambdaStringOI;
    private transient ListObjectInspector arrayOI;
    private transient ObjectInspector arrayElementOI;
    private transient ArrayList<Object> result = new ArrayList<Object>();
    private static final LambdaFactory LAMBDA_FACTORY = LambdaFactory.get();
    private Function applyFunction;

    public UDFArrayFilter() {
    }

    @Override
    public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException {
        // Check if two arguments were passed
        if (arguments.length != ARG_COUNT) {
            throw new UDFArgumentLengthException(
                    "The function array_filter(array<E>, function<E, boolean>) takes exactly " + ARG_COUNT + " arguments.");
        }

        // Check if ARRAY_IDX argument is of category LIST
        if (!arguments[ARRAY_IDX].getCategory().equals(ObjectInspector.Category.LIST)) {
            throw new UDFArgumentTypeException(ARRAY_IDX,
                    "\"" + org.apache.hadoop.hive.serde.serdeConstants.LIST_TYPE_NAME + "\" "
                            + "expected at function array_filter, but "
                            + "\"" + arguments[ARRAY_IDX].getTypeName() + "\" "
                            + "is found");
        }

        arrayOI = (ListObjectInspector) arguments[ARRAY_IDX];
        arrayElementOI = arrayOI.getListElementObjectInspector();

        lambdaStringOI = arguments[LAMBDA_FUNCTION_IDX];

        // Check if list element and value are of same type
        if (!compareTypes(PrimitiveObjectInspectorFactory.javaStringObjectInspector, lambdaStringOI)) {
            throw new UDFArgumentTypeException(LAMBDA_FUNCTION_IDX,
                    "\"" + arrayElementOI.getTypeName() + "\""
                            + " expected at function array_filter, but "
                            + "\"" + lambdaStringOI.getTypeName() + "\""
                            + " is found");
        }

        return ObjectInspectorFactory.getStandardListObjectInspector(arrayElementOI);
    }

    @Override
    public Object evaluate(DeferredObject[] arguments) throws HiveException {
        Object array = arguments[ARRAY_IDX].get();
        String lambdaString = (String) arguments[LAMBDA_FUNCTION_IDX].get();

        if (applyFunction == null) {
            try {
                applyFunction = LambdaUtils.getFilterFunction(LAMBDA_FACTORY, lambdaString, arrayElementOI);
            } catch (LambdaCreationException e) {
                throw new UDFArgumentTypeException(LAMBDA_FUNCTION_IDX, e.getMessage());
            }
        }

        int arrayLength = arrayOI.getListLength(array);
        // Check if array is null or empty or value is null
        if (array == null || arrayLength <= 0) {
            return array;
        }

        for (int i = 0; i < arrayLength; ++i) {
            Object listElement = arrayOI.getListElement(array, i);
            if ((boolean)applyFunction.apply(listElement)) {
                result.add(listElement);
            }
        }

        return result;
    }

    @Override
    public String getDisplayString(String[] strings) {
        assert (strings.length == ARG_COUNT);
        return "array_filter(" + strings[ARRAY_IDX] + ", "
                + strings[LAMBDA_FUNCTION_IDX] + ")";
    }
}