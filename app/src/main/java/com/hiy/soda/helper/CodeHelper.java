package com.hiy.soda.helper;

public class CodeHelper {

    private static class Holder {
        static CodeHelper instance = new CodeHelper();
    }

    public static CodeHelper getInstance() {
        return Holder.instance  ;
    }
}
