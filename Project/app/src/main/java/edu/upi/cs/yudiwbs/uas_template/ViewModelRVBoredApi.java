package edu.upi.cs.yudiwbs.uas_template;

import android.os.Parcelable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ViewModelRVBoredApi extends ViewModel {
    public MutableLiveData<Parcelable> recViewState;

    public ViewModelRVBoredApi() {
        recViewState = new MutableLiveData<Parcelable>();
        recViewState.setValue(null);
    }

    public LiveData<Parcelable> getRecViewState() {
        return recViewState;
    }

    public void setRecViewState (Parcelable state) {
        recViewState.setValue(state);
    }

}
