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
import com.example.mahilashaktiunnati.data.local.LoanStatus;
import com.example.mahilashaktiunnati.data.local.entity.LoanEntity;
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
public final class LoanDao_Impl implements LoanDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<LoanEntity> __insertionAdapterOfLoanEntity;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<LoanEntity> __deletionAdapterOfLoanEntity;

  private final EntityDeletionOrUpdateAdapter<LoanEntity> __updateAdapterOfLoanEntity;

  public LoanDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfLoanEntity = new EntityInsertionAdapter<LoanEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `loans` (`loanId`,`memberId`,`loanAmount`,`interestRate`,`loanDate`,`dueDate`,`totalAmountWithInterest`,`remainingBalance`,`status`) VALUES (?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final LoanEntity entity) {
        statement.bindString(1, entity.getLoanId());
        statement.bindString(2, entity.getMemberId());
        statement.bindDouble(3, entity.getLoanAmount());
        statement.bindDouble(4, entity.getInterestRate());
        final Long _tmp = __converters.dateToTimestamp(entity.getLoanDate());
        if (_tmp == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, _tmp);
        }
        final Long _tmp_1 = __converters.dateToTimestamp(entity.getDueDate());
        if (_tmp_1 == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, _tmp_1);
        }
        statement.bindDouble(7, entity.getTotalAmountWithInterest());
        statement.bindDouble(8, entity.getRemainingBalance());
        final String _tmp_2 = __converters.fromLoanStatus(entity.getStatus());
        statement.bindString(9, _tmp_2);
      }
    };
    this.__deletionAdapterOfLoanEntity = new EntityDeletionOrUpdateAdapter<LoanEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `loans` WHERE `loanId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final LoanEntity entity) {
        statement.bindString(1, entity.getLoanId());
      }
    };
    this.__updateAdapterOfLoanEntity = new EntityDeletionOrUpdateAdapter<LoanEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `loans` SET `loanId` = ?,`memberId` = ?,`loanAmount` = ?,`interestRate` = ?,`loanDate` = ?,`dueDate` = ?,`totalAmountWithInterest` = ?,`remainingBalance` = ?,`status` = ? WHERE `loanId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final LoanEntity entity) {
        statement.bindString(1, entity.getLoanId());
        statement.bindString(2, entity.getMemberId());
        statement.bindDouble(3, entity.getLoanAmount());
        statement.bindDouble(4, entity.getInterestRate());
        final Long _tmp = __converters.dateToTimestamp(entity.getLoanDate());
        if (_tmp == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, _tmp);
        }
        final Long _tmp_1 = __converters.dateToTimestamp(entity.getDueDate());
        if (_tmp_1 == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, _tmp_1);
        }
        statement.bindDouble(7, entity.getTotalAmountWithInterest());
        statement.bindDouble(8, entity.getRemainingBalance());
        final String _tmp_2 = __converters.fromLoanStatus(entity.getStatus());
        statement.bindString(9, _tmp_2);
        statement.bindString(10, entity.getLoanId());
      }
    };
  }

  @Override
  public Object insertLoan(final LoanEntity loan, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfLoanEntity.insert(loan);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteLoan(final LoanEntity loan, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfLoanEntity.handle(loan);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateLoan(final LoanEntity loan, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfLoanEntity.handle(loan);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<LoanEntity>> getAllLoans() {
    final String _sql = "SELECT * FROM loans ORDER BY loanDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"loans"}, new Callable<List<LoanEntity>>() {
      @Override
      @NonNull
      public List<LoanEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfLoanId = CursorUtil.getColumnIndexOrThrow(_cursor, "loanId");
          final int _cursorIndexOfMemberId = CursorUtil.getColumnIndexOrThrow(_cursor, "memberId");
          final int _cursorIndexOfLoanAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "loanAmount");
          final int _cursorIndexOfInterestRate = CursorUtil.getColumnIndexOrThrow(_cursor, "interestRate");
          final int _cursorIndexOfLoanDate = CursorUtil.getColumnIndexOrThrow(_cursor, "loanDate");
          final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "dueDate");
          final int _cursorIndexOfTotalAmountWithInterest = CursorUtil.getColumnIndexOrThrow(_cursor, "totalAmountWithInterest");
          final int _cursorIndexOfRemainingBalance = CursorUtil.getColumnIndexOrThrow(_cursor, "remainingBalance");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final List<LoanEntity> _result = new ArrayList<LoanEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final LoanEntity _item;
            final String _tmpLoanId;
            _tmpLoanId = _cursor.getString(_cursorIndexOfLoanId);
            final String _tmpMemberId;
            _tmpMemberId = _cursor.getString(_cursorIndexOfMemberId);
            final double _tmpLoanAmount;
            _tmpLoanAmount = _cursor.getDouble(_cursorIndexOfLoanAmount);
            final double _tmpInterestRate;
            _tmpInterestRate = _cursor.getDouble(_cursorIndexOfInterestRate);
            final Date _tmpLoanDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfLoanDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfLoanDate);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpLoanDate = _tmp_1;
            }
            final Date _tmpDueDate;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfDueDate)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfDueDate);
            }
            final Date _tmp_3 = __converters.fromTimestamp(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpDueDate = _tmp_3;
            }
            final double _tmpTotalAmountWithInterest;
            _tmpTotalAmountWithInterest = _cursor.getDouble(_cursorIndexOfTotalAmountWithInterest);
            final double _tmpRemainingBalance;
            _tmpRemainingBalance = _cursor.getDouble(_cursorIndexOfRemainingBalance);
            final LoanStatus _tmpStatus;
            final String _tmp_4;
            _tmp_4 = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __converters.toLoanStatus(_tmp_4);
            _item = new LoanEntity(_tmpLoanId,_tmpMemberId,_tmpLoanAmount,_tmpInterestRate,_tmpLoanDate,_tmpDueDate,_tmpTotalAmountWithInterest,_tmpRemainingBalance,_tmpStatus);
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
  public Object getLoanById(final String loanId,
      final Continuation<? super LoanEntity> $completion) {
    final String _sql = "SELECT * FROM loans WHERE loanId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, loanId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<LoanEntity>() {
      @Override
      @Nullable
      public LoanEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfLoanId = CursorUtil.getColumnIndexOrThrow(_cursor, "loanId");
          final int _cursorIndexOfMemberId = CursorUtil.getColumnIndexOrThrow(_cursor, "memberId");
          final int _cursorIndexOfLoanAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "loanAmount");
          final int _cursorIndexOfInterestRate = CursorUtil.getColumnIndexOrThrow(_cursor, "interestRate");
          final int _cursorIndexOfLoanDate = CursorUtil.getColumnIndexOrThrow(_cursor, "loanDate");
          final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "dueDate");
          final int _cursorIndexOfTotalAmountWithInterest = CursorUtil.getColumnIndexOrThrow(_cursor, "totalAmountWithInterest");
          final int _cursorIndexOfRemainingBalance = CursorUtil.getColumnIndexOrThrow(_cursor, "remainingBalance");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final LoanEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpLoanId;
            _tmpLoanId = _cursor.getString(_cursorIndexOfLoanId);
            final String _tmpMemberId;
            _tmpMemberId = _cursor.getString(_cursorIndexOfMemberId);
            final double _tmpLoanAmount;
            _tmpLoanAmount = _cursor.getDouble(_cursorIndexOfLoanAmount);
            final double _tmpInterestRate;
            _tmpInterestRate = _cursor.getDouble(_cursorIndexOfInterestRate);
            final Date _tmpLoanDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfLoanDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfLoanDate);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpLoanDate = _tmp_1;
            }
            final Date _tmpDueDate;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfDueDate)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfDueDate);
            }
            final Date _tmp_3 = __converters.fromTimestamp(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpDueDate = _tmp_3;
            }
            final double _tmpTotalAmountWithInterest;
            _tmpTotalAmountWithInterest = _cursor.getDouble(_cursorIndexOfTotalAmountWithInterest);
            final double _tmpRemainingBalance;
            _tmpRemainingBalance = _cursor.getDouble(_cursorIndexOfRemainingBalance);
            final LoanStatus _tmpStatus;
            final String _tmp_4;
            _tmp_4 = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __converters.toLoanStatus(_tmp_4);
            _result = new LoanEntity(_tmpLoanId,_tmpMemberId,_tmpLoanAmount,_tmpInterestRate,_tmpLoanDate,_tmpDueDate,_tmpTotalAmountWithInterest,_tmpRemainingBalance,_tmpStatus);
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
  public Flow<LoanEntity> getLoanByIdFlow(final String loanId) {
    final String _sql = "SELECT * FROM loans WHERE loanId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, loanId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"loans"}, new Callable<LoanEntity>() {
      @Override
      @Nullable
      public LoanEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfLoanId = CursorUtil.getColumnIndexOrThrow(_cursor, "loanId");
          final int _cursorIndexOfMemberId = CursorUtil.getColumnIndexOrThrow(_cursor, "memberId");
          final int _cursorIndexOfLoanAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "loanAmount");
          final int _cursorIndexOfInterestRate = CursorUtil.getColumnIndexOrThrow(_cursor, "interestRate");
          final int _cursorIndexOfLoanDate = CursorUtil.getColumnIndexOrThrow(_cursor, "loanDate");
          final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "dueDate");
          final int _cursorIndexOfTotalAmountWithInterest = CursorUtil.getColumnIndexOrThrow(_cursor, "totalAmountWithInterest");
          final int _cursorIndexOfRemainingBalance = CursorUtil.getColumnIndexOrThrow(_cursor, "remainingBalance");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final LoanEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpLoanId;
            _tmpLoanId = _cursor.getString(_cursorIndexOfLoanId);
            final String _tmpMemberId;
            _tmpMemberId = _cursor.getString(_cursorIndexOfMemberId);
            final double _tmpLoanAmount;
            _tmpLoanAmount = _cursor.getDouble(_cursorIndexOfLoanAmount);
            final double _tmpInterestRate;
            _tmpInterestRate = _cursor.getDouble(_cursorIndexOfInterestRate);
            final Date _tmpLoanDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfLoanDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfLoanDate);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpLoanDate = _tmp_1;
            }
            final Date _tmpDueDate;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfDueDate)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfDueDate);
            }
            final Date _tmp_3 = __converters.fromTimestamp(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpDueDate = _tmp_3;
            }
            final double _tmpTotalAmountWithInterest;
            _tmpTotalAmountWithInterest = _cursor.getDouble(_cursorIndexOfTotalAmountWithInterest);
            final double _tmpRemainingBalance;
            _tmpRemainingBalance = _cursor.getDouble(_cursorIndexOfRemainingBalance);
            final LoanStatus _tmpStatus;
            final String _tmp_4;
            _tmp_4 = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __converters.toLoanStatus(_tmp_4);
            _result = new LoanEntity(_tmpLoanId,_tmpMemberId,_tmpLoanAmount,_tmpInterestRate,_tmpLoanDate,_tmpDueDate,_tmpTotalAmountWithInterest,_tmpRemainingBalance,_tmpStatus);
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
  public Flow<List<LoanEntity>> getLoansForMember(final String memberId) {
    final String _sql = "SELECT * FROM loans WHERE memberId = ? ORDER BY loanDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, memberId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"loans"}, new Callable<List<LoanEntity>>() {
      @Override
      @NonNull
      public List<LoanEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfLoanId = CursorUtil.getColumnIndexOrThrow(_cursor, "loanId");
          final int _cursorIndexOfMemberId = CursorUtil.getColumnIndexOrThrow(_cursor, "memberId");
          final int _cursorIndexOfLoanAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "loanAmount");
          final int _cursorIndexOfInterestRate = CursorUtil.getColumnIndexOrThrow(_cursor, "interestRate");
          final int _cursorIndexOfLoanDate = CursorUtil.getColumnIndexOrThrow(_cursor, "loanDate");
          final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "dueDate");
          final int _cursorIndexOfTotalAmountWithInterest = CursorUtil.getColumnIndexOrThrow(_cursor, "totalAmountWithInterest");
          final int _cursorIndexOfRemainingBalance = CursorUtil.getColumnIndexOrThrow(_cursor, "remainingBalance");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final List<LoanEntity> _result = new ArrayList<LoanEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final LoanEntity _item;
            final String _tmpLoanId;
            _tmpLoanId = _cursor.getString(_cursorIndexOfLoanId);
            final String _tmpMemberId;
            _tmpMemberId = _cursor.getString(_cursorIndexOfMemberId);
            final double _tmpLoanAmount;
            _tmpLoanAmount = _cursor.getDouble(_cursorIndexOfLoanAmount);
            final double _tmpInterestRate;
            _tmpInterestRate = _cursor.getDouble(_cursorIndexOfInterestRate);
            final Date _tmpLoanDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfLoanDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfLoanDate);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpLoanDate = _tmp_1;
            }
            final Date _tmpDueDate;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfDueDate)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfDueDate);
            }
            final Date _tmp_3 = __converters.fromTimestamp(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpDueDate = _tmp_3;
            }
            final double _tmpTotalAmountWithInterest;
            _tmpTotalAmountWithInterest = _cursor.getDouble(_cursorIndexOfTotalAmountWithInterest);
            final double _tmpRemainingBalance;
            _tmpRemainingBalance = _cursor.getDouble(_cursorIndexOfRemainingBalance);
            final LoanStatus _tmpStatus;
            final String _tmp_4;
            _tmp_4 = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __converters.toLoanStatus(_tmp_4);
            _item = new LoanEntity(_tmpLoanId,_tmpMemberId,_tmpLoanAmount,_tmpInterestRate,_tmpLoanDate,_tmpDueDate,_tmpTotalAmountWithInterest,_tmpRemainingBalance,_tmpStatus);
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
  public Flow<List<LoanEntity>> getActiveLoansForMember(final String memberId) {
    final String _sql = "SELECT * FROM loans WHERE memberId = ? AND status = 'ACTIVE'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, memberId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"loans"}, new Callable<List<LoanEntity>>() {
      @Override
      @NonNull
      public List<LoanEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfLoanId = CursorUtil.getColumnIndexOrThrow(_cursor, "loanId");
          final int _cursorIndexOfMemberId = CursorUtil.getColumnIndexOrThrow(_cursor, "memberId");
          final int _cursorIndexOfLoanAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "loanAmount");
          final int _cursorIndexOfInterestRate = CursorUtil.getColumnIndexOrThrow(_cursor, "interestRate");
          final int _cursorIndexOfLoanDate = CursorUtil.getColumnIndexOrThrow(_cursor, "loanDate");
          final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "dueDate");
          final int _cursorIndexOfTotalAmountWithInterest = CursorUtil.getColumnIndexOrThrow(_cursor, "totalAmountWithInterest");
          final int _cursorIndexOfRemainingBalance = CursorUtil.getColumnIndexOrThrow(_cursor, "remainingBalance");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final List<LoanEntity> _result = new ArrayList<LoanEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final LoanEntity _item;
            final String _tmpLoanId;
            _tmpLoanId = _cursor.getString(_cursorIndexOfLoanId);
            final String _tmpMemberId;
            _tmpMemberId = _cursor.getString(_cursorIndexOfMemberId);
            final double _tmpLoanAmount;
            _tmpLoanAmount = _cursor.getDouble(_cursorIndexOfLoanAmount);
            final double _tmpInterestRate;
            _tmpInterestRate = _cursor.getDouble(_cursorIndexOfInterestRate);
            final Date _tmpLoanDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfLoanDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfLoanDate);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpLoanDate = _tmp_1;
            }
            final Date _tmpDueDate;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfDueDate)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfDueDate);
            }
            final Date _tmp_3 = __converters.fromTimestamp(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpDueDate = _tmp_3;
            }
            final double _tmpTotalAmountWithInterest;
            _tmpTotalAmountWithInterest = _cursor.getDouble(_cursorIndexOfTotalAmountWithInterest);
            final double _tmpRemainingBalance;
            _tmpRemainingBalance = _cursor.getDouble(_cursorIndexOfRemainingBalance);
            final LoanStatus _tmpStatus;
            final String _tmp_4;
            _tmp_4 = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __converters.toLoanStatus(_tmp_4);
            _item = new LoanEntity(_tmpLoanId,_tmpMemberId,_tmpLoanAmount,_tmpInterestRate,_tmpLoanDate,_tmpDueDate,_tmpTotalAmountWithInterest,_tmpRemainingBalance,_tmpStatus);
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
  public Flow<List<LoanEntity>> getLoansByStatus(final LoanStatus status) {
    final String _sql = "SELECT * FROM loans WHERE status = ? ORDER BY loanDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = __converters.fromLoanStatus(status);
    _statement.bindString(_argIndex, _tmp);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"loans"}, new Callable<List<LoanEntity>>() {
      @Override
      @NonNull
      public List<LoanEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfLoanId = CursorUtil.getColumnIndexOrThrow(_cursor, "loanId");
          final int _cursorIndexOfMemberId = CursorUtil.getColumnIndexOrThrow(_cursor, "memberId");
          final int _cursorIndexOfLoanAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "loanAmount");
          final int _cursorIndexOfInterestRate = CursorUtil.getColumnIndexOrThrow(_cursor, "interestRate");
          final int _cursorIndexOfLoanDate = CursorUtil.getColumnIndexOrThrow(_cursor, "loanDate");
          final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "dueDate");
          final int _cursorIndexOfTotalAmountWithInterest = CursorUtil.getColumnIndexOrThrow(_cursor, "totalAmountWithInterest");
          final int _cursorIndexOfRemainingBalance = CursorUtil.getColumnIndexOrThrow(_cursor, "remainingBalance");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final List<LoanEntity> _result = new ArrayList<LoanEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final LoanEntity _item;
            final String _tmpLoanId;
            _tmpLoanId = _cursor.getString(_cursorIndexOfLoanId);
            final String _tmpMemberId;
            _tmpMemberId = _cursor.getString(_cursorIndexOfMemberId);
            final double _tmpLoanAmount;
            _tmpLoanAmount = _cursor.getDouble(_cursorIndexOfLoanAmount);
            final double _tmpInterestRate;
            _tmpInterestRate = _cursor.getDouble(_cursorIndexOfInterestRate);
            final Date _tmpLoanDate;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfLoanDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfLoanDate);
            }
            final Date _tmp_2 = __converters.fromTimestamp(_tmp_1);
            if (_tmp_2 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpLoanDate = _tmp_2;
            }
            final Date _tmpDueDate;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfDueDate)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfDueDate);
            }
            final Date _tmp_4 = __converters.fromTimestamp(_tmp_3);
            if (_tmp_4 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpDueDate = _tmp_4;
            }
            final double _tmpTotalAmountWithInterest;
            _tmpTotalAmountWithInterest = _cursor.getDouble(_cursorIndexOfTotalAmountWithInterest);
            final double _tmpRemainingBalance;
            _tmpRemainingBalance = _cursor.getDouble(_cursorIndexOfRemainingBalance);
            final LoanStatus _tmpStatus;
            final String _tmp_5;
            _tmp_5 = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __converters.toLoanStatus(_tmp_5);
            _item = new LoanEntity(_tmpLoanId,_tmpMemberId,_tmpLoanAmount,_tmpInterestRate,_tmpLoanDate,_tmpDueDate,_tmpTotalAmountWithInterest,_tmpRemainingBalance,_tmpStatus);
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
  public Flow<Integer> getActiveLoanCount() {
    final String _sql = "SELECT COUNT(*) FROM loans WHERE status = 'ACTIVE'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"loans"}, new Callable<Integer>() {
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

  @Override
  public Object hasActiveUnpaidLoan(final String memberId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM loans WHERE memberId = ? AND status = 'ACTIVE'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, memberId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
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
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<Double> getTotalPendingRepayments() {
    final String _sql = "SELECT COALESCE(SUM(remainingBalance), 0.0) FROM loans WHERE status = 'ACTIVE'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"loans"}, new Callable<Double>() {
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
  public Flow<Double> getTotalLoansGiven() {
    final String _sql = "SELECT COALESCE(SUM(loanAmount), 0.0) FROM loans";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"loans"}, new Callable<Double>() {
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
  public Flow<Double> getTotalInterestEarned() {
    final String _sql = "SELECT COALESCE(SUM(totalAmountWithInterest - loanAmount), 0.0) FROM loans WHERE status = 'CLOSED'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"loans"}, new Callable<Double>() {
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
  public Flow<Integer> getPendingApprovalCount() {
    final String _sql = "SELECT COUNT(*) FROM loans WHERE status = 'PENDING_APPROVAL'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"loans"}, new Callable<Integer>() {
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
