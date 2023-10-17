package com.example.myandroidtest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myandroidtest.bean.UserInfo
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.state.ResultState

class TestViewModel: BaseViewModel() {
    var loginResult = MutableLiveData<ResultState<UserInfo>>()
}