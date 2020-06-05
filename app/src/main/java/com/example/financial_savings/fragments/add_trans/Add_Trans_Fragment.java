package com.example.financial_savings.fragments.add_trans;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.financial_savings.R;
import com.example.financial_savings.controllers.chooses.ChooseEventActivity;
import com.example.financial_savings.controllers.chooses.ChooseSavingsActivity;
import com.example.financial_savings.controllers.chooses.ChooseWalletActivity;
import com.example.financial_savings.controllers.chooses.TabHostCateActivity;
import com.example.financial_savings.entities.DanhMuc;
import com.example.financial_savings.entities.SoGiaoDich;
import com.example.financial_savings.entities.SuKien;
import com.example.financial_savings.entities.TietKiem;
import com.example.financial_savings.entities.ViCaNhan;
import com.example.financial_savings.helper.DBHelper;
import com.example.financial_savings.interfaces.IMappingView;
import com.example.financial_savings.modules.alerts.AlertConfirmModule;
import com.example.financial_savings.modules.checks.CheckEmptyModule;
import com.example.financial_savings.modules.formats.DateFormatModule;
import com.example.financial_savings.modules.formats.FormatMoneyModule;
import com.example.financial_savings.modules.others.AccountCurrentModule;
import com.example.financial_savings.modules.savings.MoneySavingsModule;
import com.example.financial_savings.modules.icons.IconsDrawableModule;
import com.example.financial_savings.modules.randoms.RandomIDModule;
import com.example.financial_savings.sessions.Session;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Add_Trans_Fragment extends Fragment implements IMappingView {
    private Button buttonSave;
    private EditText editTextMoney, editTextCategory, editTextNote, editTextDate, editTextWallet, editTextEvent, editTextSaving;
    private ImageButton buttonIconCate;
    private DBHelper dbHelper;
    private View root;
    private Session session;
    private DanhMuc danhMuc;
    private ViCaNhan viCaNhan;
    private SuKien suKien;
    private TietKiem tietKiem;
    private static final String WALLET_FROM = "from";
    private static final String DATE = "01/01/2100";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.transaction_add, container, false);
        init();
        if(getUserVisibleHint()) loadData();
        loadData();
        eventChooseWallet();
        eventChooseCate();
        eventChooseDate();
        eventChooseEvent();
        eventChooseSavings();
        eventSave();
        return root;
    }

    private void eventSave() {
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String note = editTextNote.getText().toString();
                String money = editTextMoney.getText().toString().replace(",", "");
                String dateStr = editTextDate.getText().toString();
                java.sql.Date sqlDate = DateFormatModule.getDateSQL(dateStr);
                if(tietKiem != null) {
                    if(danhMuc != null) {
                        handlingInput(money, note, sqlDate);
                    } else Toast.makeText(getActivity(), R.string.empty_cate, Toast.LENGTH_SHORT).show();
                }
                else {
                    if(viCaNhan != null && danhMuc != null) {
                        handlingInput(money, note, sqlDate);
                    } else Toast.makeText(getActivity(), R.string.empty_cate_wallet, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void handlingInput(final String money, final String note, final java.sql.Date sqlDate) {
        if(CheckEmptyModule.isEmpty(money, money, money)) {
            if(viCaNhan != null) {
                if(danhMuc.getLoaiDanhMuc().equals("khoanchi")) {
                    if(viCaNhan.getSoTien() >= Double.parseDouble(money)) {
                        handlingSaveTrans(money, note, sqlDate);
                    } else Toast.makeText(getActivity(), R.string.invalid_money, Toast.LENGTH_SHORT).show();
                } else handlingSaveTrans(money, note, sqlDate);
            }
            else {
                double total = MoneySavingsModule.getMoneySavings(dbHelper, tietKiem);
                if(danhMuc.getLoaiDanhMuc().equals("khoanchi")) {
                    if(total >= Double.parseDouble(money)) {
                        handlingSaveTrans(money, note, sqlDate);
                    } else Toast.makeText(getActivity(), R.string.invalid_money, Toast.LENGTH_SHORT).show();
                } else handlingSaveTrans(money, note, sqlDate);
            }
        } else Toast.makeText(getActivity(), R.string.empty_money, Toast.LENGTH_SHORT).show();
    }

    private void handlingSaveTrans(final String money, final String note, final java.sql.Date sqlDate) {
        if(Double.parseDouble(money) > 0) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    SoGiaoDich soGiaoDich = new SoGiaoDich();
                    soGiaoDich.setMaGiaoDich(RandomIDModule.getTransID(dbHelper));
                    soGiaoDich.setSoTien(Double.parseDouble(money));
                    soGiaoDich.setGhiChu(note);
                    soGiaoDich.setNgayGiaoDich(sqlDate);
                    soGiaoDich.setMasv(AccountCurrentModule.getSinhVienCurrent(dbHelper).getMasv());
                    soGiaoDich.setMaDanhMuc(danhMuc.getMaDanhMuc());
                    handlingSetProperty(soGiaoDich);
                    dbHelper.insert_SoGiaoDich(soGiaoDich);
                    handlingUpdateWallet(money);
                    getActivity().onBackPressed();
                    Toast.makeText(getActivity(), R.string.success_add_trans, Toast.LENGTH_SHORT).show();
                }
            };
            AlertConfirmModule.alertDialogConfirm(getActivity(), R.string.confirm_add_trans, runnable);
        } else Toast.makeText(getActivity(), R.string.invalid_money, Toast.LENGTH_SHORT).show();
    }

    private void handlingSetProperty(SoGiaoDich soGiaoDich) {
        if(viCaNhan != null) {
            soGiaoDich.setMaVi(viCaNhan.getMaVi());
        }
        else {
            soGiaoDich.setMaVi("null");
        }
        if(tietKiem != null) {
            soGiaoDich.setMaTietKiem(tietKiem.getMaTietKiem());
        }
        else {
            soGiaoDich.setMaTietKiem("null");
        }
        if(suKien != null) {
            soGiaoDich.setMaSuKien(suKien.getMaSuKien());
        }
        else {
            soGiaoDich.setMaSuKien("null");
        }
    }

    private void handlingUpdateWallet(String money) {
        if(viCaNhan != null) {
            if(danhMuc.getLoaiDanhMuc().equals("doanhthu")) {
                viCaNhan.napTien(money);
            }
            else {
                viCaNhan.rutTien(money);
            }
            dbHelper.update_ViCaNhan(viCaNhan);
        }
        else {
            double total = MoneySavingsModule.getMoneySavings(dbHelper, tietKiem);
            if(total == 0) {
                tietKiem.setNgayKetThuc(new java.sql.Date((DateFormatModule.getDate(DATE).getTime())));
            }
            else {
                try {
                    double goal = tietKiem.getSoTien();
                    double average = MoneySavingsModule.getAverageMoney(dbHelper, tietKiem);
                    int num_day = (int) (goal / average);
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    Date now = new Date(Calendar.getInstance().getTime().getTime());
                    Calendar calendar = GregorianCalendar.getInstance();
                    calendar.setTime(now);
                    calendar.add(GregorianCalendar.DAY_OF_MONTH, num_day);
                    tietKiem.setNgayKetThuc(java.sql.Date.valueOf(df.format(calendar.getTime())));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            dbHelper.update_TietKiem(tietKiem);
        }
    }

    private void eventChooseSavings() {
        editTextSaving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChooseSavingsActivity.class);
                startActivity(intent);
                session.clearWallet();
            }
        });
    }

    private void eventChooseEvent() {
        editTextEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChooseEventActivity.class);
                startActivity(intent);
            }
        });
    }

    private void eventChooseDate() {
        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int nYear = c.get(Calendar.YEAR);
                int nMonth = c.get(Calendar.MONTH);
                int nDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                        editTextDate.setText(date);
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        root.getContext(), callback, nYear, nMonth, nDay);
                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                datePickerDialog.show();
            }
        });
    }

    private void eventChooseCate() {
        editTextCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TabHostCateActivity.class);
                startActivity(intent);
            }
        });
        buttonIconCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TabHostCateActivity.class);
                startActivity(intent);
            }
        });
    }

    private void eventChooseWallet() {
        editTextWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChooseWalletActivity.class);
                intent.putExtra("name", WALLET_FROM);
                startActivity(intent);
                session.clearSavings();
            }
        });
    }

    private void loadData() {
        loadSessionCate();
        loadSessionWallet();
        loadSessionEvent();
        loadSessionSavings();
    }

    private void loadSessionCate() {
        String idCate = session.getIDCate();
        if(idCate != null && !idCate.isEmpty()) {
            try {
                danhMuc = dbHelper.getByID_DanhMuc(idCate);
                int resID = IconsDrawableModule.getResourcesDrawble(root.getContext(), danhMuc.getBieuTuong());
                buttonIconCate.setImageResource(resID);
                editTextCategory.setText(danhMuc.getTenDanhMuc());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            buttonIconCate.setImageResource(R.drawable.ic_help_black_24dp);
            editTextCategory.setText("");
            editTextCategory.setHint(getResources().getString(R.string.category));
        }
    }

    private void loadSessionWallet() {
        String idWallet = session.getIDWallet();
        if(idWallet != null && !idWallet.isEmpty()) {
            try {
                viCaNhan = dbHelper.getByID_ViCaNhan(idWallet);
                editTextWallet.setText(viCaNhan.getTenVi());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            viCaNhan = null;
            editTextWallet.setText("");
            editTextWallet.setHint(getResources().getString(R.string.wallet));
        }
    }

    private void loadSessionEvent() {
        String idEvent = session.getIDEvent();
        if(idEvent != null && !idEvent.isEmpty()) {
            try {
                suKien = dbHelper.getByID_SuKien(idEvent);
                editTextEvent.setText(suKien.getTenSuKien());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            editTextEvent.setText("");
            editTextEvent.setHint(getResources().getString(R.string.event));
        }
    }

    private void loadSessionSavings() {
        String idSavings = session.getIDSavings();
        if(idSavings != null && !idSavings.isEmpty()) {
            try {
                tietKiem = dbHelper.getByID_TietKiem(idSavings);
                editTextSaving.setText(tietKiem.getTenTietKiem());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            tietKiem = null;
            editTextSaving.setText("");
            editTextSaving.setHint(getResources().getString(R.string.saving));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && isResumed()) {
            loadData();
        }
    }

    @Override
    public void init() {
        buttonSave = root.findViewById(R.id.buttonSave_transaction_add);
        editTextMoney = root.findViewById(R.id.editTextMoney_transaction_add);
        editTextCategory = root.findViewById(R.id.editTextCategory_transaction_add);
        editTextNote = root.findViewById(R.id.editTextNote_transaction_add);
        editTextDate = root.findViewById(R.id.editTextDate_transaction_add);
        editTextWallet = root.findViewById(R.id.editTextWallet_transaction_add);
        editTextEvent = root.findViewById(R.id.editTextEvent_transaction_add);
        editTextSaving = root.findViewById(R.id.editTextSaving_transaction_add);
        buttonIconCate = root.findViewById(R.id.buttonCate_transaction_add);
        dbHelper = new DBHelper(root.getContext());
        session = new Session(root.getContext());
        session.clear();
        editTextMoney.requestFocus();
        FormatMoneyModule.formatEditTextMoney(editTextMoney);
    }
}
