package com.example.myandroidtest

import androidx.lifecycle.MutableLiveData
import com.covy.common.base.viewmodel.BaseViewModel
import com.covy.common.state.ResultState
import com.example.myandroidtest.bean.UserInfo


class TestViewModel: BaseViewModel() {
    var loginResult = MutableLiveData<ResultState<UserInfo>>()
}