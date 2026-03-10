package com.cuecall.app.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.cuecall.app.data.local.entity.TokenEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
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
public final class TokenDao_Impl implements TokenDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TokenEntity> __insertionAdapterOfTokenEntity;

  private final SharedSQLiteStatement __preparedStmtOfUpdateStatus;

  private final SharedSQLiteStatement __preparedStmtOfUpdateCalledAt;

  private final SharedSQLiteStatement __preparedStmtOfUpdateCompletedAt;

  private final SharedSQLiteStatement __preparedStmtOfUpdateSkippedAt;

  private final SharedSQLiteStatement __preparedStmtOfUpdateRecalledAt;

  public TokenDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTokenEntity = new EntityInsertionAdapter<TokenEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `tokens` (`id`,`clinicId`,`queueDayId`,`serviceId`,`counterId`,`tokenPrefix`,`tokenNumber`,`displayNumber`,`status`,`issuedAt`,`calledAt`,`completedAt`,`skippedAt`,`recalledAt`,`cancelledAt`,`notes`,`createdByDeviceId`,`updatedByDeviceId`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TokenEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getClinicId());
        statement.bindString(3, entity.getQueueDayId());
        statement.bindString(4, entity.getServiceId());
        if (entity.getCounterId() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getCounterId());
        }
        statement.bindString(6, entity.getTokenPrefix());
        statement.bindLong(7, entity.getTokenNumber());
        statement.bindString(8, entity.getDisplayNumber());
        statement.bindString(9, entity.getStatus());
        statement.bindLong(10, entity.getIssuedAt());
        if (entity.getCalledAt() == null) {
          statement.bindNull(11);
        } else {
          statement.bindLong(11, entity.getCalledAt());
        }
        if (entity.getCompletedAt() == null) {
          statement.bindNull(12);
        } else {
          statement.bindLong(12, entity.getCompletedAt());
        }
        if (entity.getSkippedAt() == null) {
          statement.bindNull(13);
        } else {
          statement.bindLong(13, entity.getSkippedAt());
        }
        if (entity.getRecalledAt() == null) {
          statement.bindNull(14);
        } else {
          statement.bindLong(14, entity.getRecalledAt());
        }
        if (entity.getCancelledAt() == null) {
          statement.bindNull(15);
        } else {
          statement.bindLong(15, entity.getCancelledAt());
        }
        statement.bindString(16, entity.getNotes());
        statement.bindString(17, entity.getCreatedByDeviceId());
        statement.bindString(18, entity.getUpdatedByDeviceId());
      }
    };
    this.__preparedStmtOfUpdateStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "\n"
                + "        UPDATE tokens \n"
                + "        SET status = ?, updatedByDeviceId = ?\n"
                + "        WHERE id = ?\n"
                + "    ";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateCalledAt = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "\n"
                + "        UPDATE tokens \n"
                + "        SET status = ?, calledAt = ?, updatedByDeviceId = ?\n"
                + "        WHERE id = ?\n"
                + "    ";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateCompletedAt = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "\n"
                + "        UPDATE tokens \n"
                + "        SET status = ?, completedAt = ?, updatedByDeviceId = ?\n"
                + "        WHERE id = ?\n"
                + "    ";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateSkippedAt = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "\n"
                + "        UPDATE tokens \n"
                + "        SET status = ?, skippedAt = ?, updatedByDeviceId = ?\n"
                + "        WHERE id = ?\n"
                + "    ";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateRecalledAt = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "\n"
                + "        UPDATE tokens \n"
                + "        SET status = ?, recalledAt = ?, updatedByDeviceId = ?\n"
                + "        WHERE id = ?\n"
                + "    ";
        return _query;
      }
    };
  }

  @Override
  public Object upsertToken(final TokenEntity token, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfTokenEntity.insert(token);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateStatus(final String tokenId, final String status,
      final String updatedByDeviceId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateStatus.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, status);
        _argIndex = 2;
        _stmt.bindString(_argIndex, updatedByDeviceId);
        _argIndex = 3;
        _stmt.bindString(_argIndex, tokenId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateCalledAt(final String tokenId, final String status, final long timestamp,
      final String deviceId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateCalledAt.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, status);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, timestamp);
        _argIndex = 3;
        _stmt.bindString(_argIndex, deviceId);
        _argIndex = 4;
        _stmt.bindString(_argIndex, tokenId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateCalledAt.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateCompletedAt(final String tokenId, final String status, final long timestamp,
      final String deviceId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateCompletedAt.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, status);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, timestamp);
        _argIndex = 3;
        _stmt.bindString(_argIndex, deviceId);
        _argIndex = 4;
        _stmt.bindString(_argIndex, tokenId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateCompletedAt.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateSkippedAt(final String tokenId, final String status, final long timestamp,
      final String deviceId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateSkippedAt.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, status);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, timestamp);
        _argIndex = 3;
        _stmt.bindString(_argIndex, deviceId);
        _argIndex = 4;
        _stmt.bindString(_argIndex, tokenId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateSkippedAt.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateRecalledAt(final String tokenId, final String status, final long timestamp,
      final String deviceId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateRecalledAt.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, status);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, timestamp);
        _argIndex = 3;
        _stmt.bindString(_argIndex, deviceId);
        _argIndex = 4;
        _stmt.bindString(_argIndex, tokenId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateRecalledAt.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<TokenEntity>> observeTokensByQueueDay(final String queueDayId) {
    final String _sql = "SELECT * FROM tokens WHERE queueDayId = ? ORDER BY issuedAt ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, queueDayId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"tokens"}, new Callable<List<TokenEntity>>() {
      @Override
      @NonNull
      public List<TokenEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfClinicId = CursorUtil.getColumnIndexOrThrow(_cursor, "clinicId");
          final int _cursorIndexOfQueueDayId = CursorUtil.getColumnIndexOrThrow(_cursor, "queueDayId");
          final int _cursorIndexOfServiceId = CursorUtil.getColumnIndexOrThrow(_cursor, "serviceId");
          final int _cursorIndexOfCounterId = CursorUtil.getColumnIndexOrThrow(_cursor, "counterId");
          final int _cursorIndexOfTokenPrefix = CursorUtil.getColumnIndexOrThrow(_cursor, "tokenPrefix");
          final int _cursorIndexOfTokenNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "tokenNumber");
          final int _cursorIndexOfDisplayNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "displayNumber");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfIssuedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "issuedAt");
          final int _cursorIndexOfCalledAt = CursorUtil.getColumnIndexOrThrow(_cursor, "calledAt");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfSkippedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "skippedAt");
          final int _cursorIndexOfRecalledAt = CursorUtil.getColumnIndexOrThrow(_cursor, "recalledAt");
          final int _cursorIndexOfCancelledAt = CursorUtil.getColumnIndexOrThrow(_cursor, "cancelledAt");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedByDeviceId = CursorUtil.getColumnIndexOrThrow(_cursor, "createdByDeviceId");
          final int _cursorIndexOfUpdatedByDeviceId = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedByDeviceId");
          final List<TokenEntity> _result = new ArrayList<TokenEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TokenEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpClinicId;
            _tmpClinicId = _cursor.getString(_cursorIndexOfClinicId);
            final String _tmpQueueDayId;
            _tmpQueueDayId = _cursor.getString(_cursorIndexOfQueueDayId);
            final String _tmpServiceId;
            _tmpServiceId = _cursor.getString(_cursorIndexOfServiceId);
            final String _tmpCounterId;
            if (_cursor.isNull(_cursorIndexOfCounterId)) {
              _tmpCounterId = null;
            } else {
              _tmpCounterId = _cursor.getString(_cursorIndexOfCounterId);
            }
            final String _tmpTokenPrefix;
            _tmpTokenPrefix = _cursor.getString(_cursorIndexOfTokenPrefix);
            final int _tmpTokenNumber;
            _tmpTokenNumber = _cursor.getInt(_cursorIndexOfTokenNumber);
            final String _tmpDisplayNumber;
            _tmpDisplayNumber = _cursor.getString(_cursorIndexOfDisplayNumber);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final long _tmpIssuedAt;
            _tmpIssuedAt = _cursor.getLong(_cursorIndexOfIssuedAt);
            final Long _tmpCalledAt;
            if (_cursor.isNull(_cursorIndexOfCalledAt)) {
              _tmpCalledAt = null;
            } else {
              _tmpCalledAt = _cursor.getLong(_cursorIndexOfCalledAt);
            }
            final Long _tmpCompletedAt;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmpCompletedAt = null;
            } else {
              _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            final Long _tmpSkippedAt;
            if (_cursor.isNull(_cursorIndexOfSkippedAt)) {
              _tmpSkippedAt = null;
            } else {
              _tmpSkippedAt = _cursor.getLong(_cursorIndexOfSkippedAt);
            }
            final Long _tmpRecalledAt;
            if (_cursor.isNull(_cursorIndexOfRecalledAt)) {
              _tmpRecalledAt = null;
            } else {
              _tmpRecalledAt = _cursor.getLong(_cursorIndexOfRecalledAt);
            }
            final Long _tmpCancelledAt;
            if (_cursor.isNull(_cursorIndexOfCancelledAt)) {
              _tmpCancelledAt = null;
            } else {
              _tmpCancelledAt = _cursor.getLong(_cursorIndexOfCancelledAt);
            }
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final String _tmpCreatedByDeviceId;
            _tmpCreatedByDeviceId = _cursor.getString(_cursorIndexOfCreatedByDeviceId);
            final String _tmpUpdatedByDeviceId;
            _tmpUpdatedByDeviceId = _cursor.getString(_cursorIndexOfUpdatedByDeviceId);
            _item = new TokenEntity(_tmpId,_tmpClinicId,_tmpQueueDayId,_tmpServiceId,_tmpCounterId,_tmpTokenPrefix,_tmpTokenNumber,_tmpDisplayNumber,_tmpStatus,_tmpIssuedAt,_tmpCalledAt,_tmpCompletedAt,_tmpSkippedAt,_tmpRecalledAt,_tmpCancelledAt,_tmpNotes,_tmpCreatedByDeviceId,_tmpUpdatedByDeviceId);
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
  public Flow<List<TokenEntity>> observeWaitingTokens(final String queueDayId,
      final String serviceId) {
    final String _sql = "\n"
            + "        SELECT * FROM tokens \n"
            + "        WHERE queueDayId = ? \n"
            + "        AND status = 'WAITING'\n"
            + "        AND (? IS NULL OR serviceId = ?)\n"
            + "        ORDER BY tokenNumber ASC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindString(_argIndex, queueDayId);
    _argIndex = 2;
    if (serviceId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, serviceId);
    }
    _argIndex = 3;
    if (serviceId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, serviceId);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"tokens"}, new Callable<List<TokenEntity>>() {
      @Override
      @NonNull
      public List<TokenEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfClinicId = CursorUtil.getColumnIndexOrThrow(_cursor, "clinicId");
          final int _cursorIndexOfQueueDayId = CursorUtil.getColumnIndexOrThrow(_cursor, "queueDayId");
          final int _cursorIndexOfServiceId = CursorUtil.getColumnIndexOrThrow(_cursor, "serviceId");
          final int _cursorIndexOfCounterId = CursorUtil.getColumnIndexOrThrow(_cursor, "counterId");
          final int _cursorIndexOfTokenPrefix = CursorUtil.getColumnIndexOrThrow(_cursor, "tokenPrefix");
          final int _cursorIndexOfTokenNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "tokenNumber");
          final int _cursorIndexOfDisplayNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "displayNumber");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfIssuedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "issuedAt");
          final int _cursorIndexOfCalledAt = CursorUtil.getColumnIndexOrThrow(_cursor, "calledAt");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfSkippedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "skippedAt");
          final int _cursorIndexOfRecalledAt = CursorUtil.getColumnIndexOrThrow(_cursor, "recalledAt");
          final int _cursorIndexOfCancelledAt = CursorUtil.getColumnIndexOrThrow(_cursor, "cancelledAt");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedByDeviceId = CursorUtil.getColumnIndexOrThrow(_cursor, "createdByDeviceId");
          final int _cursorIndexOfUpdatedByDeviceId = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedByDeviceId");
          final List<TokenEntity> _result = new ArrayList<TokenEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TokenEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpClinicId;
            _tmpClinicId = _cursor.getString(_cursorIndexOfClinicId);
            final String _tmpQueueDayId;
            _tmpQueueDayId = _cursor.getString(_cursorIndexOfQueueDayId);
            final String _tmpServiceId;
            _tmpServiceId = _cursor.getString(_cursorIndexOfServiceId);
            final String _tmpCounterId;
            if (_cursor.isNull(_cursorIndexOfCounterId)) {
              _tmpCounterId = null;
            } else {
              _tmpCounterId = _cursor.getString(_cursorIndexOfCounterId);
            }
            final String _tmpTokenPrefix;
            _tmpTokenPrefix = _cursor.getString(_cursorIndexOfTokenPrefix);
            final int _tmpTokenNumber;
            _tmpTokenNumber = _cursor.getInt(_cursorIndexOfTokenNumber);
            final String _tmpDisplayNumber;
            _tmpDisplayNumber = _cursor.getString(_cursorIndexOfDisplayNumber);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final long _tmpIssuedAt;
            _tmpIssuedAt = _cursor.getLong(_cursorIndexOfIssuedAt);
            final Long _tmpCalledAt;
            if (_cursor.isNull(_cursorIndexOfCalledAt)) {
              _tmpCalledAt = null;
            } else {
              _tmpCalledAt = _cursor.getLong(_cursorIndexOfCalledAt);
            }
            final Long _tmpCompletedAt;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmpCompletedAt = null;
            } else {
              _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            final Long _tmpSkippedAt;
            if (_cursor.isNull(_cursorIndexOfSkippedAt)) {
              _tmpSkippedAt = null;
            } else {
              _tmpSkippedAt = _cursor.getLong(_cursorIndexOfSkippedAt);
            }
            final Long _tmpRecalledAt;
            if (_cursor.isNull(_cursorIndexOfRecalledAt)) {
              _tmpRecalledAt = null;
            } else {
              _tmpRecalledAt = _cursor.getLong(_cursorIndexOfRecalledAt);
            }
            final Long _tmpCancelledAt;
            if (_cursor.isNull(_cursorIndexOfCancelledAt)) {
              _tmpCancelledAt = null;
            } else {
              _tmpCancelledAt = _cursor.getLong(_cursorIndexOfCancelledAt);
            }
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final String _tmpCreatedByDeviceId;
            _tmpCreatedByDeviceId = _cursor.getString(_cursorIndexOfCreatedByDeviceId);
            final String _tmpUpdatedByDeviceId;
            _tmpUpdatedByDeviceId = _cursor.getString(_cursorIndexOfUpdatedByDeviceId);
            _item = new TokenEntity(_tmpId,_tmpClinicId,_tmpQueueDayId,_tmpServiceId,_tmpCounterId,_tmpTokenPrefix,_tmpTokenNumber,_tmpDisplayNumber,_tmpStatus,_tmpIssuedAt,_tmpCalledAt,_tmpCompletedAt,_tmpSkippedAt,_tmpRecalledAt,_tmpCancelledAt,_tmpNotes,_tmpCreatedByDeviceId,_tmpUpdatedByDeviceId);
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
  public Flow<TokenEntity> observeCalledToken(final String queueDayId, final String serviceId) {
    final String _sql = "\n"
            + "        SELECT * FROM tokens \n"
            + "        WHERE queueDayId = ? \n"
            + "        AND serviceId = ? \n"
            + "        AND status IN ('CALLED', 'RECALLED')\n"
            + "        ORDER BY calledAt DESC \n"
            + "        LIMIT 1\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, queueDayId);
    _argIndex = 2;
    _statement.bindString(_argIndex, serviceId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"tokens"}, new Callable<TokenEntity>() {
      @Override
      @Nullable
      public TokenEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfClinicId = CursorUtil.getColumnIndexOrThrow(_cursor, "clinicId");
          final int _cursorIndexOfQueueDayId = CursorUtil.getColumnIndexOrThrow(_cursor, "queueDayId");
          final int _cursorIndexOfServiceId = CursorUtil.getColumnIndexOrThrow(_cursor, "serviceId");
          final int _cursorIndexOfCounterId = CursorUtil.getColumnIndexOrThrow(_cursor, "counterId");
          final int _cursorIndexOfTokenPrefix = CursorUtil.getColumnIndexOrThrow(_cursor, "tokenPrefix");
          final int _cursorIndexOfTokenNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "tokenNumber");
          final int _cursorIndexOfDisplayNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "displayNumber");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfIssuedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "issuedAt");
          final int _cursorIndexOfCalledAt = CursorUtil.getColumnIndexOrThrow(_cursor, "calledAt");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfSkippedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "skippedAt");
          final int _cursorIndexOfRecalledAt = CursorUtil.getColumnIndexOrThrow(_cursor, "recalledAt");
          final int _cursorIndexOfCancelledAt = CursorUtil.getColumnIndexOrThrow(_cursor, "cancelledAt");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedByDeviceId = CursorUtil.getColumnIndexOrThrow(_cursor, "createdByDeviceId");
          final int _cursorIndexOfUpdatedByDeviceId = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedByDeviceId");
          final TokenEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpClinicId;
            _tmpClinicId = _cursor.getString(_cursorIndexOfClinicId);
            final String _tmpQueueDayId;
            _tmpQueueDayId = _cursor.getString(_cursorIndexOfQueueDayId);
            final String _tmpServiceId;
            _tmpServiceId = _cursor.getString(_cursorIndexOfServiceId);
            final String _tmpCounterId;
            if (_cursor.isNull(_cursorIndexOfCounterId)) {
              _tmpCounterId = null;
            } else {
              _tmpCounterId = _cursor.getString(_cursorIndexOfCounterId);
            }
            final String _tmpTokenPrefix;
            _tmpTokenPrefix = _cursor.getString(_cursorIndexOfTokenPrefix);
            final int _tmpTokenNumber;
            _tmpTokenNumber = _cursor.getInt(_cursorIndexOfTokenNumber);
            final String _tmpDisplayNumber;
            _tmpDisplayNumber = _cursor.getString(_cursorIndexOfDisplayNumber);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final long _tmpIssuedAt;
            _tmpIssuedAt = _cursor.getLong(_cursorIndexOfIssuedAt);
            final Long _tmpCalledAt;
            if (_cursor.isNull(_cursorIndexOfCalledAt)) {
              _tmpCalledAt = null;
            } else {
              _tmpCalledAt = _cursor.getLong(_cursorIndexOfCalledAt);
            }
            final Long _tmpCompletedAt;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmpCompletedAt = null;
            } else {
              _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            final Long _tmpSkippedAt;
            if (_cursor.isNull(_cursorIndexOfSkippedAt)) {
              _tmpSkippedAt = null;
            } else {
              _tmpSkippedAt = _cursor.getLong(_cursorIndexOfSkippedAt);
            }
            final Long _tmpRecalledAt;
            if (_cursor.isNull(_cursorIndexOfRecalledAt)) {
              _tmpRecalledAt = null;
            } else {
              _tmpRecalledAt = _cursor.getLong(_cursorIndexOfRecalledAt);
            }
            final Long _tmpCancelledAt;
            if (_cursor.isNull(_cursorIndexOfCancelledAt)) {
              _tmpCancelledAt = null;
            } else {
              _tmpCancelledAt = _cursor.getLong(_cursorIndexOfCancelledAt);
            }
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final String _tmpCreatedByDeviceId;
            _tmpCreatedByDeviceId = _cursor.getString(_cursorIndexOfCreatedByDeviceId);
            final String _tmpUpdatedByDeviceId;
            _tmpUpdatedByDeviceId = _cursor.getString(_cursorIndexOfUpdatedByDeviceId);
            _result = new TokenEntity(_tmpId,_tmpClinicId,_tmpQueueDayId,_tmpServiceId,_tmpCounterId,_tmpTokenPrefix,_tmpTokenNumber,_tmpDisplayNumber,_tmpStatus,_tmpIssuedAt,_tmpCalledAt,_tmpCompletedAt,_tmpSkippedAt,_tmpRecalledAt,_tmpCancelledAt,_tmpNotes,_tmpCreatedByDeviceId,_tmpUpdatedByDeviceId);
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
  public Object getToken(final String tokenId,
      final Continuation<? super TokenEntity> $completion) {
    final String _sql = "SELECT * FROM tokens WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, tokenId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<TokenEntity>() {
      @Override
      @Nullable
      public TokenEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfClinicId = CursorUtil.getColumnIndexOrThrow(_cursor, "clinicId");
          final int _cursorIndexOfQueueDayId = CursorUtil.getColumnIndexOrThrow(_cursor, "queueDayId");
          final int _cursorIndexOfServiceId = CursorUtil.getColumnIndexOrThrow(_cursor, "serviceId");
          final int _cursorIndexOfCounterId = CursorUtil.getColumnIndexOrThrow(_cursor, "counterId");
          final int _cursorIndexOfTokenPrefix = CursorUtil.getColumnIndexOrThrow(_cursor, "tokenPrefix");
          final int _cursorIndexOfTokenNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "tokenNumber");
          final int _cursorIndexOfDisplayNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "displayNumber");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfIssuedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "issuedAt");
          final int _cursorIndexOfCalledAt = CursorUtil.getColumnIndexOrThrow(_cursor, "calledAt");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfSkippedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "skippedAt");
          final int _cursorIndexOfRecalledAt = CursorUtil.getColumnIndexOrThrow(_cursor, "recalledAt");
          final int _cursorIndexOfCancelledAt = CursorUtil.getColumnIndexOrThrow(_cursor, "cancelledAt");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedByDeviceId = CursorUtil.getColumnIndexOrThrow(_cursor, "createdByDeviceId");
          final int _cursorIndexOfUpdatedByDeviceId = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedByDeviceId");
          final TokenEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpClinicId;
            _tmpClinicId = _cursor.getString(_cursorIndexOfClinicId);
            final String _tmpQueueDayId;
            _tmpQueueDayId = _cursor.getString(_cursorIndexOfQueueDayId);
            final String _tmpServiceId;
            _tmpServiceId = _cursor.getString(_cursorIndexOfServiceId);
            final String _tmpCounterId;
            if (_cursor.isNull(_cursorIndexOfCounterId)) {
              _tmpCounterId = null;
            } else {
              _tmpCounterId = _cursor.getString(_cursorIndexOfCounterId);
            }
            final String _tmpTokenPrefix;
            _tmpTokenPrefix = _cursor.getString(_cursorIndexOfTokenPrefix);
            final int _tmpTokenNumber;
            _tmpTokenNumber = _cursor.getInt(_cursorIndexOfTokenNumber);
            final String _tmpDisplayNumber;
            _tmpDisplayNumber = _cursor.getString(_cursorIndexOfDisplayNumber);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final long _tmpIssuedAt;
            _tmpIssuedAt = _cursor.getLong(_cursorIndexOfIssuedAt);
            final Long _tmpCalledAt;
            if (_cursor.isNull(_cursorIndexOfCalledAt)) {
              _tmpCalledAt = null;
            } else {
              _tmpCalledAt = _cursor.getLong(_cursorIndexOfCalledAt);
            }
            final Long _tmpCompletedAt;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmpCompletedAt = null;
            } else {
              _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            final Long _tmpSkippedAt;
            if (_cursor.isNull(_cursorIndexOfSkippedAt)) {
              _tmpSkippedAt = null;
            } else {
              _tmpSkippedAt = _cursor.getLong(_cursorIndexOfSkippedAt);
            }
            final Long _tmpRecalledAt;
            if (_cursor.isNull(_cursorIndexOfRecalledAt)) {
              _tmpRecalledAt = null;
            } else {
              _tmpRecalledAt = _cursor.getLong(_cursorIndexOfRecalledAt);
            }
            final Long _tmpCancelledAt;
            if (_cursor.isNull(_cursorIndexOfCancelledAt)) {
              _tmpCancelledAt = null;
            } else {
              _tmpCancelledAt = _cursor.getLong(_cursorIndexOfCancelledAt);
            }
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final String _tmpCreatedByDeviceId;
            _tmpCreatedByDeviceId = _cursor.getString(_cursorIndexOfCreatedByDeviceId);
            final String _tmpUpdatedByDeviceId;
            _tmpUpdatedByDeviceId = _cursor.getString(_cursorIndexOfUpdatedByDeviceId);
            _result = new TokenEntity(_tmpId,_tmpClinicId,_tmpQueueDayId,_tmpServiceId,_tmpCounterId,_tmpTokenPrefix,_tmpTokenNumber,_tmpDisplayNumber,_tmpStatus,_tmpIssuedAt,_tmpCalledAt,_tmpCompletedAt,_tmpSkippedAt,_tmpRecalledAt,_tmpCancelledAt,_tmpNotes,_tmpCreatedByDeviceId,_tmpUpdatedByDeviceId);
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
  public Object getTokensForDay(final String queueDayId,
      final Continuation<? super List<TokenEntity>> $completion) {
    final String _sql = "SELECT * FROM tokens WHERE queueDayId = ? ORDER BY issuedAt ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, queueDayId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<TokenEntity>>() {
      @Override
      @NonNull
      public List<TokenEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfClinicId = CursorUtil.getColumnIndexOrThrow(_cursor, "clinicId");
          final int _cursorIndexOfQueueDayId = CursorUtil.getColumnIndexOrThrow(_cursor, "queueDayId");
          final int _cursorIndexOfServiceId = CursorUtil.getColumnIndexOrThrow(_cursor, "serviceId");
          final int _cursorIndexOfCounterId = CursorUtil.getColumnIndexOrThrow(_cursor, "counterId");
          final int _cursorIndexOfTokenPrefix = CursorUtil.getColumnIndexOrThrow(_cursor, "tokenPrefix");
          final int _cursorIndexOfTokenNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "tokenNumber");
          final int _cursorIndexOfDisplayNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "displayNumber");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfIssuedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "issuedAt");
          final int _cursorIndexOfCalledAt = CursorUtil.getColumnIndexOrThrow(_cursor, "calledAt");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfSkippedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "skippedAt");
          final int _cursorIndexOfRecalledAt = CursorUtil.getColumnIndexOrThrow(_cursor, "recalledAt");
          final int _cursorIndexOfCancelledAt = CursorUtil.getColumnIndexOrThrow(_cursor, "cancelledAt");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedByDeviceId = CursorUtil.getColumnIndexOrThrow(_cursor, "createdByDeviceId");
          final int _cursorIndexOfUpdatedByDeviceId = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedByDeviceId");
          final List<TokenEntity> _result = new ArrayList<TokenEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TokenEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpClinicId;
            _tmpClinicId = _cursor.getString(_cursorIndexOfClinicId);
            final String _tmpQueueDayId;
            _tmpQueueDayId = _cursor.getString(_cursorIndexOfQueueDayId);
            final String _tmpServiceId;
            _tmpServiceId = _cursor.getString(_cursorIndexOfServiceId);
            final String _tmpCounterId;
            if (_cursor.isNull(_cursorIndexOfCounterId)) {
              _tmpCounterId = null;
            } else {
              _tmpCounterId = _cursor.getString(_cursorIndexOfCounterId);
            }
            final String _tmpTokenPrefix;
            _tmpTokenPrefix = _cursor.getString(_cursorIndexOfTokenPrefix);
            final int _tmpTokenNumber;
            _tmpTokenNumber = _cursor.getInt(_cursorIndexOfTokenNumber);
            final String _tmpDisplayNumber;
            _tmpDisplayNumber = _cursor.getString(_cursorIndexOfDisplayNumber);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final long _tmpIssuedAt;
            _tmpIssuedAt = _cursor.getLong(_cursorIndexOfIssuedAt);
            final Long _tmpCalledAt;
            if (_cursor.isNull(_cursorIndexOfCalledAt)) {
              _tmpCalledAt = null;
            } else {
              _tmpCalledAt = _cursor.getLong(_cursorIndexOfCalledAt);
            }
            final Long _tmpCompletedAt;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmpCompletedAt = null;
            } else {
              _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            final Long _tmpSkippedAt;
            if (_cursor.isNull(_cursorIndexOfSkippedAt)) {
              _tmpSkippedAt = null;
            } else {
              _tmpSkippedAt = _cursor.getLong(_cursorIndexOfSkippedAt);
            }
            final Long _tmpRecalledAt;
            if (_cursor.isNull(_cursorIndexOfRecalledAt)) {
              _tmpRecalledAt = null;
            } else {
              _tmpRecalledAt = _cursor.getLong(_cursorIndexOfRecalledAt);
            }
            final Long _tmpCancelledAt;
            if (_cursor.isNull(_cursorIndexOfCancelledAt)) {
              _tmpCancelledAt = null;
            } else {
              _tmpCancelledAt = _cursor.getLong(_cursorIndexOfCancelledAt);
            }
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final String _tmpCreatedByDeviceId;
            _tmpCreatedByDeviceId = _cursor.getString(_cursorIndexOfCreatedByDeviceId);
            final String _tmpUpdatedByDeviceId;
            _tmpUpdatedByDeviceId = _cursor.getString(_cursorIndexOfUpdatedByDeviceId);
            _item = new TokenEntity(_tmpId,_tmpClinicId,_tmpQueueDayId,_tmpServiceId,_tmpCounterId,_tmpTokenPrefix,_tmpTokenNumber,_tmpDisplayNumber,_tmpStatus,_tmpIssuedAt,_tmpCalledAt,_tmpCompletedAt,_tmpSkippedAt,_tmpRecalledAt,_tmpCancelledAt,_tmpNotes,_tmpCreatedByDeviceId,_tmpUpdatedByDeviceId);
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
