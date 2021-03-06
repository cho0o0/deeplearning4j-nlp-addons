package org.deeplearning4j.text.tokenization.tokenizer;

import com.atilika.kuromoji.TokenizerBase;
import com.atilika.kuromoji.ipadic.Token;
import com.atilika.kuromoji.ipadic.Tokenizer;

import java.util.ArrayList;
import java.util.List;

// A thin wrapper for Japanese Morphological Analyzer Kuromoji with ipadic (ver. 0.9.0),
// it tokenizes texts which is written in languages
// that words are not separated by whitespaces.
//
// In thenory, Kuromoji is a language-independent Morphological Analyzer library,
// so if you want to tokenize non-Japanese texts (Chinese, Korean etc.),
// you can do it with MeCab style dictionary for each languages.
//
// If you want to use a different dictionary such as jumandic, unidic, etc., try to change the dependency.
public class JapaneseTokenizer implements org.deeplearning4j.text.tokenization.tokenizer.Tokenizer {

  private List<String> tokens;
  //private List<String> originalTokens;
  private int index;
  private org.deeplearning4j.text.tokenization.tokenizer.TokenPreProcess preProcess;
  public Tokenizer tokenizer;

  public JapaneseTokenizer(String toTokenize) {
    this(toTokenize, TokenizerBase.Mode.NORMAL, false);
  }

  // You can choose Segmentation Mode from options
  // Mode.NORMAL - recommend
  // Mode.SEARCH
  // Mode.EXTENDED
  public JapaneseTokenizer(String toTokenize, TokenizerBase.Mode mode, boolean useBaseForm) {
    this(
    		new com.atilika.kuromoji.ipadic.Tokenizer.Builder().mode(mode).build(),
            toTokenize,
            useBaseForm
    );
  }

  // This is used by JapaneseTokenizerFactory
  public JapaneseTokenizer(Tokenizer tokenizer, String toTokenize, boolean useBaseForm) {
    this.tokens = new ArrayList<>();
    this.tokenizer = tokenizer;

    for (Token token : tokenizer.tokenize(toTokenize)) {
      if (useBaseForm) {
        tokens.add(token.getBaseForm());
      } else {
        tokens.add(token.getSurface());
      }
    }

    index = tokens.size() > 0 ? 0 : -1;
  }
  @Override
  public boolean hasMoreTokens() {
    if (index < 0) {
      return false;
    } else {
      return index < tokens.size();
    }
  }

  @Override
  public int countTokens() {
    return tokens.size();
  }

  @Override
  public String nextToken() {
    if (index < 0) {
      return null;
    }

    String ret = tokens.get(index);
    index++;
    return preProcess != null ? preProcess.preProcess(ret) : ret;
  }

  @Override
  public List<String> getTokens() {
    List<String> tokens = new ArrayList<>();
    while (hasMoreTokens()) {
      tokens.add(nextToken());
    }
    return tokens;
  }

  @Override
  public void setTokenPreProcessor(org.deeplearning4j.text.tokenization.tokenizer.TokenPreProcess tokenPreProcessor) {
    this.preProcess = tokenPreProcessor;
  }

  public void resetIterator() {
    index = countTokens() > 0 ? 0 : -1;
  }
}

