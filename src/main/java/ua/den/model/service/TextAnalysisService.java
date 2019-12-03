package ua.den.model.service;

import org.springframework.util.comparator.Comparators;
import ua.den.model.dto.TextInfoDto;
import ua.den.model.entity.TextDataXml;
import ua.den.model.entity.TextDescriptorXml;

import java.util.*;
import java.util.stream.Collectors;

public class TextAnalysisService {
    private String textToAnalyze;
    private Set<String> keyWords;

    public TextAnalysisService() {
        keyWords = new HashSet<>();

        keyWords.add("\\.");
        keyWords.add(",");
        keyWords.add(";");
        keyWords.add("\\n");
        keyWords.add("\\r");
        keyWords.add("\\t");
    }

    public TextInfoDto analyzeText(String text) {
        setTextToAnalyze(text);

        return analyze();
    }

    private TextInfoDto analyze() {
        TextInfoDto textInfoDto = new TextInfoDto();
        TextDataXml textDataXml = new TextDataXml();
        String[] sentences = textToAnalyze.split("(" + String.join("|", keyWords) + ")+");
        Map<String, Integer> wordsAmount = new LinkedHashMap<>();

        for (String sentence : sentences) {
            Map<String, Integer> wordsBinding = new HashMap<>();

            textDataXml.getTextDescriptors().add(analyzeWordBindings(sentence));
            countWordsAmount(wordsAmount, sentence);
        }

        wordsAmount = wordsAmount.entrySet().stream().
                sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).
                collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

        textInfoDto.setUsages(wordsAmount);
        return textInfoDto;
    }

    private TextDescriptorXml analyzeWordBindings(String sentence) {

        return null;
    }

    private void countWordsAmount(Map<String, Integer> wordsAmountMap, String sentence) {
        String[] words = sentence.split("(\\s|,|\"|\\s'|'\\s|:|\\s(-)+\\s|(-)+\\s|-{2,}|_|!|\\?|\\n|\\t|\\r)+");

        for (String word : words) {
            if (word != null && !word.isEmpty()) {
                wordsAmountMap.merge(word.toLowerCase(), 1, Integer::sum);
            }
        }
    }

    public String getTextToAnalyze() {
        return textToAnalyze;
    }

    public void setTextToAnalyze(String textToAnalyze) {
        this.textToAnalyze = textToAnalyze;
    }
}
