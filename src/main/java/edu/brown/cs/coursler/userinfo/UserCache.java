package edu.brown.cs.coursler.userinfo;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * A cache of user objects.
 *
 * @author adevor
 *
 */
public class UserCache {

  private LoadingCache<String, User> idToUserCache;
  private DbProxy db;

  /**
   * Constructor for User Cache.
   *
   * @param database
   *          the database the cache is calling from
   */
  public UserCache(DbProxy database) {
    idToUserCache = setUpUserCache();
    db = database;
  }

  /**
   * Gets user given id.
   *
   * @param id
   *          the id of the user.
   * @return the user
   */
  public User getUserForId(String id) {
    User user = null;
    try {
      user = idToUserCache.get(id);
    } catch (ExecutionException e) {
      System.out.println("ERROR: Error getting user from id");
      return null;
    }
    return user;
  }

  private LoadingCache<String, User> setUpUserCache() {
    final int maxCacheSize = 10000;
    final int maxCacheTimePersistence = 30;
    LoadingCache<String,
        User> cache = CacheBuilder.newBuilder().maximumSize(maxCacheSize)
            .expireAfterAccess(maxCacheTimePersistence, TimeUnit.MINUTES)
            .build(new CacheLoader<String, User>() {

              @Override
              public User load(String id) throws Exception {
                return db.getUserFromId(id);
              }
            });
    return cache;
  }
}
