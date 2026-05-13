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
import com.example.mahilashaktiunnati.data.local.entity.MemberEntity;
import java.lang.Class;
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
public final class MemberDao_Impl implements MemberDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<MemberEntity> __insertionAdapterOfMemberEntity;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<MemberEntity> __deletionAdapterOfMemberEntity;

  private final EntityDeletionOrUpdateAdapter<MemberEntity> __updateAdapterOfMemberEntity;

  public MemberDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMemberEntity = new EntityInsertionAdapter<MemberEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `members` (`memberId`,`fullName`,`age`,`address`,`contactNumber`,`joiningDate`,`photoUri`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MemberEntity entity) {
        statement.bindString(1, entity.getMemberId());
        statement.bindString(2, entity.getFullName());
        statement.bindLong(3, entity.getAge());
        statement.bindString(4, entity.getAddress());
        statement.bindString(5, entity.getContactNumber());
        final Long _tmp = __converters.dateToTimestamp(entity.getJoiningDate());
        if (_tmp == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, _tmp);
        }
        if (entity.getPhotoUri() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getPhotoUri());
        }
      }
    };
    this.__deletionAdapterOfMemberEntity = new EntityDeletionOrUpdateAdapter<MemberEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `members` WHERE `memberId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MemberEntity entity) {
        statement.bindString(1, entity.getMemberId());
      }
    };
    this.__updateAdapterOfMemberEntity = new EntityDeletionOrUpdateAdapter<MemberEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `members` SET `memberId` = ?,`fullName` = ?,`age` = ?,`address` = ?,`contactNumber` = ?,`joiningDate` = ?,`photoUri` = ? WHERE `memberId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MemberEntity entity) {
        statement.bindString(1, entity.getMemberId());
        statement.bindString(2, entity.getFullName());
        statement.bindLong(3, entity.getAge());
        statement.bindString(4, entity.getAddress());
        statement.bindString(5, entity.getContactNumber());
        final Long _tmp = __converters.dateToTimestamp(entity.getJoiningDate());
        if (_tmp == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, _tmp);
        }
        if (entity.getPhotoUri() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getPhotoUri());
        }
        statement.bindString(8, entity.getMemberId());
      }
    };
  }

  @Override
  public Object insertMember(final MemberEntity member,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfMemberEntity.insert(member);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteMember(final MemberEntity member,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfMemberEntity.handle(member);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateMember(final MemberEntity member,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfMemberEntity.handle(member);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<MemberEntity>> getAllMembers() {
    final String _sql = "SELECT * FROM members ORDER BY fullName ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"members"}, new Callable<List<MemberEntity>>() {
      @Override
      @NonNull
      public List<MemberEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfMemberId = CursorUtil.getColumnIndexOrThrow(_cursor, "memberId");
          final int _cursorIndexOfFullName = CursorUtil.getColumnIndexOrThrow(_cursor, "fullName");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfContactNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "contactNumber");
          final int _cursorIndexOfJoiningDate = CursorUtil.getColumnIndexOrThrow(_cursor, "joiningDate");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
          final List<MemberEntity> _result = new ArrayList<MemberEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MemberEntity _item;
            final String _tmpMemberId;
            _tmpMemberId = _cursor.getString(_cursorIndexOfMemberId);
            final String _tmpFullName;
            _tmpFullName = _cursor.getString(_cursorIndexOfFullName);
            final int _tmpAge;
            _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            final String _tmpAddress;
            _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            final String _tmpContactNumber;
            _tmpContactNumber = _cursor.getString(_cursorIndexOfContactNumber);
            final Date _tmpJoiningDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfJoiningDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfJoiningDate);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpJoiningDate = _tmp_1;
            }
            final String _tmpPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
              _tmpPhotoUri = null;
            } else {
              _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            }
            _item = new MemberEntity(_tmpMemberId,_tmpFullName,_tmpAge,_tmpAddress,_tmpContactNumber,_tmpJoiningDate,_tmpPhotoUri);
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
  public Object getMemberById(final String memberId,
      final Continuation<? super MemberEntity> $completion) {
    final String _sql = "SELECT * FROM members WHERE memberId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, memberId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<MemberEntity>() {
      @Override
      @Nullable
      public MemberEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfMemberId = CursorUtil.getColumnIndexOrThrow(_cursor, "memberId");
          final int _cursorIndexOfFullName = CursorUtil.getColumnIndexOrThrow(_cursor, "fullName");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfContactNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "contactNumber");
          final int _cursorIndexOfJoiningDate = CursorUtil.getColumnIndexOrThrow(_cursor, "joiningDate");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
          final MemberEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpMemberId;
            _tmpMemberId = _cursor.getString(_cursorIndexOfMemberId);
            final String _tmpFullName;
            _tmpFullName = _cursor.getString(_cursorIndexOfFullName);
            final int _tmpAge;
            _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            final String _tmpAddress;
            _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            final String _tmpContactNumber;
            _tmpContactNumber = _cursor.getString(_cursorIndexOfContactNumber);
            final Date _tmpJoiningDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfJoiningDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfJoiningDate);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpJoiningDate = _tmp_1;
            }
            final String _tmpPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
              _tmpPhotoUri = null;
            } else {
              _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            }
            _result = new MemberEntity(_tmpMemberId,_tmpFullName,_tmpAge,_tmpAddress,_tmpContactNumber,_tmpJoiningDate,_tmpPhotoUri);
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
  public Flow<MemberEntity> getMemberByIdFlow(final String memberId) {
    final String _sql = "SELECT * FROM members WHERE memberId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, memberId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"members"}, new Callable<MemberEntity>() {
      @Override
      @Nullable
      public MemberEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfMemberId = CursorUtil.getColumnIndexOrThrow(_cursor, "memberId");
          final int _cursorIndexOfFullName = CursorUtil.getColumnIndexOrThrow(_cursor, "fullName");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfContactNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "contactNumber");
          final int _cursorIndexOfJoiningDate = CursorUtil.getColumnIndexOrThrow(_cursor, "joiningDate");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
          final MemberEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpMemberId;
            _tmpMemberId = _cursor.getString(_cursorIndexOfMemberId);
            final String _tmpFullName;
            _tmpFullName = _cursor.getString(_cursorIndexOfFullName);
            final int _tmpAge;
            _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            final String _tmpAddress;
            _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            final String _tmpContactNumber;
            _tmpContactNumber = _cursor.getString(_cursorIndexOfContactNumber);
            final Date _tmpJoiningDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfJoiningDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfJoiningDate);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpJoiningDate = _tmp_1;
            }
            final String _tmpPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
              _tmpPhotoUri = null;
            } else {
              _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            }
            _result = new MemberEntity(_tmpMemberId,_tmpFullName,_tmpAge,_tmpAddress,_tmpContactNumber,_tmpJoiningDate,_tmpPhotoUri);
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
  public Flow<List<MemberEntity>> searchMembers(final String query) {
    final String _sql = "SELECT * FROM members WHERE fullName LIKE '%' || ? || '%' OR memberId LIKE '%' || ? || '%' OR contactNumber LIKE '%' || ? || '%'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindString(_argIndex, query);
    _argIndex = 2;
    _statement.bindString(_argIndex, query);
    _argIndex = 3;
    _statement.bindString(_argIndex, query);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"members"}, new Callable<List<MemberEntity>>() {
      @Override
      @NonNull
      public List<MemberEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfMemberId = CursorUtil.getColumnIndexOrThrow(_cursor, "memberId");
          final int _cursorIndexOfFullName = CursorUtil.getColumnIndexOrThrow(_cursor, "fullName");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfContactNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "contactNumber");
          final int _cursorIndexOfJoiningDate = CursorUtil.getColumnIndexOrThrow(_cursor, "joiningDate");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
          final List<MemberEntity> _result = new ArrayList<MemberEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MemberEntity _item;
            final String _tmpMemberId;
            _tmpMemberId = _cursor.getString(_cursorIndexOfMemberId);
            final String _tmpFullName;
            _tmpFullName = _cursor.getString(_cursorIndexOfFullName);
            final int _tmpAge;
            _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            final String _tmpAddress;
            _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            final String _tmpContactNumber;
            _tmpContactNumber = _cursor.getString(_cursorIndexOfContactNumber);
            final Date _tmpJoiningDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfJoiningDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfJoiningDate);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpJoiningDate = _tmp_1;
            }
            final String _tmpPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
              _tmpPhotoUri = null;
            } else {
              _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            }
            _item = new MemberEntity(_tmpMemberId,_tmpFullName,_tmpAge,_tmpAddress,_tmpContactNumber,_tmpJoiningDate,_tmpPhotoUri);
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
  public Flow<Integer> getMemberCount() {
    final String _sql = "SELECT COUNT(*) FROM members";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"members"}, new Callable<Integer>() {
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
