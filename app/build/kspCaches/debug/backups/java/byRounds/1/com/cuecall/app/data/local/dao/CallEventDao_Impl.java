package com.cuecall.app.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.cuecall.app.data.local.entity.CallEventEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class CallEventDao_Impl implements CallEventDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CallEventEntity> __insertionAdapterOfCallEventEntity;

  public CallEventDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCallEventEntity = new EntityInsertionAdapter<CallEventEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `call_events` (`id`,`clinicId`,`tokenId`,`queueDayId`,`actionType`,`serviceId`,`counterId`,`createdAt`,`createdByDeviceId`,`metadataJson`) VALUES (?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CallEventEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getClinicId());
        statement.bindString(3, entity.getTokenId());
        statement.bindString(4, entity.getQueueDayId());
        statement.bindString(5, entity.getActionType());
        statement.bindString(6, entity.getServiceId());
        if (entity.getCounterId() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getCounterId());
        }
        statement.bindLong(8, entity.getCreatedAt());
        statement.bindString(9, entity.getCreatedByDeviceId());
        statement.bindString(10, entity.getMetadataJson());
      }
    };
  }

  @Override
  public Object insertEvent(final CallEventEntity event,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCallEventEntity.insert(event);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<CallEventEntity>> observeEventsForDay(final String queueDayId) {
    final String _sql = "SELECT * FROM call_events WHERE queueDayId = ? ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, queueDayId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"call_events"}, new Callable<List<CallEventEntity>>() {
      @Override
      @NonNull
      public List<CallEventEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfClinicId = CursorUtil.getColumnIndexOrThrow(_cursor, "clinicId");
          final int _cursorIndexOfTokenId = CursorUtil.getColumnIndexOrThrow(_cursor, "tokenId");
          final int _cursorIndexOfQueueDayId = CursorUtil.getColumnIndexOrThrow(_cursor, "queueDayId");
          final int _cursorIndexOfActionType = CursorUtil.getColumnIndexOrThrow(_cursor, "actionType");
          final int _cursorIndexOfServiceId = CursorUtil.getColumnIndexOrThrow(_cursor, "serviceId");
          final int _cursorIndexOfCounterId = CursorUtil.getColumnIndexOrThrow(_cursor, "counterId");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfCreatedByDeviceId = CursorUtil.getColumnIndexOrThrow(_cursor, "createdByDeviceId");
          final int _cursorIndexOfMetadataJson = CursorUtil.getColumnIndexOrThrow(_cursor, "metadataJson");
          final List<CallEventEntity> _result = new ArrayList<CallEventEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CallEventEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpClinicId;
            _tmpClinicId = _cursor.getString(_cursorIndexOfClinicId);
            final String _tmpTokenId;
            _tmpTokenId = _cursor.getString(_cursorIndexOfTokenId);
            final String _tmpQueueDayId;
            _tmpQueueDayId = _cursor.getString(_cursorIndexOfQueueDayId);
            final String _tmpActionType;
            _tmpActionType = _cursor.getString(_cursorIndexOfActionType);
            final String _tmpServiceId;
            _tmpServiceId = _cursor.getString(_cursorIndexOfServiceId);
            final String _tmpCounterId;
            if (_cursor.isNull(_cursorIndexOfCounterId)) {
              _tmpCounterId = null;
            } else {
              _tmpCounterId = _cursor.getString(_cursorIndexOfCounterId);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final String _tmpCreatedByDeviceId;
            _tmpCreatedByDeviceId = _cursor.getString(_cursorIndexOfCreatedByDeviceId);
            final String _tmpMetadataJson;
            _tmpMetadataJson = _cursor.getString(_cursorIndexOfMetadataJson);
            _item = new CallEventEntity(_tmpId,_tmpClinicId,_tmpTokenId,_tmpQueueDayId,_tmpActionType,_tmpServiceId,_tmpCounterId,_tmpCreatedAt,_tmpCreatedByDeviceId,_tmpMetadataJson);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getEventsForDay(final String queueDayId,
      final Continuation<? super List<CallEventEntity>> $completion) {
    final String _sql = "SELECT * FROM call_events WHERE queueDayId = ? ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, queueDayId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<CallEventEntity>>() {
      @Override
      @NonNull
      public List<CallEventEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfClinicId = CursorUtil.getColumnIndexOrThrow(_cursor, "clinicId");
          final int _cursorIndexOfTokenId = CursorUtil.getColumnIndexOrThrow(_cursor, "tokenId");
          final int _cursorIndexOfQueueDayId = CursorUtil.getColumnIndexOrThrow(_cursor, "queueDayId");
          final int _cursorIndexOfActionType = CursorUtil.getColumnIndexOrThrow(_cursor, "actionType");
          final int _cursorIndexOfServiceId = CursorUtil.getColumnIndexOrThrow(_cursor, "serviceId");
          final int _cursorIndexOfCounterId = CursorUtil.getColumnIndexOrThrow(_cursor, "counterId");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfCreatedByDeviceId = CursorUtil.getColumnIndexOrThrow(_cursor, "createdByDeviceId");
          final int _cursorIndexOfMetadataJson = CursorUtil.getColumnIndexOrThrow(_cursor, "metadataJson");
          final List<CallEventEntity> _result = new ArrayList<CallEventEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CallEventEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpClinicId;
            _tmpClinicId = _cursor.getString(_cursorIndexOfClinicId);
            final String _tmpTokenId;
            _tmpTokenId = _cursor.getString(_cursorIndexOfTokenId);
            final String _tmpQueueDayId;
            _tmpQueueDayId = _cursor.getString(_cursorIndexOfQueueDayId);
            final String _tmpActionType;
            _tmpActionType = _cursor.getString(_cursorIndexOfActionType);
            final String _tmpServiceId;
            _tmpServiceId = _cursor.getString(_cursorIndexOfServiceId);
            final String _tmpCounterId;
            if (_cursor.isNull(_cursorIndexOfCounterId)) {
              _tmpCounterId = null;
            } else {
              _tmpCounterId = _cursor.getString(_cursorIndexOfCounterId);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final String _tmpCreatedByDeviceId;
            _tmpCreatedByDeviceId = _cursor.getString(_cursorIndexOfCreatedByDeviceId);
            final String _tmpMetadataJson;
            _tmpMetadataJson = _cursor.getString(_cursorIndexOfMetadataJson);
            _item = new CallEventEntity(_tmpId,_tmpClinicId,_tmpTokenId,_tmpQueueDayId,_tmpActionType,_tmpServiceId,_tmpCounterId,_tmpCreatedAt,_tmpCreatedByDeviceId,_tmpMetadataJson);
            _result.add(_item);
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
