package example.com.randomusergenerator.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import example.com.randomusergenerator.R
import example.com.randomusergenerator.domain.RandomUser
import example.com.randomusergenerator.domain.UseCaseProviders
import example.com.randomusergenerator.ui.adapter.EndlessRecyclerViewScrollListener
import example.com.randomusergenerator.ui.adapter.OnItemPositionClickListener
import example.com.randomusergenerator.ui.adapter.UsersListAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), OnItemPositionClickListener {

    private var userListAdapter = UsersListAdapter(this)
    private lateinit var viewModel: RandomUserViewModel

    private val randomUsersObserver: Observer<List<RandomUser>> = Observer {
        userListAdapter.updateData(it)
    }

    private val isFailedObserver: Observer<Event<Boolean>> = Observer {
        it.getContentIfNotHandled()?.let {
            Toast.makeText(this, "Network error!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getContentView(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation =  (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        viewModel = ViewModelProviders.of(
            this, RandomUserViewModelFactory(
                UseCaseProviders.provideGetRandomUsersUseCase()
            )
        ).get(RandomUserViewModel::class.java)
        viewModel.randomUsers.observe(this, randomUsersObserver)
        viewModel.isFailed.observe(this, isFailedObserver)
        listRV.adapter = userListAdapter
        listRV.addOnScrollListener(object : EndlessRecyclerViewScrollListener(listRV.layoutManager!!){
            override fun onLoadMore(page: Int, totalItemsCount: Int) {
                viewModel.getRandomUsers(page)
            }
        })
    }

    override fun onItemClicked(position: Int) {

    }
}
