package com.cuecall.app.di;

import com.cuecall.app.data.local.AppDatabase;
import com.cuecall.app.data.local.dao.CounterDao;
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
public final class DatabaseModule_ProvideCounterDaoFactory implements Factory<CounterDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideCounterDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public CounterDao get() {
    return provideCounterDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideCounterDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideCounterDaoFactory(dbProvider);
  }

  public static CounterDao provideCounterDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideCounterDao(db));
  }
}
