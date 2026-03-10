package com.cuecall.app.di;

import com.cuecall.app.data.local.AppDatabase;
import com.cuecall.app.data.local.dao.TokenDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class DatabaseModule_ProvideTokenDaoFactory implements Factory<TokenDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideTokenDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public TokenDao get() {
    return provideTokenDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideTokenDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideTokenDaoFactory(dbProvider);
  }

  public static TokenDao provideTokenDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideTokenDao(db));
  }
}
