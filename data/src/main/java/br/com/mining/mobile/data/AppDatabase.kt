package br.com.mining.mobile.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.mining.mobile.data.dao.*
import br.com.mining.mobile.data.model.*

@Database(
    entities = [
        ChecklistEntity::class,
        ChecklistItemEntity::class,
        EquipmentEntity::class,
        InboundEntity::class,
        OperatorEntity::class,
        OrganizationEntity::class,
        OutboundEntity::class,
        SiteEntity::class,
        TransactionEntity::class,
        UserEntity::class
    ], version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun checkListDao(): ChecklistDao

    abstract fun checkListItemDao(): ChecklistItemDao

    abstract fun equipmentDao(): EquipmentDao

    abstract fun inboundDao(): InboundDao

    abstract fun operatorDao(): OperatorDao

    abstract fun organizationDao(): OrganizationDao

    abstract fun outboundDao(): OutboundDao

    abstract fun siteDao(): SiteDao

    abstract fun transactionDao(): TransactionDao

    abstract fun userDao(): UserDao


}