package com.greensoft.myapplication.appUtil;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.greensoft.myapplication.Medication;

import java.util.ArrayList;
import java.util.List;

public class myDiffUtilCallBacks extends DiffUtil.Callback {
    List<Medication> old_meds = new ArrayList<>();
    List<Medication> new_meds = new ArrayList<>();

    public myDiffUtilCallBacks(List<Medication> old_meds, List<Medication> new_meds) {
        this.old_meds = old_meds;
        this.new_meds = new_meds;
    }

    @Override
    public int getOldListSize() {
        return old_meds != null ?old_meds.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return new_meds != null ? new_meds.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return true;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {

        int result = new_meds.get(newItemPosition).compareTo(old_meds.get(oldItemPosition));
        if(result == 0)
            return true;

            return false;
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {

        Medication newMedication = new_meds.get(newItemPosition);
        Medication oldMedication = old_meds.get(oldItemPosition);

        Bundle  bundle = new Bundle();
        if(newMedication.getIf_drug_taken() != oldMedication.getIf_drug_taken()){
            bundle.putString("taken",newMedication.getIf_drug_taken()+"");
        }
        if(newMedication.isSkipped() != oldMedication.isSkipped()){
            bundle.putString("skipped",newMedication.isSkipped()+"");
        }

        if(bundle.size() == 0)
            return null;
        return bundle;

    }
}
