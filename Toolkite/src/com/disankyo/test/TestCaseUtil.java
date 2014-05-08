package com.disankyo.test;
import com.disankyo.page.Page;

import org.easymock.IArgumentMatcher;
import static org.easymock.EasyMock.*;
/**
 *
 * @version 1.00 2010-12-28 14:29:04
 * @since 1.6
 * @author Allean
 */
public class TestCaseUtil {

    /**
     * 分页对象的比较
     * @param page 分页对象
     * @return 实际分页对象
     */
    public static Page equalPage(Page page) {
        reportMatcher(new PageEquals(page));
        return page;
    }

    /**
     * 分页对象的比较器
     */
    private static class PageEquals implements IArgumentMatcher {
        private Page page;

        public PageEquals(Page page) {
            this.page = page;
        }

        public boolean matches(Object o) {
            if (o == null && page == null) {
                return true;
            }
            if (o instanceof Page) {
                Page value = (Page) o;
                if (page.hasNextPage() == value.hasNextPage()
                        && page.isSinglePage() == value.isSinglePage()
                        && page.getIndex() == value.getIndex()) {
                    return true;
                }
            }
            return false;
        }

        public void appendTo(StringBuffer sb) {
        }
    }

    /**
     * boolean的参数比较
     * @param boo boolean参数
     * @return
     */
    public static Boolean isBoolean(Boolean boo){
        reportMatcher(new BooleanEqual(boo));
        return boo;
    }

    /**
     * Boolean类型的参数比较器
     */
    private static class BooleanEqual implements IArgumentMatcher{
        private Boolean expectBoolean = null;
        public BooleanEqual(Boolean expectBoolean){
            this.expectBoolean = expectBoolean;
        }
        public boolean matches(Object actualBoolean) {
            if(expectBoolean == null){
                if(actualBoolean == null){
                    return true;
                }
                return false;
            } else if(actualBoolean instanceof Boolean){
                return expectBoolean.equals(actualBoolean);
            } else{
                return false;
            }
        }

        public void appendTo(StringBuffer sb) {
        }
    }

    /**
     * String类型的参数比较
     * @param actualString
     * @return
     */
    public static String equalString(String actualString){
        reportMatcher(new StringEquals(actualString));
        return actualString;
    }

    /**
     * String类型的参数比较器
     */
    private static class StringEquals implements IArgumentMatcher{
        private String expectString = null;
        public StringEquals(String expectString){
            this.expectString = expectString;
        }

        public boolean matches(Object actualString) {
            if((expectString == null || expectString.isEmpty()) && actualString== null){
                return true;
            } else if(actualString instanceof String){
                return expectString.equalsIgnoreCase((String)actualString);
            } else{
                return false;
            }
        }

        public void appendTo(StringBuffer sb) {
        }
    }

    public static Long equalLong(Long expectLong){
        reportMatcher(new LongEquals(expectLong));
        return expectLong;
    }

    private static class LongEquals implements IArgumentMatcher{
        private Long expectLong = null;
        public LongEquals(Long expectLong) {
            this.expectLong = expectLong;
        }

        public boolean matches(Object o) {
            if(expectLong == null && o == null){
                return true;
            } else if(o instanceof Long){
                return expectLong.equals(o);
            } else {
                return false;
            }
        }

        public void appendTo(StringBuffer sb) {
        }

    }
}
