package com.kanci.ui.book.add;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;

import com.kanci.R;
import com.kanci.data.model.bean.Book;
import com.kanci.databinding.ActivityBookAddBinding;
import com.kanci.ui.base.BaseActivity;
import com.kanci.ui.base.BaseViewModel;
import com.kanci.ui.book.list.ListActivity;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends BaseActivity<ActivityBookAddBinding, AddViewModel> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_book_add;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int bookId = getIntent().getIntExtra("bookId", 0);
        VM().doLoadBook(bookId);
    }

    public void onAddBook() {
        VM().doCreateTask();
    }

    public void onCreateTask() {
        finish();
    }

    public void onChangePlanClick(NumberPicker picker, int oldVal, int newVal) {
        getBinding().planView.setValue(newVal);
        getBinding().remainDaysView.setValue(newVal);
    }

    public void onAddBookClick(View view) {
        int value = getBinding().planView.getValue();
        int plan = Integer.parseInt(planOptions[value]);
        VM().doAddBook(plan);
    }

    String[] planOptions = planOptions(5, 50, 10);

    public void onLoadBook(Book book) {
        getBinding().planView.setMinValue(0);
        getBinding().planView.setMaxValue(planOptions.length - 1);
        getBinding().planView.setDisplayedValues(planOptions);
        int curValue = optionValue(planOptions, book.plan);
        getBinding().planView.setValue(curValue);

        String[] remainDaysOptions = remainDaysOptions(planOptions);
        getBinding().remainDaysView.setMinValue(0);
        getBinding().remainDaysView.setMaxValue(remainDaysOptions.length - 1);
        getBinding().remainDaysView.setDisplayedValues(remainDaysOptions);
        getBinding().remainDaysView.setValue(curValue);
    }

    String[] planOptions(int min, int max, int inc) {
        List<String> options = new ArrayList<>();
        for (int i = min; i <= max; i += inc) {
            options.add(String.valueOf(i));
        }
        return options.toArray(new String[options.size()]);
    }

    int optionValue(String[] options, int display) {
        for (int i = 0; i < options.length; i++) {
            if (Integer.parseInt(options[i]) == display) {
                return i;
            }
        }
        return 0;
    }

    String[] remainDaysOptions(String[] plans) {
        String[] options = new String[plans.length];
        for (int i = 0; i < plans.length; i++) {
            int plan = Integer.parseInt(plans[i]);
            int remainDays = VM().book.get().getRemainWord() / plan + Math.min(VM().book.get().getRemainWord() % plan, 1);
            options[i] = String.valueOf(remainDays) + "天";
        }
        return options;
    }

    public void onBookListClick(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        startActivityForResult(intent, 0);
    }
}
