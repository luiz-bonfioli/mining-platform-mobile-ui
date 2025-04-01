package br.com.mining.mobile.service

import android.annotation.SuppressLint
import br.com.mining.mobile.shared.enums.EquipmentServiceState
import br.com.mining.mobile.shared.model.Checklist
import br.com.mining.mobile.shared.model.ChecklistItem
import br.com.mining.mobile.shared.model.Equipment
import br.com.mining.mobile.shared.repository.ChecklistRepository
import br.com.mining.mobile.shared.service.ChecklistService
import com.mining.platform.checklist.ChecklistPackageOuterClass
import com.mining.platform.equipment.EquipmentPackageOuterClass
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*

@SuppressLint("CheckResult")
object ChecklistServiceImpl : ChecklistService, KoinComponent {

    private const val DEFAULT_RESULT_OFFSET = 50

    private val repository: ChecklistRepository by inject()

    override fun createChecklist(result: () -> Unit) {
        Single.fromCallable {
            for (i in 0..30) {
                val checklist: Checklist by inject()
                checklist.name = "Travas de segurança 0$i?"
                checklist.id = UUID.randomUUID().toString()
                repository.insert(checklist)
            }
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                result()
            }, { e ->
                e.printStackTrace()
            })
    }

    override fun getChecklist(page: Int, callback: (checklists: List<Checklist>) -> Unit) {
        Single.fromCallable {
            repository.getAll(DEFAULT_RESULT_OFFSET, page)
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                callback(it)
            }, { e ->
                e.printStackTrace()
                callback(mutableListOf())
            })
    }

    private fun parseChecklist(message: ByteArray) {
        Single.fromCallable {
            val checklistListPackage = ChecklistPackageOuterClass.ChecklistListPackage.parseFrom(message)
            checklistListPackage.checklistsList.forEach {
                val checklist: Checklist by inject()
                checklist.id = it.id
                checklist.name = it.name
                it.checklistItemsList.forEach {
                    val checklistItem: ChecklistItem by inject()
                }
                repository.insert(checklist)
            }
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
            }, { e ->
                e.printStackTrace()
            })
    }
}