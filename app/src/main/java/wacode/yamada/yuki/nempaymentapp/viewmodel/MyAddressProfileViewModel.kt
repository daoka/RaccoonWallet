package wacode.yamada.yuki.nempaymentapp.viewmodel

import android.arch.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import wacode.yamada.yuki.nempaymentapp.event.MyProfileEvent
import wacode.yamada.yuki.nempaymentapp.model.MyProfileEntity
import wacode.yamada.yuki.nempaymentapp.room.address.MyAddress
import wacode.yamada.yuki.nempaymentapp.store.MyAddressProfileStore
import wacode.yamada.yuki.nempaymentapp.utils.RxBus
import javax.inject.Inject

class MyAddressProfileViewModel @Inject constructor(private val store: MyAddressProfileStore) : BaseViewModel() {
    val createLiveData: MutableLiveData<Unit>
            = MutableLiveData()
    val myProfileEntityEvent: MutableLiveData<MyProfileEntity>
            = MutableLiveData()

    init {
        store.getter.createObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    createLiveData.value = Unit
                })
                .let {
                    addDisposable(it)
                }

        RxBus.receive(MyProfileEvent::class.java)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    myProfileEntityEvent.value = it.myProfileEntity
                }
    }

    fun insert(myAddress: MyAddress) {
        store.actionCreator.insert(myAddress)
    }
}