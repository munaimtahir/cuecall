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
import com.cuecall.app.data.local.entity.QueueDayEntity;
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
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class QueueDayDao_Impl implements QueueDayDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<QueueDayEntity> __insertionAdapterOfQueueDayEntity;

  public QueueDayDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfQueueDayEntity = new EntityInsertionAdapter<QueueDayEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `queue_days` (`id`,`clinicId`,`businessDate`,`isOpen`,`resetStrategy`,`createdAt`,`updatedAt`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final QueueDayEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getClinicId());
        statement.bindString(3, entity.getBusinessDate());
        final int _tmp = entity.isOpen() ? 1 : 0;
        statement.bindLong(4, _tmp);
        statement.bindString(5, entity.getResetStrategy());
        statement.bindLong(6, entity.getCreatedAt());
        statement.bindLong(7, entity.getUpdatedAt());
      }
    };
  }

  @Override
  public Object upsertQueueDay(final QueueDayEntity queueDay,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfQueueDayEntity.insert(queueDay);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getQueueDay(final String queueDayId,
      final Continuation<? super QueueDayEntity> $completion) {
    final String _sql = "SELECT * FROM queue_days WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, queueDayId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<QueueDayEntity>() {
      @Override
      @Nullable
      public QueueDayEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfClinicId = CursorUtil.getColumnIndexOrThrow(_cursor, "clinicId");
          final int _cursorIndexOfBusinessDate = CursorUtil.getColumnIndexOrThrow(_cursor, "businessDate");
          final int _cursorIndexOfIsOpen = CursorUtil.getColumnIndexOrThrow(_cursor, "isOpen");
          final int _cursorIndexOfResetStrategy = CursorUtil.getColumnIndexOrThrow(_cursor, "resetStrategy");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final QueueDayEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpClinicId;
            _tmpClinicId = _cursor.getString(_cursorIndexOfClinicId);
            final String _tmpBusinessDate;
            _tmpBusinessDate = _cursor.getString(_cursorIndexOfBusinessDate);
            final boolean _tmpIsOpen;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsOpen);
            _tmpIsOpen = _tmp != 0;
            final String _tmpResetStrategy;
            _tmpResetStrategy = _cursor.getString(_cursorIndexOfResetStrategy);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new QueueDayEntity(_tmpId,_tmpClinicId,_tmpBusinessDate,_tmpIsOpen,_tmpResetStrategy,_tmpCreatedAt,_tmpUpdatedAt);
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

  @Override
  public Flow<QueueDayEntity> observeQueueDay(final String queueDayId) {
    final String _sql = "SELECT * FROM queue_days WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, queueDayId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"queue_days"}, new Callable<QueueDayEntity>() {
      @Override
      @Nullable
      public QueueDayEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfClinicId = CursorUtil.getColumnIndexOrThrow(_cursor, "clinicId");
          final int _cursorIndexOfBusinessDate = CursorUtil.getColumnIndexOrThrow(_cursor, "businessDate");
          final int _cursorIndexOfIsOpen = CursorUtil.getColumnIndexOrThrow(_cursor, "isOpen");
          final int _cursorIndexOfResetStrategy = CursorUtil.getColumnIndexOrThrow(_cursor, "resetStrategy");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final QueueDayEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpClinicId;
            _tmpClinicId = _cursor.getString(_cursorIndexOfClinicId);
            final String _tmpBusinessDate;
            _tmpBusinessDate = _cursor.getString(_cursorIndexOfBusinessDate);
            final boolean _tmpIsOpen;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsOpen);
            _tmpIsOpen = _tmp != 0;
            final String _tmpResetStrategy;
            _tmpResetStrategy = _cursor.getString(_cursorIndexOfResetStrategy);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new QueueDayEntity(_tmpId,_tmpClinicId,_tmpBusinessDate,_tmpIsOpen,_tmpResetStrategy,_tmpCreatedAt,_tmpUpdatedAt);
          } else {
            _result = null;
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
  public Object getQueueDayByDate(final String clinicId, final String businessDate,
      final Continuation<? super QueueDayEntity> $completion) {
    final String _sql = "SELECT * FROM queue_days WHERE clinicId = ? AND businessDate = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, clinicId);
    _argIndex = 2;
    _statement.bindString(_argIndex, businessDate);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<QueueDayEntity>() {
      @Override
      @Nullable
      public QueueDayEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfClinicId = CursorUtil.getColumnIndexOrThrow(_cursor, "clinicId");
          final int _cursorIndexOfBusinessDate = CursorUtil.getColumnIndexOrThrow(_cursor, "businessDate");
          final int _cursorIndexOfIsOpen = CursorUtil.getColumnIndexOrThrow(_cursor, "isOpen");
          final int _cursorIndexOfResetStrategy = CursorUtil.getColumnIndexOrThrow(_cursor, "resetStrategy");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final QueueDayEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpClinicId;
            _tmpClinicId = _cursor.getString(_cursorIndexOfClinicId);
            final String _tmpBusinessDate;
            _tmpBusinessDate = _cursor.getString(_cursorIndexOfBusinessDate);
            final boolean _tmpIsOpen;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsOpen);
            _tmpIsOpen = _tmp != 0;
            final String _tmpResetStrategy;
            _tmpResetStrategy = _cursor.getString(_cursorIndexOfResetStrategy);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new QueueDayEntity(_tmpId,_tmpClinicId,_tmpBusinessDate,_tmpIsOpen,_tmpResetStrategy,_tmpCreatedAt,_tmpUpdatedAt);
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
