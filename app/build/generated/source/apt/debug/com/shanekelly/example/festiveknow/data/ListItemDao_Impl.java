package com.shanekelly.example.festiveknow.data;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.lifecycle.ComputableLiveData;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.InvalidationTracker.Observer;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public final class ListItemDao_Impl implements ListItemDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfListItem;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfListItem;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfListItem;

  public ListItemDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfListItem = new EntityInsertionAdapter<ListItem>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `ListItem`(`itemId`,`message`,`colorResource`) VALUES (?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ListItem value) {
        if (value.getItemId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getItemId());
        }
        if (value.getMessage() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getMessage());
        }
        stmt.bindLong(3, value.getColorResource());
      }
    };
    this.__deletionAdapterOfListItem = new EntityDeletionOrUpdateAdapter<ListItem>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `ListItem` WHERE `itemId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ListItem value) {
        if (value.getItemId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getItemId());
        }
      }
    };
    this.__updateAdapterOfListItem = new EntityDeletionOrUpdateAdapter<ListItem>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR REPLACE `ListItem` SET `itemId` = ?,`message` = ?,`colorResource` = ? WHERE `itemId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ListItem value) {
        if (value.getItemId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getItemId());
        }
        if (value.getMessage() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getMessage());
        }
        stmt.bindLong(3, value.getColorResource());
        if (value.getItemId() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getItemId());
        }
      }
    };
  }

  @Override
  public Long insertListItem(ListItem listItem) {
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfListItem.insertAndReturnId(listItem);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteListItem(ListItem listItem) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfListItem.handle(listItem);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateListItem(ListItem listItem) {
    __db.beginTransaction();
    try {
      __updateAdapterOfListItem.handle(listItem);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<ListItem> getListItemById(String itemId) {
    final String _sql = "SELECT * FROM ListItem WHERE itemId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (itemId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, itemId);
    }
    return new ComputableLiveData<ListItem>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected ListItem compute() {
        if (_observer == null) {
          _observer = new Observer("ListItem") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfItemId = _cursor.getColumnIndexOrThrow("itemId");
          final int _cursorIndexOfMessage = _cursor.getColumnIndexOrThrow("message");
          final int _cursorIndexOfColorResource = _cursor.getColumnIndexOrThrow("colorResource");
          final ListItem _result;
          if(_cursor.moveToFirst()) {
            final String _tmpItemId;
            _tmpItemId = _cursor.getString(_cursorIndexOfItemId);
            final String _tmpMessage;
            _tmpMessage = _cursor.getString(_cursorIndexOfMessage);
            final int _tmpColorResource;
            _tmpColorResource = _cursor.getInt(_cursorIndexOfColorResource);
            _result = new ListItem(_tmpItemId,_tmpMessage,_tmpColorResource);
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
    }.getLiveData();
  }

  @Override
  public ListItem findListItemByIdReturnList(String itemId) {
    final String _sql = "SELECT * FROM ListItem WHERE itemId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (itemId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, itemId);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfItemId = _cursor.getColumnIndexOrThrow("itemId");
      final int _cursorIndexOfMessage = _cursor.getColumnIndexOrThrow("message");
      final int _cursorIndexOfColorResource = _cursor.getColumnIndexOrThrow("colorResource");
      final ListItem _result;
      if(_cursor.moveToFirst()) {
        final String _tmpItemId;
        _tmpItemId = _cursor.getString(_cursorIndexOfItemId);
        final String _tmpMessage;
        _tmpMessage = _cursor.getString(_cursorIndexOfMessage);
        final int _tmpColorResource;
        _tmpColorResource = _cursor.getInt(_cursorIndexOfColorResource);
        _result = new ListItem(_tmpItemId,_tmpMessage,_tmpColorResource);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<ListItem>> getListItems() {
    final String _sql = "SELECT * FROM ListItem";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<ListItem>>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected List<ListItem> compute() {
        if (_observer == null) {
          _observer = new Observer("ListItem") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfItemId = _cursor.getColumnIndexOrThrow("itemId");
          final int _cursorIndexOfMessage = _cursor.getColumnIndexOrThrow("message");
          final int _cursorIndexOfColorResource = _cursor.getColumnIndexOrThrow("colorResource");
          final List<ListItem> _result = new ArrayList<ListItem>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final ListItem _item;
            final String _tmpItemId;
            _tmpItemId = _cursor.getString(_cursorIndexOfItemId);
            final String _tmpMessage;
            _tmpMessage = _cursor.getString(_cursorIndexOfMessage);
            final int _tmpColorResource;
            _tmpColorResource = _cursor.getInt(_cursorIndexOfColorResource);
            _item = new ListItem(_tmpItemId,_tmpMessage,_tmpColorResource);
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
    }.getLiveData();
  }
}
