package example.com.randomusergenerator.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import example.com.randomusergenerator.R
import example.com.randomusergenerator.domain.RandomUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UsersListAdapter(private var itemClickListener: OnItemPositionClickListener) : BaseRecyclerAdapter<RandomUser>() {

    private val uiScope = CoroutineScope(Dispatchers.Main)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<RandomUser> {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false)
        return UsersListViewHolder(view, itemClickListener)
    }

    fun updateData(usersList: List<RandomUser>){
        uiScope.launch {
            val diffResult = withContext(Dispatchers.IO) {
                DiffUtil.calculateDiff(RandomUsersDiffCallback(usersList))
            }
            this@UsersListAdapter.data.clear()
            this@UsersListAdapter.data.addAll(usersList)
            diffResult.dispatchUpdatesTo(this@UsersListAdapter)
        }
    }

    inner class RandomUsersDiffCallback(private val usersList: List<RandomUser>) :
        DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return data[oldItemPosition].email == usersList[newItemPosition].email
        }

        override fun getOldListSize() = data.size

        override fun getNewListSize() = usersList.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return data[oldItemPosition] == usersList[newItemPosition]
        }

    }
}