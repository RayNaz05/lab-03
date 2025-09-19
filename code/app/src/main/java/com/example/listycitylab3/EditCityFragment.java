package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class EditCityFragment extends DialogFragment {
    interface EditCityDialogListener {
        void updateCity(City city, int position);
    }

    private EditCityDialogListener listener;
    private int cityPosition;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EditCityDialogListener) {
            listener = (EditCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement EditCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);


        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);


        if (getArguments() != null) {
            editCityName.setText(getArguments().getString("cityName"));
            editProvinceName.setText(getArguments().getString("provinceName"));
            cityPosition = getArguments().getInt("position");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Edit city")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Save", (dialog, which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();
                    listener.updateCity(new City(cityName, provinceName), cityPosition);
                })
                .create();
    }

    public static EditCityFragment newInstance(City cityData, int index) {
        EditCityFragment dialog = new EditCityFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("cityObj", cityData);
        bundle.putInt("cityIndex", index);
        dialog.setArguments(bundle);
        return dialog;
    }
}
