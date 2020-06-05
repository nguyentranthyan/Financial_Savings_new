package com.example.financial_savings.modules.randoms;

import com.example.financial_savings.helper.DBHelper;

public class RandomIDModule {

    public static long getRandomID() {
        return (long) Math.floor(Math.random() * 9000000000000L) + 1000000000000L;
    }

    // Vi ca nhan
    public static String getWalletID(DBHelper dbHelper) {
        String id;
        do {
            id = String.valueOf(RandomIDModule.getRandomID());
        } while (!isExistWalletID(id, dbHelper));
        return id;
    }

    private static boolean isExistWalletID(String id, DBHelper dbHelper) {
        try {
            dbHelper.getByID_ViCaNhan(id);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    // Danh muc
    public static String getCategoryID(DBHelper dbHelper) {
        String id;
        do {
            id = String.valueOf(RandomIDModule.getRandomID());
        } while (!isExistCategoryID(id, dbHelper));
        return id;
    }

    private static boolean isExistCategoryID(String id, DBHelper dbHelper) {
        try {
            dbHelper.getByID_DanhMuc(id);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    // So giao dich
    public static String getTransID(DBHelper dbHelper) {
        String id;
        do {
            id = String.valueOf(RandomIDModule.getRandomID());
        } while (!isExistTransID(id, dbHelper));
        return id;
    }

    private static boolean isExistTransID(String id, DBHelper dbHelper) {
        try {
            dbHelper.getByID_SoGiaoDich(id);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    // Su kien
    public static String getEventID(DBHelper dbHelper) {
        String id;
        do {
            id = String.valueOf(RandomIDModule.getRandomID());
        } while (!isExistEventID(id, dbHelper));
        return id;
    }

    private static boolean isExistEventID(String id, DBHelper dbHelper) {
        try {
            dbHelper.getByID_SuKien(id);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    // Tiet kiem
    public static String getSavingsID(DBHelper dbHelper) {
        String id;
        do {
            id = String.valueOf(RandomIDModule.getRandomID());
        } while (!isExistSavingsID(id, dbHelper));
        return id;
    }

    private static boolean isExistSavingsID(String id, DBHelper dbHelper) {
        try {
            dbHelper.getByID_TietKiem(id);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    // Ngan sach
    public static String getBudgetsID(DBHelper dbHelper) {
        String id;
        do {
            id = String.valueOf(RandomIDModule.getRandomID());
        } while (!isExistBudgetsID(id, dbHelper));
        return id;
    }

    private static boolean isExistBudgetsID(String id, DBHelper dbHelper) {
        try {
            dbHelper.getByID_NganSach(id);
            return false;
        } catch (Exception e) {
            return true;
        }
    }
}
