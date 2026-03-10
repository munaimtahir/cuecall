package com.cuecall.app.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.cuecall.app.data.local.dao.CallEventDao;
import com.cuecall.app.data.local.dao.CallEventDao_Impl;
import com.cuecall.app.data.local.dao.ClinicDao;
import com.cuecall.app.data.local.dao.ClinicDao_Impl;
import com.cuecall.app.data.local.dao.CounterDao;
import com.cuecall.app.data.local.dao.CounterDao_Impl;
import com.cuecall.app.data.local.dao.DeviceDao;
import com.cuecall.app.data.local.dao.DeviceDao_Impl;
import com.cuecall.app.data.local.dao.QueueDayDao;
import com.cuecall.app.data.local.dao.QueueDayDao_Impl;
import com.cuecall.app.data.local.dao.ServiceDao;
import com.cuecall.app.data.local.dao.ServiceDao_Impl;
import com.cuecall.app.data.local.dao.TokenDao;
import com.cuecall.app.data.local.dao.TokenDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile ClinicDao _clinicDao;

  private volatile ServiceDao _serviceDao;

  private volatile CounterDao _counterDao;

  private volatile QueueDayDao _queueDayDao;

  private volatile TokenDao _tokenDao;

  private volatile CallEventDao _callEventDao;

  private volatile DeviceDao _deviceDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `clinics` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `logoUrl` TEXT NOT NULL, `address` TEXT NOT NULL, `phone` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `services` (`id` TEXT NOT NULL, `clinicId` TEXT NOT NULL, `name` TEXT NOT NULL, `code` TEXT NOT NULL, `tokenPrefix` TEXT NOT NULL, `isActive` INTEGER NOT NULL, `sortOrder` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_services_clinicId` ON `services` (`clinicId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_services_isActive` ON `services` (`isActive`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `counters` (`id` TEXT NOT NULL, `clinicId` TEXT NOT NULL, `name` TEXT NOT NULL, `serviceIdsJson` TEXT NOT NULL, `isActive` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_counters_clinicId` ON `counters` (`clinicId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `queue_days` (`id` TEXT NOT NULL, `clinicId` TEXT NOT NULL, `businessDate` TEXT NOT NULL, `isOpen` INTEGER NOT NULL, `resetStrategy` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_queue_days_clinicId` ON `queue_days` (`clinicId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_queue_days_businessDate` ON `queue_days` (`businessDate`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `tokens` (`id` TEXT NOT NULL, `clinicId` TEXT NOT NULL, `queueDayId` TEXT NOT NULL, `serviceId` TEXT NOT NULL, `counterId` TEXT, `tokenPrefix` TEXT NOT NULL, `tokenNumber` INTEGER NOT NULL, `displayNumber` TEXT NOT NULL, `status` TEXT NOT NULL, `issuedAt` INTEGER NOT NULL, `calledAt` INTEGER, `completedAt` INTEGER, `skippedAt` INTEGER, `recalledAt` INTEGER, `cancelledAt` INTEGER, `notes` TEXT NOT NULL, `createdByDeviceId` TEXT NOT NULL, `updatedByDeviceId` TEXT NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_tokens_queueDayId` ON `tokens` (`queueDayId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_tokens_serviceId` ON `tokens` (`serviceId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_tokens_status` ON `tokens` (`status`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_tokens_queueDayId_serviceId_status` ON `tokens` (`queueDayId`, `serviceId`, `status`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `call_events` (`id` TEXT NOT NULL, `clinicId` TEXT NOT NULL, `tokenId` TEXT NOT NULL, `queueDayId` TEXT NOT NULL, `actionType` TEXT NOT NULL, `serviceId` TEXT NOT NULL, `counterId` TEXT, `createdAt` INTEGER NOT NULL, `createdByDeviceId` TEXT NOT NULL, `metadataJson` TEXT NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_call_events_queueDayId` ON `call_events` (`queueDayId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_call_events_tokenId` ON `call_events` (`tokenId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `devices` (`id` TEXT NOT NULL, `clinicId` TEXT NOT NULL, `deviceName` TEXT NOT NULL, `deviceMode` TEXT NOT NULL, `assignedServiceId` TEXT, `assignedCounterId` TEXT, `printerAddress` TEXT, `lastSeenAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'e0f93fc0b5891e8cc17c06c588bb3a97')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `clinics`");
        db.execSQL("DROP TABLE IF EXISTS `services`");
        db.execSQL("DROP TABLE IF EXISTS `counters`");
        db.execSQL("DROP TABLE IF EXISTS `queue_days`");
        db.execSQL("DROP TABLE IF EXISTS `tokens`");
        db.execSQL("DROP TABLE IF EXISTS `call_events`");
        db.execSQL("DROP TABLE IF EXISTS `devices`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsClinics = new HashMap<String, TableInfo.Column>(7);
        _columnsClinics.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClinics.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClinics.put("logoUrl", new TableInfo.Column("logoUrl", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClinics.put("address", new TableInfo.Column("address", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClinics.put("phone", new TableInfo.Column("phone", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClinics.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClinics.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysClinics = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesClinics = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoClinics = new TableInfo("clinics", _columnsClinics, _foreignKeysClinics, _indicesClinics);
        final TableInfo _existingClinics = TableInfo.read(db, "clinics");
        if (!_infoClinics.equals(_existingClinics)) {
          return new RoomOpenHelper.ValidationResult(false, "clinics(com.cuecall.app.data.local.entity.ClinicEntity).\n"
                  + " Expected:\n" + _infoClinics + "\n"
                  + " Found:\n" + _existingClinics);
        }
        final HashMap<String, TableInfo.Column> _columnsServices = new HashMap<String, TableInfo.Column>(9);
        _columnsServices.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsServices.put("clinicId", new TableInfo.Column("clinicId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsServices.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsServices.put("code", new TableInfo.Column("code", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsServices.put("tokenPrefix", new TableInfo.Column("tokenPrefix", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsServices.put("isActive", new TableInfo.Column("isActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsServices.put("sortOrder", new TableInfo.Column("sortOrder", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsServices.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsServices.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysServices = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesServices = new HashSet<TableInfo.Index>(2);
        _indicesServices.add(new TableInfo.Index("index_services_clinicId", false, Arrays.asList("clinicId"), Arrays.asList("ASC")));
        _indicesServices.add(new TableInfo.Index("index_services_isActive", false, Arrays.asList("isActive"), Arrays.asList("ASC")));
        final TableInfo _infoServices = new TableInfo("services", _columnsServices, _foreignKeysServices, _indicesServices);
        final TableInfo _existingServices = TableInfo.read(db, "services");
        if (!_infoServices.equals(_existingServices)) {
          return new RoomOpenHelper.ValidationResult(false, "services(com.cuecall.app.data.local.entity.ServiceEntity).\n"
                  + " Expected:\n" + _infoServices + "\n"
                  + " Found:\n" + _existingServices);
        }
        final HashMap<String, TableInfo.Column> _columnsCounters = new HashMap<String, TableInfo.Column>(7);
        _columnsCounters.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCounters.put("clinicId", new TableInfo.Column("clinicId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCounters.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCounters.put("serviceIdsJson", new TableInfo.Column("serviceIdsJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCounters.put("isActive", new TableInfo.Column("isActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCounters.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCounters.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCounters = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCounters = new HashSet<TableInfo.Index>(1);
        _indicesCounters.add(new TableInfo.Index("index_counters_clinicId", false, Arrays.asList("clinicId"), Arrays.asList("ASC")));
        final TableInfo _infoCounters = new TableInfo("counters", _columnsCounters, _foreignKeysCounters, _indicesCounters);
        final TableInfo _existingCounters = TableInfo.read(db, "counters");
        if (!_infoCounters.equals(_existingCounters)) {
          return new RoomOpenHelper.ValidationResult(false, "counters(com.cuecall.app.data.local.entity.CounterEntity).\n"
                  + " Expected:\n" + _infoCounters + "\n"
                  + " Found:\n" + _existingCounters);
        }
        final HashMap<String, TableInfo.Column> _columnsQueueDays = new HashMap<String, TableInfo.Column>(7);
        _columnsQueueDays.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQueueDays.put("clinicId", new TableInfo.Column("clinicId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQueueDays.put("businessDate", new TableInfo.Column("businessDate", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQueueDays.put("isOpen", new TableInfo.Column("isOpen", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQueueDays.put("resetStrategy", new TableInfo.Column("resetStrategy", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQueueDays.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQueueDays.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysQueueDays = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesQueueDays = new HashSet<TableInfo.Index>(2);
        _indicesQueueDays.add(new TableInfo.Index("index_queue_days_clinicId", false, Arrays.asList("clinicId"), Arrays.asList("ASC")));
        _indicesQueueDays.add(new TableInfo.Index("index_queue_days_businessDate", false, Arrays.asList("businessDate"), Arrays.asList("ASC")));
        final TableInfo _infoQueueDays = new TableInfo("queue_days", _columnsQueueDays, _foreignKeysQueueDays, _indicesQueueDays);
        final TableInfo _existingQueueDays = TableInfo.read(db, "queue_days");
        if (!_infoQueueDays.equals(_existingQueueDays)) {
          return new RoomOpenHelper.ValidationResult(false, "queue_days(com.cuecall.app.data.local.entity.QueueDayEntity).\n"
                  + " Expected:\n" + _infoQueueDays + "\n"
                  + " Found:\n" + _existingQueueDays);
        }
        final HashMap<String, TableInfo.Column> _columnsTokens = new HashMap<String, TableInfo.Column>(18);
        _columnsTokens.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTokens.put("clinicId", new TableInfo.Column("clinicId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTokens.put("queueDayId", new TableInfo.Column("queueDayId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTokens.put("serviceId", new TableInfo.Column("serviceId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTokens.put("counterId", new TableInfo.Column("counterId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTokens.put("tokenPrefix", new TableInfo.Column("tokenPrefix", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTokens.put("tokenNumber", new TableInfo.Column("tokenNumber", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTokens.put("displayNumber", new TableInfo.Column("displayNumber", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTokens.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTokens.put("issuedAt", new TableInfo.Column("issuedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTokens.put("calledAt", new TableInfo.Column("calledAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTokens.put("completedAt", new TableInfo.Column("completedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTokens.put("skippedAt", new TableInfo.Column("skippedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTokens.put("recalledAt", new TableInfo.Column("recalledAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTokens.put("cancelledAt", new TableInfo.Column("cancelledAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTokens.put("notes", new TableInfo.Column("notes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTokens.put("createdByDeviceId", new TableInfo.Column("createdByDeviceId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTokens.put("updatedByDeviceId", new TableInfo.Column("updatedByDeviceId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTokens = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTokens = new HashSet<TableInfo.Index>(4);
        _indicesTokens.add(new TableInfo.Index("index_tokens_queueDayId", false, Arrays.asList("queueDayId"), Arrays.asList("ASC")));
        _indicesTokens.add(new TableInfo.Index("index_tokens_serviceId", false, Arrays.asList("serviceId"), Arrays.asList("ASC")));
        _indicesTokens.add(new TableInfo.Index("index_tokens_status", false, Arrays.asList("status"), Arrays.asList("ASC")));
        _indicesTokens.add(new TableInfo.Index("index_tokens_queueDayId_serviceId_status", false, Arrays.asList("queueDayId", "serviceId", "status"), Arrays.asList("ASC", "ASC", "ASC")));
        final TableInfo _infoTokens = new TableInfo("tokens", _columnsTokens, _foreignKeysTokens, _indicesTokens);
        final TableInfo _existingTokens = TableInfo.read(db, "tokens");
        if (!_infoTokens.equals(_existingTokens)) {
          return new RoomOpenHelper.ValidationResult(false, "tokens(com.cuecall.app.data.local.entity.TokenEntity).\n"
                  + " Expected:\n" + _infoTokens + "\n"
                  + " Found:\n" + _existingTokens);
        }
        final HashMap<String, TableInfo.Column> _columnsCallEvents = new HashMap<String, TableInfo.Column>(10);
        _columnsCallEvents.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCallEvents.put("clinicId", new TableInfo.Column("clinicId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCallEvents.put("tokenId", new TableInfo.Column("tokenId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCallEvents.put("queueDayId", new TableInfo.Column("queueDayId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCallEvents.put("actionType", new TableInfo.Column("actionType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCallEvents.put("serviceId", new TableInfo.Column("serviceId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCallEvents.put("counterId", new TableInfo.Column("counterId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCallEvents.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCallEvents.put("createdByDeviceId", new TableInfo.Column("createdByDeviceId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCallEvents.put("metadataJson", new TableInfo.Column("metadataJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCallEvents = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCallEvents = new HashSet<TableInfo.Index>(2);
        _indicesCallEvents.add(new TableInfo.Index("index_call_events_queueDayId", false, Arrays.asList("queueDayId"), Arrays.asList("ASC")));
        _indicesCallEvents.add(new TableInfo.Index("index_call_events_tokenId", false, Arrays.asList("tokenId"), Arrays.asList("ASC")));
        final TableInfo _infoCallEvents = new TableInfo("call_events", _columnsCallEvents, _foreignKeysCallEvents, _indicesCallEvents);
        final TableInfo _existingCallEvents = TableInfo.read(db, "call_events");
        if (!_infoCallEvents.equals(_existingCallEvents)) {
          return new RoomOpenHelper.ValidationResult(false, "call_events(com.cuecall.app.data.local.entity.CallEventEntity).\n"
                  + " Expected:\n" + _infoCallEvents + "\n"
                  + " Found:\n" + _existingCallEvents);
        }
        final HashMap<String, TableInfo.Column> _columnsDevices = new HashMap<String, TableInfo.Column>(8);
        _columnsDevices.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDevices.put("clinicId", new TableInfo.Column("clinicId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDevices.put("deviceName", new TableInfo.Column("deviceName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDevices.put("deviceMode", new TableInfo.Column("deviceMode", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDevices.put("assignedServiceId", new TableInfo.Column("assignedServiceId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDevices.put("assignedCounterId", new TableInfo.Column("assignedCounterId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDevices.put("printerAddress", new TableInfo.Column("printerAddress", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDevices.put("lastSeenAt", new TableInfo.Column("lastSeenAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDevices = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesDevices = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoDevices = new TableInfo("devices", _columnsDevices, _foreignKeysDevices, _indicesDevices);
        final TableInfo _existingDevices = TableInfo.read(db, "devices");
        if (!_infoDevices.equals(_existingDevices)) {
          return new RoomOpenHelper.ValidationResult(false, "devices(com.cuecall.app.data.local.entity.DeviceEntity).\n"
                  + " Expected:\n" + _infoDevices + "\n"
                  + " Found:\n" + _existingDevices);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "e0f93fc0b5891e8cc17c06c588bb3a97", "e11fb8f8b0dd01d1965855906c206177");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "clinics","services","counters","queue_days","tokens","call_events","devices");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `clinics`");
      _db.execSQL("DELETE FROM `services`");
      _db.execSQL("DELETE FROM `counters`");
      _db.execSQL("DELETE FROM `queue_days`");
      _db.execSQL("DELETE FROM `tokens`");
      _db.execSQL("DELETE FROM `call_events`");
      _db.execSQL("DELETE FROM `devices`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(ClinicDao.class, ClinicDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ServiceDao.class, ServiceDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CounterDao.class, CounterDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(QueueDayDao.class, QueueDayDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(TokenDao.class, TokenDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CallEventDao.class, CallEventDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(DeviceDao.class, DeviceDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public ClinicDao clinicDao() {
    if (_clinicDao != null) {
      return _clinicDao;
    } else {
      synchronized(this) {
        if(_clinicDao == null) {
          _clinicDao = new ClinicDao_Impl(this);
        }
        return _clinicDao;
      }
    }
  }

  @Override
  public ServiceDao serviceDao() {
    if (_serviceDao != null) {
      return _serviceDao;
    } else {
      synchronized(this) {
        if(_serviceDao == null) {
          _serviceDao = new ServiceDao_Impl(this);
        }
        return _serviceDao;
      }
    }
  }

  @Override
  public CounterDao counterDao() {
    if (_counterDao != null) {
      return _counterDao;
    } else {
      synchronized(this) {
        if(_counterDao == null) {
          _counterDao = new CounterDao_Impl(this);
        }
        return _counterDao;
      }
    }
  }

  @Override
  public QueueDayDao queueDayDao() {
    if (_queueDayDao != null) {
      return _queueDayDao;
    } else {
      synchronized(this) {
        if(_queueDayDao == null) {
          _queueDayDao = new QueueDayDao_Impl(this);
        }
        return _queueDayDao;
      }
    }
  }

  @Override
  public TokenDao tokenDao() {
    if (_tokenDao != null) {
      return _tokenDao;
    } else {
      synchronized(this) {
        if(_tokenDao == null) {
          _tokenDao = new TokenDao_Impl(this);
        }
        return _tokenDao;
      }
    }
  }

  @Override
  public CallEventDao callEventDao() {
    if (_callEventDao != null) {
      return _callEventDao;
    } else {
      synchronized(this) {
        if(_callEventDao == null) {
          _callEventDao = new CallEventDao_Impl(this);
        }
        return _callEventDao;
      }
    }
  }

  @Override
  public DeviceDao deviceDao() {
    if (_deviceDao != null) {
      return _deviceDao;
    } else {
      synchronized(this) {
        if(_deviceDao == null) {
          _deviceDao = new DeviceDao_Impl(this);
        }
        return _deviceDao;
      }
    }
  }
}
