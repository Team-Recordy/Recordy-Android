package com.record.mypage

import com.record.ui.base.BaseViewModel

class MypageViewModel : BaseViewModel<MypageState, MypageSideEffect>(MypageState()) {
    fun selectTab(tab: MypageTab) {
        intent {
            copy(mypageTab = tab)
        }
    }
}
