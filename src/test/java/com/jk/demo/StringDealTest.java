package com.jk.demo;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import org.junit.Test;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by zengping on 2017/4/7.
 */
public class StringDealTest {

    @Test
    public void findSubRange() {
        findSub((res, sub) -> {
            if (null == sub || "".equals(sub))
                return null;
            System.out.println("res str : " + res + "||| target : " + sub);
            List<Range<Integer>> reRange = Lists.newArrayList();
            for (int i = 0; i < res.length(); i++) {
                int commonIndex = 0;
                for (int j = 0; j < sub.length() && i + j < res.length(); j++) {
                    if (res.charAt(i + j) != sub.charAt(j))
                        break;
                    commonIndex++;
                }
                if (commonIndex == sub.length()) {
                    reRange.add(Range.closed(++i, i + --commonIndex));
                    i = --i + commonIndex;
                }
            }
            return reRange;
        }, "sdglfglfglglfflggglgglgglgccwoowglgmm", "glg");
    }

    public void findSub(BiFunction<String, String, List<Range<Integer>>> findSubFun, String res, String sub) {
        System.out.println(MoreObjects.toStringHelper(List.class).addValue(findSubFun.apply(res, sub)).toString());
    }

    @Test
    public void replaceTest() {
        replaceStr((params) -> {
            System.out.println("res str : " + params.getRes() + "||| replace : " + params.getReplace() + " replacement : " + params.getReplacement());
            String res = params.getRes(),replace = params.getReplace();
            char[] chars = res.toCharArray();
            int start = 0;
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < res.length(); i++) {
                int commonIndex = 0;
                for (int j = 0; j < replace.length() && i + j < res.length(); j++) {
                    if (res.charAt(i + j) != replace.charAt(j))
                        break;
                    commonIndex++;
                }
                if (commonIndex == replace.length()) {
                    if (i - start > 1)
                        builder.append(chars, start, i - start);
                    builder.append(params.getReplacement());
                    i = --i + commonIndex;
                    start = i + 1;
                }
            }
            builder.append(chars, start, chars.length - start);
            return builder.toString();
        }, new StringPramas().setRes("sdglfglfglglfflggglgglgglgccwoowglgmm")
                .setReplace("glg").setReplacement("Kvvvv"));
    }

    //替换字符串
    public void replaceStr(Function<StringPramas, String> replaceFun, StringPramas parmas) {
        System.out.println(replaceFun.apply(parmas));
    }

    class StringPramas {
        private String res;
        private String replace;
        private String replacement;

        public String getRes() {
            return res;
        }

        public StringPramas setRes(String res) {
            this.res = res;
            return this;
        }

        public String getReplace() {
            return replace;
        }

        public StringPramas setReplace(String replace) {
            this.replace = replace;
            return this;
        }

        public String getReplacement() {
            return replacement;
        }

        public StringPramas setReplacement(String replacement) {
            this.replacement = replacement;
            return this;
        }
    }


}
