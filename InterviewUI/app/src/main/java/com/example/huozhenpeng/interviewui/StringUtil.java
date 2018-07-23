package com.example.huozhenpeng.interviewui;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.TextUtils;


public class StringUtil {
    private static String idNumRegular = "(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])";
    /**
     * 校验身份证号
     *
     * @return
     */
    public static boolean isCardID(String idNum) {
        // 定义判别用户身份证号的正则表达式（要么是15位，要么是18位，最后一位可以为字母）
        Pattern idNumPattern = Pattern.compile(idNumRegular);
        // 通过Pattern获得Matcher
        Matcher idNumMatcher = idNumPattern.matcher(idNum);
        // 判断用户输入是否为身份证号
        if (idNumMatcher.matches()) {
            return true;
        }
        return false;
    }

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input) || "null".equalsIgnoreCase(input)) {
            return true;
        }
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
        String telRegex = "(((13[0-9]{1})|(15[012356789]{1})|145|147|(17[0678]{1})|(18[012356789]{1}))+\\d{8})";
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        } else {
            return mobiles.matches(telRegex);
        }
    }

    /**
     * 验证邮箱格式
     */
    public static boolean isEmail(String strEmail) {
        if (StringUtil.isEmpty(strEmail)) {
            return false;
        }
//        String strPattern = "^([a-zA-Z0-9]*[-_][a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_][a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})$";
        String strPattern = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }


    /**
     * 按长度格式化中文字符
     *
     * @param input
     * @param num
     * @return
     */
    public static String getChineseStringByWeiShu(String input, int num) {
        if (input == null || input.length() == 0) {
            return MyApplication.instance.getResources().getString(R.string.default_value);
        }
        char[] ch = input.toCharArray();
        StringBuffer output = new StringBuffer();
        double valueLength = 0;
        int endCount = 0;
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                // 中文字符长度为1
                valueLength += 1;
            } else {
                valueLength += 0.5;
            }
            output.append(c);
            if (valueLength >= num) {
                endCount = i;
                break;
            }
        }
        if (valueLength < num) {
            return output.toString();
        } else if (endCount == ch.length - 1) {
            return output.toString();
        } else {
            return output.toString() + "...";
        }
    }

    /**
     * 按长度格式化中文字符
     *
     * @param input
     * @param num
     * @return
     */
    public static String getChineseStringByWeiShu2(String input, int num) {
        if (input == null || input.length() == 0) {
            return MyApplication.instance.getResources().getString(R.string.default_value);
        }
        char[] ch = input.toCharArray();
        StringBuffer output = new StringBuffer();
        double valueLength = 0;
        int endCount = 0;
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                // 中文字符长度为1
                valueLength += 1;
            } else {
                valueLength += 0.5;
            }
            output.append(c);
            if (valueLength >= num) {
                endCount = i;
                break;
            }
        }
        if (valueLength < num) {
            return output.toString();
        } else if (endCount == ch.length - 1) {
            return output.toString();
        } else {
            return output.toString();
        }
    }

    /**
     * 计算字符长度
     *
     * @param input
     * @return
     */
    public static int getChineseLength(String input) {
        if (input == null || input.length() == 0) {
            return 0;
        }
        char[] ch = input.toCharArray();
        // StringBuffer output = new StringBuffer();
        float valueLength = 0;
        // int endCount = 0;
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                // 中文字符长度为1
                valueLength += 1;
            } else {
                valueLength += 0.5;
            }
        }
        return (int) valueLength;
    }

    /**
     * 根据Unicode编码完美的判断中文汉字和符号
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

    /**
     * 验证密码
     */
    public static boolean isPwd(String pwd) {
        if (isEmpty(pwd)) {
            return false;
        }
        boolean flag = true;
        for (int i = 0; i < pwd.length(); i++) {
            // 内容只能是数字字符
            if (!Character.isDigit(pwd.charAt(i)) && !Character.isLetter(pwd.charAt(i))) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    /**
     * Converts an InputStream to String
     *
     * @param in
     * @return
     * @throws IOException
     */
    public static String Inputstr2Str_Reader(InputStream in) {
        StringBuffer sb = new StringBuffer();
        InputStreamReader isr = new InputStreamReader(in);
        char buf[] = new char[20];
        int nBufLen = 0;
        try {
            nBufLen = isr.read(buf);
            while (nBufLen != -1) {
                sb.append(new String(buf, 0, nBufLen));
                nBufLen = isr.read(buf);
            }
            return sb.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Integer转字符串
     *
     * @param value
     * @return
     */
    public static Integer stringTransInt(String value) {
        if (StringUtil.isEmpty(value)) {
            return null;
        }
        return Integer.parseInt(value);
    }

    /**
     * 字符串为空的时候，返回"--"
     *
     * @param content
     * @return
     */
    public static String showStringContent(String content) {
        if (isEmpty(content)) {
            return MyApplication.instance.getResources().getString(R.string.default_value);
        }
        return content;
    }

    public static String getModileNumber(String number) {
        if (StringUtil.isEmpty(number)) {
            return "";
        }
        String newNumber = "";
        if (number.length() > 7) {
            newNumber = number.substring(0, 3) + "****" + number.substring(7);
        }
        return newNumber;
    }

    /**
     * 把输入的金额转换为汉语中人民币的大写
     *
     * @param numberOfMoney 输入的金额
     * @return 对应的汉语大写
     */
    public static String number2CNMontrayUnit(BigDecimal numberOfMoney) {
        StringBuffer sb = new StringBuffer();
        // -1, 0, or 1 as the value of this BigDecimal is negative, zero, or
        // positive.
        // 汉语中数字大写
        String[] CN_UPPER_NUMBER = {"零", "壹", "贰", "叁", "肆",
                "伍", "陆", "柒", "捌", "玖"};
//        String[] CN_UPPER_NUMBER = { "零", "一", "二", "三", "四",
//                "五", "六", "七", "八", "九" };
        // 汉语中货币单位大写，这样的设计类似于占位符
        String[] CN_UPPER_MONETRAY_UNIT = {"分", "角", "元",
                "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "兆", "拾",
                "佰", "仟"};
//		String[] CN_UPPER_MONETRAY_UNIT = { "分", "角", "元",
//	            "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千", "兆", "十",
//	            "百", "千" };
        // 特殊字符：整
        String CN_FULL = "整";
        // 特殊字符：负
        String CN_NEGATIVE = "负";
        // 金额的精度，默认值为2
        int MONEY_PRECISION = 2;
        // 特殊字符：零元整
        String CN_ZEOR_FULL = "零元" + CN_FULL;
        int signum = numberOfMoney.signum();
        // 零元整的情况
        if (signum == 0) {
            return CN_ZEOR_FULL;
        }

        //这里会进行金额的四舍五入
        long number = numberOfMoney.movePointRight(MONEY_PRECISION)
                .setScale(0, 4).abs().longValue();
        // 得到小数点后两位值
        long scale = number % 100;
        int numUnit = 0;
        int numIndex = 0;
        boolean getZero = false;
        // 判断最后两位数，一共有四中情况：00 = 0, 01 = 1, 10, 11
        if (!(scale > 0)) {
            numIndex = 2;
            number = number / 100;
            getZero = true;
        }
        if ((scale > 0) && (!(scale % 10 > 0))) {
            numIndex = 1;
            number = number / 10;
            getZero = true;
        }
        int zeroSize = 0;
        while (true) {
            if (number <= 0) {
                break;
            }
            // 每次获取到最后一个数
            numUnit = (int) (number % 10);
            if (numUnit > 0) {
                if ((numIndex == 9) && (zeroSize >= 3)) {
                    sb.insert(0, CN_UPPER_MONETRAY_UNIT[6]);
                }
                if ((numIndex == 13) && (zeroSize >= 3)) {
                    sb.insert(0, CN_UPPER_MONETRAY_UNIT[10]);
                }
                sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                sb.insert(0, CN_UPPER_NUMBER[numUnit]);
                getZero = false;
                zeroSize = 0;
            } else {
                ++zeroSize;
                if (!(getZero)) {
                    sb.insert(0, CN_UPPER_NUMBER[numUnit]);
                }
                if (numIndex == 2) {
                    if (number > 0) {
                        sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                    }
                } else if (((numIndex - 2) % 4 == 0) && (number % 1000 > 0)) {
                    sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                }
                getZero = true;
            }
            // 让number每次都去掉最后一个数
            number = number / 10;
            ++numIndex;
        }
        // 如果signum == -1，则说明输入的数字为负数，就在最前面追加特殊字符：负
        if (signum == -1) {
            sb.insert(0, CN_NEGATIVE);
        }
        // 输入的数字小数点后两位为"00"的情况，则要在最后追加特殊字符：整
        if (!(scale > 0)) {
            sb.append(CN_FULL);
        }

        if (sb.substring(0, 2).equals("一十")) {
            return sb.replace(0, 2, "十").toString();
        }
        return sb.toString();
    }

    public static boolean isNum(String content) {
        try {
            int num = Integer.valueOf(content);//把字符串强制转换为数字
            return true;//如果是数字，返回True
        } catch (Exception e) {
            return false;//如果抛出异常，返回False
        }
    }

    public static boolean chickPhoneNumber(String number) {
        if (StringUtil.isEmpty(number)) {
            return false;
        }
        String regEx = "[0-9-()（）]{7,18}";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(number);
        return m.find();
    }

    public static boolean chickStringContent(String number, String regEx) {
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(number);
        return m.find();
    }

    /**
     * 从身份证中获取出生你那月
     *
     * @param idcard
     * @return
     */
    public static String getBirthdayFromIdcard(String idcard) {
        if (TextUtils.isEmpty(idcard)) {
            return "";
        }
        String strYear = "";
        String strMonth = "";
        String strDay = "";
        if (idcard.length() == 15) {
            strYear = "19" + idcard.substring(6, 8);
            strMonth = idcard.substring(8, 10);
            strDay = idcard.substring(10, 12);
            return strYear + "-" + strMonth + "-" + strDay;
        }

        if (idcard.length() == 18) {
            strYear = idcard.substring(6, 10);
            strMonth = idcard.substring(10, 12);
            strDay = idcard.substring(12, 14);
            return strYear + "-" + strMonth + "-" + strDay;
        }
        return "";
    }

    /**
     * 从身份证中获取男女
     *
     * @return 0代表女，1代表男
     */
    public static int getSexFormIdcard(String idcard) {
        if (TextUtils.isEmpty(idcard)) {
            return -1;
        }
        if (idcard.length() == 15)// 15位身份证最后一位代表男女，奇数为男，偶数为女
        {
            if (Integer.parseInt(idcard.substring(14, 15)) % 2 == 0) {
                return 0;
            } else {
                return 1;
            }
        }
        if (idcard.length() == 18)// 18位身份证倒数第二位代表男女，奇数为男，偶数为女
        {
            if (Integer.parseInt(idcard.substring(16, 17)) % 2 == 0) {// 应该从0开始的
                return 0;
            } else {
                return 1;
            }
        }
        return -1;
    }

}
