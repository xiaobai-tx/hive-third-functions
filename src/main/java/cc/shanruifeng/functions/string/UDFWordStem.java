package cc.shanruifeng.functions.string;

import com.google.common.collect.ImmutableMap;
import io.airlift.slice.Slice;
import io.airlift.slice.Slices;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.io.Text;
import org.tartarus.snowball.SnowballProgram;
import org.tartarus.snowball.ext.*;

import java.util.Map;
import java.util.function.Supplier;

import static io.airlift.slice.Slices.utf8Slice;

/**
 * @author ruifeng.shan
 * @date 2018-07-27 下午12:22
 */
@Description(name = "word_stem"
        , value = "_FUNC_(word) - returns the stem of a word in the English language\n" +
        "_FUNC_(word, language) - returns the stem of a word in the given language."
        , extended = "Example:\n > select _FUNC_(string, language_str) from src;")
public class UDFWordStem extends UDF {
    private Text result = new Text();

    private static final Map<Slice, Supplier<SnowballProgram>> STEMMERS = ImmutableMap.<Slice, Supplier<SnowballProgram>>builder()
            .put(utf8Slice("ca"), () -> new CatalanStemmer())
            .put(utf8Slice("da"), DanishStemmer::new)
            .put(utf8Slice("de"), German2Stemmer::new)
            .put(utf8Slice("en"), EnglishStemmer::new)
            .put(utf8Slice("es"), SpanishStemmer::new)
            .put(utf8Slice("eu"), BasqueStemmer::new)
            .put(utf8Slice("fi"), FinnishStemmer::new)
            .put(utf8Slice("fr"), FrenchStemmer::new)
            .put(utf8Slice("hu"), HungarianStemmer::new)
            .put(utf8Slice("hy"), ArmenianStemmer::new)
            .put(utf8Slice("ir"), IrishStemmer::new)
            .put(utf8Slice("it"), ItalianStemmer::new)
            .put(utf8Slice("lt"), LithuanianStemmer::new)
            .put(utf8Slice("nl"), DutchStemmer::new)
            .put(utf8Slice("no"), NorwegianStemmer::new)
            .put(utf8Slice("pt"), PortugueseStemmer::new)
            .put(utf8Slice("ro"), RomanianStemmer::new)
            .put(utf8Slice("ru"), RussianStemmer::new)
            .put(utf8Slice("sv"), SwedishStemmer::new)
            .put(utf8Slice("tr"), TurkishStemmer::new)
            .build();


    public UDFWordStem() {
    }

    public Text evaluate(Text text) {
        if (text == null) {
            return null;
        }

        String string = wordStem(Slices.utf8Slice(text.toString()), new EnglishStemmer()).toString();
        result.set(string);

        return result;
    }

    public Text evaluate(Text text, Text language) throws HiveException {
        if (text == null) {
            return null;
        }

        Supplier<SnowballProgram> stemmer = STEMMERS.get(language.toString());
        if (stemmer == null) {
            throw new HiveException("Unknown stemmer language: " + language.toString());
        }

        String string = wordStem(Slices.utf8Slice(text.toString()), stemmer.get()).toString();
        result.set(string);

        return result;
    }

    private static Slice wordStem(Slice slice, SnowballProgram stemmer)
    {
        stemmer.setCurrent(slice.toStringUtf8());
        return stemmer.stem() ? utf8Slice(stemmer.getCurrent()) : slice;
    }
}