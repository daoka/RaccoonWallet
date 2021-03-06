package wacode.yamada.yuki.nempaymentapp.usecase

import io.reactivex.Single
import wacode.yamada.yuki.nempaymentapp.repository.AddressBookRepository
import wacode.yamada.yuki.nempaymentapp.repository.WalletInfoRepository
import wacode.yamada.yuki.nempaymentapp.room.address_book.FriendAddress
import wacode.yamada.yuki.nempaymentapp.room.address_book.FriendInfo
import wacode.yamada.yuki.nempaymentapp.room.address_book.FriendInfoSortType
import wacode.yamada.yuki.nempaymentapp.view.custom_view.BackLayerSearchView
import javax.inject.Inject


class AddressBookListUseCase @Inject constructor(private val addressBookRepository: AddressBookRepository, private val walletInfoRepository: WalletInfoRepository) {

    fun findFriendInfo(queryName: String, searchType: BackLayerSearchView.SearchType, sortType: FriendInfoSortType): Single<List<FriendInfo>> {
        return when (searchType) {
            BackLayerSearchView.SearchType.TWITTER -> addressBookRepository.queryFriendInfo(queryName, true, sortType)
            BackLayerSearchView.SearchType.LOCAL -> addressBookRepository.queryFriendInfo(queryName, false, sortType)
            else -> addressBookRepository.queryFriendInfo(queryName, sortType)
        }
    }

    fun removeAddressBook(friendId: Long) = addressBookRepository.removeFriendAddressByFriendId(friendId)

    fun removeFriendInfo(friendId: Long) = addressBookRepository.removeFriendInfoByFriendId(friendId)
}