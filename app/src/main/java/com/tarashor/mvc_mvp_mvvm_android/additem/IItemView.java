package com.tarashor.mvc_mvp_mvvm_android.additem;

public interface IItemView {
    void onPositiveButtonClicked(String messageToShow);

    void onNegativeButtonPressed();

    String getTextNameField();

    void setTextFieldName(String name);
}
