package org.deeplearning4j.text.tokenization.tokenizerfactory;

import org.deeplearning4j.text.tokenization.tokenizer.TokenPreProcess;
import org.deeplearning4j.text.tokenization.tokenizer.Tokenizer;
import org.deeplearning4j.text.tokenization.tokenizer.FormerJapaneseTokenizer;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;

import java.io.InputStream;

public class FormerJapaneseTokenizerFactory implements TokenizerFactory {
  private org.atilika.kuromoji.Tokenizer tokenizer;
  private TokenPreProcess preProcess;
  private boolean useBaseForm;

  public FormerJapaneseTokenizerFactory() {
    this(
            org.atilika.kuromoji.Tokenizer.builder().mode(org.atilika.kuromoji.Tokenizer.Mode.NORMAL).build(),
            false
    );
  }

  public FormerJapaneseTokenizerFactory(org.atilika.kuromoji.Tokenizer.Mode mode, boolean useBaseForm) {
    this(
            org.atilika.kuromoji.Tokenizer.builder().mode(mode).build(),
            useBaseForm
    );
  }

  // If you want further customization, you can give a raw kuromoji's tokenizer.
  public FormerJapaneseTokenizerFactory(org.atilika.kuromoji.Tokenizer tokenizer, boolean useBaseForm) {
    this.tokenizer = tokenizer;
    this.useBaseForm = useBaseForm;
  }

  @Override
  public Tokenizer create(String toTokenize) {
    if (toTokenize == null || toTokenize.isEmpty()) {
      throw new IllegalArgumentException("Unable to proceed; no sentence to tokenize");
    }
    Tokenizer ret = new FormerJapaneseTokenizer(tokenizer, toTokenize, useBaseForm);
    return ret;
  }

  @Override
  public Tokenizer create(InputStream toTokenize) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setTokenPreProcessor(TokenPreProcess preProcessor) {
    this.preProcess = preProcess;
  }

}

