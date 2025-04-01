package br.com.mining.mobile.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.mining.mobile.application.databinding.ItemEquipmentBinding
import br.com.mining.mobile.viewmodels.items.EquipmentItemViewModel
import com.unikode.mobile.fragments.listeners.OnRecyclerViewClickListener
import org.jetbrains.anko.layoutInflater


class EquipmentAdapter(
    val clickListener: OnRecyclerViewClickListener<EquipmentItemViewModel>
) : RecyclerView.Adapter<EquipmentAdapter.ViewHolder>() {

    private val items = ArrayList<EquipmentItemViewModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEquipmentBinding.inflate(parent.context.layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items[position], position)

    fun updateItems(data: List<EquipmentItemViewModel>) {
        items.clear()
        items.addAll(data)

        notifyDataSetChanged()
    }

    fun update(newItems: List<EquipmentItemViewModel>, clearData: Boolean = false) {
        if (clearData) {
            items.clear()
        }
        items.addAll(newItems)
        notifyDataSetChanged()
    }


    //region ~~~~ ViewHolder ~~~~
    inner class ViewHolder(val binding: ItemEquipmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: EquipmentItemViewModel, position: Int) {
            binding.viewModel = item
            binding.viewContainerItem.setOnClickListener {
                clickListener.onItemSelected(item, position)
            }
            binding.executePendingBindings()
        }
    }
    //endregion

}