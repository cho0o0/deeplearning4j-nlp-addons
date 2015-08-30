package org.deeplearning4j.text.tokenization.tokenizer;

import static org.junit.Assert.assertEquals;

import org.deeplearning4j.text.tokenization.tokenizer.KuromojiTokenizer;
import org.deeplearning4j.text.tokenization.tokenizerfactory.KuromojiTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizer.Tokenizer;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.junit.Test;

public class KuromojiTokenizerTest {

    @Test
    public void testKuromojiTokenizer() throws Exception {
        String toTokenize = "黒い瞳の綺麗な女の子";
        TokenizerFactory t = new KuromojiTokenizerFactory();
        Tokenizer tokenizer = t.create(toTokenize);
        String[] expect = { "黒い", "瞳", "の", "綺麗", "な", "女の子" };

        assertEquals(expect.length, tokenizer.countTokens());
        for (int i = 0; i < tokenizer.countTokens(); ++i) {
            assertEquals(tokenizer.nextToken(), expect[i]);
        }
    }
}
