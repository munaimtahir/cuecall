package com.cuecall.app.sync;

import com.cuecall.app.data.local.dao.TokenDao;
import com.cuecall.app.data.remote.source.FirestoreTokenSource;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class TokenSyncManager_Factory implements Factory<TokenSyncManager> {
  private final Provider<FirestoreTokenSource> firestoreSourceProvider;

  private final Provider<TokenDao> tokenDaoProvider;

  public TokenSyncManager_Factory(Provider<FirestoreTokenSource> firestoreSourceProvider,
      Provider<TokenDao> tokenDaoProvider) {
    this.firestoreSourceProvider = firestoreSourceProvider;
    this.tokenDaoProvider = tokenDaoProvider;
  }

  @Override
  public TokenSyncManager get() {
    return newInstance(firestoreSourceProvider.get(), tokenDaoProvider.get());
  }

  public static TokenSyncManager_Factory create(
      Provider<FirestoreTokenSource> firestoreSourceProvider, Provider<TokenDao> tokenDaoProvider) {
    return new TokenSyncManager_Factory(firestoreSourceProvider, tokenDaoProvider);
  }

  public static TokenSyncManager newInstance(FirestoreTokenSource firestoreSource,
      TokenDao tokenDao) {
    return new TokenSyncManager(firestoreSource, tokenDao);
  }
}
