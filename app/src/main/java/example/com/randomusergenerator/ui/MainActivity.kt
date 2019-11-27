package example.com.randomusergenerator.ui

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import example.com.randomusergenerator.App
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
    private val linearLayoutManager = LinearLayoutManager(this)
    private val gridLayoutManager = GridLayoutManager(this, 2)
    private val userSettings = App.INSTANCE.userSettings
    private var endlessRecyclerViewScrollListener: EndlessRecyclerViewScrollListener? = null

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
        requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        viewModel = ViewModelProviders.of(
            this, RandomUserViewModelFactory(
                UseCaseProviders.provideGetRandomUsersUseCase()
            )
        ).get(RandomUserViewModel::class.java)
        viewModel.randomUsers.observe(this, randomUsersObserver)
        viewModel.isFailed.observe(this, isFailedObserver)
        listRV.adapter = userListAdapter
        setupLayoutManager()
        listRV.layoutManager?.let {
            endlessRecyclerViewScrollListener = object :
                EndlessRecyclerViewScrollListener(it) {
                override fun onLoadMore(page: Int, totalItemsCount: Int) {
                    viewModel.getRandomUsers(page)
                }
            }.also { listener ->
                listRV.addOnScrollListener(listener)
            }

        }
    }

    private fun setupLayoutManager() {
        listRV.layoutManager = (if (userSettings.isGrid()) gridLayoutManager else linearLayoutManager)
            .also {
                endlessRecyclerViewScrollListener?.replaceLayoutManager(it)
            }
    }

    override fun onItemClicked(position: Int) {
        val intent = Intent(this, UserDetailsActivity::class.java)
        val bundle = Bundle()
        val parcelUser = userListAdapter.getItem(position)
        bundle.putParcelable("user", parcelUser)
        intent.putExtra("bundle", bundle)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        menu.findItem(R.id.changeLayout)?.let {
            if (userSettings.isGrid()) {
                it.icon = ContextCompat.getDrawable(this, R.drawable.ic_list_white_24dp)
            } else {
                it.icon = ContextCompat.getDrawable(this, R.drawable.ic_grid_on_white_24dp)
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.changeLayout) {
            userSettings.setGrid(!userSettings.isGrid())
            invalidateOptionsMenu()
            setupLayoutManager()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
