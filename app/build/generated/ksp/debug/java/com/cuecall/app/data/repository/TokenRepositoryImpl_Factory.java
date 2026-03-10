package com.cuecall.app.data.repository;

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
public final class TokenRepositoryImpl_Factory implements Factory<TokenRepositoryImpl> {
  private final Provider<TokenDao> tokenDaoProvider;

  private final Provider<FirestoreTokenSource> firestoreSourceProvider;

  public TokenRepositoryImpl_Factory(Provider<TokenDao> tokenDaoProvider,
      Provider<FirestoreTokenSource> firestoreSourceProvider) {
    this.tokenDaoProvider = tokenDaoProvider;
    this.firestoreSourceProvider = firestoreSourceProvider;
  }

  @Override
  public TokenRepositoryImpl get() {
    return newInstance(tokenDaoProvider.get(), firestoreSourceProvider.get());
  }

  public static TokenRepositoryImpl_Factory create(Provider<TokenDao> tokenDaoProvider,
      Provider<FirestoreTokenSource> firestoreSourceProvider) {
    return new TokenRepositoryImpl_Factory(tokenDaoProvider, firestoreSourceProvider);
  }

  public static TokenRepositoryImpl newInstance(TokenDao tokenDao,
      FirestoreTokenSource firestoreSource) {
    return new TokenRepositoryImpl(tokenDao, firestoreSource);
  }
}
