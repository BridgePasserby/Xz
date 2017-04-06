package com.zice.xz.database;

import java.util.HashMap;

/**
 * Author Kevin
 * Date 2016/11/4 20:57
 * Email Bridge_passerby@outlook.com
 */
public class DefaultTableContent {
    private HashMap<String, String> consumeCategoryMap;
    private HashMap<String, String> consumeTypeMap;
    /* 消费分类表 字段 */
    public static final String CONSUME_CATEGORY_FOOD = "01";// 食物
    public static final String CONSUME_CATEGORY_HOUSE = "02";// 住房
    public static final String CONSUME_CATEGORY_TRAVEL = "03";// 出行
    public static final String CONSUME_CATEGORY_MEDICINE = "04";// 医药
    public static final String CONSUME_CATEGORY_ENTERTAINMENT = "05";// 娱乐
    public static final String CONSUME_CATEGORY_ELECTRONIC_PRODUCT = "06";// 电子产品
    public static final String CONSUME_CATEGORY_SHOPPING = "07";// 购物
    public static final String CONSUME_CATEGORY_STUDY = "08";// 学习
    public static final String CONSUME_CATEGORY_OTHER = "99";// 其他

    /* 消费类型表 字段 */
    public static final String CONSUME_TYPE_BREAKFAST = CONSUME_CATEGORY_FOOD + "01";
    public static final String CONSUME_TYPE_LUNCH = CONSUME_CATEGORY_FOOD + "02";
    public static final String CONSUME_TYPE_AFTERNOON_TEA = CONSUME_CATEGORY_FOOD + "03";
    public static final String CONSUME_TYPE_DINNER = CONSUME_CATEGORY_FOOD + "04";
    public static final String CONSUME_TYPE_SUPPER = CONSUME_CATEGORY_FOOD + "05";
    public static final String CONSUME_TYPE_SNACKS = CONSUME_CATEGORY_FOOD + "06";
//    public static final String CONSUME_TYPE_OTHER_1 = CONSUME_CATEGORY_OTHER + "07";

    public static final String CONSUME_TYPE_WATER = CONSUME_CATEGORY_HOUSE + "01";
    public static final String CONSUME_TYPE_ELECTRIC = CONSUME_CATEGORY_HOUSE + "02";
    public static final String CONSUME_TYPE_GAS = CONSUME_CATEGORY_HOUSE + "03";
    public static final String CONSUME_TYPE_NETWORK = CONSUME_CATEGORY_HOUSE + "04";
    public static final String CONSUME_TYPE_HOUSE_FEE = CONSUME_CATEGORY_HOUSE + "05";
    public static final String CONSUME_TYPE_TENEMENT = CONSUME_CATEGORY_HOUSE + "06";
//    public static final String CONSUME_TYPE_OTHER_2 = CONSUME_CATEGORY_OTHER + "001";

    public static final String CONSUME_TYPE_BUS = CONSUME_CATEGORY_TRAVEL + "01";
    public static final String CONSUME_TYPE_COACH = CONSUME_CATEGORY_TRAVEL + "02";
    public static final String CONSUME_TYPE_NET_CAR = CONSUME_CATEGORY_TRAVEL + "03";
    public static final String CONSUME_TYPE_TRAIN = CONSUME_CATEGORY_TRAVEL + "04";
    public static final String CONSUME_TYPE_AIRPLANE = CONSUME_CATEGORY_TRAVEL + "05";
    public static final String CONSUME_TYPE_SHIP = CONSUME_CATEGORY_TRAVEL + "06";
    public static final String CONSUME_TYPE_OIL = CONSUME_CATEGORY_TRAVEL + "07";
//    public static final String CONSUME_TYPE_OTHER_3 = CONSUME_CATEGORY_OTHER + "002";

    public static final String CONSUME_TYPE_OPERATION_FEE = CONSUME_CATEGORY_MEDICINE + "01";
    public static final String CONSUME_TYPE_MEDICAL_FEE = CONSUME_CATEGORY_MEDICINE + "02";
    public static final String CONSUME_TYPE_INQUIRY_FEE = CONSUME_CATEGORY_MEDICINE + "03";
//    public static final String CONSUME_TYPE_OTHER_4 = CONSUME_CATEGORY_OTHER + "003";

    public static final String CONSUME_TYPE_KTV = CONSUME_CATEGORY_ENTERTAINMENT + "01";
    public static final String CONSUME_TYPE_INTERNET_BAR = CONSUME_CATEGORY_ENTERTAINMENT + "02";
    public static final String CONSUME_TYPE_WINE_BAR = CONSUME_CATEGORY_ENTERTAINMENT + "03";
    public static final String CONSUME_TYPE_NIGHTCLUB = CONSUME_CATEGORY_ENTERTAINMENT + "04";
    public static final String CONSUME_TYPE_MOVIE = CONSUME_CATEGORY_ENTERTAINMENT + "05";
    public static final String CONSUME_TYPE_SAUNA = CONSUME_CATEGORY_ENTERTAINMENT + "06";
    public static final String CONSUME_TYPE_FITNESS = CONSUME_CATEGORY_ENTERTAINMENT + "07";
    public static final String CONSUME_TYPE_CONCERT = CONSUME_CATEGORY_ENTERTAINMENT + "08";
    public static final String CONSUME_TYPE_GAME = CONSUME_CATEGORY_ENTERTAINMENT + "09";
//    public static final String CONSUME_TYPE_OTHER_5 = CONSUME_CATEGORY_OTHER + "004";

    public static final String CONSUME_TYPE_COMPUTER = CONSUME_CATEGORY_ELECTRONIC_PRODUCT + "01";
    public static final String CONSUME_TYPE_PERIPHERAL = CONSUME_CATEGORY_ELECTRONIC_PRODUCT + "02";
    public static final String CONSUME_TYPE_ROUTER = CONSUME_CATEGORY_ELECTRONIC_PRODUCT + "03";
    public static final String CONSUME_TYPE_PHONE = CONSUME_CATEGORY_ELECTRONIC_PRODUCT + "04";
    public static final String CONSUME_TYPE_ACCESSORIES = CONSUME_CATEGORY_ELECTRONIC_PRODUCT + "05";
//    public static final String CONSUME_TYPE_OTHER_6 = CONSUME_CATEGORY_OTHER + "005";

    public static final String CONSUME_TYPE_CLOTHES = CONSUME_CATEGORY_SHOPPING + "01";
    public static final String CONSUME_TYPE_SHOES = CONSUME_CATEGORY_SHOPPING + "02";
    public static final String CONSUME_TYPE_COSMETIC = CONSUME_CATEGORY_SHOPPING + "03";
    public static final String CONSUME_TYPE_JEWELRY = CONSUME_CATEGORY_SHOPPING + "04";
    public static final String CONSUME_TYPE_NURSE_PRODUCT = CONSUME_CATEGORY_SHOPPING + "05";
    public static final String CONSUME_TYPE_CLEAN_PRODUCT = CONSUME_CATEGORY_SHOPPING + "06";
    public static final String CONSUME_TYPE_COMMODITY = CONSUME_CATEGORY_SHOPPING + "07";
//    public static final String CONSUME_TYPE_OTHER_7 = CONSUME_CATEGORY_OTHER + "006";

    public static final String CONSUME_TYPE_PEN = CONSUME_CATEGORY_STUDY + "01";
    public static final String CONSUME_TYPE_PAPER = CONSUME_CATEGORY_STUDY + "02";
    public static final String CONSUME_TYPE_BOOK = CONSUME_CATEGORY_STUDY + "03";
//    public static final String CONSUME_TYPE_OTHER_8 = CONSUME_CATEGORY_OTHER + "007";
    
    public static final String CONSUME_TYPE_OTHER = CONSUME_CATEGORY_OTHER + "99";


    public DefaultTableContent() {
        init();
    }

    private void init() {
        consumeCategoryMap = new HashMap<>();
        consumeCategoryMap.put(CONSUME_CATEGORY_FOOD, "食物");
        consumeCategoryMap.put(CONSUME_CATEGORY_HOUSE, "住房");
        consumeCategoryMap.put(CONSUME_CATEGORY_TRAVEL, "出行");
        consumeCategoryMap.put(CONSUME_CATEGORY_MEDICINE, "医疗");
        consumeCategoryMap.put(CONSUME_CATEGORY_ENTERTAINMENT, "娱乐");
        consumeCategoryMap.put(CONSUME_CATEGORY_ELECTRONIC_PRODUCT, "电子数码");
        consumeCategoryMap.put(CONSUME_CATEGORY_SHOPPING, "购物");
        consumeCategoryMap.put(CONSUME_CATEGORY_STUDY, "学习");
        consumeCategoryMap.put(CONSUME_CATEGORY_OTHER, "其他");

        consumeTypeMap = new HashMap<>();
        consumeTypeMap.put(CONSUME_TYPE_BREAKFAST, "早饭");
        consumeTypeMap.put(CONSUME_TYPE_LUNCH, "午饭");
        consumeTypeMap.put(CONSUME_TYPE_AFTERNOON_TEA, "下午茶");
        consumeTypeMap.put(CONSUME_TYPE_DINNER, "晚饭");
        consumeTypeMap.put(CONSUME_TYPE_SUPPER, "夜宵");
        consumeTypeMap.put(CONSUME_TYPE_SNACKS, "零食");
//        consumeTypeMap.put(CONSUME_TYPE_OTHER_1, "其他");

        consumeTypeMap.put(CONSUME_TYPE_WATER, "水");
        consumeTypeMap.put(CONSUME_TYPE_ELECTRIC, "电");
        consumeTypeMap.put(CONSUME_TYPE_GAS, "气");
        consumeTypeMap.put(CONSUME_TYPE_NETWORK, "网");
        consumeTypeMap.put(CONSUME_TYPE_HOUSE_FEE, "房租");
        consumeTypeMap.put(CONSUME_TYPE_TENEMENT, "物业");
//        consumeTypeMap.put(CONSUME_TYPE_OTHER_2, "其他");

        consumeTypeMap.put(CONSUME_TYPE_BUS, "公工交通");
        consumeTypeMap.put(CONSUME_TYPE_COACH, "长途客车");
        consumeTypeMap.put(CONSUME_TYPE_NET_CAR, "网约车");
        consumeTypeMap.put(CONSUME_TYPE_TRAIN, "火车");
        consumeTypeMap.put(CONSUME_TYPE_AIRPLANE, "飞机");
        consumeTypeMap.put(CONSUME_TYPE_SHIP, "船舶");
        consumeTypeMap.put(CONSUME_TYPE_OIL, "油");
//        consumeTypeMap.put(CONSUME_TYPE_OTHER_3, "其他");

        consumeTypeMap.put(CONSUME_TYPE_OPERATION_FEE, "手术费");
        consumeTypeMap.put(CONSUME_TYPE_MEDICAL_FEE, "医药费");
        consumeTypeMap.put(CONSUME_TYPE_INQUIRY_FEE, "问诊费");
//        consumeTypeMap.put(CONSUME_TYPE_OTHER_4, "其他");

        consumeTypeMap.put(CONSUME_TYPE_KTV, "KTV");
        consumeTypeMap.put(CONSUME_TYPE_INTERNET_BAR, "网吧");
        consumeTypeMap.put(CONSUME_TYPE_WINE_BAR, "酒吧");
        consumeTypeMap.put(CONSUME_TYPE_NIGHTCLUB, "夜总会");
        consumeTypeMap.put(CONSUME_TYPE_MOVIE, "电影院");
        consumeTypeMap.put(CONSUME_TYPE_SAUNA, "桑拿");
        consumeTypeMap.put(CONSUME_TYPE_FITNESS, "健身");
        consumeTypeMap.put(CONSUME_TYPE_CONCERT, "音乐会");
        consumeTypeMap.put(CONSUME_TYPE_GAME, "游戏");
//        consumeTypeMap.put(CONSUME_TYPE_OTHER_5, "其他");

        consumeTypeMap.put(CONSUME_TYPE_COMPUTER, "电脑");
        consumeTypeMap.put(CONSUME_TYPE_PERIPHERAL, "外设");
        consumeTypeMap.put(CONSUME_TYPE_ROUTER, "路由器");
        consumeTypeMap.put(CONSUME_TYPE_PHONE, "手机");
        consumeTypeMap.put(CONSUME_TYPE_ACCESSORIES, "配件");
//        consumeTypeMap.put(CONSUME_TYPE_OTHER_6, "其他");

        consumeTypeMap.put(CONSUME_TYPE_CLOTHES, "衣服");
        consumeTypeMap.put(CONSUME_TYPE_SHOES, "鞋子");
        consumeTypeMap.put(CONSUME_TYPE_COSMETIC, "化妆品");
        consumeTypeMap.put(CONSUME_TYPE_JEWELRY, "首饰");
        consumeTypeMap.put(CONSUME_TYPE_NURSE_PRODUCT, "洗护");
        consumeTypeMap.put(CONSUME_TYPE_CLEAN_PRODUCT, "清洁");
        consumeTypeMap.put(CONSUME_TYPE_COMMODITY, "家居日用");
//        consumeTypeMap.put(CONSUME_TYPE_OTHER_7, "其他");

        consumeTypeMap.put(CONSUME_TYPE_PEN, "笔");
        consumeTypeMap.put(CONSUME_TYPE_PAPER, "本");
        consumeTypeMap.put(CONSUME_TYPE_BOOK, "书");
        
        consumeTypeMap.put(CONSUME_TYPE_OTHER, "其他");
    }

    public HashMap<String, String> getConsumeCategoryMap() {
        return consumeCategoryMap;
    }

    public HashMap<String, String> getConsumeTypeMap() {
        return consumeTypeMap;
    }
}
