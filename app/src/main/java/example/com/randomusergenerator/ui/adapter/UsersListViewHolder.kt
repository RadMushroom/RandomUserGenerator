package example.com.randomusergenerator.ui.adapter

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import example.com.randomusergenerator.domain.RandomUser
import kotlinx.android.synthetic.main.user_list_item.view.*

class UsersListViewHolder(view: View, itemClickListener: OnItemPositionClickListener):
    BaseViewHolder<RandomUser>(view) {

    init {
        itemView.setOnClickListener {
            itemClickListener.onItemClicked(adapterPosition)
        }
    }
        override fun bind(model: RandomUser) {
            with(itemView){
                Glide.with(context)
                    .load(model.thumbnail)
                    .apply(RequestOptions.circleCropTransform())
                    .into(thumbnailImage)
                firstNameText.text = model.firstName
                secondNameText.text = model.secondName
                countryText.text = model.country
            }
        }
    }