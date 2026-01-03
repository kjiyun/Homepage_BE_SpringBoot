package kahlua.KahluaProject.global.utils;

import org.springframework.stereotype.Component;

@Component
public class KoreanUtils {
    private static final char[] CHOSUNG ={
            'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ',
            'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    };

    //문자열에서 초성 추출
    public String extractInitials(String text){
        if(text==null || text.length()==0){
            return "";
        }

        StringBuilder chosung = new StringBuilder();
        for (char ch : text.toCharArray()) {
            if(isKorean(ch)){
                int index = (ch-0xAC00) / (21 * 28);
                if (index >=0 && index < CHOSUNG.length){
                    chosung.append(CHOSUNG[index]);
                }
            } else {
                chosung.append(ch);
            }
        }

        return chosung.toString();
    }

    private boolean isKorean(char ch){
        return ch>= 0xAC00 && ch <=0xD7A3;
    }

    public boolean matchesChosung(String text, String chosungQuery) {
        String textChosung = extractInitials(text);
        return textChosung.toLowerCase().contains(chosungQuery.toLowerCase());
    }

    public boolean isChosungQuery(String query) {
        if (query == null || query.isEmpty()) {
            return false;
        }
        return query.chars().anyMatch(ch -> ch >= 0x3131 && ch <= 0x3163);
    }
}
