package com.example.mahilashaktiunnati.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.mahilashaktiunnati.data.local.Converters;
import com.example.mahilashaktiunnati.data.local.SavingsStatus;
import com.example.mahilashaktiunnati.data.local.entity.SavingsEntity;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.IllegalStateException;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class SavingsDao_Impl implements SavingsDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SavingsEntity> __insertionAdapterOfSavingsEntity;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<SavingsEntity> __deletionAdapterOfSavingsEntity;

  private final EntityDeletionOrUpdateAdapter<SavingsEntity> __updateAdapterOfSavingsEntity;

  public SavingsDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSavingsEntity = new EntityInsertionAdapter<SavingsEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `savings` (`savingsId`,`memberId`,`amountPaid`,`paymentDate`,`status`) VALUES (?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SavingsEntity entity) {
        statement.bindString(1, entity.getSavingsId());
        statement.bindString(2, entity.getMemberId());
        statement.bindDouble(3, entity.getAmountPaid());
        final Long _tmp = __converters.dateToTimestamp(entity.getPaymentDate());
        if (_tmp == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, _tmp);
        }
        final String _tmp_1 = __converters.fromSavingsStatus(entity.getStatus());
        statement.bindString(5, _tmp_1);
      }
    };
    this.__deletionAdapterOfSavingsEntity = new EntityDeletionOrUpdateAdapter<SavingsEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `savings` WHERE `savingsId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SavingsEntity entity) {
        statement.bindString(1, entity.getSavingsId());
      }
    };
    this.__updateAdapterOfSavingsEntity = new EntityDeletionOrUpdateAdapter<SavingsEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `savings` SET `savingsId` = ?,`memberId` = ?,`amountPaid` = ?,`paymentDate` = ?,`status` = ? WHERE `savingsId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SavingsEntity entity) {
        statement.bindString(1, entity.getSavingsId());
        statement.bindString(2, entity.getMemberId());
        statement.bindDouble(3, entity.getAmountPaid());
        final Long _tmp = __converters.dateToTimestamp(entity.getPaymentDate());
        if (_tmp == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, _tmp);
        }
        final String _tmp_1 = __converters.fromSavingsStatus(entity.getStatus());
        statement.bindString(5, _tmp_1);
        statement.bindString(6, entity.getSavingsId());
      }
    };
  }

  @Override
  public Object insertSavings(final SavingsEntity savings,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfSavingsEntity.insert(savings);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteSavings(final SavingsEntity savings,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfSavingsEntity.handle(savings);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateSavings(final SavingsEntity savings,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfSavingsEntity.handle(savings);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<SavingsEntity>> getAllSavings() {
    final String _sql = "SELECT * FROM savings ORDER BY paymentDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"savings"}, new Callable<List<SavingsEntity>>() {
      @Override
      @NonNull
      public List<SavingsEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfSavingsId = CursorUtil.getColumnIndexOrThrow(_cursor, "savingsId");
          final int _cursorIndexOfMemberId = CursorUtil.getColumnIndexOrThrow(_cursor, "memberId");
          final int _cursorIndexOfAmountPaid = CursorUtil.getColumnIndexOrThrow(_cursor, "amountPaid");
          final int _cursorIndexOfPaymentDate = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentDate");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final List<SavingsEntity> _result = new ArrayList<SavingsEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SavingsEntity _item;
            final String _tmpSavingsId;
            _tmpSavingsId = _cursor.getString(_cursorIndexOfSavingsId);
            final String _tmpMemberId;
            _tmpMemberId = _cursor.getString(_cursorIndexOfMemberId);
            final double _tmpAmountPaid;
            _tmpAmountPaid = _cursor.getDouble(_cursorIndexOfAmountPaid);
            final Date _tmpPaymentDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfPaymentDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfPaymentDate);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpPaymentDate = _tmp_1;
            }
            final SavingsStatus _tmpStatus;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __converters.toSavingsStatus(_tmp_2);
            _item = new SavingsEntity(_tmpSavingsId,_tmpMemberId,_tmpAmountPaid,_tmpPaymentDate,_tmpStatus);
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
  public Object getSavingsById(final String savingsId,
      final Continuation<? super SavingsEntity> $completion) {
    final String _sql = "SELECT * FROM savings WHERE savingsId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, savingsId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<SavingsEntity>() {
      @Override
      @Nullable
      public SavingsEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfSavingsId = CursorUtil.getColumnIndexOrThrow(_cursor, "savingsId");
          final int _cursorIndexOfMemberId = CursorUtil.getColumnIndexOrThrow(_cursor, "memberId");
          final int _cursorIndexOfAmountPaid = CursorUtil.getColumnIndexOrThrow(_cursor, "amountPaid");
          final int _cursorIndexOfPaymentDate = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentDate");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final SavingsEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpSavingsId;
            _tmpSavingsId = _cursor.getString(_cursorIndexOfSavingsId);
            final String _tmpMemberId;
            _tmpMemberId = _cursor.getString(_cursorIndexOfMemberId);
            final double _tmpAmountPaid;
            _tmpAmountPaid = _cursor.getDouble(_cursorIndexOfAmountPaid);
            final Date _tmpPaymentDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfPaymentDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfPaymentDate);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpPaymentDate = _tmp_1;
            }
            final SavingsStatus _tmpStatus;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __converters.toSavingsStatus(_tmp_2);
            _result = new SavingsEntity(_tmpSavingsId,_tmpMemberId,_tmpAmountPaid,_tmpPaymentDate,_tmpStatus);
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
  public Flow<List<SavingsEntity>> getSavingsForMember(final String memberId) {
    final String _sql = "SELECT * FROM savings WHERE memberId = ? ORDER BY paymentDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, memberId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"savings"}, new Callable<List<SavingsEntity>>() {
      @Override
      @NonNull
      public List<SavingsEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfSavingsId = CursorUtil.getColumnIndexOrThrow(_cursor, "savingsId");
          final int _cursorIndexOfMemberId = CursorUtil.getColumnIndexOrThrow(_cursor, "memberId");
          final int _cursorIndexOfAmountPaid = CursorUtil.getColumnIndexOrThrow(_cursor, "amountPaid");
          final int _cursorIndexOfPaymentDate = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentDate");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final List<SavingsEntity> _result = new ArrayList<SavingsEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SavingsEntity _item;
            final String _tmpSavingsId;
            _tmpSavingsId = _cursor.getString(_cursorIndexOfSavingsId);
            final String _tmpMemberId;
            _tmpMemberId = _cursor.getString(_cursorIndexOfMemberId);
            final double _tmpAmountPaid;
            _tmpAmountPaid = _cursor.getDouble(_cursorIndexOfAmountPaid);
            final Date _tmpPaymentDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfPaymentDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfPaymentDate);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpPaymentDate = _tmp_1;
            }
            final SavingsStatus _tmpStatus;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __converters.toSavingsStatus(_tmp_2);
            _item = new SavingsEntity(_tmpSavingsId,_tmpMemberId,_tmpAmountPaid,_tmpPaymentDate,_tmpStatus);
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
  public Flow<Double> getTotalGroupSavings() {
    final String _sql = "SELECT COALESCE(SUM(amountPaid), 0.0) FROM savings WHERE status = 'PAID'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"savings"}, new Callable<Double>() {
      @Override
      @NonNull
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final double _tmp;
            _tmp = _cursor.getDouble(0);
            _result = _tmp;
          } else {
            _result = 0.0;
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
  public Flow<Double> getTotalMemberSavings(final String memberId) {
    final String _sql = "SELECT COALESCE(SUM(amountPaid), 0.0) FROM savings WHERE memberId = ? AND status = 'PAID'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, memberId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"savings"}, new Callable<Double>() {
      @Override
      @NonNull
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final double _tmp;
            _tmp = _cursor.getDouble(0);
            _result = _tmp;
          } else {
            _result = 0.0;
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
  public Flow<List<SavingsEntity>> getSavingsByStatus(final SavingsStatus status) {
    final String _sql = "SELECT * FROM savings WHERE status = ? ORDER BY paymentDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = __converters.fromSavingsStatus(status);
    _statement.bindString(_argIndex, _tmp);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"savings"}, new Callable<List<SavingsEntity>>() {
      @Override
      @NonNull
      public List<SavingsEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfSavingsId = CursorUtil.getColumnIndexOrThrow(_cursor, "savingsId");
          final int _cursorIndexOfMemberId = CursorUtil.getColumnIndexOrThrow(_cursor, "memberId");
          final int _cursorIndexOfAmountPaid = CursorUtil.getColumnIndexOrThrow(_cursor, "amountPaid");
          final int _cursorIndexOfPaymentDate = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentDate");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final List<SavingsEntity> _result = new ArrayList<SavingsEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SavingsEntity _item;
            final String _tmpSavingsId;
            _tmpSavingsId = _cursor.getString(_cursorIndexOfSavingsId);
            final String _tmpMemberId;
            _tmpMemberId = _cursor.getString(_cursorIndexOfMemberId);
            final double _tmpAmountPaid;
            _tmpAmountPaid = _cursor.getDouble(_cursorIndexOfAmountPaid);
            final Date _tmpPaymentDate;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfPaymentDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfPaymentDate);
            }
            final Date _tmp_2 = __converters.fromTimestamp(_tmp_1);
            if (_tmp_2 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpPaymentDate = _tmp_2;
            }
            final SavingsStatus _tmpStatus;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __converters.toSavingsStatus(_tmp_3);
            _item = new SavingsEntity(_tmpSavingsId,_tmpMemberId,_tmpAmountPaid,_tmpPaymentDate,_tmpStatus);
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
  public Flow<List<SavingsEntity>> getPendingSavings() {
    final String _sql = "SELECT * FROM savings WHERE status = 'PENDING' ORDER BY paymentDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"savings"}, new Callable<List<SavingsEntity>>() {
      @Override
      @NonNull
      public List<SavingsEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfSavingsId = CursorUtil.getColumnIndexOrThrow(_cursor, "savingsId");
          final int _cursorIndexOfMemberId = CursorUtil.getColumnIndexOrThrow(_cursor, "memberId");
          final int _cursorIndexOfAmountPaid = CursorUtil.getColumnIndexOrThrow(_cursor, "amountPaid");
          final int _cursorIndexOfPaymentDate = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentDate");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final List<SavingsEntity> _result = new ArrayList<SavingsEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SavingsEntity _item;
            final String _tmpSavingsId;
            _tmpSavingsId = _cursor.getString(_cursorIndexOfSavingsId);
            final String _tmpMemberId;
            _tmpMemberId = _cursor.getString(_cursorIndexOfMemberId);
            final double _tmpAmountPaid;
            _tmpAmountPaid = _cursor.getDouble(_cursorIndexOfAmountPaid);
            final Date _tmpPaymentDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfPaymentDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfPaymentDate);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpPaymentDate = _tmp_1;
            }
            final SavingsStatus _tmpStatus;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __converters.toSavingsStatus(_tmp_2);
            _item = new SavingsEntity(_tmpSavingsId,_tmpMemberId,_tmpAmountPaid,_tmpPaymentDate,_tmpStatus);
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
  public Flow<Integer> getPendingSavingsCount() {
    final String _sql = "SELECT COUNT(*) FROM savings WHERE status = 'PENDING'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"savings"}, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
