package com.example.huozhenpeng.interviewui;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;


public class FloatUtil {

    private FloatUtil() {
        throw new Error("工具类FloatUtil不可实例化");
    }

    /**
     * 格式化数据返回百分比 小数点后两位表示
     */
    public static String toPercentage(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return MyApplication.getInstance().getResources().getString(R.string.default_value);
        }
        DecimalFormat format = new DecimalFormat("0.00%");
        format.setRoundingMode(RoundingMode.HALF_UP);
        return format.format(bigDecimal);
    }

    /**
     * 格式化数据返回百分比 小数点后两位表示
     */
    public static String toPercentage(Double bigDecimal) {
        if (bigDecimal == null) {
            return MyApplication.getInstance().getResources().getString(R.string.default_value);
        }
        DecimalFormat format = new DecimalFormat("0.00%");
        format.setRoundingMode(RoundingMode.HALF_UP);
        return format.format(bigDecimal);
    }

    /**
     * 格式化数据返回百分比 小数点后两位表示
     */
    public static String toPercentage(Float bigDecimal) {
        if (bigDecimal == null) {
            return MyApplication.getInstance().getResources().getString(R.string.default_value);
        }
        DecimalFormat format = new DecimalFormat("#.##");
        format.setRoundingMode(RoundingMode.HALF_UP);
        return format.format(bigDecimal);
    }

    /**
     * 格式化数据返回 小数点后四位表示
     */
    public static String toFourDianString(Double doubleValue) {
        if (doubleValue == null) {
            return MyApplication.getInstance().getResources().getString(R.string.default_value);
        }
        DecimalFormat format = new DecimalFormat("#0.0000");
        format.setRoundingMode(RoundingMode.HALF_UP);
        return format.format(doubleValue);
    }

    /**
     * 格式化数据返回 小数点后四位表示
     */
    public static String toFourDianString(Float doubleValue) {
        if (doubleValue == null) {
            return MyApplication.getInstance().getResources().getString(R.string.default_value);
        }
        DecimalFormat format = new DecimalFormat("#0.0000");
        format.setRoundingMode(RoundingMode.HALF_UP);
        return format.format(doubleValue);
    }

    /**
     * 格式化数据返回 小数点后两位表示
     */
    public static String toTwoDianString(Float doubleValue) {
        if (doubleValue == null) {
            return MyApplication.getInstance().getResources().getString(R.string.default_value);
        }
        DecimalFormat format = new DecimalFormat("#0.00");
        format.setRoundingMode(RoundingMode.HALF_UP);
        return format.format(doubleValue);
    }

    /**
     * 格式化数据返回 小数点后两位表示
     */
    public static String toTwoDianString(Double doubleValue) {
        if (doubleValue == null) {
            return MyApplication.getInstance().getResources().getString(R.string.default_value);
        }
        DecimalFormat format = new DecimalFormat("#0.00");
        format.setRoundingMode(RoundingMode.HALF_UP);
        return format.format(doubleValue);
    }

    /**
     * 格式化数据返回 小数点后两位表示
     */
    public static String toTwoDianStringSeparator(Double doubleValue) {
        if (doubleValue == null) {
            return MyApplication.getInstance().getResources().getString(R.string.default_value);
        }
        DecimalFormat format = new DecimalFormat("#,##0.00");
        format.setRoundingMode(RoundingMode.HALF_UP);
        return format.format(doubleValue);
    }

    /**
     * 格式化数据返回 小数点后两位表示
     */
    public static String toZeroDianStringSeparator(Double doubleValue) {
        if (doubleValue == null) {
            return MyApplication.getInstance().getResources().getString(R.string.default_value);
        }
        DecimalFormat format = new DecimalFormat("#,##0.##");
        format.setRoundingMode(RoundingMode.HALF_UP);
        return format.format(doubleValue);
    }

    /**
     * 格式化数据返回 小数点后两位表示
     */
    public static String toTwoDianStringSeparator(BigDecimal doubleValue) {
        if (doubleValue == null) {
            return MyApplication.getInstance().getResources().getString(R.string.default_amount);
        }
        DecimalFormat format = new DecimalFormat("#,##0.00");
        format.setRoundingMode(RoundingMode.HALF_UP);
        return format.format(doubleValue);
    }

    /**
     * 格式化数据返回 小数点后三位表示
     */
    public static String toThreeDianStringSeparator(BigDecimal doubleValue) {
        if (doubleValue == null) {
            return MyApplication.getInstance().getResources().getString(R.string.default_amount);
        }
        DecimalFormat format = new DecimalFormat("#,##0.###");
        format.setRoundingMode(RoundingMode.HALF_UP);
        return format.format(doubleValue);
    }

    /**
     * 格式化数据返回 小数点后两位表示
     */
    public static String toTwoDianStringSeparatorNoPoint(BigDecimal doubleValue) {
        if (doubleValue == null) {
            return MyApplication.getInstance().getResources().getString(R.string.default_amount);
        }
        DecimalFormat format = new DecimalFormat("#,##0");
        format.setRoundingMode(RoundingMode.HALF_UP);
        return format.format(doubleValue);
    }

    /**
     * 格式化数据返回 小数点后两位表示
     */
    public static String toTwoDianStringSeparator(Float doubleValue) {
        if (doubleValue == null) {
            return MyApplication.getInstance().getResources().getString(R.string.default_amount);
        }
        DecimalFormat format = new DecimalFormat("#,##0.00");
        format.setRoundingMode(RoundingMode.HALF_UP);
        return format.format(doubleValue);
    }

    /**
     * 格式化数据返回 小数点后两位表示
     */
    public static String toStringSeparator(Integer doubleValue) {
        if (doubleValue == null) {
            return MyApplication.getInstance().getResources().getString(R.string.default_value);
        }
        DecimalFormat format = new DecimalFormat("#,##0");
        format.setRoundingMode(RoundingMode.HALF_UP);
        return format.format(doubleValue);
    }

    public static String toStringSeparator(Long doubleValue) {
        if (doubleValue == null) {
            return MyApplication.getInstance().getResources().getString(R.string.default_value);
        }
        DecimalFormat format = new DecimalFormat("#,##0");
        format.setRoundingMode(RoundingMode.HALF_UP);
        return format.format(doubleValue);
    }

    public static String toStringSeparator2(Long doubleValue) {
        if (doubleValue == null) {
            return MyApplication.getInstance().getResources().getString(R.string.default_amount);
        }
        DecimalFormat format = new DecimalFormat("#,##0.00");
        format.setRoundingMode(RoundingMode.HALF_UP);
        return format.format(doubleValue);
    }

    /**
     * 格式化数据返回 小数点后两位表示
     */
    public static String toThreeDianString(Double doubleValue) {
        if (doubleValue == null) {
            return MyApplication.getInstance().getResources().getString(R.string.default_amount);
        }
        DecimalFormat format = new DecimalFormat("#0.000");
        format.setRoundingMode(RoundingMode.HALF_UP);
        return format.format(doubleValue);
    }

    /**
     * 格式化数据返回四舍五入两位表示 BigDecimal
     */
    public static String toSDianString(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return MyApplication.getInstance().getResources().getString(R.string.default_value);
        }
        return bigDecimal.setScale(3, BigDecimal.ROUND_HALF_UP).toString();
    }

    public static String toSDianStringSR(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return MyApplication.getInstance().getResources().getString(R.string.default_value);
        }
        return bigDecimal.setScale(4, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 格式化数据返回四舍五入两位表示 BigDecimal
     */
    public static String toTwoDianString(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return MyApplication.getInstance().getResources().getString(R.string.default_value);
        }
        return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

    public static String toFourDianString(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return MyApplication.getInstance().getResources().getString(R.string.default_value);
        }
        return bigDecimal.setScale(4, BigDecimal.ROUND_HALF_UP).toString();
    }

    public static String yesterDayProfit(Double doubleValue) {
        if (doubleValue == null) {
            return MyApplication.getInstance().getResources().getString(R.string.default_value);
        }
        DecimalFormat format = new DecimalFormat("0");
        format.setRoundingMode(RoundingMode.HALF_UP);
        if (doubleValue < 9999.99) {
            return toTwoDianString(doubleValue);
        } else if (doubleValue <= 999999) {
            return format.format(doubleValue);
        } else if (doubleValue <= 9999999) {
            return toTwoDianString(doubleValue / 10000) + "万";
        } else {
            return format.format(doubleValue / 10000) + "万";
        }
    }

    /**
     * 去掉多余小数位
     */
    public static String toString(Double doubleValue) {
        if (doubleValue == null) {
            return MyApplication.getInstance().getResources().getString(R.string.default_value);
        }
        DecimalFormat format = new DecimalFormat("0.##");
        format.setRoundingMode(RoundingMode.HALF_UP);
        return format.format(doubleValue);
    }

    /**
     * 去掉多余小数位
     */
    public static String toStringThree(Double doubleValue) {
        if (doubleValue == null) {
            return MyApplication.getInstance().getResources().getString(R.string.default_value);
        }
        DecimalFormat format = new DecimalFormat("0.###");
        format.setRoundingMode(RoundingMode.HALF_UP);
        return format.format(doubleValue);
    }

    /**
     * 去掉多余小数位
     */
    public static String toStringSeparator(Double doubleValue) {
        if (doubleValue == null) {
            return MyApplication.getInstance().getResources().getString(R.string.default_value);
        }
        DecimalFormat format = new DecimalFormat("#,##0");
        format.setRoundingMode(RoundingMode.HALF_UP);
        return format.format(doubleValue);
    }


    /**
     * 格式化数据返回 小数点后一位表示
     */
    public static String toTwoDianString1(Double doubleValue) {
        if (doubleValue == null) {
            return MyApplication.getInstance().getResources().getString(R.string.default_value);
        }
        DecimalFormat format = new DecimalFormat("#0.0");
        format.setRoundingMode(RoundingMode.HALF_UP);
        return format.format(doubleValue);
    }

    /**
     * 格式化数据返回 小数点后两位表示
     */
    public static String toOneDianStringSeparator(Double doubleValue) {
        if (doubleValue == null) {
            return MyApplication.getInstance().getResources().getString(R.string.default_value);
        }
        DecimalFormat format = new DecimalFormat("#,##0.0");
        format.setRoundingMode(RoundingMode.HALF_UP);
        return format.format(doubleValue);
    }


}
