package ml;

import com.google.common.base.Splitter;
import yangUtil.YangCollectionUtil;

import java.util.*;

/**
 * S(Vi) = (1-d) + d*Sigma(j->in(vi)) 1/out(Vj) * S(Vj)
 * S(Vi)是网页i的中重要性（PR值）。d是阻尼系数，一般设置为0.85。In(Vi)是存在指向网页i的链接的网页集合。Out(Vj)是网页j中的链接存在的链接指向的网页的集合。|Out(Vj)|是集合中元素的个数。
 * Created by hwyang on 2015/8/24.
 */
public class TextRank {
    public static void main(String[] args) {
        List<String> wordList = segWords("");
        Map<String, Set<String>> toupiao = toupiao(wordList);
        Map<String, Double> scoreMap = new LinkedHashMap<>();
        double d = 0.85;//阻尼系数
        int maxIter = 1000;//最大迭代数
        while (maxIter-- > 0) {
            for (Map.Entry<String, Set<String>> entry : toupiao.entrySet()) {
                String word = entry.getKey();
                Set<String> js = entry.getValue();
                double score = 0;
                for (String j : js) {
                    if (j.equals(word)) {
                        continue;
                    }
                    int size = toupiao.get(j).size();
                    Double weight = scoreMap.get(j) == null ? 1 : scoreMap.get(j);//初始权重都为1
                    score += d * 1 / (size + 1) * weight;
                }
                score = 0.15 + score;
                scoreMap.put(word, score);
            }
        }
        YangCollectionUtil.sortMapByValue(scoreMap, true);
        for (Map.Entry<String, Double> entry : scoreMap.entrySet()) {
            System.out.println(entry.getKey() + "\t" + entry.getValue());
        }
        System.out.println();
    }

    private static Map<String, Set<String>> toupiao(List<String> wordList) {
        Map<String, Set<String>> map = new HashMap<>();
        for (int i = 0, max = wordList.size(); i < wordList.size(); i++) {
            int start = i - 5 >= 0 ? i - 5 : 0;
            int end = i + 5 >= max ? max : i + 5;
            String key = wordList.get(i);
            Set<String> value = map.get(key);
            HashSet<String> set = new HashSet<>(wordList.subList(start, end));
            if (value == null) {
                map.put(key, set);
            } else {
                value.addAll(set);
            }
        }
        return map;
    }

    public static List<String> segWords(String line) {
        return Splitter.on(", ").omitEmptyStrings().splitToList("程序员, 英文, 程序, 开发, 维护, 专业, 人员, 程序员, 分为, 程序, 设计, 人员, 程序, 编码, 人员, 界限, 特别, 中国, 软件, 人员, 分为, 程序员, 高级, 程序员, 系统, 分析员, 项目, 经理");
    }
}
