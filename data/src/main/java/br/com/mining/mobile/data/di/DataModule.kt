package br.com.mining.mobile.data.di

import androidx.room.Room
import br.com.mining.mobile.data.AppDatabase
import br.com.mining.mobile.data.model.*
import br.com.mining.mobile.data.repository.*
import br.com.mining.mobile.shared.model.*
import br.com.mining.mobile.shared.repository.*
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "mining").build()
    }

    single { get<AppDatabase>().checkListDao() }
    single<Checklist> { ChecklistEntity() }
    single<ChecklistRepository> { ChecklistRepositoryImpl() }

    single { get<AppDatabase>().checkListItemDao() }
    single<ChecklistItem> { ChecklistItemEntity() }
    single<ChecklistItemRepository> { ChecklistItemRepositoryImpl() }

    single { get<AppDatabase>().equipmentDao() }
    single<Equipment> { EquipmentEntity() }
    single<EquipmentRepository> { EquipmentRepositoryImpl() }

    single { get<AppDatabase>().inboundDao() }
    single<Inbound> { InboundEntity() }
    single<InboundRepository> { InboundRepositoryImpl() }

    single { get<AppDatabase>().operatorDao() }
    single<Operator> { OperatorEntity() }
    single<OperatorRepository> { OperatorRepositoryImpl() }

    single { get<AppDatabase>().organizationDao() }
    single<Organization> { OrganizationEntity() }
    single<OrganizationRepository> { OrganizationRepositoryImpl() }

    single { get<AppDatabase>().outboundDao() }
    single<Outbound> { OutboundEntity() }
    single<OutboundRepository> { OutboundRepositoryImpl() }

    single { get<AppDatabase>().siteDao() }
    single<Site> { SiteEntity() }
    single<SiteRepository> { SiteRepositoryImpl() }

    single { get<AppDatabase>().transactionDao() }
    single<Transaction> { TransactionEntity() }
    single<TransactionRepository> { TransactionRepositoryImpl() }

    single { get<AppDatabase>().userDao() }
    single<User> { UserEntity() }
    single<UserRepository> { UserRepositoryImpl() }


}


