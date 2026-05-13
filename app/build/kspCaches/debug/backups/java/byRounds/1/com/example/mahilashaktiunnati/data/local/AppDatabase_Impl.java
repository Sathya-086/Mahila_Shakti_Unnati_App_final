package com.example.mahilashaktiunnati.data.local;

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
import com.example.mahilashaktiunnati.data.local.dao.LoanDao;
import com.example.mahilashaktiunnati.data.local.dao.LoanDao_Impl;
import com.example.mahilashaktiunnati.data.local.dao.MemberDao;
import com.example.mahilashaktiunnati.data.local.dao.MemberDao_Impl;
import com.example.mahilashaktiunnati.data.local.dao.RepaymentDao;
import com.example.mahilashaktiunnati.data.local.dao.RepaymentDao_Impl;
import com.example.mahilashaktiunnati.data.local.dao.SavingsDao;
import com.example.mahilashaktiunnati.data.local.dao.SavingsDao_Impl;
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
  private volatile MemberDao _memberDao;

  private volatile SavingsDao _savingsDao;

  private volatile LoanDao _loanDao;

  private volatile RepaymentDao _repaymentDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `members` (`memberId` TEXT NOT NULL, `fullName` TEXT NOT NULL, `age` INTEGER NOT NULL, `address` TEXT NOT NULL, `contactNumber` TEXT NOT NULL, `joiningDate` INTEGER NOT NULL, `photoUri` TEXT, PRIMARY KEY(`memberId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `savings` (`savingsId` TEXT NOT NULL, `memberId` TEXT NOT NULL, `amountPaid` REAL NOT NULL, `paymentDate` INTEGER NOT NULL, `status` TEXT NOT NULL, PRIMARY KEY(`savingsId`), FOREIGN KEY(`memberId`) REFERENCES `members`(`memberId`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_savings_memberId` ON `savings` (`memberId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `loans` (`loanId` TEXT NOT NULL, `memberId` TEXT NOT NULL, `loanAmount` REAL NOT NULL, `interestRate` REAL NOT NULL, `loanDate` INTEGER NOT NULL, `dueDate` INTEGER NOT NULL, `totalAmountWithInterest` REAL NOT NULL, `remainingBalance` REAL NOT NULL, `status` TEXT NOT NULL, PRIMARY KEY(`loanId`), FOREIGN KEY(`memberId`) REFERENCES `members`(`memberId`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_loans_memberId` ON `loans` (`memberId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `repayments` (`repaymentId` TEXT NOT NULL, `loanId` TEXT NOT NULL, `amountPaid` REAL NOT NULL, `paymentDate` INTEGER NOT NULL, `balanceRemaining` REAL NOT NULL, PRIMARY KEY(`repaymentId`), FOREIGN KEY(`loanId`) REFERENCES `loans`(`loanId`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_repayments_loanId` ON `repayments` (`loanId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '3f8f826bce6e7c2563e6e9aac5f8e333')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `members`");
        db.execSQL("DROP TABLE IF EXISTS `savings`");
        db.execSQL("DROP TABLE IF EXISTS `loans`");
        db.execSQL("DROP TABLE IF EXISTS `repayments`");
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
        db.execSQL("PRAGMA foreign_keys = ON");
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
        final HashMap<String, TableInfo.Column> _columnsMembers = new HashMap<String, TableInfo.Column>(7);
        _columnsMembers.put("memberId", new TableInfo.Column("memberId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMembers.put("fullName", new TableInfo.Column("fullName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMembers.put("age", new TableInfo.Column("age", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMembers.put("address", new TableInfo.Column("address", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMembers.put("contactNumber", new TableInfo.Column("contactNumber", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMembers.put("joiningDate", new TableInfo.Column("joiningDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMembers.put("photoUri", new TableInfo.Column("photoUri", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMembers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMembers = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMembers = new TableInfo("members", _columnsMembers, _foreignKeysMembers, _indicesMembers);
        final TableInfo _existingMembers = TableInfo.read(db, "members");
        if (!_infoMembers.equals(_existingMembers)) {
          return new RoomOpenHelper.ValidationResult(false, "members(com.example.mahilashaktiunnati.data.local.entity.MemberEntity).\n"
                  + " Expected:\n" + _infoMembers + "\n"
                  + " Found:\n" + _existingMembers);
        }
        final HashMap<String, TableInfo.Column> _columnsSavings = new HashMap<String, TableInfo.Column>(5);
        _columnsSavings.put("savingsId", new TableInfo.Column("savingsId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSavings.put("memberId", new TableInfo.Column("memberId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSavings.put("amountPaid", new TableInfo.Column("amountPaid", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSavings.put("paymentDate", new TableInfo.Column("paymentDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSavings.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSavings = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysSavings.add(new TableInfo.ForeignKey("members", "CASCADE", "NO ACTION", Arrays.asList("memberId"), Arrays.asList("memberId")));
        final HashSet<TableInfo.Index> _indicesSavings = new HashSet<TableInfo.Index>(1);
        _indicesSavings.add(new TableInfo.Index("index_savings_memberId", false, Arrays.asList("memberId"), Arrays.asList("ASC")));
        final TableInfo _infoSavings = new TableInfo("savings", _columnsSavings, _foreignKeysSavings, _indicesSavings);
        final TableInfo _existingSavings = TableInfo.read(db, "savings");
        if (!_infoSavings.equals(_existingSavings)) {
          return new RoomOpenHelper.ValidationResult(false, "savings(com.example.mahilashaktiunnati.data.local.entity.SavingsEntity).\n"
                  + " Expected:\n" + _infoSavings + "\n"
                  + " Found:\n" + _existingSavings);
        }
        final HashMap<String, TableInfo.Column> _columnsLoans = new HashMap<String, TableInfo.Column>(9);
        _columnsLoans.put("loanId", new TableInfo.Column("loanId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLoans.put("memberId", new TableInfo.Column("memberId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLoans.put("loanAmount", new TableInfo.Column("loanAmount", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLoans.put("interestRate", new TableInfo.Column("interestRate", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLoans.put("loanDate", new TableInfo.Column("loanDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLoans.put("dueDate", new TableInfo.Column("dueDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLoans.put("totalAmountWithInterest", new TableInfo.Column("totalAmountWithInterest", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLoans.put("remainingBalance", new TableInfo.Column("remainingBalance", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLoans.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysLoans = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysLoans.add(new TableInfo.ForeignKey("members", "CASCADE", "NO ACTION", Arrays.asList("memberId"), Arrays.asList("memberId")));
        final HashSet<TableInfo.Index> _indicesLoans = new HashSet<TableInfo.Index>(1);
        _indicesLoans.add(new TableInfo.Index("index_loans_memberId", false, Arrays.asList("memberId"), Arrays.asList("ASC")));
        final TableInfo _infoLoans = new TableInfo("loans", _columnsLoans, _foreignKeysLoans, _indicesLoans);
        final TableInfo _existingLoans = TableInfo.read(db, "loans");
        if (!_infoLoans.equals(_existingLoans)) {
          return new RoomOpenHelper.ValidationResult(false, "loans(com.example.mahilashaktiunnati.data.local.entity.LoanEntity).\n"
                  + " Expected:\n" + _infoLoans + "\n"
                  + " Found:\n" + _existingLoans);
        }
        final HashMap<String, TableInfo.Column> _columnsRepayments = new HashMap<String, TableInfo.Column>(5);
        _columnsRepayments.put("repaymentId", new TableInfo.Column("repaymentId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRepayments.put("loanId", new TableInfo.Column("loanId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRepayments.put("amountPaid", new TableInfo.Column("amountPaid", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRepayments.put("paymentDate", new TableInfo.Column("paymentDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRepayments.put("balanceRemaining", new TableInfo.Column("balanceRemaining", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRepayments = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysRepayments.add(new TableInfo.ForeignKey("loans", "CASCADE", "NO ACTION", Arrays.asList("loanId"), Arrays.asList("loanId")));
        final HashSet<TableInfo.Index> _indicesRepayments = new HashSet<TableInfo.Index>(1);
        _indicesRepayments.add(new TableInfo.Index("index_repayments_loanId", false, Arrays.asList("loanId"), Arrays.asList("ASC")));
        final TableInfo _infoRepayments = new TableInfo("repayments", _columnsRepayments, _foreignKeysRepayments, _indicesRepayments);
        final TableInfo _existingRepayments = TableInfo.read(db, "repayments");
        if (!_infoRepayments.equals(_existingRepayments)) {
          return new RoomOpenHelper.ValidationResult(false, "repayments(com.example.mahilashaktiunnati.data.local.entity.RepaymentEntity).\n"
                  + " Expected:\n" + _infoRepayments + "\n"
                  + " Found:\n" + _existingRepayments);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "3f8f826bce6e7c2563e6e9aac5f8e333", "591ebc9aebfced085231737301d6241b");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "members","savings","loans","repayments");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `members`");
      _db.execSQL("DELETE FROM `savings`");
      _db.execSQL("DELETE FROM `loans`");
      _db.execSQL("DELETE FROM `repayments`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
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
    _typeConvertersMap.put(MemberDao.class, MemberDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(SavingsDao.class, SavingsDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(LoanDao.class, LoanDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(RepaymentDao.class, RepaymentDao_Impl.getRequiredConverters());
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
  public MemberDao memberDao() {
    if (_memberDao != null) {
      return _memberDao;
    } else {
      synchronized(this) {
        if(_memberDao == null) {
          _memberDao = new MemberDao_Impl(this);
        }
        return _memberDao;
      }
    }
  }

  @Override
  public SavingsDao savingsDao() {
    if (_savingsDao != null) {
      return _savingsDao;
    } else {
      synchronized(this) {
        if(_savingsDao == null) {
          _savingsDao = new SavingsDao_Impl(this);
        }
        return _savingsDao;
      }
    }
  }

  @Override
  public LoanDao loanDao() {
    if (_loanDao != null) {
      return _loanDao;
    } else {
      synchronized(this) {
        if(_loanDao == null) {
          _loanDao = new LoanDao_Impl(this);
        }
        return _loanDao;
      }
    }
  }

  @Override
  public RepaymentDao repaymentDao() {
    if (_repaymentDao != null) {
      return _repaymentDao;
    } else {
      synchronized(this) {
        if(_repaymentDao == null) {
          _repaymentDao = new RepaymentDao_Impl(this);
        }
        return _repaymentDao;
      }
    }
  }
}
