package br.com.mining.mobile.adapters

import android.content.Context
import android.graphics.PorterDuff
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.mining.mobile.application.R
import br.com.mining.mobile.application.databinding.ItemChecklistBinding
import br.com.mining.mobile.viewmodels.items.ChecklistItemViewModel
import com.unikode.mobile.fragments.listeners.OnRecyclerViewClickListener
import org.jetbrains.anko.layoutInflater

class ChecklistAdapter(
    private val context: Context,
    private val clickListener: OnRecyclerViewClickListener<ChecklistItemViewModel>
    ) : RecyclerView.Adapter<ChecklistAdapter.ViewHolder>() {

    private val items = ArrayList<ChecklistItemViewModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemChecklistBinding.inflate(parent.context.layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items[position], position)

    fun updateItems(data: List<ChecklistItemViewModel>) {
        items.clear()
        items.addAll(data)

        notifyDataSetChanged()
    }

    fun update(newItems: List<ChecklistItemViewModel>, clearData: Boolean = false) {
        if (clearData) {
            items.clear()
        }
        items.addAll(newItems)
        notifyDataSetChanged()
    }


    //region ~~~~ ViewHolder ~~~~
    inner class ViewHolder(val binding: ItemChecklistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ChecklistItemViewModel, position: Int) {
            binding.viewModel = item
            binding.viewContainerItem.setOnClickListener {
                clickListener.onItemSelected(item, position)
            }

            binding.icOn.setOnClickListener {
                binding.icOn.setColorFilter(ContextCompat.getColor(context, R.color.green), PorterDuff.Mode.MULTIPLY)
                binding.icOff.setColorFilter(ContextCompat.getColor(context, R.color.black), PorterDuff.Mode.MULTIPLY)
            }
            binding.icOff.setOnClickListener {
                binding.icOn.setColorFilter(ContextCompat.getColor(context, R.color.black), PorterDuff.Mode.MULTIPLY)
                binding.icOff.setColorFilter(ContextCompat.getColor(context, R.color.red), PorterDuff.Mode.MULTIPLY)
            }
            binding.executePendingBindings()
        }
    }
    //endregion

}
