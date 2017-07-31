package com.zice.xz.mvp.mode;

/**
 * Created by Zkai on 2017/7/29.
 */

public class DataMode {

    public static class ConsumeData {
        private int money;
        private String name;

        public ConsumeData(int money, String name) {
            this.money = money;
            this.name = name;
        }

        public int getMoney() {
            return money;
        }

        public String getName() {
            return name;
        }
    }
}
