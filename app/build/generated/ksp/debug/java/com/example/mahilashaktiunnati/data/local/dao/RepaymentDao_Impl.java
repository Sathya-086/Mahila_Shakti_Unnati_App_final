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
import com.example.mahilashaktiunnati.data.local.entity.RepaymentEntity;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.IllegalStateException;
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
public final class RepaymentDao_Impl implements RepaymentDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<RepaymentEntity> __insertionAdapterOfRepaymentEntity;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<RepaymentEntity> __deletionAdapterOfRepaymentEntity;

  private final EntityDeletionOrUpdateAdapter<RepaymentEntity> __updateAdapterOfRepaymentEntity;

  public RepaymentDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRepaymentEntity = new EntityInsertionAdapter<RepaymentEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `repayments` (`repaymentId`,`loanId`,`amountPaid`,`paymentDate`,`balanceRemaining`) VALUES (?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RepaymentEntity entity) {
        statement.bindString(1, entity.getRepaymentId());
        statement.bindString(2, entity.getLoanId());
        statement.bindDouble(3, entity.getAmountPaid());
        final Long _tmp = __converters.dateToTimestamp(entity.getPaymentDate());
        if (_tmp == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, _tmp);
        }
        statement.bindDouble(5, entity.getBalanceRemaining());
      }
    };
    this.__deletionAdapterOfRepaymentEntity = new EntityDeletionOrUpdateAdapter<RepaymentEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `repayments` WHERE `repaymentId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RepaymentEntity entity) {
        statement.bindString(1, entity.getRepaymentId());
      }
    };
    this.__updateAdapterOfRepaymentEntity = new EntityDeletionOrUpdateAdapter<RepaymentEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `repayments` SET `repaymentId` = ?,`loanId` = ?,`amountPaid` = ?,`paymentDate` = ?,`balanceRemaining` = ? WHERE `repaymentId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RepaymentEntity entity) {
        statement.bindString(1, entity.getRepaymentId());
        statement.bindString(2, entity.getLoanId());
        statement.bindDouble(3, entity.getAmountPaid());
        final Long _tmp = __converters.dateToTimestamp(entity.getPaymentDate());
        if (_tmp == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, _tmp);
        }
        statement.bindDouble(5, entity.getBalanceRemaining());
        statement.bindString(6, entity.getRepaymentId());
      }
    };
  }

  @Override
  public Object insertRepayment(final RepaymentEntity repayment,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfRepaymentEntity.insert(repayment);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteRepayment(final RepaymentEntity repayment,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfRepaymentEntity.handle(repayment);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateRepayment(final RepaymentEntity repayment,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfRepaymentEntity.handle(repayment);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<RepaymentEntity>> getRepaymentsForLoan(final String loanId) {
    final String _sql = "SELECT * FROM repayments WHERE loanId = ? ORDER BY paymentDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, loanId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"repayments"}, new Callable<List<RepaymentEntity>>() {
      @Override
      @NonNull
      public List<RepaymentEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfRepaymentId = CursorUtil.getColumnIndexOrThrow(_cursor, "repaymentId");
          final int _cursorIndexOfLoanId = CursorUtil.getColumnIndexOrThrow(_cursor, "loanId");
          final int _cursorIndexOfAmountPaid = CursorUtil.getColumnIndexOrThrow(_cursor, "amountPaid");
          final int _cursorIndexOfPaymentDate = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentDate");
          final int _cursorIndexOfBalanceRemaining = CursorUtil.getColumnIndexOrThrow(_cursor, "balanceRemaining");
          final List<RepaymentEntity> _result = new ArrayList<RepaymentEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final RepaymentEntity _item;
            final String _tmpRepaymentId;
            _tmpRepaymentId = _cursor.getString(_cursorIndexOfRepaymentId);
            final String _tmpLoanId;
            _tmpLoanId = _cursor.getString(_cursorIndexOfLoanId);
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
            final double _tmpBalanceRemaining;
            _tmpBalanceRemaining = _cursor.getDouble(_cursorIndexOfBalanceRemaining);
            _item = new RepaymentEntity(_tmpRepaymentId,_tmpLoanId,_tmpAmountPaid,_tmpPaymentDate,_tmpBalanceRemaining);
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
  public Flow<Double> getTotalRepaidForLoan(final String loanId) {
    final String _sql = "SELECT COALESCE(SUM(amountPaid), 0.0) FROM repayments WHERE loanId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, loanId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"repayments"}, new Callable<Double>() {
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
  public Flow<List<RepaymentEntity>> getAllRepayments() {
    final String _sql = "SELECT * FROM repayments ORDER BY paymentDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"repayments"}, new Callable<List<RepaymentEntity>>() {
      @Override
      @NonNull
      public List<RepaymentEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfRepaymentId = CursorUtil.getColumnIndexOrThrow(_cursor, "repaymentId");
          final int _cursorIndexOfLoanId = CursorUtil.getColumnIndexOrThrow(_cursor, "loanId");
          final int _cursorIndexOfAmountPaid = CursorUtil.getColumnIndexOrThrow(_cursor, "amountPaid");
          final int _cursorIndexOfPaymentDate = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentDate");
          final int _cursorIndexOfBalanceRemaining = CursorUtil.getColumnIndexOrThrow(_cursor, "balanceRemaining");
          final List<RepaymentEntity> _result = new ArrayList<RepaymentEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final RepaymentEntity _item;
            final String _tmpRepaymentId;
            _tmpRepaymentId = _cursor.getString(_cursorIndexOfRepaymentId);
            final String _tmpLoanId;
            _tmpLoanId = _cursor.getString(_cursorIndexOfLoanId);
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
            final double _tmpBalanceRemaining;
            _tmpBalanceRemaining = _cursor.getDouble(_cursorIndexOfBalanceRemaining);
            _item = new RepaymentEntity(_tmpRepaymentId,_tmpLoanId,_tmpAmountPaid,_tmpPaymentDate,_tmpBalanceRemaining);
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
  public Object getRepaymentById(final String repaymentId,
      final Continuation<? super RepaymentEntity> $completion) {
    final String _sql = "SELECT * FROM repayments WHERE repaymentId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, repaymentId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<RepaymentEntity>() {
      @Override
      @Nullable
      public RepaymentEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfRepaymentId = CursorUtil.getColumnIndexOrThrow(_cursor, "repaymentId");
          final int _cursorIndexOfLoanId = CursorUtil.getColumnIndexOrThrow(_cursor, "loanId");
          final int _cursorIndexOfAmountPaid = CursorUtil.getColumnIndexOrThrow(_cursor, "amountPaid");
          final int _cursorIndexOfPaymentDate = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentDate");
          final int _cursorIndexOfBalanceRemaining = CursorUtil.getColumnIndexOrThrow(_cursor, "balanceRemaining");
          final RepaymentEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpRepaymentId;
            _tmpRepaymentId = _cursor.getString(_cursorIndexOfRepaymentId);
            final String _tmpLoanId;
            _tmpLoanId = _cursor.getString(_cursorIndexOfLoanId);
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
            final double _tmpBalanceRemaining;
            _tmpBalanceRemaining = _cursor.getDouble(_cursorIndexOfBalanceRemaining);
            _result = new RepaymentEntity(_tmpRepaymentId,_tmpLoanId,_tmpAmountPaid,_tmpPaymentDate,_tmpBalanceRemaining);
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
