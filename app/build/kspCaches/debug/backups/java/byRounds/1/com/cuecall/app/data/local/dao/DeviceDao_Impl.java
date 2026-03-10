package com.cuecall.app.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.cuecall.app.data.local.entity.DeviceEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class DeviceDao_Impl implements DeviceDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<DeviceEntity> __insertionAdapterOfDeviceEntity;

  public DeviceDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDeviceEntity = new EntityInsertionAdapter<DeviceEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `devices` (`id`,`clinicId`,`deviceName`,`deviceMode`,`assignedServiceId`,`assignedCounterId`,`printerAddress`,`lastSeenAt`) VALUES (?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final DeviceEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getClinicId());
        statement.bindString(3, entity.getDeviceName());
        statement.bindString(4, entity.getDeviceMode());
        if (entity.getAssignedServiceId() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getAssignedServiceId());
        }
        if (entity.getAssignedCounterId() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getAssignedCounterId());
        }
        if (entity.getPrinterAddress() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getPrinterAddress());
        }
        statement.bindLong(8, entity.getLastSeenAt());
      }
    };
  }

  @Override
  public Object upsertDevice(final DeviceEntity device,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfDeviceEntity.insert(device);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getDevice(final String deviceId,
      final Continuation<? super DeviceEntity> $completion) {
    final String _sql = "SELECT * FROM devices WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, deviceId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<DeviceEntity>() {
      @Override
      @Nullable
      public DeviceEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfClinicId = CursorUtil.getColumnIndexOrThrow(_cursor, "clinicId");
          final int _cursorIndexOfDeviceName = CursorUtil.getColumnIndexOrThrow(_cursor, "deviceName");
          final int _cursorIndexOfDeviceMode = CursorUtil.getColumnIndexOrThrow(_cursor, "deviceMode");
          final int _cursorIndexOfAssignedServiceId = CursorUtil.getColumnIndexOrThrow(_cursor, "assignedServiceId");
          final int _cursorIndexOfAssignedCounterId = CursorUtil.getColumnIndexOrThrow(_cursor, "assignedCounterId");
          final int _cursorIndexOfPrinterAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "printerAddress");
          final int _cursorIndexOfLastSeenAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSeenAt");
          final DeviceEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpClinicId;
            _tmpClinicId = _cursor.getString(_cursorIndexOfClinicId);
            final String _tmpDeviceName;
            _tmpDeviceName = _cursor.getString(_cursorIndexOfDeviceName);
            final String _tmpDeviceMode;
            _tmpDeviceMode = _cursor.getString(_cursorIndexOfDeviceMode);
            final String _tmpAssignedServiceId;
            if (_cursor.isNull(_cursorIndexOfAssignedServiceId)) {
              _tmpAssignedServiceId = null;
            } else {
              _tmpAssignedServiceId = _cursor.getString(_cursorIndexOfAssignedServiceId);
            }
            final String _tmpAssignedCounterId;
            if (_cursor.isNull(_cursorIndexOfAssignedCounterId)) {
              _tmpAssignedCounterId = null;
            } else {
              _tmpAssignedCounterId = _cursor.getString(_cursorIndexOfAssignedCounterId);
            }
            final String _tmpPrinterAddress;
            if (_cursor.isNull(_cursorIndexOfPrinterAddress)) {
              _tmpPrinterAddress = null;
            } else {
              _tmpPrinterAddress = _cursor.getString(_cursorIndexOfPrinterAddress);
            }
            final long _tmpLastSeenAt;
            _tmpLastSeenAt = _cursor.getLong(_cursorIndexOfLastSeenAt);
            _result = new DeviceEntity(_tmpId,_tmpClinicId,_tmpDeviceName,_tmpDeviceMode,_tmpAssignedServiceId,_tmpAssignedCounterId,_tmpPrinterAddress,_tmpLastSeenAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
