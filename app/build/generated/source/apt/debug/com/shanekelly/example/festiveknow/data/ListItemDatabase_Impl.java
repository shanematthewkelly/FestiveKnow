package com.shanekelly.example.festiveknow.data;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public final class ListItemDatabase_Impl extends ListItemDatabase {
  private volatile ListItemDao _listItemDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `ListItem` (`itemId` TEXT NOT NULL, `message` TEXT, `colorResource` INTEGER NOT NULL, PRIMARY KEY(`itemId`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"b5eb00e63ef778857c0d0626aa4786e1\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `ListItem`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsListItem = new HashMap<String, TableInfo.Column>(3);
        _columnsListItem.put("itemId", new TableInfo.Column("itemId", "TEXT", true, 1));
        _columnsListItem.put("message", new TableInfo.Column("message", "TEXT", false, 0));
        _columnsListItem.put("colorResource", new TableInfo.Column("colorResource", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysListItem = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesListItem = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoListItem = new TableInfo("ListItem", _columnsListItem, _foreignKeysListItem, _indicesListItem);
        final TableInfo _existingListItem = TableInfo.read(_db, "ListItem");
        if (! _infoListItem.equals(_existingListItem)) {
          throw new IllegalStateException("Migration didn't properly handle ListItem(com.shanekelly.example.festiveknow.data.ListItem).\n"
                  + " Expected:\n" + _infoListItem + "\n"
                  + " Found:\n" + _existingListItem);
        }
      }
    }, "b5eb00e63ef778857c0d0626aa4786e1", "e747a4715ee38306c57eb4403e7ea141");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "ListItem");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `ListItem`");
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
  public ListItemDao listItemDao() {
    if (_listItemDao != null) {
      return _listItemDao;
    } else {
      synchronized(this) {
        if(_listItemDao == null) {
          _listItemDao = new ListItemDao_Impl(this);
        }
        return _listItemDao;
      }
    }
  }
}
