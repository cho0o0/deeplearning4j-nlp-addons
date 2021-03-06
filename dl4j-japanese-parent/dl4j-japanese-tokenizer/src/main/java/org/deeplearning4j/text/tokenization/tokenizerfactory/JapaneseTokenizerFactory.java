package org.deeplearning4j.text.tokenization.tokenizerfactory;

import org.deeplearning4j.text.tokenization.tokenizer.TokenPreProcess;
import org.deeplearning4j.text.tokenization.tokenizer.Tokenizer;
import org.deeplearning4j.text.tokenization.tokenizer.JapaneseTokenizer;

import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;

import java.io.InputStream;

public class JapaneseTokenizerFactory implements TokenizerFactory {
  private com.atilika.kuromoji.ipadic.Tokenizer tokenizer;
  private boolean useBaseForm;
  private TokenPreProcess tokenPreProcess;

  public JapaneseTokenizerFactory() {
    this(
      com.atilika.kuromoji.TokenizerBase.Mode.NORMAL,
      false
    );
  }

  public JapaneseTokenizerFactory(com.atilika.kuromoji.TokenizerBase.Mode mode, boolean useBaseForm) {
	  this.useBaseForm = useBaseForm;
	  this.tokenizer = new com.atilika.kuromoji.ipadic.Tokenizer.Builder().mode(mode).build();
  }

  // If you want further customization, you can give a raw kuromoji's tokenizer.
  public JapaneseTokenizerFactory(com.atilika.kuromoji.ipadic.Tokenizer tokenizer, boolean useBaseForm) {
    this.tokenizer = tokenizer;
    this.useBaseForm = useBaseForm;
  }

  @Override
  public Tokenizer create(String toTokenize) {
    if (toTokenize == null || toTokenize.isEmpty()) {
      throw new IllegalArgumentException("Unable to proceed; no sentence to tokenize");
    }
    Tokenizer ret = new JapaneseTokenizer(tokenizer, toTokenize, useBaseForm);
    if(tokenPreProcess != null) {
    	ret.setTokenPreProcessor(tokenPreProcess);
    }

    return ret;
  }

  // By using kuromoji, inputstream is not be supported yet
  @Override
  public Tokenizer create(InputStream toTokenize) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setTokenPreProcessor(TokenPreProcess preProcessor) {
	  this.tokenPreProcess = preProcessor;
  }

}

