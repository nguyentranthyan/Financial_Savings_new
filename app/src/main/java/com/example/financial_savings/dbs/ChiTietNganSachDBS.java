package com.example.financial_savings.dbs;

public class ChiTietNganSachDBS {

    private static final String TABLE_NAME = "ChiTietNganSach";

    private static final String TRANS = "maGiaoDich";
    private static final String BUDGET = "maNganSach";

    private static final String TABLE_FK_1 = "SoGiaoDich";
    private static final String TABLE_FK_2 = "NganSach";

    private static final String CASCADE = "ON DELETE CASCADE ON UPDATE CASCADE";

    public static String createTable(){
        return "CREATE TABLE " + TABLE_NAME + "(" +
                TRANS + " TEXT CONSTRAINT " + TRANS + " REFERENCES " + TABLE_FK_1 + "(" + TRANS + ") " + CASCADE + ", " +
                BUDGET + " TEXT CONSTRAINT " + BUDGET + " REFERENCES " + TABLE_FK_2 + "(" + BUDGET + ") " + CASCADE +
                ")";
    }

    public static String deleteTable(){
        return "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
